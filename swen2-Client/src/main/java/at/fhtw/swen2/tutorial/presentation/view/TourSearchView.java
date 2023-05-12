package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.viewmodel.SearchViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourSearchViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Slf4j
public class TourSearchView {
    @Autowired
    private TourSearchViewModel toursearchViewModel;

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Label searchLabel;

    @FXML
    private void initialize() {

        searchField.textProperty().bindBidirectional(toursearchViewModel.searchStringProperty());

        // search panel
        searchButton.setText("Search");
        searchButton.setOnAction(event -> loadData());
        searchButton.setStyle("-fx-background-color: slateblue; -fx-text-fill: white;");
        searchField.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                loadData();
            }
        });
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchLabel.setText(newValue);
        });
    }

    private void loadData() {
        toursearchViewModel.search();
    }
}
