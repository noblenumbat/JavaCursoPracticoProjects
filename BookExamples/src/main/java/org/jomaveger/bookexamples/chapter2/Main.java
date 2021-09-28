package org.jomaveger.bookexamples.chapter2;

public class Main {

    public static void main(String[] args) {

        Counter count = new Counter();

        System.out.println("Counter value: " +  count.currentCount());

        count.incrementCount();
        count.incrementCount();
        count.incrementCount();

        System.out.println("Counter value: " + count.currentCount());

        System.out.println("Number of Points: " + Point2D.getNumberOfPoints());

        Point2D point = new Point2D(12.0, 5.0);

        System.out.println("Number of Points: " + Point2D.getNumberOfPoints());

        System.out.println("Radio de Coordenadas Polares: " + point.getRho());

        System.out.println("Angulo en Grados de Coordenadas Polares: " + point.getTheta());

        Point2D pointA = new Point2D(-1.0 , -1.0);
        Point2D pointB = new Point2D(2.0 , 3.0);

        System.out.println("Distancia del punto (-1.0, -1.0) " +
                "al punto (2.0, 3.0): " + pointA.getDistance(pointB));

        Point2D pointC = new Point2D(1.0 , 3.0);
        Point2D pointD = new Point2D(3.0 , 6.0);
        pointD.rotate(pointC, 270.0);

        System.out.println("Resultado de rotar el punto (3.0, 6.0) " +
                "alrededor del punto (1.0, 3.0) un angulo de 270 grados: "
                + pointD.getX() + " - " + pointD.getY());

        System.out.println("Number of Points: " + Point2D.getNumberOfPoints());
    }
}
