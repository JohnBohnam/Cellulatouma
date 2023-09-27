package com.example.gridstuff;

import javafx.scene.canvas.GraphicsContext;

import java.util.function.Function;

public class CellularAutomate2 {

    Grid<Complex> grid;
    Rules<Complex> rules;
    double alpha=0, beta=0, gamma=0;
    Function<Double, Double> hoodFunction;
    Hood hood = Hood.hood8;
    double speed = 0.05;

    CellularAutomate2(int width, int height, double speed, GraphicsContext gc) {
        grid = new GridSimple<>(width, height, ColorConverter.complexRainbowConverter, new Complex(0.0 , 0.0), gc);
        updateHoodFunction(0.0, 0.0, 0.0);
        rules = Rules.inertiaLife(speed, hoodFunction);
        rules.setHood(hood);
    }
    void update() {
        grid.setStates(rules.calculateNextStates(grid.getTilesMap()));
    }

    void randomAlive(double probAlive) {
        for (var t : grid.getTilesMap().values()) {
            if (Math.random()>=probAlive)
                t.setState(new Complex(Math.random(), 0));
        }
    }

    void randomStatesGlobal() {
        for (var t : grid.getTilesMap().values()) {
            t.setState(new Complex(Math.random(), Math.random()));
        }
    }

    void draw() {
        grid.draw();
    }

    void updateHoodFunction(double alpha, double beta, double gamma) {
        this.alpha = alpha;
        this.beta = beta;
        this.gamma = gamma;
        hoodFunction = x -> alpha + (x * beta) + Math.sin(x) * gamma;
        rules = Rules.inertiaLife(speed, hoodFunction);
        rules.setHood(hood);
    }
}
