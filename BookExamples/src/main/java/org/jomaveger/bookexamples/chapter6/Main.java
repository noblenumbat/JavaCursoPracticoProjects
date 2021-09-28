package org.jomaveger.bookexamples.chapter6;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.jomaveger.functional.control.Option;
import org.jomaveger.functional.control.Result;

public class Main {
	
	public static void main(String[] args) {
		
//		Address ad = new Address("1058  Lords Way", "Jackson", "usa", "1");
//		Agent jason = new Agent("Jason", "Bourne", 1970);
//		Agent robert = new Agent("Robert", "McCall", 1960);
//		Agent jack = new Agent("Jack", "Ryan", 1965, "jack.ryan@cia.us", ad);
		
//		Option.instance(robert)
//			.flatMap(Agent::getEmail)
//			.forEach(System.out::println);
//		
//		Option.instance(jack)
//			.flatMap(Agent::getAddress)
//			.flatMap(Address::getCountryCode)
//			.forEach(System.out::println);
//		
//		Function<String, Option<String>> upperOption = Option.hlift(String::toUpperCase);
//		
//		System.out.println(upperOption.apply("hola pepe"));
		
//			forEach(System.out::println);
//		Option.instance(robert).map(Agent::getEmail).forEach(System.out::println);
//		Option.instance(jack).map(Agent::getEmail).forEach(System.out::println);
			
//		System.out.println();
		
//		Option<Option<String>> map = Option.instance(robert).map(Student::getEmail);		
//		map.get().get();
		
//		Option<String> fmap = Option.instance(robert).flatMap(Agent::getEmail);
//		fmap.get();
		
//		System.out.println("The best is " + Option.instance(jack).map(Student::getEmail).get());
//		System.out.println("The best is " + Option.instance(robert).map(Student::getEmail).get());
//		System.out.println(Option.instance(jason).map(Student::getEmail).get());
		
		org.jomaveger.bookexamples.chapter6.Map<String, Spy> spies = new org.jomaveger.bookexamples.chapter6.Map<String, Spy>()
				.put("Jason", new Spy("Jason", "Bourne", "jason.bourne@cia.gov.us"))
				.put("Robert", new Spy("Robert", "McCall"))
				.put("Jack", new Spy("Jack", "Ryan", "jack.ryan@cia.gov.us"));
				
//		Result<String> result =
//				getName().flatMap(spies::get).flatMap(Spy::getEmail);
		
//		System.out.println(result);
		
		List<String> words = Arrays.asList("Han", "Luke", "Leia");
		Stream<String> stream = words.stream();
		Consumer<String> print = t -> {
		    System.out.println(t.length());
		    System.out.println(t);
		};
		stream.forEach(print);
		
		Stream<Integer> intStream = Stream.of(1, 2, 3, 4, 5, 6, 7);
		System.out.println(
				intStream.noneMatch(i -> i > 0)
		); // false
		
		Stream<Integer> intStream2 = Stream.of(1, 2, 3, 4, 5, 6, 7);
		System.out.println(
				intStream2.noneMatch(i -> i%3 == 0)
		); // false

		Stream<Integer> intStream3 = Stream.of(1, 2, 3, 4, 5, 6, 7);
		System.out.println(
				intStream3.noneMatch(i -> i > 10)
		); // true
		
		List<Integer> list = Arrays.asList(57, 38, 37, 54, 2);
		list.stream()
		    .sorted()
		    .forEach(System.out::println);
		
		List<String> strings =
			    Arrays.asList("Han", "Luke", "Chewbacca", "Darth Vader");
		strings.stream()
			   .sorted( (s1, s2) -> s2.length() - s1.length() )
			   .forEach(System.out::println);
		
		List<Integer> lista = Arrays.asList(57, 38, 37, 54, 2);
		System.out.println(lista.stream().count()); // 5
		
		List<String> min = Arrays.asList("Han", "Luke", "Chewbacca", "Darth Vader");
		Option.of(min.stream().min( Comparator.comparing((String s) -> s.length()))).forEach(System.out::println); //Han
		
		List<String> max = Arrays.asList("Han", "Luke", "Chewbacca", "Darth Vader");
		Option.of(max.stream().max( Comparator.comparing((String s) -> s.length()))).forEach(System.out::println); //Darth Vader
		
		Stream.of('a', 'b', 'c', 'd', 'e')
	    .map(c -> (int)c)
	    .forEach(i -> System.out.format("%d ", i));
		
		List<Character> aToD = Arrays.asList('a', 'b', 'c', 'd');
		List<Character> eToG = Arrays.asList('e', 'f', 'g');
		Stream<List<Character>> cstream = Stream.of(aToD, eToG);
		cstream.flatMap(l -> l.stream())
	    		.map(c -> (int)c)
	    		.forEach(i -> System.out.format("%d ", i)); //97 98 99 100 101 102 103
		
		List<List<Rectangle>> rectangleLists = Arrays.asList(
				Arrays.asList(new Rectangle(10, 10, 20, 20),
				new Rectangle(10, 20, 30, 40),
				new Rectangle(40, 30, 20, 20)),
				Arrays.asList(new Rectangle(50, 50, 30, 30),
				new Rectangle(60, 60, 20, 20)),
				Arrays.asList(new Rectangle(100, 100, 30, 40),
				new Rectangle(110, 10, 20, 20),
				new Rectangle(120, 10, 50, 60))
				);
		rectangleLists.stream()
			.flatMap((l) -> l.stream())
			.forEach(System.out::println);
		
		rectangleLists.stream()
			.flatMap((l) -> l.stream()
							.map(r -> {
				r.setHeight(30);
				return r;
			})
		.filter(r -> r.getArea() > 900)
		)
		.map(r -> r.getArea())
		.distinct()
		.forEach(System.out::println);
		
		List<Integer> lreduce = Arrays.asList(1, 2, 3, 4, 5, 6);
		System.out.println(lreduce.stream().reduce(0, (sum, n) -> sum + n));
		
		List<Person> people = Arrays.asList(new Person("Juan", 1980), new Person("Julia", 1964));
		int computedAges = people.stream().reduce(0, (partialAgeResult, person) -> partialAgeResult + person.getAge(2019), Integer::sum);
		System.out.println(computedAges);
		
		List<String> l = Arrays.asList("Luke", "Leia", "Han");
		String s = l.stream().collect(StringBuilder::new,
		                            (sb, s1) -> sb.append(" ").append(s1),
		                            (sb1, sb2) -> sb1.append(sb2.toString())).toString();
		List<String> l1 = Arrays.asList("Luke", "Leia", "Han");
		String t = l1.stream().reduce("", (s1, s2) -> s1 + " " + s2);
		System.out.println(t);
		
		List<Integer> l2 = Stream.of(1, 2, 3, 4, 5)
			        .collect(Collectors.toCollection(LinkedList::new)); // [1, 2, 3, 4, 5]
		System.out.println(l2);
		
		Stream<String> sw = Stream.of("Luke", "Han", "Leia");
        List<String> uList = sw.collect(Collectors.collectingAndThen(
                            Collectors.toList(), Collections::unmodifiableList)); // [Luke, Han, Leia]
        System.out.println(uList);
        
        List<String> l3 = Arrays.asList("Luke", "Leia", "Han", "Luke");
        Map<String, Integer> result = l3.stream().collect(Collectors.toMap(Function.identity(), String::length, (item, identicalItem) -> item)); // {Han=3, Leia=4, Luke=4}
        System.out.println(result);
        
        String v = Stream.of("Luke", "Han", "Leia").collect(Collectors.joining("-", "PRE-", "-POST")); // PRE-Luke-Han-Leia-POST
        System.out.println(v);
        
        Long count = Stream.of("Luke", "Han", "Leia").collect(Collectors.counting()); // 3
        System.out.println(count);
        
        Map<Integer, List<Integer>> map =
        		   Stream.of(2, 34, 54, 23, 33, 20, 59, 11, 19, 37)
        		      .collect( Collectors.groupingBy (i -> i/10 * 10 ) );
        System.out.println(map);
        
        Map<Integer, Long> map2 =
        	    Stream.of(2, 34, 54, 23, 33, 20, 59, 11, 19, 37)
        	        .collect(
        	        		Collectors.groupingBy(i -> i/10 * 10,
        	            		Collectors.counting()
        	            )
        	        );
        System.out.println(map2);
        
        Map<Integer, Map<String, List<Integer>>> map3 =
        		   Stream.of(2, 34, 54, 23, 33, 20, 59, 11, 19, 37)
        		       .collect(Collectors.groupingBy(i -> i/10 * 10,
        		    		   Collectors.groupingBy(i ->
        		                               i%2 == 0 ? "EVEN" : "ODD")
        		                )
        		       );
        
        System.out.println(map3);
        
        Map<Integer, Map<String, List<Integer>>> map4 =
        	    Stream.of(2,34,54,23,33,20,59,11,19,37)
        	       .collect( Collectors.groupingBy(i -> i/10 * 10,
        	                 TreeMap::new,
        	                 Collectors.groupingBy(i -> i%2 == 0 ? "EVEN" : "ODD")
        	               )
        	       );
        System.out.println(map4);
        
        Map<Boolean, List<Integer>> map5 =
        	    Stream.of(45, 9, 65, 77, 12, 89, 31, 12)
        	        .collect(Collectors.partitioningBy(i -> i < 50));
        System.out.println(map5);
        
        Map<Boolean, Set<Integer>> map6 =
        	    Stream.of(45, 9, 65, 77, 12, 89, 31, 12)
        	        .collect(
        	        		Collectors.partitioningBy(i -> i < 50,
        	        				Collectors.toSet()
        	            )
        	        );
        System.out.println(map6);
	}
	
	public static Result<String> getName() {
//		return Result.success("Jason");
//		return Result.failure(new IOException("Input error"));
//		return Result.success("Robert");
		return Result.success("Peter");
	}

}
