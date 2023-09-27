package com.example.gridstuff;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.XYChart;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.text.DecimalFormat;

public class MainApplication extends Application {
    MainWindowController controller = new MainWindowController();
    int pxWidth = 1000;
    int pxHeight = 600;
    CellularAutomate2 automate;
    Timeline timeline;
    XYChart.Series<Number, Number> chartSeries;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-window.fxml"));
        fxmlLoader.setController(controller);
        Scene scene = new Scene(fxmlLoader.load(), pxWidth, pxHeight);
        stage.setTitle("Hello!");
        stage.setScene(scene);

        timeline = new Timeline(new KeyFrame(Duration.millis(30),
                actionEvent -> {
                    updateAutomate();
                }));
        timeline.setCycleCount(Animation.INDEFINITE); // Run indefinitely
        controller.ASlider.valueProperty().addListener((observable -> updateChart()));
        controller.BSlider.valueProperty().addListener((observable -> updateChart()));
        controller.CSlider.valueProperty().addListener((observable -> updateChart()));
        chartSeries = new XYChart.Series<>();
        controller.chart.getData().add(chartSeries);
        controller.chart.setCreateSymbols(false);

        System.out.println(controller.chart.getXAxis());
        System.out.println(controller.chart.getYAxis());

        scene.setOnKeyPressed(this::onKeyPressed);
        scene.setOnMousePressed(this::onMousePressed);
        int widthTiles = (int) controller.canvas.getWidth() / SimpleTile.size;
        int heightTiles = (int) controller.canvas.getHeight() / SimpleTile.size;
        automate = new CellularAutomate2(widthTiles, heightTiles, 0.01, controller.canvas.getGraphicsContext2D());

        int hoodSize = automate.hood.get().size();
        controller.BSlider.setMax(hoodSize+3);
        controller.BSlider.setMin(-hoodSize-3);
        controller.CSlider.setMax(hoodSize+3);
        controller.CSlider.setMin(-hoodSize-3);
        controller.ASlider.setMax(10.0/hoodSize);
        controller.ASlider.setMin(-10.0/hoodSize);

        automate.draw();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public void updateAutomate() {
        automate.update();
        controller.avgHoodLabel.setText("average hood: "+automate.rules.getAvgHood());
        controller.avgStateLabel.setText("average state: "+automate.rules.getAvgState());
        automate.draw();
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.SPACE) {
            updateAutomate();
        }
        if (keyEvent.getCode() == KeyCode.ENTER) {
            if (timeline.getStatus() == Animation.Status.RUNNING) {
                timeline.stop();
            } else {
                timeline.play();
            }
        }
        if (keyEvent.getCode() == KeyCode.F) {
            automate.randomStatesGlobal();
            updateAutomate();
        }
    }

    public void onMousePressed(MouseEvent event) {
        Canvas canvas = controller.canvas;
        int X = (int) (event.getX() - canvas.getLayoutX()) / SimpleTile.size;
        int Y = (int) (event.getY() - canvas.getLayoutY()) / SimpleTile.size;
        System.out.println("mouse clicked on" + X + " " + Y);
        System.out.println("pos: " + X + ", " + Y);
        if (automate.grid.containsPos(X, Y)) {
            var tile = automate.grid.getTile(X, Y);
            System.out.printf("old state: " + tile.getState());
            tile.setState(new Complex(Math.random(), Math.random()));
            System.out.printf("new state: " + tile.getState());
        }
        automate.draw();
    }

    void updateChart() {
        DecimalFormat format = new DecimalFormat("#.##");
        double a = controller.ASlider.getValue();
        double b = controller.BSlider.getValue();
        double c = controller.CSlider.getValue();
        automate.updateHoodFunction(a, b, c);

        controller.ALabel.setText("a = " + format.format(a));
        controller.BLabel.setText("b = " + format.format(b));
        controller.CLabel.setText("c = " + format.format(c));

        chartSeries.getData().clear();
        int hoodSize = automate.hood.get().size();
        for (double x = -hoodSize; x <= hoodSize; x += 0.3) {
            chartSeries.getData().add(new XYChart.Data<>(x, automate.hoodFunction.apply(x)));
        }
    }
}