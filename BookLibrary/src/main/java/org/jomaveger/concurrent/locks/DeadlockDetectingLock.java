package org.jomaveger.concurrent.locks;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.jomaveger.concurrent.exceptions.DeadlockDetectedException;

public class DeadlockDetectingLock extends ReentrantLock {
    
    private static List<DeadlockDetectingLock> deadlockLocksRegistry = new ArrayList<>();

    private static synchronized void registerLock(DeadlockDetectingLock ddl) {
        if (!deadlockLocksRegistry.contains(ddl))
            deadlockLocksRegistry.add(ddl);
    }

    private static synchronized void unregisterLock(DeadlockDetectingLock ddl) {
        if (deadlockLocksRegistry.contains(ddl))
            deadlockLocksRegistry.remove(ddl);
    }
    
    private List<Thread> hardwaitingThreads = new ArrayList<>(); 

    private static synchronized void markAsHardwait(List<Thread> l, Thread t) {
        if (!l.contains(t))
            l.add(t);
    }

    private static synchronized void freeIfHardwait(List<Thread> l, Thread t) {
        if (l.contains(t))
            l.remove(t);
    }

    private static Iterator<DeadlockDetectingLock> getAllLocksOwned(Thread t) {
        DeadlockDetectingLock current;
        List<DeadlockDetectingLock> results = new ArrayList<>();

        Iterator<DeadlockDetectingLock> itr = deadlockLocksRegistry.iterator();
        while (itr.hasNext()) {
            current = itr.next();
            if (current.getOwner() == t)
                results.add(current);
        }
        return results.iterator(); 
    }

    private static Iterator<Thread> getAllThreadsHardwaiting(DeadlockDetectingLock l) {
        return l.hardwaitingThreads.iterator();
    }
    
    private static synchronized boolean canThreadWaitOnLock(Thread t, DeadlockDetectingLock l) {
        if (l.getOwner() == null) {
            return true;
        }
        return canThreadWaitOnLock0(t, l);
    }

    private static boolean canThreadWaitOnLock0(Thread t, DeadlockDetectingLock l) {
        Iterator<DeadlockDetectingLock> locksOwned = getAllLocksOwned(t);
        
        while (locksOwned.hasNext()) {
            
            DeadlockDetectingLock current = locksOwned.next();
            
            if (current == l) return false;

            Iterator<Thread> waitingThreads = getAllThreadsHardwaiting(current);
            
            while (waitingThreads.hasNext()) {
                
                Thread otherthread = waitingThreads.next();
                
                if (!canThreadWaitOnLock0(otherthread, l)) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    public static boolean DDLFastFail = false; 
    public static boolean DDLCleanUp = false;
    public static int DDLHWSWTime = 60;
    
    public DeadlockDetectingLock() {
        this(false, false);
    }

    public DeadlockDetectingLock(boolean fair) {
        this(fair, false);
    }

    private boolean debugging;

    public DeadlockDetectingLock(boolean fair, boolean debug) {
        super(fair);
        debugging = debug;
        registerLock(this);
    }

    private static boolean DDLdeadlockDETECTED = false;
    
    @Override
    public void lock() {
        if (DDLFastFail && DDLdeadlockDETECTED) {
            throw new DeadlockDetectedException("EARLIER DEADLOCK DETECTED");
        }

        if (isHeldByCurrentThread()) {
            if (debugging) {
                System.out.println("Already Own Lock");
            }
            super.lock();
            freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            return;
        }

        markAsHardwait(hardwaitingThreads, Thread.currentThread());
        if (canThreadWaitOnLock(Thread.currentThread(), this)) {
            if (debugging) {
                System.out.println("Waiting For Lock");
            }
            super.lock();
            freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            if (debugging) {
                System.out.println("Got New Lock");
            }
        } else {
            DDLdeadlockDETECTED = true;
            if (DDLCleanUp) {
                freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            }
            throw new DeadlockDetectedException("DEADLOCK DETECTED");
        }
    }
    
    @Override
    public void lockInterruptibly() throws InterruptedException {
        if (DDLFastFail && DDLdeadlockDETECTED) {
            throw new DeadlockDetectedException("EARLIER DEADLOCK DETECTED");
        }

        if (isHeldByCurrentThread()) {
            if (debugging) {
                System.out.println("Already Own Lock");
            }
            try {
                super.lockInterruptibly();
            } finally {
                freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            }
            return;
        }

        markAsHardwait(hardwaitingThreads, Thread.currentThread());
        if (canThreadWaitOnLock(Thread.currentThread(), this)) {
            if (debugging) {
                System.out.println("Waiting For Lock");
            }
            try {
                super.lockInterruptibly();
            } finally {
                freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            }
            if (debugging) {
                System.out.println("Got New Lock");
            }
        } else {
            DDLdeadlockDETECTED = true;
            if (DDLCleanUp) {
                freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            }
            throw new DeadlockDetectedException("DEADLOCK DETECTED");
        }
    }
    
    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        if (DDLFastFail && DDLdeadlockDETECTED) {
            throw new DeadlockDetectedException("EARLIER DEADLOCK DETECTED");
        }

        if (unit.toSeconds(time) < DDLHWSWTime) {
            return super.tryLock(time, unit);
        }

        if (isHeldByCurrentThread()) {
            if (debugging) {
                System.out.println("Already Own Lock");
            }
            try {
                return super.tryLock(time, unit);
            } finally {
                freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            }
        }

        markAsHardwait(hardwaitingThreads, Thread.currentThread());
        if (canThreadWaitOnLock(Thread.currentThread(), this)) {
            if (debugging) {
                System.out.println("Waiting For Lock");
            }
            try {
                return super.tryLock(time, unit);
            } finally {
                freeIfHardwait(hardwaitingThreads, Thread.currentThread());
                if (debugging) {
                    System.out.println("Got New Lock");
                }
            }
        } else {
            DDLdeadlockDETECTED = true;
            if (DDLCleanUp) {
                freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            }
            throw new DeadlockDetectedException("DEADLOCK DETECTED");
        }
    }
    
    @Override
    public Condition newCondition() {
        return new DeadlockDetectingCondition(this, super.newCondition());
    }
    
    private class DeadlockDetectingCondition implements Condition {

        private Condition embedded;
        private DeadlockDetectingLock lock;

        public DeadlockDetectingCondition(DeadlockDetectingLock lock, Condition embedded) {
            this.embedded = embedded;
            this.lock = lock;
        }

        @Override
        public void await() throws InterruptedException {
            try {
                markAsHardwait(hardwaitingThreads, Thread.currentThread());
                embedded.await();
            } finally {
                freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            }
        }

        @Override
        public void awaitUninterruptibly() {
            markAsHardwait(hardwaitingThreads, Thread.currentThread());
            embedded.awaitUninterruptibly();
            freeIfHardwait(hardwaitingThreads, Thread.currentThread());
        }

        @Override
        public long awaitNanos(long nanosTimeout) throws InterruptedException {
            try {
                markAsHardwait(hardwaitingThreads, Thread.currentThread());
                return embedded.awaitNanos(nanosTimeout);
            } finally {
                freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            }
        }

        @Override
        public boolean await(long time, TimeUnit unit) throws InterruptedException {
            try {
                markAsHardwait(hardwaitingThreads, Thread.currentThread());
                return embedded.await(time, unit);
            } finally {
                freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            }
        }

        @Override
        public boolean awaitUntil(Date deadline) throws InterruptedException {
            try {
                markAsHardwait(hardwaitingThreads, Thread.currentThread());
                return embedded.awaitUntil(deadline);
            } finally {
                freeIfHardwait(hardwaitingThreads, Thread.currentThread());
            }
        }

        @Override
        public void signal() {
            embedded.signal();
        }

        @Override
        public void signalAll() {
            embedded.signalAll();
        }
    }
}
