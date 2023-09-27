package com.example.gridstuff;

public record Vec2Int(int x, int y) {

    public Vec2Int add(Vec2Int other) {
        return new Vec2Int(this.x+other.x, this.y+other.y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public Double abs() {
        return Math.sqrt(x*x+y*y);
    }
}
