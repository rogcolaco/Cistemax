package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ParentalControl {

    private final ObservableList<String> pControl = FXCollections.observableArrayList();


    public ObservableList<String> loadParentalControl() {
        pControl.add("Livre");
        pControl.add("+12 (doze) anos");
        pControl.add("+14 (catorze) anos");
        pControl.add("+16 (dezesseis) anos");
        pControl.add("+18 (dezoito) anos");

        return pControl;
    }
}
