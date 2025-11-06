import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileInputStream;

public class Main extends Application {

    int cookiesClicked = 0;
    Color bgColor;
    Scene scene;
    int multiplier = 1;

    @Override
    public void start(Stage primaryStage) throws Exception {

        FileInputStream chocoInput = new FileInputStream("src/cookie_PNG13656.png");
        Image chocoImage = new Image(chocoInput);
        ImageView chocolateChipImage = new ImageView(chocoImage);
        chocolateChipImage.setPreserveRatio(true);
        chocolateChipImage.setFitHeight(100);

        FileInputStream redInput = new FileInputStream("src/redVelvet.png");
        Image redImage = new Image(redInput);
        ImageView redVelvetImage = new ImageView(redImage);
        redVelvetImage.setPreserveRatio(true);
        redVelvetImage.setFitHeight(100);

        Text welcomeMsg = new Text("Welcome to Cookie Clicker!");
        Label cookieData = new Label("Cookies Clicked: " + cookiesClicked);
        cookieData.setFont(new Font("Ariel", 24));
        Label multiplierData = new Label("Multiplier: " + multiplier + "x");
        multiplierData.setFont(new Font("Ariel", 24));

        ProgressBar progressBar = new ProgressBar(0);

        progressBar.setProgress(0);

        Button cookieButton = new Button("", chocolateChipImage);
        cookieButton.setOnAction(actionEvent ->  {
            // probability to gain more or lose cookies, spinner
            cookiesClicked += multiplier;
            if (cookiesClicked < 100) {
                progressBar.setProgress((double) cookiesClicked /100);
            }
            else if (cookiesClicked > 100 && cookiesClicked < 500) {
                multiplier = 2;
                progressBar.setProgress((double) cookiesClicked /500);
            } else if (cookiesClicked > 500 && cookiesClicked < 1500) {
                multiplier = 5;
                progressBar.setProgress((double) cookiesClicked /1500);
            } else {
                multiplier = 10;
                progressBar.setProgress(1);
            }
            multiplierData.setText("Multiplier: " + multiplier + "x");
            cookieData.setText("Cookies Clicked: " + cookiesClicked);
        });

        Accordion menu = new Accordion();

        Button resetButton = new Button("Reset Cookies");
        resetButton.setOnAction(actionEvent -> {
            cookiesClicked = 0;
            multiplier = 0;
            cookieData.setText("Cookies Clicked: " + cookiesClicked);
        });

        ColorPicker bgColorPicker = new ColorPicker();
        RadioButton chocolateChip = new RadioButton("Chocolate Chip Cookie");
        chocolateChip.setSelected(true);
        RadioButton redVelvet = new RadioButton("Red Velvet Cookie");

        chocolateChip.setOnAction(actionEvent -> {
            if (chocolateChip.isSelected()) {
                redVelvet.setSelected(false);
                cookieButton.setGraphic(chocolateChipImage);
            }
        });

        redVelvet.setOnAction(actionEvent -> {
            if (redVelvet.isSelected()) {
                chocolateChip.setSelected(false);
                cookieButton.setGraphic(redVelvetImage);
            }
        });

        Slider cookieSize = new Slider(0, 500, 100);
        cookieSize.setOnScroll(scrollEvent -> {
            chocolateChipImage.setFitHeight(cookieSize.getValue());
            redVelvetImage.setFitHeight(cookieSize.getValue());
        });

        CheckBox autoclicker = new CheckBox("Auto Clicker");
        autoclicker.setOnAction(actionEvent -> {
            AutoClicker clicker = new AutoClicker(autoclicker, progressBar, cookieData, multiplierData, multiplier, cookiesClicked);
            Thread autoclicking = new Thread(clicker);
            if (autoclicker.isSelected()) {
                autoclicking.start();
            }
            else{
                autoclicking.interrupt();
                multiplier = clicker.multiplier;
                cookiesClicked = clicker.cookiesClicked;
            }
        });

        Text goalMsg = new Text("Cookie Clicking Goal:");
        TextArea goalsetting = new TextArea();
        goalsetting.setPrefSize(500, 100);

        VBox cookiePane = new VBox(chocolateChip, redVelvet, cookieSize);

        VBox settingPane = new VBox(autoclicker, bgColorPicker, resetButton);

        TitledPane pane1 = new TitledPane("Cookie Type" , cookiePane);
        TitledPane pane2 = new TitledPane("Setting", settingPane);

        menu.getPanes().add(pane1);
        menu.getPanes().add(pane2);

        VBox vbox = new VBox(welcomeMsg, cookieData, cookieButton, multiplierData, progressBar, goalMsg, goalsetting);
        HBox hbox = new HBox(menu, vbox);

        bgColorPicker.setOnAction(actionEvent -> {
            bgColor = bgColorPicker.getValue();
            BackgroundFill backgroundFill = new BackgroundFill(bgColor, CornerRadii.EMPTY, Insets.EMPTY);
            Background background = new Background(backgroundFill);
            hbox.setBackground(background);
        });

        primaryStage.setTitle("Cookie Clicker");
        scene = new Scene(hbox, 1000, 500);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}