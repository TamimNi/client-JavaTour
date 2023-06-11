package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.viewmodel.NewTourLogWindowViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourLogListViewModel;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
@Component
@Scope("prototype")
@Slf4j
public class NewTourLogWindow implements Initializable {
    @FXML
    public TextField dateLogTextField;
    @FXML
    public TextField commentLogTextField;
    @FXML
    public TextField difficultyLogTextField;
    @FXML
    public TextField totalTimeLogTextField;
    @FXML
    public TextField ratingLogTextField;
    @FXML
    public Button submitTourLogButton;
    @FXML
    public Label NewTourWindowTitleLabel;
    @Autowired
    private NewTourLogWindowViewModel newTourLogWindowViewModel;


    public NewTourLogWindow() {
        //newTourWindowViewModel = new NewTourWindowViewModel();
    }
    @Autowired
    private TourLogListViewModel tourLogListViewModel;
    private boolean editLog = false;

    private static final Logger logger = LogManager.getLogger(NewTourLogWindow.class);
    @Override
    public void initialize(URL location, ResourceBundle rb) {

        dateLogTextField.textProperty().bindBidirectional(newTourLogWindowViewModel.dateLogProperty());
        commentLogTextField.textProperty().bindBidirectional(newTourLogWindowViewModel.commentLogProperty());
        difficultyLogTextField.textProperty().bindBidirectional(newTourLogWindowViewModel.difficultyLogProperty());
        totalTimeLogTextField.textProperty().bindBidirectional(newTourLogWindowViewModel.totalTimeLogProperty(), new NumberStringConverter());
        ratingLogTextField.textProperty().bindBidirectional(newTourLogWindowViewModel.ratingLogProperty(), new NumberStringConverter());

        if(tourLogListViewModel.getSelectedTour() != null){
            logger.info("edit tourlog");
            NewTourWindowTitleLabel.setText("Edit Tour");
            editLog = true;
            TourLog tourLog = tourLogListViewModel.getSelectedTour();
            dateLogTextField.setText(tourLog.getDateLog());
            commentLogTextField.setText(tourLog.getCommentLog());
            difficultyLogTextField.setText(tourLog.getDifficultyLog());
            totalTimeLogTextField.setText(String.valueOf(tourLog.getTotalTimeLog()));
            ratingLogTextField.setText(String.valueOf(tourLog.getRatingLog()));
        }else{
            logger.info("new tourlog");
            NewTourWindowTitleLabel.setText("New Tour");
            editLog = false;
            dateLogTextField.setText("");
            commentLogTextField.setText("");
            difficultyLogTextField.setText("");
            totalTimeLogTextField.setText("");
            ratingLogTextField.setText("");
        }

    }


    @Autowired
    TourListViewModel tourListViewModel;
    public void submitTourLogButtonAction(ActionEvent actionEvent) throws IOException {
       // newTourLogWindowViewModel.createTourLog();

        // Get the stage from the submitTourButton and close it
        if(validateForm()) {
            if (editLog == false) {
                newTourLogWindowViewModel.createTourLog();
            } else {
                newTourLogWindowViewModel.editTest();
            }
            Stage stage = (Stage) submitTourLogButton.getScene().getWindow();
            stage.close();
        }
    }

    private boolean validateForm() {
        // Check if all required fields are filled in
        boolean isValid = false;
        if(difficultyLogTextField.getText().matches("\\d+") && totalTimeLogTextField.getText().matches("\\d+") &&
                ratingLogTextField.getText().matches("\\d+")) {

            isValid = commentLogTextField.getText().length() > 2
                    && Long.parseLong(difficultyLogTextField.getText()) > 0
                    && Long.parseLong(difficultyLogTextField.getText()) <= 100
                    && Long.parseLong(totalTimeLogTextField.getText()) > 0
                    && Long.parseLong(totalTimeLogTextField.getText()) < 10000000
                    && Long.parseLong(ratingLogTextField.getText()) > 0
                    && Long.parseLong(ratingLogTextField.getText()) < 10;

        }else{
            logger.info("only valid numbers accpeted");
        }

            // Enable/disable submit button based on validation result
            return isValid;
    }
}
