package com.example.gridstuff;

import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public interface Tile<T> {
    T getState();
    void setState(T newState);
    Vec2Int getPos();
    void draw(GraphicsContext gc, Color c);
}
