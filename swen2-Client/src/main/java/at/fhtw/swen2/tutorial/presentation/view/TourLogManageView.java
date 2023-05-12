package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.ViewManager;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourLogListViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
@Slf4j
public class TourLogManageView implements Initializable {

    public Button darkMode;
    //  @Autowired
   // private TourLogService tourLogService;
    @Autowired
    private SearchView searchView;

    //  @FXML
    //  private Text feedbackText;
    //  @FXML
    //  private TextField nameTextField;

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        //    nameTextField.textProperty().bindBidirectional(newPersonViewModel.nameProperty());
    }

    public void submitButtonAction(ActionEvent event) {
        //  if (nameTextField.getText().isEmpty()) {
        //      feedbackText.setText("nothing entered!");
        //      return;
        //  }

        // newPersonViewModel.addNewPerson();
    }

    @Autowired
    private ViewManager viewManager;

    public void addTourAction(ActionEvent actionEvent) throws IOException {
       if(tourListViewModel.getSelectedTour() != null) {
           try {

               tourLogListViewModel.selectRow(null);
               Stage stage = new Stage();
               Parent root1 = viewManager.load("at/fhtw/swen2/tutorial/presentation/view/NewTourLogWindow.fxml", stage);
               stage.setScene(new Scene(root1));
               stage.show();
           } catch (Exception e) {
               e.printStackTrace();
           }
       }
        //testView.addNewPerson();
        //newPersonViewModel.addNewTour();
    }
    @Autowired
    private TourListViewModel tourListViewModel;
    @Autowired
    private TourLogListViewModel tourLogListViewModel;
    public void delTourAction(ActionEvent actionEvent) throws IOException {
        tourLogListViewModel.deleteRow();
    }

    public void editTourAction(ActionEvent actionEvent) {
        if(tourLogListViewModel.getSelectedTour() != null && tourListViewModel.getSelectedTour() != null) {
            try {
                Stage stage = new Stage();
                Parent root1 = viewManager.load("at/fhtw/swen2/tutorial/presentation/view/NewTourLogWindow.fxml", stage);
                stage.setScene(new Scene(root1));
                stage.show();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
