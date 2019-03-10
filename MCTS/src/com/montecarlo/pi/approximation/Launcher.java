package com.montecarlo.pi.approximation;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Launcher {
    private static int N = 1000000;

    public static void main(String[] args) {
//        System.out.println("args = [" + Runtime.getRuntime().availableProcessors() + "]");
        long startTime = System.currentTimeMillis();
        CountDownLatch doneSignal = new CountDownLatch(N);
        CollectPoints collectPoints = new CollectPoints();
        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (long i = 0; i <= N; i++) {
            Algorithm algo = new Algorithm(10, 50, collectPoints, doneSignal);
            executor.execute(algo);
            System.out.println(i + " ]");

        }

        try {
            doneSignal.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        printPi(collectPoints);
        long executionTime = (System.currentTimeMillis() - startTime) / 1000;
        System.out.println("executionTime = " + executionTime + " seconds");
    }

    private static void printPi(CollectPoints collectPoints) {
        long pointsInCircle = collectPoints.getPointsInCircle();
        long pointsInSquare = collectPoints.getPointsInSquare();
        printTotalPointsThrown(pointsInCircle, pointsInSquare);

        Double pi = Algorithm.getPi(pointsInCircle, pointsInSquare);
        System.out.println("] Pi approximation : " + pi);
    }

    private static void printTotalPointsThrown(long pointsInCircle, long pointsInSquare) {
        System.out.println("points in circle = " + pointsInCircle);
        System.out.println("points in square = " + pointsInSquare);
        System.out.println("total points = " + (pointsInSquare + pointsInCircle));
    }
}
