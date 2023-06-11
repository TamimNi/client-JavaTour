package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.viewmodel.NewTourWindowViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.toServer.ToServer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
public class NewTourWindow implements Initializable {
    @FXML
    public TextField nameTextField;
    @FXML
    public TextField tourDescriptionTextField;
    @FXML
    public TextField fromTextField;
    @FXML
    public TextField toTextField;
    @FXML
    public TextField transportTypeTextField;
    @FXML
    public Button submitTourButton;
    @FXML
    public Label NewTourWindowTitleLabel;
    @Autowired
    private NewTourWindowViewModel testViewModel;

    private static final Logger logger = LogManager.getLogger(NewTourWindow.class);
    @Override
    public void initialize(URL location, ResourceBundle rb) {
        nameTextField.textProperty().bindBidirectional(testViewModel.nameProperty());
        tourDescriptionTextField.textProperty().bindBidirectional(testViewModel.tourDescriptionProperty());
        fromTextField.textProperty().bindBidirectional(testViewModel.fromProperty());
        toTextField.textProperty().bindBidirectional(testViewModel.toProperty());
        transportTypeTextField.textProperty().bindBidirectional(testViewModel.transportTypeProperty());
        if(tourListViewModel.getSelectedTour() != null){
            NewTourWindowTitleLabel.setText("Edit Tour");
            Tour tour = tourListViewModel.getSelectedTour();
            nameTextField.setText(tour.getName());
            tourDescriptionTextField.setText(tour.getTourDescription());
            fromTextField.setText(tour.getFrom());
            toTextField.setText(tour.getTo());
            transportTypeTextField.setText(tour.getTransportType());
        }else{
            NewTourWindowTitleLabel.setText("New Tour");
            nameTextField.setText("");
            tourDescriptionTextField.setText("");
            fromTextField.setText("");
            toTextField.setText("");
            transportTypeTextField.setText("");
        }
    }
    @Autowired
    TourListViewModel tourListViewModel;
    @Autowired
    ToServer toServer;
    public void submitTourButtonAction(ActionEvent actionEvent) throws IOException {
       if(validateForm()) {

           if (tourListViewModel.getSelectedTour() == null) {
               testViewModel.addTest();
               logger.debug("new");
           } else {
               testViewModel.editTest();
               logger.debug("edit");
           }
           Stage stage = (Stage) nameTextField.getScene().getWindow();
           stage.close();
       }
    }
    private boolean validateForm() {
        // Check if all required fields are filled in
        boolean isValid = nameTextField.getText().length() > 2
                && tourDescriptionTextField.getText().length() > 2
                && fromTextField.getText().length() > 2
                && toTextField.getText().length() > 2
                && transportTypeTextField.getText().length() > 2;


        // Enable/disable submit button based on validation result
        return isValid;
    }
}
