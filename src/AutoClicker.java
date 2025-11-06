import javafx.application.Platform;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class AutoClicker implements Runnable{
    CheckBox autoclicker;
    ProgressBar progressBar;
    Label cookieData;
    Label multiplierData;
    int multiplier;
    int cookiesClicked;

    public AutoClicker(CheckBox autoclicker, ProgressBar progressBar, Label cookieData, Label multiplierData, int multiplier, int cookiesClicked) {
        this.autoclicker = autoclicker;
        this.progressBar = progressBar;
        this.cookieData = cookieData;
        this.multiplierData = multiplierData;
        this.multiplier = multiplier;
        this.cookiesClicked = cookiesClicked;
    }

    public void click () {
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
    }


    public void run() {
        while (autoclicker.isSelected()) {
            Platform.runLater(this::click);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
