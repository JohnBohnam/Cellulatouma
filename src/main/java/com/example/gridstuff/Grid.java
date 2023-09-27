package com.example.gridstuff;

import java.util.Collection;
import java.util.Map;

public interface Grid<S> {
    void draw();

    void setStates(Map<Vec2Int, ? extends S> newStates);

    Map<Vec2Int, Tile<S>> getTilesMap();

    Collection<Tile<S>> getTiles();

    Tile<S> getTile(int x, int y);

    Tile<S> getTile(Vec2Int pos);

    boolean containsPos(Vec2Int pos);

    boolean containsPos(int x, int y);
}
