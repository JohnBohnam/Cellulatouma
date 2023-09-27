package com.example.gridstuff;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public interface Rules<S> {
    void setHood(Hood newHood);
    Hood getHood();
    default double getAvgHood() { return 0;}
    default double getAvgState() { return 0;}
    Map<Vec2Int, S> calculateNextStates(Map<Vec2Int, ? extends Tile<S>> tiles);

    static double sigmoid(double x) {
        return (1.0/(1.0+Math.exp(-x)));
    }

    private static double clip(double x) {
        return Math.min(1, Math.max(0, x));
    }
    Rules<Boolean> GameOfLife = new Rules<>() {
        Hood hood = Hood.hood8;
        @Override
        public void setHood(Hood newHood) {
            hood = newHood;
        }

        @Override
        public Hood getHood() {
            return hood;
        }

        @Override
        public Map<Vec2Int, Boolean> calculateNextStates(Map<Vec2Int, ? extends Tile<Boolean>> tiles) {
            Map<Vec2Int, Boolean> nextStates = new HashMap<>();
            for (var tile : tiles.values()) {
                int sum = 0;
                for (Vec2Int dir : hood.get()) {
                    Vec2Int currPos = tile.getPos().add(dir);
                    if (tiles.containsKey(currPos) && tiles.get(currPos).getState()) {
                        sum++;
                    }
                }
                if (tile.getState() && (sum == 2 || sum == 3)) nextStates.put(tile.getPos(), Boolean.TRUE);
                else if (!tile.getState() && (sum == 3)) nextStates.put(tile.getPos(), Boolean.TRUE);
                else nextStates.put(tile.getPos(), Boolean.FALSE);
            }
            return nextStates;
        }
    };

    static Rules<Double> weirdLife(Function<Double, Double> hoodToState) {
        return new Rules<>() {
            Hood hood;
            double avgHood = 0;
            double avgState = 0;
            @Override
            public void setHood(Hood newHood) {
                this.hood = newHood;
            }

            @Override
            public Hood getHood() {
                return hood;
            }

            @Override
            public Map<Vec2Int, Double> calculateNextStates(Map<Vec2Int, ? extends Tile<Double>> tiles) {
                Map<Vec2Int, Double> res = new HashMap<>();
                int nTiles =0;
                double sumIn = 0;
                double sumState = 0;
                for (var t : tiles.values()) {
                    double nbh = 0;
                    for (Vec2Int dir : hood.get()) {
                        Vec2Int pos = t.getPos().add(dir);
                        if(!tiles.containsKey(pos))
                            continue;
                        double inc = tiles.get(pos).getState();
                        if (dir.abs()!=0)
                            inc /= dir.abs()*dir.abs();
                        nbh += inc;
                    }
                    sumIn += nbh;
                    double newState = hoodToState.apply(nbh);
                    sumState += t.getState();
                    nTiles++;
                    res.put(t.getPos(), newState);
                }
                avgHood = sumIn/nTiles;
                avgState = sumState/nTiles;
                return res;
            }

            @Override
            public double getAvgHood() {
                return avgHood;
            }

            @Override
            public double getAvgState() {
                return avgState;
            }
        };
    }

    static Rules<Complex> inertiaLife(double speed, Function<Double, Double> hoodToState){
        return new Rules<Complex>() {
            Hood hood = Hood.hoodn(4);
            double avgHood =0;
            double avgState =0;
            @Override
            public void setHood(Hood newHood) {
                this.hood = newHood;
            }

            @Override
            public Hood getHood() {
                return hood;
            }

            @Override
            public Map<Vec2Int, Complex> calculateNextStates(Map<Vec2Int, ? extends Tile<Complex>> tiles) {
                Map<Vec2Int, Complex> res = new HashMap<>();
                double stateSum = 0;
                double hoodSum = 0;
                for (var t : tiles.values()) {
                    stateSum+= t.getState().real();
                    double nbh = 0;
                    for (var h : hood.get()){
                        Vec2Int pos = t.getPos().add(h);
                        if (tiles.containsKey(pos)){
                            double inc = tiles.get(pos).getState().real();
                            if(h.abs()!=0) inc /= h.abs()*h.abs();
                            nbh += inc;
                        }
                    }
                    hoodSum += nbh;
                    double oldX = t.getState().real();
                    double oldV = t.getState().imaginary();
                    double newX = (oldX + oldV*speed);
                    double newV = clip(oldV + hoodToState.apply(nbh));
                    res.put(t.getPos(), new Complex(newX, newV));
                }
                avgHood = hoodSum/tiles.size();
                avgState = stateSum/tiles.size();
                return res;
            }

            @Override
            public double getAvgHood() {
                return avgHood;
            }
            @Override
            public double getAvgState() {
                return avgState;
            }
        };
    }
}
