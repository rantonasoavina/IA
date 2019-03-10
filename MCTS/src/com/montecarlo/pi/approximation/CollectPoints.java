package com.montecarlo.pi.approximation;

public final class CollectPoints {
    volatile long pointsInCircle;
    volatile long pointsInSquare;

    public long getPointsInCircle() {
        return pointsInCircle;
    }

    public void setPointsInCircle(long pointsInCircle) {
        this.pointsInCircle = pointsInCircle;
    }

    public long getPointsInSquare() {
        return pointsInSquare;
    }

    public void setPointsInSquare(long pointsInSquare) {
        this.pointsInSquare = pointsInSquare;
    }
}
