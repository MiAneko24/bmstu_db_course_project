package com.fuzzy.fuzzyexpertsystemstool;

import com.fuzzy.fuzzyexpertsystemstool.database.DatabaseWorker;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private final DatabaseWorker worker = new DatabaseWorker();

    @Override
    public void start(Stage stage) throws IOException {
        test();
        worker.connect();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("tryview.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        InterfaceController controller = fxmlLoader.getController();
        controller.setWorker(worker);
        stage.setResizable(false);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void stop() throws Exception {
        worker.disconnect();
        super.stop();
    }

    public void test() {
    }

    public static void main(String[] args) {
        launch();
    }
}