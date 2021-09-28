package org.jomaveger.functional.data;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.jomaveger.functional.control.Result;
import org.jomaveger.functional.tuples.Tuple2;

public abstract class ImmutableList<A> implements Iterable<A> {
	
	private static final ImmutableList<?> empty = new EmptyImmutableList<>();
	
	private ImmutableList() {
	}
	
	public static <A> ImmutableList<A> empty() {
		
		return (ImmutableList<A>) empty;
	}
	
	public final ImmutableList<A> cons(final A left) {
        
		return cons(Objects.requireNonNull(left), this);
    }
	
	public static <A> ImmutableList<A> cons(final A head, final ImmutableList<A> tail) {
        
		return new NonEmptyImmutableList<>(Objects.requireNonNull(head), Objects.requireNonNull(tail));
    }
	
	private static <A> ImmutableList<A> from(final ArrayList<A> list) {
		
		ArrayList<A> list2 = Objects.requireNonNull(list);
		ImmutableList<A> l = empty();
		int size = list2.size();
		for (int i = size - 1; i >= 0; i--) {
			l = cons(list2.get(i), l);
		}
		return l;
	}
	
	public static <A> ImmutableList<A> from(final Iterable<A> list) {
		
		Iterable<A> list2 = Objects.requireNonNull(list);
		if (list2 instanceof ArrayList) {
			return from((ArrayList<A>) list);
		} else if (list2 instanceof Deque) {
			ImmutableList<A> l = empty();
			for (Iterator<A> iterator = ((Deque<A>) list2).descendingIterator(); iterator.hasNext();) {
				A item = iterator.next();
				l = cons(item, l);
			}
			return l;
		}
		ArrayList<A> arrayList = new ArrayList<>();
		list2.forEach(arrayList::add);
		return from(arrayList);
	}
	
	public static <A> ImmutableList<A> from(final A head, final A... el) {
		
		if (null == el || el.length == 0) {
			return cons(Objects.requireNonNull(head), ImmutableList.empty());
		}
		ImmutableList<A> l = cons(Objects.requireNonNull(el[el.length - 1]), ImmutableList.empty());
		for (int i = el.length - 2; i >= 0; i--) {
			l = cons(Objects.requireNonNull(el[i]), l);
		}
		return cons(Objects.requireNonNull(head), l);
	}
	
	public static <A> ImmutableList<A> fromBounded(A[] el, int start, int end) {
		if (null == el || el.length == 0) {
			return empty();
		}
		if (end == start) {
			return empty();
		}
		ImmutableList<A> l = cons(el[end - 1], ImmutableList.empty());
		for (int i = end - 2; i >= start; i--) {
			l = cons(el[i], l);
		}
		return l;
	}
	
	public static <A> ImmutableList<A> of(A... el) {
        return fromBounded(el, 0, el.length);
    }
	
	public static <A, S> ImmutableList<A> unfold(S z, Function<S, Result<Tuple2<A, S>>> f) {
		return f.apply(z).map(x -> cons(x._1, unfold(x._2, f))).getOrElse(empty());
	}
	
	public final A[] toArray(final Class<A> type) {
		
		Class<A> t = Objects.requireNonNull(type);
		A[] array = (A[]) java.lang.reflect.Array.newInstance(t, this.length());
        ImmutableList<A> l = this;
        for (int i = 0; i < this.length(); i++) {
            array[i] = ((NonEmptyImmutableList<A>) l).head;
            l = ((NonEmptyImmutableList<A>) l).tail;
        }
        return array;
	}
	
	public final ArrayList<A> toArrayList() {
        
		ArrayList<A> list = new ArrayList<>(this.length());
        ImmutableList<A> l = this;
        for (int i = 0; i < this.length(); i++) {
            list.add(((NonEmptyImmutableList<A>) l).head);
            l = ((NonEmptyImmutableList<A>) l).tail;
        }
        return list;
    }
	
	public final LinkedList<A> toLinkedList() {
        
		LinkedList<A> list = new LinkedList<>();
        ImmutableList<A> l = this;
        for (int i = 0; i < this.length(); i++) {
            list.add(((NonEmptyImmutableList<A>) l).head);
            l = ((NonEmptyImmutableList<A>) l).tail;
        }
        return list;
    }
	
	public static <A> Collector<A, ?, ImmutableList<A>> collector() {
		
		return new Collector<A, ArrayList<A>, ImmutableList<A>>() {
			@Override
			public Supplier<ArrayList<A>> supplier() {
				return ArrayList::new;
			}

			@Override
			public BiConsumer<ArrayList<A>, A> accumulator() {
				return ArrayList::add;
			}

			@Override
			public BinaryOperator<ArrayList<A>> combiner() {
				return (left, right) -> {
					left.addAll(right);
					return left;
				};
			}

			@Override
			public Function<ArrayList<A>, ImmutableList<A>> finisher() {
				return ImmutableList::from;
			}

			@Override
			public Set<Characteristics> characteristics() {
				return new HashSet<>();
			}
		};
	}
	
	@Override
    public final Iterator<A> iterator() {
        
		return new Iterator<A>() {
            private ImmutableList<A> curr = ImmutableList.this;

            @Override
            public boolean hasNext() {
                return !this.curr.isEmpty();
            }

            @Override
            public A next() {
                if (this.curr instanceof NonEmptyImmutableList) {
                    NonEmptyImmutableList<A> nel = (NonEmptyImmutableList<A>) this.curr;
                    this.curr = nel.tail;
                    return nel.head;
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    @Override
    public final Spliterator<A> spliterator() {
        
    	return Spliterators.spliterator(iterator(), 
    			this.length(), 
    			Spliterator.IMMUTABLE | Spliterator.NONNULL);
    }

    public final Stream<A> stream() {
        
    	return StreamSupport.stream(this.spliterator(), false);
    }
    
    @Override
    public void forEach(final Consumer<? super A> action) {
    	Consumer<? super A> act = Objects.requireNonNull(action);
        ImmutableList<A> list = this;
        while (list instanceof NonEmptyImmutableList) {
            A head = ((NonEmptyImmutableList<A>) list).head;
            act.accept(head);
            list = ((NonEmptyImmutableList<A>) list).tail;
        }
    }
    
    public final boolean isNotEmpty() {
        return !this.isEmpty();
    }
    
    public final Result<A> index(int index) {
        ImmutableList<A> l = this;
        if (index < 0) {
            return Result.failure("negative index");
        }
        while (index > 0) {
            if (l.isEmpty()) {
                return Result.empty();
            }
            index--;
            l = ((NonEmptyImmutableList<A>) l).tail;
        }
        return l.head();
    }
	
    public final Result<A> find(final Predicate<A> p) {
        ImmutableList<A> self = this;
        while (self instanceof NonEmptyImmutableList) {
            NonEmptyImmutableList<A> selfNel = (NonEmptyImmutableList<A>) self;
            boolean result = p.test(selfNel.head);
            if (result) {
            	return Result.success(selfNel.head);
            }
            self = selfNel.tail;
        }
        return Result.empty();
    }
    
    public final Result<Integer> findIndex(final Predicate<A> p) {
        ImmutableList<A> self = this;
        int i = 0;
        while (self instanceof NonEmptyImmutableList) {
            NonEmptyImmutableList<A> selfNel = (NonEmptyImmutableList<A>) self;
            if (p.test(selfNel.head)) {
                return Result.success(i);
            }
            self = selfNel.tail;
            ++i;
        }
        return Result.empty();
    }
    
	public abstract int length();
	
	public abstract boolean isEmpty();
	
	public abstract <B> B foldLeft(final B init, final BiFunction<B, ? super A, B> f);
	
	public abstract <B> B foldRight(final B init, final BiFunction<? super A, B, B> f);
	
	public abstract Result<A> head();
	
	public abstract Result<A> last();
	
	public abstract Result<ImmutableList<A>> tail();
	
	public abstract Result<ImmutableList<A>> init();
	
	public abstract ImmutableList<A> filter(final Predicate<A> p);
	
	public abstract int count(final Predicate<A> p);
	
	public abstract <B> ImmutableList<B> map(final Function<A, B> f);
	
	public abstract ImmutableList<A> take(int n);
	
	public abstract ImmutableList<A> drop(int n);
	
	public abstract <B, C> ImmutableList<C> zipWith(final BiFunction<A, B, C> f, final ImmutableList<B> list);
	
	public abstract <B extends A> ImmutableList<A> append(final ImmutableList<? extends B> list);
	
	public abstract boolean exists(final Predicate<A> p);
	
	public abstract boolean every(final Predicate<A> p);
	
	public abstract boolean contains(final A a);
	
	public abstract Tuple2<ImmutableList<A>, ImmutableList<A>> span(Predicate<A> p);
	
	public abstract <B> ImmutableList<B> flatMap(Function<A, ImmutableList<B>> f);
	
	public abstract ImmutableList<A> removeAll(final Predicate<A> p);
	
	public abstract ImmutableList<A> reverse();

	
	private static class EmptyImmutableList<A> extends ImmutableList<A> {

		@Override
		public String toString() {
			
			StringBuilder string = new StringBuilder();
			string.append("[");
			string.append("Nil");
	        string.append("]");
	        return string.toString();
		}
		
		@Override
		public boolean equals(Object o) {			
	        
			if (o == null) return false;
			if (!(o.getClass() == this.getClass()))
				return false;
			return this == o;
		}
		
		@Override
		public int hashCode() {
			
			return "Nil".hashCode();
		}
		
		@Override
		public int length() {
			
			return 0;
		}

		@Override
		public boolean isEmpty() {
			
			return true;
		}

		@Override
		public <B> B foldLeft(B init, BiFunction<B, ? super A, B> f) {
			return init;
		}

		@Override
		public <B> B foldRight( B init, BiFunction<? super A, B, B> f) {
			return init;
		}

		@Override
		public Result<A> head() {
			return Result.empty();
		}

		@Override
		public Result<A> last() {
			return Result.empty();
		}

		@Override
		public Result<ImmutableList<A>> tail() {
			return Result.empty();
		}

		@Override
		public Result<ImmutableList<A>> init() {
			return Result.empty();
		}

		@Override
		public ImmutableList<A> filter(Predicate<A> p) {
			return this;
		}

		@Override
		public int count(Predicate<A> p) {
			return 0;
		}

		@Override
		public <B> ImmutableList<B> map(Function<A, B> f) {
			return empty();
		}

		@Override
		public ImmutableList<A> take(int n) {
			return this;
		}

		@Override
		public ImmutableList<A> drop(int n) {
			return this;
		}

		@Override
		public <B, C> ImmutableList<C> zipWith(BiFunction<A, B, C> f, ImmutableList<B> list) {
			return (ImmutableList<C>) this;
		}

		@Override
		public <B extends A> ImmutableList<A> append(ImmutableList<? extends B> list) {
			return (ImmutableList<A>) list;
		}

		@Override
		public boolean exists(Predicate<A> p) {
			return false;
		}

		@Override
		public boolean every(Predicate<A> p) {
			return true;
		}

		@Override
		public boolean contains(A a) {
			return false;
		}

		@Override
		public Tuple2<ImmutableList<A>, ImmutableList<A>> span(Predicate<A> p) {
			return new Tuple2(empty(), empty());
		}

		@Override
		public <B> ImmutableList<B> flatMap(Function<A, ImmutableList<B>> f) {
			return (ImmutableList<B>) this;
		}

		@Override
		public ImmutableList<A> removeAll(Predicate<A> p) {
			return this;
		}

		@Override
		public ImmutableList<A> reverse() {
			return this;
		}
		
	}
	
	
	private static class NonEmptyImmutableList<A> extends ImmutableList<A> {
		
		private final A head;
		private final ImmutableList<A> tail;
		

		public NonEmptyImmutableList(final A head, final ImmutableList<A> tail) {
	        
			this.head = Objects.requireNonNull(head);
	        this.tail = Objects.requireNonNull(tail);
	    }
		
		@Override
		public String toString() {
			
			StringBuilder string = new StringBuilder();
			string.append("[");
			ImmutableList<A> l = this;
	        for (int i = 0; i < this.length(); i++) {
	            string.append(((NonEmptyImmutableList<A>) l).head).append(", ");
	            l = ((NonEmptyImmutableList<A>) l).tail;
	        }
	        string.append("NIL");
	        string.append("]");
	        return string.toString();
		}
		
		@Override
		public boolean equals(Object o) {
			
			if (this == o) {
				return true;
			}
			if (!(o instanceof NonEmptyImmutableList)) {
				return false;
			}

			ImmutableList<A> l = this;
			ImmutableList<A> r = (ImmutableList<A>) o;

			if (l.length() != r.length()) {
				return false;
			}

			while (l instanceof NonEmptyImmutableList 
					&& r instanceof NonEmptyImmutableList) {
				if (l == r) {
					return true;
				}
				NonEmptyImmutableList<A> nelL = (NonEmptyImmutableList<A>) l;
				NonEmptyImmutableList<A> nelR = (NonEmptyImmutableList<A>) r;
				if (!nelL.head.equals(nelR.head)) {
					return false;
				}
				l = nelL.tail;
				r = nelR.tail;
			}
			return l == r;
		}
		
		@Override
		public int hashCode() {
			
			ImmutableList<A> l = this;
			Object[] array = new Object[this.length() + 1];
			array[0] = this.length();
			int j = 1;
	        for (int i = 0; i < this.length(); i++) {
	            array[j] = ((NonEmptyImmutableList<A>) l).head;
	            l = ((NonEmptyImmutableList<A>) l).tail;
	            j++;
	        }
	        return Objects.hash(array);
		}

		@Override
		public int length() {
			
			return 1 + tail.length();
		}

		@Override
		public boolean isEmpty() {
			
			return false;
		}

		@Override
		public <B> B foldLeft(B init, BiFunction<B, ? super A, B> f) {
			f = Objects.requireNonNull(f);
			init = Objects.requireNonNull(init);
			ImmutableList<A> list = this;
	        while (list instanceof NonEmptyImmutableList) {
	            init = f.apply(init, ((NonEmptyImmutableList<A>) list).head);
	            list = ((NonEmptyImmutableList<A>) list).tail;
	        }
	        return init;
		}

		@Override
		public <B> B foldRight(B init, BiFunction<? super A, B, B> f) {
			f = Objects.requireNonNull(f);
			init = Objects.requireNonNull(init);
			ArrayList<A> list = this.toArrayList();
	        for (int i = this.length() - 1; i >= 0; i--) {
	            init = f.apply(list.get(i), init);
	        }
	        return init;
		}

		@Override
		public Result<A> head() {
			return Result.of(this.head);
		}

		@Override
		public Result<A> last() {
			return Result.of(this.getLast());
		}

		private A getLast() {
			NonEmptyImmutableList<A> other = this;
	        while (true) {
	            if (other.tail instanceof NonEmptyImmutableList) {
	                other = ((NonEmptyImmutableList<A>) other.tail);
	            } else {
	                return other.head;
	            }
	        }
		}

		@Override
		public Result<ImmutableList<A>> tail() {
			return Result.of(this.tail);
		}

		@Override
		public Result<ImmutableList<A>> init() {
			return Result.of(this.getInit());
		}

		private ImmutableList<A> getInit() {
			ArrayList<A> arrList = this.toArrayList();
			arrList.remove(arrList.size() - 1);
			return from(arrList);
		}

		@Override
		public ImmutableList<A> filter(Predicate<A> p) {
			p = Objects.requireNonNull(p);
			A[] result = (A[]) new Object[this.length()];
	        ImmutableList<A> list = this;
	        int j = 0;
	        for (int i = 0; i < this.length(); i++) {
	            A el = ((NonEmptyImmutableList<A>) list).head;
	            if (p.test(el)) {
	                result[j] = el;
	                j++;
	            }
	            list = ((NonEmptyImmutableList<A>) list).tail;
	        }
	        return fromBounded(result, 0, j);
		}

		@Override
		public int count(Predicate<A> p) {
			p = Objects.requireNonNull(p);
			int count = 0;
	        ImmutableList<A> list = this;
	        for (int i = 0; i < this.length(); i++) {
	            if (p.test(((NonEmptyImmutableList<A>) list).head)) {
	                count++;
	            }
	            list = ((NonEmptyImmutableList<A>) list).tail;
	        }
	        return count;
		}

		@Override
		public <B> ImmutableList<B> map(Function<A, B> f) {
			f = Objects.requireNonNull(f);
			List<B> result = new ArrayList<>();
	        ImmutableList<A> list = this;
	        for (int i = 0; i < this.length(); i++) {
	            result.add(f.apply(((NonEmptyImmutableList<A>) list).head));
	            list = ((NonEmptyImmutableList<A>) list).tail;
	        }
	        return (ImmutableList<B>) ImmutableList.from(result);
		}

		@Override
		public ImmutableList<A> take(int n) {
			if (n <= 0) {
	            return empty();
	        }
	        A[] result = (A[]) new Object[n];
	        ImmutableList<A> list = this;
	        for (int i = 0; i < n; i++) {
	            result[i] = ((NonEmptyImmutableList<A>) list).head;
	            list = ((NonEmptyImmutableList<A>) list).tail;
	        }
	        return ImmutableList.fromBounded(result, 0, result.length);
		}

		@Override
		public ImmutableList<A> drop(int n) {
			if (n <= 0) {
	            return this;
	        }
	        ImmutableList<A> list = this;
	        while (n > 0) {
	            if (list instanceof NonEmptyImmutableList) {
	                list = ((NonEmptyImmutableList<A>) list).tail;
	                n--;
	            }
	        }
	        return list;
		}

		@Override
		public <B, C> ImmutableList<C> zipWith(BiFunction<A, B, C> f, ImmutableList<B> list) {
			
			f = Objects.requireNonNull(f);
			list = Objects.requireNonNull(list);
			
			ImmutableList<A> list1 = this;
	        ImmutableList<B> list2 = list;
	        int n = Math.min(list1.length(), list2.length());
	        C[] result = (C[]) new Object[n];
	        for (int i = 0; i < n; i++) {
	            result[i] = f.apply(((NonEmptyImmutableList<A>) list1).head, ((NonEmptyImmutableList<B>) list2).head);
	            list1 = ((NonEmptyImmutableList<A>) list1).tail;
	            list2 = ((NonEmptyImmutableList<B>) list2).tail;
	        }
	        return ImmutableList.fromBounded(result, 0, result.length);
		}

		@Override
		public <B extends A> ImmutableList<A> append(ImmutableList<? extends B> list) {
			list = Objects.requireNonNull(list);
			if (list.length() == 0) {
	            return this;
	        }
			ArrayList<A> copy = this.toArrayList();
	        ImmutableList<A> listT = (ImmutableList<A>) list;
	        for (int i = copy.size() - 1; i >= 0; i--) {
	            listT = cons(copy.get(i), listT);
	        }
	        return listT;
		}

		@Override
		public boolean exists(Predicate<A> p) {
			p = Objects.requireNonNull(p);
			NonEmptyImmutableList<A> list = this;
	        while (true) {
	            if (p.test(list.head)) {
	                return true;
	            }
	            if (list.tail instanceof EmptyImmutableList) {
	                return false;
	            }
	            list = ((NonEmptyImmutableList<A>) list.tail);
	        }
		}

		@Override
		public boolean every(Predicate<A> p) {
			p = Objects.requireNonNull(p);
			NonEmptyImmutableList<A> list = this;
	        while (true) {
	            if (!p.test(list.head)) {
	                return false;
	            }
	            if (list.tail instanceof EmptyImmutableList) {
	                return true;
	            }
	            list = ((NonEmptyImmutableList<A>) list.tail);
	        }
		}

		@Override
		public boolean contains(A a) {
			a = Objects.requireNonNull(a);
			NonEmptyImmutableList<A> list = this;
	        while (true) {
	            if (list.head == a) {
	                return true;
	            }
	            if (list.tail instanceof EmptyImmutableList) {
	                return false;
	            }
	            list = ((NonEmptyImmutableList<A>) list.tail);
	        }
		}

		@Override
		public Tuple2<ImmutableList<A>, ImmutableList<A>> span(Predicate<A> p) {
			p = Objects.requireNonNull(p);
			A[] result = (A[]) new Object[this.length()];
	        ImmutableList<A> list = this;
	        int j = 0;
	        for (int i = 0; i < this.length(); i++) {
	            A el = ((NonEmptyImmutableList<A>) list).head;
	            if (p.test(el)) {
	                result[j] = el;
	                j++;
	            } else {
	                break;
	            }
	            list = ((NonEmptyImmutableList<A>) list).tail;
	        }
	        return new Tuple2(fromBounded(result, 0, j), list);
		}

		@Override
		public <B> ImmutableList<B> flatMap(Function<A, ImmutableList<B>> f) {
			f = Objects.requireNonNull(f);
			ArrayList<B> result = new ArrayList<>();
	        ImmutableList<A> list = this;
	        while (list instanceof NonEmptyImmutableList) {
	            ImmutableList<B> bucket = f.apply(((NonEmptyImmutableList<A>) list).head);
	            while (bucket instanceof NonEmptyImmutableList) {
	                result.add(((NonEmptyImmutableList<B>) bucket).head);
	                bucket = ((NonEmptyImmutableList<B>) bucket).tail;
	            }
	            list = ((NonEmptyImmutableList<A>) list).tail;
	        }
	        return from(result);
		}

		@Override
		public ImmutableList<A> removeAll(Predicate<A> p) {
			Predicate<A> q = Objects.requireNonNull(p);
			return this.filter(x -> !q.test(x));
		}

		@Override
		public ImmutableList<A> reverse() {
			ImmutableList<A> list = this;
	        ImmutableList<A> acc = empty();
	        while (list instanceof NonEmptyImmutableList) {
	            acc = acc.cons(((NonEmptyImmutableList<A>) list).head);
	            list = ((NonEmptyImmutableList<A>) list).tail;
	        }
	        return (NonEmptyImmutableList<A>) acc;
		}		
	}
}
