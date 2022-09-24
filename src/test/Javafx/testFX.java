package Javafx;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit.ApplicationTest;

public class testFX extends ApplicationTest {
    private Button button;
    private TextField textField;

    @Override
    public void start(Stage stage){
        button = new Button("add Troops");
        textField = new TextField();
        button.setOnAction(actionEvent -> textField.setText("123456"));
        stage.setScene(new Scene(new StackPane(button), 100, 100));
        stage.show();
    }

    @Test
    public void testButton(){
        Assertions.assertThat(button).hasText("add Troops");
    }

    @Test
    public void testAction(){
        clickOn(".button");
        Assertions.assertThat(textField).hasText("123456");
    }
}
