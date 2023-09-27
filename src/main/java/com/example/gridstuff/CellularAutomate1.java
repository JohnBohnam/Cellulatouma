package com.example.gridstuff;

import javafx.scene.canvas.GraphicsContext;

import java.util.function.Function;

public class CellularAutomate1 {
    Grid<Double> grid;
    Rules<Double> rules;
    double alpha=0, beta=0, gamma=0;
    Function<Double, Double> hoodFunction;
    Hood hood = Hood.hoodn(5);
    static double sigmoid(double x) {
        return (1.0/(1.0+Math.exp(-x)));
    }

    CellularAutomate1(int width, int height, double speed, GraphicsContext gc) {
        //grid = new GridSimple<>(width, height, ColorConverter.complexGrayConverter, new Complex(0.0 , 0.0), gc);
        grid = new GridSimple<>(width, height, ColorConverter.continuousGrayConverter, 0.0, gc);
        updateHoodFunction(0.0, 0.0, 0.0);
        rules = Rules.weirdLife(hoodFunction);
        rules.setHood(hood);
    }
    void update() {
        grid.setStates(rules.calculateNextStates(grid.getTilesMap()));
    }

    void randomAlive(double probAlive) {
        for (var t : grid.getTilesMap().values()) {
            if (Math.random()>=probAlive)
                //t.setState(new Complex(Math.random(), 0));
                t.setState(1.0);
        }
    }

    void randomStatesGlobal() {
        for (var t : grid.getTilesMap().values()) {
//            t.setState(new Complex(Math.random(), Math.random()-0.5));
            t.setState(Math.random());
        }
    }

    void draw() {
        grid.draw();
    }

    void updateHoodFunction(double alpha, double beta, double gamma) {
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        hoodFunction = x -> sigmoid(-alpha * (x - beta) * (x - gamma));
        rules = Rules.weirdLife(hoodFunction);
        rules.setHood(hood);
    }
}
