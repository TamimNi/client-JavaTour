package at.fhtw.swen2.tutorial.presentation.view;

import at.fhtw.swen2.tutorial.presentation.ViewManager;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourListViewModel;
import at.fhtw.swen2.tutorial.presentation.viewmodel.TourManageViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

@Component
@Scope("prototype")
@Slf4j
public class TourManageView implements Initializable {
    public void editTourAction(ActionEvent actionEvent) {
        try {
            if(tourListViewModel.getSelectedTour() != null) {
                Stage stage = new Stage();
                Parent root1 = viewManager.load("at/fhtw/swen2/tutorial/presentation/view/NewTourWindow.fxml", stage);
                stage.setScene(new Scene(root1));
                stage.show();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Autowired
    private ViewManager viewManager;
    @Autowired
    private TourListViewModel tourListViewModel;
    @Autowired
    TourManageViewModel tourManageViewModel;

    @Override
    public void initialize(URL location, ResourceBundle rb) {
    }
    public void addTourAction(ActionEvent actionEvent) throws IOException {
        try {
            tourListViewModel.resetSelectedTour();
            Stage stage = new Stage();
            Parent root1 = viewManager.load("at/fhtw/swen2/tutorial/presentation/view/NewTourWindow.fxml", stage);
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void delTourAction(ActionEvent actionEvent) throws IOException {
        tourListViewModel.deleteRow();
    }
    public void singlePdfAction(ActionEvent actionEvent) throws IOException {
        if(tourListViewModel.getSelectedTour() != null){
        String url = tourManageViewModel.singlePdf(tourListViewModel.getSelectedTour().getId());
            openUpFile(url);
        }
    }

    public void summaryPdfAction(ActionEvent actionEvent) throws IOException {
        String url = tourManageViewModel.summaryPdf();
        openUpFile(url);
    }

    //UNIQUE FEATURE OPENS FILE IN WEBBROWSER VIA URL
    private void openUpFile(String url){

        String os = System.getProperty("os.name").toLowerCase();
        Runtime rt = Runtime.getRuntime();

        try {
            if (os.contains("win")) {
                // this doesn't support showing urls in the form of "page.html#nameLink"
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                rt.exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux")) {
                // Do a best guess on unix until we get a platform independent way
                String[] browsers = {"epiphany", "firefox", "mozilla", "konqueror",
                        "netscape", "opera", "links", "lynx"};

                StringBuffer cmd = new StringBuffer();
                for (int i = 0; i < browsers.length; i++)
                    if(i == 0)
                        cmd.append(String.format( "%s \"%s\"", browsers[i], url));
                    else
                        cmd.append(String.format(" || %s \"%s\"", browsers[i], url));
                // If the first didn't work, try the next browser and so on
                rt.exec(new String[]{"sh", "-c", cmd.toString()});
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
