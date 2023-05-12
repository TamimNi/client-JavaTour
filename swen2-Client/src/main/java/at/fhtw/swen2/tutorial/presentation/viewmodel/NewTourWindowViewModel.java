package at.fhtw.swen2.tutorial.presentation.viewmodel;

import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.toServer.ToServer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NewTourWindowViewModel {

    @Autowired
    private TourListViewModel tourListViewModel;
    private final SimpleStringProperty name = new SimpleStringProperty("");
    private final SimpleStringProperty tourDescription = new SimpleStringProperty("");
    private final SimpleStringProperty from = new SimpleStringProperty("");
    private final SimpleStringProperty to = new SimpleStringProperty("");
    private final SimpleStringProperty transportType = new SimpleStringProperty("");
    @Autowired
    ToServer toServer;

    public StringProperty nameProperty() { return name; }
    public StringProperty tourDescriptionProperty() { return tourDescription; }
    public StringProperty fromProperty() { return from; }
    public StringProperty toProperty() { return to; }
    public StringProperty transportTypeProperty() { return transportType; }
    public void editTest() throws IOException {
        Tour tour = Tour.builder().name(name.get()).tourDescription(tourDescription.get())
                .transportType(transportType.get()).tourDistance(12L)
                .estimatedTime(11L).from(from.get()).to(to.get()).id(tourListViewModel.getSelectedTour().getId()).build();
        tour = toServer.putReq("/api/tour", tour);
        tourListViewModel.delItem(tourListViewModel.getSelectedTour(),false);
        tourListViewModel.addItem((tour));
    }

    public void addTest() throws IOException {
        Tour tour = Tour.builder().name(name.get()).tourDescription(tourDescription.get())
                .transportType(transportType.get()).tourDistance(12L)
                .estimatedTime(11L).from(from.get()).to(to.get()).build();
        tourListViewModel.addItem((Tour) toServer.postReq("/api/tour", tour));
    }
}
