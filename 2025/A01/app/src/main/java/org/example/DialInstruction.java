package org.example;

public class DialInstruction {
    enum Rotation {
        LEFT,
        RIGHT
    }

    public Rotation rotation;
    public int distance;

    DialInstruction(Rotation rotation, int distance) {
        this.rotation = rotation;
        this.distance = distance;
    }

    @Override
    public String toString() {
        return this.rotation.equals(Rotation.LEFT) ? "L" + distance : "R" + distance;
    }
}
