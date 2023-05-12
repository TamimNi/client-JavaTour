package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.viewmodel.TourLogListViewModel;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
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

import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class TourLogListView implements Initializable {

    @Autowired
    public TourLogListViewModel tourLogListViewModel;

    @FXML
    public TableView tableView = new TableView<>();
    @FXML
    private VBox dataContainer;

    @Override
    public void initialize(URL location, ResourceBundle rb){
        tableView.setItems(tourLogListViewModel.getTourLogListItems());
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn date = new TableColumn("DATE");
        date.setCellValueFactory(new PropertyValueFactory("dateLog"));
        TableColumn comment = new TableColumn("COMMENT");
        comment.setCellValueFactory(new PropertyValueFactory("commentLog"));
        TableColumn difficulty = new TableColumn("DIFFICULTY");
        difficulty.setCellValueFactory(new PropertyValueFactory("difficultyLog"));
        TableColumn time = new TableColumn("TIME");
        time.setCellValueFactory(new PropertyValueFactory("totalTimeLog"));
        TableColumn rating = new TableColumn("RATING");
        rating.setCellValueFactory(new PropertyValueFactory("ratingLog"));;
        TableColumn tourid = new TableColumn("TOURID");
        tourid.setCellValueFactory(new PropertyValueFactory("tourID"));
        //tableView.getColumns().addAll(id, name);
        tableView.getColumns().addAll(date, comment, difficulty, time, rating);

        tableView.setRowFactory(tv -> {
            TableRow<TourLog> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    TourLog rowItem = row.getItem();
                    System.out.println("clicked but not saved ->"+rowItem+"<-");
                    // do something with the tour object
                   tourLogListViewModel.selectRow(rowItem);
                    System.out.println("chekc out "+tourLogListViewModel.getSelectedTour());

                }

            });
            return row;
        });

        dataContainer.getChildren().add(tableView);
        // System.out.println("items "+tourLogListViewModel.getTourLogListItems());
    }

}
