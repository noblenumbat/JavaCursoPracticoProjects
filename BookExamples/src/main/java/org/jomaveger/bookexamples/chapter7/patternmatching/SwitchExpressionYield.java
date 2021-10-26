package org.jomaveger.bookexamples.chapter7.patternmatching;

public class SwitchExpressionYield {

    public enum Person {
        Mozart, Picasso, Goethe, Dostoevsky, Prokofiev, Dali,

        Newton, Einstein
    }

    public static void main(String[] args) {
        print(Person.Mozart);
        print(Person.Dali);
        print(Person.Einstein);
    }

    public static void print(Person person) {
        String title = switch (person) {
            case Dali, Picasso      -> "painter";
            case Mozart, Prokofiev  -> "composer";
            case Goethe, Dostoevsky -> "writer";
            default                 -> {
                System.out.printf("Oops! I don't know about %s%n", person);
                yield "...";
            }
        };
        System.out.printf("%s was a %s%n", person, title);
    }
}