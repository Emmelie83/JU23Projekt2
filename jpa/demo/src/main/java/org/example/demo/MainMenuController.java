package org.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import mainclass.Menu;

import java.io.IOException;


public class MainMenuController {

    public Button b2;
    @FXML
    private TextArea textArea;

    private static Stage stage;

    public void initialize() {
        showMenu();
    }

    public void showMenu(){
        textArea.setText(Menu.showMainTwo());
    }


    public void newMenu(MouseEvent mouseEvent) throws IOException {
        Button button =(Button) mouseEvent.getSource();

        switch (button.getId()){
            case "b1" -> System.out.println("1");
            case "b2" -> loadTwo();
        }
    }

    private void loadTwo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("menuTwo.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Hello!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void loadMain() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);
        stage.setTitle("Hello!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}