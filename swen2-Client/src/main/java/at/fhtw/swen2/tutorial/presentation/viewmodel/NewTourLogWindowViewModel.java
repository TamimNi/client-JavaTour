package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.presentation.view.TourManageView;
import at.fhtw.swen2.tutorial.service.dto.TourLog;
import at.fhtw.swen2.tutorial.toServer.ToServer;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NewTourLogWindowViewModel {
    private SimpleLongProperty idLogProperty = new SimpleLongProperty();
    private SimpleLongProperty tourIDProperty = new SimpleLongProperty();
    private SimpleStringProperty dateLogProperty = new SimpleStringProperty();
    private SimpleStringProperty commentLogProperty = new SimpleStringProperty();
    private SimpleStringProperty difficultyLogProperty = new SimpleStringProperty();
    private SimpleLongProperty totalTimeLogProperty = new SimpleLongProperty();
    private SimpleLongProperty ratingLogProperty = new SimpleLongProperty();


    public NewTourLogWindowViewModel() {
        //tourService = new TourServiceImpl();
    }
    private TourLog tourLog;

    public SimpleLongProperty idLogProperty() { return idLogProperty; }
    public SimpleLongProperty tourIDProperty() { return tourIDProperty; }
    public SimpleStringProperty dateLogProperty() { return dateLogProperty; }
    public SimpleStringProperty commentLogProperty() { return commentLogProperty; }
    public SimpleStringProperty difficultyLogProperty() { return difficultyLogProperty; }
    public SimpleLongProperty totalTimeLogProperty() { return totalTimeLogProperty; }
    public SimpleLongProperty ratingLogProperty() { return ratingLogProperty; }
    @Autowired
    private TourListViewModel tourListViewModel;
    @Autowired
    private TourLogListViewModel tourLogListViewModel;
    @Autowired
    ToServer toServer;

    private static final Logger logger = LogManager.getLogger(NewTourLogWindowViewModel.class);
    public void createTourLog() throws IOException {
       // Here you would implement the logic to create a new tour
        // using the provided tour name and location.
        TourLog tourLog = TourLog.builder()
                .tourId(tourListViewModel.getSelectedTour().getId())
                .idLog(idLogProperty.get())
                .dateLog(dateLogProperty.get())
                .commentLog(commentLogProperty.get())
                .difficultyLog(difficultyLogProperty.get())
                .totalTimeLog(totalTimeLogProperty.get())
                .ratingLog(ratingLogProperty.get())
                .build();
        logger.debug("create tourlog");
        //tourLog = tourLogService.addNew(tourLog);

        tourLogListViewModel.addItem((TourLog) toServer.postReq("/api/tourLog", tourLog));
        //tourLogListViewModel.addItem(tourLog);

    }

    public void editTest() throws IOException {
        TourLog tourLog = TourLog.builder()
                .idLog(tourLogListViewModel.getSelectedTour().getIdLog())
                .tourId(tourLogListViewModel.getSelectedTour().getTourId())
                .dateLog(null)
                .commentLog(commentLogProperty.get())
                .difficultyLog(difficultyLogProperty.get())
                .totalTimeLog(totalTimeLogProperty.get())
                .ratingLog(ratingLogProperty.get())
                .build();
        //tourListViewModel.delItem(test);
        tourLogListViewModel.delItem(tourLog);
        //tourLog = tourLogService.updOld(tourLog);
        toServer.putReq("/api/putlog",tourLog);
        tourLogListViewModel.addItem(tourLog);
        logger.debug("edit tourlog");
    }
}
