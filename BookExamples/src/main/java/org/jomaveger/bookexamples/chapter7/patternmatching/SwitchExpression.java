package org.jomaveger.bookexamples.chapter7.patternmatching;

public class SwitchExpression {

    public enum Person {
        Mozart, Picasso, Goethe, Dostoevsky, Prokofiev, Dali
    }

    public static void main(String[] args) {
        print(Person.Mozart);
        print(Person.Dali);
        print(Person.Dostoevsky);
    }

    public static void print(Person person) {
        String title = switch (person) {
            case Dali, Picasso      -> "painter";
            case Mozart, Prokofiev  -> "composer";
            case Goethe, Dostoevsky -> "writer";
        };
        System.out.printf("%s was a %s%n", person, title);
    }
}