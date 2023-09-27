package com.example.gridstuff;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public interface Hood{
    Collection<Vec2Int> get();

    Hood hood8 = new Hood() {
        final List<Vec2Int> hood = List.of(
                new Vec2Int(-1, -1),
                new Vec2Int(-1, 0),
                new Vec2Int(-1, 1),
                new Vec2Int(0, -1),
                new Vec2Int(0, 1),
                new Vec2Int(1, -1),
                new Vec2Int(1, 0),
                new Vec2Int(1, 1));
        @Override
        public Collection<Vec2Int> get() {
            return hood;
        }
    };

    static Hood hoodn(int n) {
        List<Vec2Int> res = new ArrayList<>();
        for (int x=-n; x<=n; x++)
            for (int y=-n; y<=n; y++)
                res.add(new Vec2Int(x, y));
        return new Hood() {
            @Override
            public Collection<Vec2Int> get() {
                return res;
            }
        };
    }
}
