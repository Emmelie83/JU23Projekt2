package org.example.demo;

import crud.Read;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import mainclass.Main;
import mainclass.Menu;

import java.io.IOException;
import java.util.List;

public class SecondMenuController {

    public Button b2;
    @FXML
    private TextArea textArea;

    public void initialize() {
        showMenu();
    }

    public void showMenu(){
        textArea.setText(Menu.readTwo());
    }


    public void newMenu(MouseEvent mouseEvent) throws IOException {
        Button button =(Button) mouseEvent.getSource();

        switch (button.getId()){
            case "b1" -> Platform.runLater(()->{
                showb1(Read.showCoursesFX());
            });
            case "b2" -> System.out.println("2");
            case "Back" -> returnToMain();
        }
    }

    private void showb1(List<String> strings) {
        StringBuilder str = new StringBuilder();
        strings.forEach((e)-> str.append(e+"\n"));
        textArea.setText(String.valueOf(str));
    }

    private void returnToMain() throws IOException {
        MainMenuController.loadMain();
    }


}
