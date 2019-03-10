package com.montecarlo.pi.approximation;

import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Position {

    public static Double getRandomCoordinate(int min, int max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
}
