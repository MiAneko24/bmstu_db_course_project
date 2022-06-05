package com.fuzzy.fuzzyexpertsystemstool;

import com.fuzzy.fuzzyexpertsystemstool.database.DataController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FuzzyExpertSystemApplication extends Application {
    private final DataController worker = new DataController();

    @Override
    public void start(Stage stage) throws IOException {
        test();
        worker.connect();
        FXMLLoader fxmlLoader = new FXMLLoader(FuzzyExpertSystemApplication.class.getResource("tryview.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        InterfaceController controller = fxmlLoader.getController();
        controller.setWorker(worker);
        stage.setResizable(false);
        stage.setTitle("FuzzyExpertSystemTool");
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