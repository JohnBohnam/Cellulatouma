package com.example.gridstuff;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class GridSimple<S> implements Grid<S> {
    Map<Vec2Int, Tile<S>> tiles;
    Function<? super S, ? extends Color> converter;
    GraphicsContext gc;
    int width, height;
    GridSimple(int width, int height, Function <? super S, ? extends Color> colorConverter, S startValue, GraphicsContext gc) {
        this.width = width;
        this.height = height;
        this.converter = colorConverter;
        this.gc = gc;
        tiles = new HashMap<>();
        for (int i=0; i<width; i++) {
            for (int j=0; j<height; j++) {
                Vec2Int pos = new Vec2Int(i, j);
                tiles.put(pos, new SimpleTile<>(pos, startValue));
            }
        }
    }

    @Override
    public void draw(){
        for (var t : tiles.values()) {
            t.draw(gc, converter.apply(t.getState()));
        }
    }

    @Override
    public void setStates(Map<Vec2Int, ? extends S> newStates) {
        for (var entry : newStates.entrySet()) {
            tiles.get(entry.getKey()).setState(entry.getValue());
        }
    }

    @Override
    public Map<Vec2Int, Tile<S>> getTilesMap() {
        return tiles;
    }

    @Override
    public Collection<Tile<S>> getTiles() {
        return tiles.values();
    }

    @Override
    public Tile<S> getTile(int x, int y) {
        return tiles.get(new Vec2Int(x, y));
    }

    @Override
    public Tile<S> getTile(Vec2Int pos) {
        return tiles.get(pos);
    }

    @Override
    public boolean containsPos(Vec2Int pos) {
        return tiles.containsKey(pos);
    }

    @Override
    public boolean containsPos(int x, int y) {
        return tiles.containsKey(new Vec2Int(x, y));
    }
}
