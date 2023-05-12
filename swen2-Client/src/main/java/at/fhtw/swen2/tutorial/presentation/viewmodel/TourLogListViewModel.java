package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.toServer.ToServer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TourLogListViewModel {


    private List<TourLog> masterData = new ArrayList<>();
    private ObservableList<TourLog> tourLogListItems = FXCollections.observableArrayList();

    public ObservableList<TourLog> getTourLogListItems() {
        return tourLogListItems;
    }

    public void addItem(TourLog tourLog) {
        tourLogListItems.add(tourLog);
        masterData.add(tourLog);
    }

    public void delItem(TourLog tourLog) {
        //tourListItems.remove(test);
        tourLogListItems.removeIf(t -> t.getIdLog().equals(tourLog.getIdLog()));
        masterData.removeIf(t -> t.getIdLog().equals(tourLog.getIdLog()));
        selectedTour = null;
    }
    public void delItemByTourId(Long id) {
        //tourListItems.remove(test);
        tourLogListItems.removeIf(t -> t.getTourId().equals(id));
        masterData.removeIf(t -> t.getTourId().equals(id));
        selectedTour = null;
    }

    public void removeLogsOfTheTour(Long tourId) {
        System.out.println("ITTTEMS\n\n\n\n" + tourLogListItems + "\n\n\n.................");
        //tourLogListItems.removeIf(t -> t.getTest().getId().equals(tourId));
        // masterData.removeIf(t -> t.getTest().getId().equals(tourId));
        selectedTour = null;
    }

    public void clearItems() {
        tourLogListItems.clear();
    }

    public TourLog getSelectedTour() {
        return selectedTour;
    }

    public Tour getSelectedTourMain() {
        return selectedTourMain;
    }

    public Tour setSelectedTourMain(Tour selectedMain) {
        return selectedTourMain = selectedMain;
    }

    private Tour selectedTourMain = null;
    private TourLog selectedTour = null;

    public void selectRow(TourLog tourLog) {
        this.selectedTour = tourLog;
        System.out.println(selectedTour);
        // create and show the notification box
    }

    public void deleteRow() throws IOException {
        if (selectedTour != null) {
            System.out.println("del");

            toServer.deleteReq("/api/dellog", selectedTour.getIdLog());
            delItem(selectedTour);
/*            TourLog tourLog = TourLog.builder().name(name.get()).tourDescription(tourDescription.get())
                    .transportType(transportType.get()).tourDistance(12L)
                    .estimatedTime(11L).from(from.get()).to(to.get()).build();*/
            //  TourLog tourLog = tourLogService.delOld(selectedTour);
            //   delItem(tourLog);
            //    System.out.println("thNAME "+tourLog+ "this name: ." +tourLog);
        } else
            System.out.println("no select");
    }

    @Autowired
    ToServer toServer;

    public void getLogList(String tourId) throws IOException {
        System.out.println("yes");
        // tourLogService.getTourLogList().forEach(p -> {
        //    addItem(p);
        // });
        List<TourLog> tourLogs = toServer.getReq("/api/tourLog/" + tourId, new ParameterizedTypeReference<List<TourLog>>() {
        });
        masterData.clear();
        tourLogListItems.clear();
        masterData.addAll(tourLogs);
        tourLogListItems.addAll(tourLogs);

    }

    public void filterList(String searchText) {
        Task<List<TourLog>> task = new Task<>() {
            @Override
            protected List<TourLog> call() throws Exception {
                updateMessage("Loading data");
                return masterData
                        .stream()
                        .filter(value -> {
                            // filter by all values in Test object
                            return (value.getCommentLog().toLowerCase().contains(searchText.toLowerCase())
                                    || value.getDifficultyLog().toLowerCase().contains(searchText.toLowerCase())
                                    || value.getCommentLog().toLowerCase().contains(searchText.toLowerCase())
                                    || String.valueOf(value.getRatingLog()).toLowerCase().contains(searchText.toLowerCase())
                                    || String.valueOf(value.getTotalTimeLog()).toLowerCase().contains(searchText.toLowerCase()));
                        })
                        .collect(Collectors.toList()); //|| value.getDateLog().equals(searchText.toLowerCase())
            }
        };

        task.setOnSucceeded(event -> {
            tourLogListItems.setAll(task.getValue());
        });

        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }




}
