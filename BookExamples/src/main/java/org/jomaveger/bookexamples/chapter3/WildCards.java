package org.jomaveger.bookexamples.chapter3;

import java.util.ArrayList;
import java.util.List;

import org.jomaveger.bookexamples.chapter2.Employee;

public interface WildCards {

    static void printList(List<?> list) {
        for (int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));
    }

    static void reverse(List<?> list) {
        reverseHelper(list);
    }

    static <T> void reverseHelper(List<T> list) {
        List<T> reverse = new ArrayList<>();
        for (Integer i = 0; i < list.size(); i++)
            reverse.add(list.get(list.size() - i - 1));
        for (Integer i = 0; i < list.size(); i++)
            list.set(i, reverse.get(i));
    }

    static <T> List<T> rev(List<T> list) {
        List<T> reverse = new ArrayList<T>();
        for (int i = 0; i < list.size(); i++) {
            reverse.add(list.get(list.size() - i - 1));
        }
        return reverse;
    }

    static <T> void print1(List<Pair<T>> list) {
        for (Pair<T> pair : list) {
            System.out.println(pair);
        }
    }

    static void print2(List<Pair<?>> list) {
        for (Pair<?> pair : list) {
            System.out.println(pair);
        }
    }

    static void print3(List<? extends Pair<?>> list) {
        for (Pair<?> pair : list) {
            System.out.println(pair);
        }
    }

    static Object projection(Pair<?> pair, Integer index) {
        if (index.equals(1)) {
            return pair.getFirst();
        } else {
            return pair.getSecond();
        }
    }

    static Double totalSalary(List<? extends Employee> list) {
        Double sum = 0.0;
        for (int i = 0; i < list.size(); i++)
            sum = sum + list.get(i).getSueldo();
        return sum;
    }

    static Boolean sameLength(List<?> one, List<?> two) {
        return one.size() == two.size();
    }

    static void swap(Pair<?> p) {
        swapHelper(p);
    }

    static <T> void swapHelper(Pair<T> p) {
        T t = p.getFirst();
        p.setFirst(p.getSecond());
        p.setSecond(t);
    }

    static <T> void copy(List<? super T> dest, List<? extends T> src) {
        for (int i = 0; i < src.size(); i++) {
            dest.add(src.get(i));
        }
    }
    
    static <T extends Comparable<? super T>> T min(List<T> list) {
        if (list.size() == 0) return null;
        T smallest = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (smallest.compareTo(list.get(i)) > 0) {
                smallest = list.get(i);
            }
        }
        return smallest;
    }
}