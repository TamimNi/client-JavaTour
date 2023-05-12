package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.toServer.ToServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TourListViewModel {


    private List<Tour> masterData = new ArrayList<>();
    private ObservableList<Tour> tourListItems = FXCollections.observableArrayList();

    public ObservableList<Tour> getTourListItemsListItems() {
        return tourListItems;
    }

    public void addItem(Tour tour) {
        tourListItems.add(tour);
        masterData.add(tour);
    }

    @Autowired
    private TourLogListViewModel tourLogListViewModel;

    public void delItem(Tour tour, boolean delLogs) {
        //tourListItems.remove(test);
        tourListItems.removeIf(t -> t.getId().equals(tour.getId()));
        masterData.removeIf(t -> t.getId().equals(tour.getId()));
        //  if(delLogs == true){tourLogListViewModel.removeLogsOfTheTour(test.getId());}
        tourLogListViewModel.delItemByTourId(tour.getId());
        resetSelectedTour();
    }

    public void clearItems() {
        tourListItems.clear();
    }

    public Tour getSelectedTour() {
        return selectedTour;
    }

    public void resetSelectedTour() {
        this.selectedTour = null;
    }

    private Tour selectedTour = null;

    public void selectRow(Tour tour) {
        this.selectedTour = tour;
        System.out.println(selectedTour);
        // create and show the notification box
    }

    public void deleteRow() throws IOException {
        if (selectedTour != null) {
            System.out.println("del");
            toServer.deleteReq("/api/tour",selectedTour.getId());
            delItem(selectedTour, true);
            this.selectedTour = null;
        } else
            System.out.println("no select");
    }

    @Autowired
    ToServer toServer;

    public void initList() throws IOException {
        List<Tour> tours = toServer.getReq("/api/tour", new ParameterizedTypeReference<List<Tour>>() {
        });
        if (tours != null) {
            masterData.addAll(tours);
            tourListItems.setAll(tours);
        }
    }

    public void filterList(String searchText) {
        Task<List<Tour>> task = new Task<>() {
            @Override
            protected List<Tour> call() throws Exception {
                updateMessage("Loading data");
                System.out.println("still->"+searchText);
                return masterData
                        .stream()
                        .filter(value -> {
                            // filter by all values in Tour object
                            return value.getName().toLowerCase().contains(searchText.toLowerCase())
                                    || value.getTourDescription().toLowerCase().contains(searchText.toLowerCase())
                                    || value.getTransportType().toLowerCase().contains(searchText.toLowerCase())
                                    || value.getFrom().toLowerCase().contains(searchText.toLowerCase())
                                    || value.getTo().toLowerCase().contains(searchText.toLowerCase())
                                    || String.valueOf(value.getTourDistance()).toLowerCase().contains(searchText.toLowerCase())
                                    || String.valueOf(value.getEstimatedTime()).toLowerCase().contains(searchText.toLowerCase())
                                    || String.valueOf(value.getId()).toLowerCase().contains(searchText.toLowerCase())
                                    || value.getPopularity().toLowerCase().startsWith(searchText.toLowerCase())
                                    || value.getChildFriendly().toLowerCase().startsWith(searchText.toLowerCase());
                        })
                        .collect(Collectors.toList());
            }
        };

        task.setOnSucceeded(event -> {
            tourListItems.setAll(task.getValue());
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }


    public void showItem(Tour rowItem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Tour");
        alert.setHeaderText("Tour info");
        alert.setContentText("The row contains: " + selectedTour.toString());
        alert.showAndWait();
    }
}
