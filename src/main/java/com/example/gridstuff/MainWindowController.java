package com.example.gridstuff;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MainWindowController {
    @FXML
    public Canvas canvas;
    @FXML
    public LineChart<Number, Number> chart;
    @FXML
    public Label functionLabel;
    @FXML
    public Slider ASlider;
    @FXML
    public Label ALabel;
    @FXML
    public Slider BSlider;
    @FXML
    public Label BLabel;
    @FXML
    public Slider CSlider;
    @FXML
    public Label CLabel;
    @FXML
    public Label avgHoodLabel;
    @FXML
    public Label avgStateLabel;

}