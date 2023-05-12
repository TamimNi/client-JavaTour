package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.service.dto.Tour;
import at.fhtw.swen2.tutorial.toServer.MapQuestImage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
public class ImageView implements Initializable {
    @FXML
    public javafx.scene.image.ImageView mapView;

    @Override
    public void initialize(URL location, ResourceBundle rb) {
    }

    @Autowired
    MapQuestImage mapQuestImage;
    @Autowired
    TourListViewModel tourListViewModel;
    public void showImageAction(ActionEvent actionEvent) {
        try {
            Tour tour = tourListViewModel.getSelectedTour();
            if(tour != null) {
                mapView.setImage(mapQuestImage.getImage(tour.getFrom(), tour.getTo()));
            }else{
                System.out.println("no Tour selected");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

