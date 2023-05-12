package at.fhtw.swen2.tutorial.presentation.viewmodel;

import javafx.beans.property.*;
import org.springframework.stereotype.Component;

@Component
public class NewTourLogViewModel {
    private SimpleLongProperty id = new SimpleLongProperty();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleBooleanProperty isEmployed = new SimpleBooleanProperty();


    public long getId() {
        return id.get();
    }

    public LongProperty idProperty() {
        return id;
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }



}
