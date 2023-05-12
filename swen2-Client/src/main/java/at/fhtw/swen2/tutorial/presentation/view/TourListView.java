package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.ViewManager;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourLogListViewModel;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.toServer.MapQuestImage;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class TourListView implements Initializable {

    @Autowired
    public TourListViewModel tourListViewModel;

    @FXML
    public TableView tableView = new TableView<>();

    @FXML
    private VBox dataContainer;

    @Autowired
    TourLogListViewModel tourLogListViewModel;
    @Autowired
    MapQuestImage mapQuestImage;

    @Autowired
    private ViewManager viewManager;
    @Override
    public void initialize(URL location, ResourceBundle rb) {
        tableView.setItems(tourListViewModel.getTourListItemsListItems());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        //TableColumn id = new TableColumn("ID");
        //id.setCellValueFactory(new PropertyValueFactory("id"));
        TableColumn name = new TableColumn("TOURS");
        name.setCellValueFactory(new PropertyValueFactory("name"));
        //tableView.getColumns().addAll(id, name);
        tableView.getColumns().addAll(name);

        tableView.setRowFactory(tv -> {
            TableRow<Tour> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    Tour rowItem = row.getItem();
                    // do something with the tour object
                    tourListViewModel.selectRow(rowItem);

                    try {
                        tourLogListViewModel.getLogList(String.valueOf(rowItem.getId()));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Tour rowItem = row.getItem();
                    // do something with the tour object
                    tourListViewModel.showItem(rowItem);
                }
            });
            return row;
        });

        dataContainer.getChildren().add(tableView);
        try {
            tourListViewModel.initList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("items " + tourListViewModel.getTourListItemsListItems());
    }

}
