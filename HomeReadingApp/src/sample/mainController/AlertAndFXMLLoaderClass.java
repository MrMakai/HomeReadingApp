package sample.mainController;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.io.IOException;

class AlertAndFXMLLoaderClass {

    public static Alert alertTypeError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    public static Alert alertTypeInformation(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    public static Alert alertTypeConfirmation(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    public static FXMLLoader fxmlLoader(Dialog<ButtonType> dialog, String path, String exceptionMessage) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(AlertAndFXMLLoaderClass.class.getClassLoader().getResource(path));

        try {
            dialog.getDialogPane().setContent(fxmlLoader.load());
        } catch (IOException e) {
            System.out.println(exceptionMessage + " " + e.getMessage());
            e.printStackTrace();

        }
        return fxmlLoader;
    }
}
