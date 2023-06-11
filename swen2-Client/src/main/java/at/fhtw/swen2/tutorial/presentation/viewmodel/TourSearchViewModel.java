package at.fhtw.swen2.tutorial.presentation.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TourSearchViewModel {
    @Autowired
    private TourListViewModel tourListViewModel;

    private static final Logger logger = LogManager.getLogger(TourSearchViewModel.class);
    private SimpleStringProperty searchString = new SimpleStringProperty();


    public String getSearchString() {
        return searchString.get();
    }

    public SimpleStringProperty searchStringProperty() {
        return searchString;
    }

    public void setSearchString(String searchString) {
        this.searchString.set(searchString);
    }

    public void search() {
        logger.debug("searching");
        System.out.println("filter called->"+getSearchString()+"<-");
        tourListViewModel.filterList(getSearchString());
    }


}
