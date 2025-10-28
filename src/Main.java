import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application {

    int cookiesClicked = 0;
    Color bgColor;
    Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FileInputStream input = new FileInputStream("src/cookie_PNG13656.png");
        Image image = new Image(input);
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(100);

        Label cookieData = new Label("Cookies Clicked: " + cookiesClicked);
        cookieData.setFont(new Font("Ariel", 24));
        Button cookieButton = new Button("", imageView);
        cookieButton.setOnAction(actionEvent ->  {
            cookiesClicked++;
            cookieData.setText("Cookies Clicked: " + cookiesClicked);
        });

        Accordion menu = new Accordion();

        Button resetButton = new Button("Reset Cookies");
        resetButton.setOnAction(actionEvent -> {
            cookiesClicked = 0;
            cookieData.setText("Cookies Clicked: " + cookiesClicked);
        });

        ColorPicker bgColorPicker = new ColorPicker();
        bgColor = bgColorPicker.getValue();

        VBox settingPane = new VBox(resetButton, bgColorPicker);

        TitledPane pane1 = new TitledPane("Cookie Type" , new Label("Show all cookies available"));
        TitledPane pane2 = new TitledPane("Clicker Finger"  , new Label("Show all pointer available"));
        TitledPane pane3 = new TitledPane("Setting", settingPane);

        menu.getPanes().add(pane1);
        menu.getPanes().add(pane2);
        menu.getPanes().add(pane3);

        VBox vbox = new VBox(cookieData, cookieButton);
        HBox hbox = new HBox(menu, vbox);

        bgColorPicker.setOnAction(actionEvent -> {
            primaryStage.setTitle("Cookie Clicker");
            scene = new Scene(hbox, 1000, 500, bgColor);
            primaryStage.setScene(scene);

            primaryStage.show();
        });

        primaryStage.setTitle("Cookie Clicker");
        scene = new Scene(hbox, 1000, 500, bgColor);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}