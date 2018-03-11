package sample.mainController;


import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import sample.classes.NewClassDialogController;
import sample.datamodel.ClassGroup;

import java.util.Optional;

public class ClassGroupController {

    public ClassGroup showCreateNewClassDialog(BorderPane borderPane) {
        ClassGroup newClassGroup = null;
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        dialog.setTitle("Create new class");
        FXMLLoader fxmlLoader = AlertAndFXMLLoaderClass.fxmlLoader(dialog,
                "sample\\classes\\newClassDialog.fxml",
                "Couldn't load the dialog:");

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            NewClassDialogController newClassDialogController = fxmlLoader.getController();
            newClassGroup = newClassDialogController.createNewClass();
            while (newClassGroup.getName().equals("0") &&
                    (newClassGroup.getStudyYear().getStartOfYear().getYear() == 1) &&
                    (newClassGroup.getStudyYear().getStartOfYear().getMonthValue() == 1) &&
                    (newClassGroup.getStudyYear().getStartOfYear().getDayOfMonth() == 1)) {
                Alert alert = AlertAndFXMLLoaderClass.alertTypeError("Missing information",
                        null, "Please fill out all " +
                        " the required information marked with *");
                alert.showAndWait();
                Optional<ButtonType> anotherResult = dialog.showAndWait();
                if (anotherResult.isPresent() && anotherResult.get() == ButtonType.OK) {
                    newClassGroup = newClassDialogController.createNewClass();
                } else if (anotherResult.isPresent() && anotherResult.get() == ButtonType.CANCEL) {
                    break;
                }
            }

            if (newClassGroup.getName().equals("0") &&
                    (newClassGroup.getStudyYear().getStartOfYear().getYear() == 1) &&
                    (newClassGroup.getStudyYear().getStartOfYear().getMonthValue() == 1) &&
                    (newClassGroup.getStudyYear().getStartOfYear().getDayOfMonth() == 1)) {
                return null;
            }
        }
        return newClassGroup;
    }

    public ClassGroup showEditClassDialog(BorderPane borderPane, ClassGroup classGroup) {
        ClassGroup newClassGroup = null;
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(borderPane.getScene().getWindow());
        dialog.setTitle("Edit class");
        FXMLLoader fxmlLoader = AlertAndFXMLLoaderClass.fxmlLoader(dialog,
                "sample\\classes\\newClassDialog.fxml",
                "Couldn't load the dialog");

        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        NewClassDialogController newClassDialogController = fxmlLoader.getController();
        newClassDialogController.editClass(classGroup);
        Optional<ButtonType> result = dialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            newClassGroup = newClassDialogController.updateClass(classGroup);
            while (newClassGroup.getName().equals("0") &&
                    (newClassGroup.getStudyYear().getStartOfYear().getYear() == 1) &&
                    (newClassGroup.getStudyYear().getStartOfYear().getMonthValue() == 1) &&
                    (newClassGroup.getStudyYear().getStartOfYear().getDayOfMonth() == 1)) {
                Alert alert = AlertAndFXMLLoaderClass.alertTypeError("Missing information",
                        null, "Please fill out all " +
                        " the required information marked with *");
                alert.showAndWait();
                Optional<ButtonType> anotherResult = dialog.showAndWait();
                if (anotherResult.isPresent() && anotherResult.get() == ButtonType.OK) {
                    newClassGroup = newClassDialogController.updateClass(newClassGroup);
                } else if (anotherResult.isPresent() && anotherResult.get() == ButtonType.CANCEL) {
                    break;
                }
            }

            if (newClassGroup.getName().equals("0") &&
                    (newClassGroup.getStudyYear().getStartOfYear().getYear() == 1) &&
                    (newClassGroup.getStudyYear().getStartOfYear().getMonthValue() == 1) &&
                    (newClassGroup.getStudyYear().getStartOfYear().getDayOfMonth() == 1)) {
                return null;
            }
        }
        return newClassGroup;
    }

    public ClassGroup deleteClassDialog(ClassGroup classGroup) {
        Alert alert = AlertAndFXMLLoaderClass.alertTypeConfirmation("Removing class from the list",
                null, "Are you sure you want to remove " + classGroup.getName() +
                        " from the list?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            return classGroup;
        }
        return null;
    }
}