package com.example.gridstuff;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SimpleTile<T> implements Tile<T>{
    public static final int size = 2;
    T state;
    Vec2Int pos;
    SimpleTile(Vec2Int pos, T state) {
        this.pos = pos;
        this.state = state;
    }
    @Override
    public T getState() {
        return state;
    }

    @Override
    public void setState(T newState) {
        state = newState;
    }

    @Override
    public Vec2Int getPos() {
        return pos;
    }

    @Override
    public void draw(GraphicsContext gc, Color c) {
        gc.setFill(c);
        gc.fillRect(pos.x()*size, pos.y()*size, size, size);
    }
}
