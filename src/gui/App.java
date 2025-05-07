package gui;

import gui.testDataGui.TestData;
import javafx.application.Application;

public class App {
    public static void main(String[] args) {
        TestData.initTestData();

        Application.launch(StartWindow.class);
    }
}
