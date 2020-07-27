package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class ParentalControl {

    private ObservableList<String> pControl = FXCollections.observableArrayList();


    public ObservableList<String> loadParentalControl(){
        pControl.add(new String("Livre"));
        pControl.add(new String("+12 (doze) anos"));
        pControl.add(new String("+14 (catorze) anos"));
        pControl.add(new String("+16 (dezesseis) anos"));
        pControl.add(new String("+18 (dezoito) anos"));

        return pControl;
    }
}
