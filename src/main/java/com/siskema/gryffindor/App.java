package com.siskema.gryffindor;

import com.siskema.gryffindor.ui.LoginStage;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Mulai aplikasi dengan LoginStage
        new LoginStage().show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}