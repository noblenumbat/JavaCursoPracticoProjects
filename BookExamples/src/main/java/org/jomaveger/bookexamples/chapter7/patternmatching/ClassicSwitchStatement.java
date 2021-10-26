package org.jomaveger.bookexamples.chapter7.patternmatching;

public class ClassicSwitchStatement {

	public enum Person {
		Mozart, Picasso, Goethe, Dostoevsky, Prokofiev, Dali
	}

	public static void main(String[] args) {
		print(Person.Mozart);
		print(Person.Dali);
		print(Person.Dostoevsky);
	}

	public static void print(Person person) {
		switch (person) {
		case Dali:
		case Picasso:
			System.out.printf("%s was a painter%n", person);
			break;
		case Mozart:
		case Prokofiev:
			System.out.printf("%s was a composer%n", person);
			break;
		case Goethe:
		case Dostoevsky:
			System.out.printf("%s was a writer%n", person);
			break;
		default:
			throw new IllegalArgumentException(String.format("Unknown person: %s", person));
		}
	}
}