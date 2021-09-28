package org.jomaveger.bookexamples.chapter2;

public class Main2 {

	public static void main(String[] args) {
		Fruits[] allFruits = Fruits.values();
	    for (Fruits f : allFruits)
	        System.out.println(f);

	    System.out.println();

	    Fruits fruit = Fruits.valueOf("ORANGE");
	    System.out.println("fruit is " + fruit);

	    fruit = Fruits.APPLE;

	    if (fruit == Fruits.APPLE)
	        System.out.println("fruit is an apple.");

	    switch(fruit) {
	        case APPLE:
	            System.out.println("The price of " + Fruits.APPLE + " is " + Fruits.APPLE.getPrice());
	            break;
	        case ORANGE:
	            System.out.println("The price of " + Fruits.ORANGE + " is " + Fruits.ORANGE.getPrice());
	                break;
	        case PEAR:
	            System.out.println("The price of " + Fruits.PEAR + " is " + Fruits.PEAR.getPrice());
	            break;
	        case BANANA:
	            System.out.println("The price of " + Fruits.BANANA + " is " + Fruits.BANANA.getPrice());
	            break;
	        }

	    for (Fruits f : allFruits)
	        System.out.println(f + " " + f.ordinal());


	    Fruits orange = Fruits.ORANGE;
	    Fruits apple = Fruits.APPLE;
	    Fruits orange2 = Fruits.ORANGE;

	    if(orange.compareTo(apple) < 0)
	        System.out.println(orange + " comes before " + apple);
	    if(orange.compareTo(apple) > 0)
	        System.out.println(apple + " comes before " + orange);
	    if(orange.compareTo(orange2) == 0)
	        System.out.println(orange + " equals " + orange2);

	    System.out.println();

	    if(orange.equals(apple))
	        System.out.println("Error!");
	    if(orange.equals(orange2))
	        System.out.println(orange + " equals " + orange2);
	    if(orange == orange2)
	        System.out.println(orange + " == " + orange2);
	}
}
