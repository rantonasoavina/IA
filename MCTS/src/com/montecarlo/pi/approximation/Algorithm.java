package com.montecarlo.pi.approximation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.stream.IntStream;

public class Algorithm implements Runnable {
    private int radius;
    private int throwNumber;
    private volatile CollectPoints collect;
    private final CountDownLatch doneSignal;

    public Algorithm(int throwNumber, int radius, CollectPoints collect, CountDownLatch doneSignal) {
        this.throwNumber = throwNumber;
        this.radius = radius;
        this.collect = collect;
        this.doneSignal = doneSignal;
    }

    public CollectPoints getCollect() {
        return collect;
    }

    public void setCollect(CollectPoints collect) {
        this.collect = collect;
    }

    @Override
    public void run() {
//        System.out.println("current thread : " + Thread.currentThread().getName());
        printPiApproximation();
        doneSignal.countDown();
    }

    private synchronized void printPiApproximation() {
        for (int u = 0; u < throwNumber; u++) {
            double xCoordinate = Position.getRandomCoordinate(-radius, radius);
            double yCoordinate = Position.getRandomCoordinate(-radius, radius);
            if (formulaPythagore(xCoordinate, yCoordinate) <= radius * radius) {
                long pointsInCircle = collect.getPointsInCircle();
                System.out.println("points in circle : " + pointsInCircle + " / Thread name : " + Thread.currentThread().getName());

                collect.setPointsInCircle(++pointsInCircle);
            } else {
                long pointsInSquare = collect.getPointsInSquare();
                collect.setPointsInSquare(++pointsInSquare);
            }
        }
    }

    /**
     * 4 x ratio(points in Circle / points in Square)
     *
     * @param circlePointNumber
     * @param squarePointNumber
     * @return
     */
    public synchronized static Double getPi(long circlePointNumber, long squarePointNumber) {
        return 4 * calculateRatio(circlePointNumber, squarePointNumber);
    }

    /**
     * sqrt(x² + y²)
     *
     * @param x
     * @param y
     * @return distance
     */
    private static Double formulaPythagore(double x, double y) {
        return Math.pow(x, 2) + Math.pow(y, 2);
    }

    /**
     * probability for the points in circle to square
     *
     * @param circlePointNumber
     * @param squarePointNumber
     * @return
     */
    private static double calculateRatio(long circlePointNumber, long squarePointNumber) {
        return (double)circlePointNumber / (double)circlePointNumber + squarePointNumber;
    }
}
