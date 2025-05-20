package gui.produktionsAdm.tabPanes;

import gui.component.LabeledListViewInput;
import gui.component.LabeledTextInput;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public abstract class BaseTab<T> extends GridPane {

    protected LabeledTextInput søgeFelt;
    protected LabeledListViewInput<T> liste;

    protected VBox attributVisning;

    public BaseTab(String søgeFeltTekst, String listeTekst){
        setPadding(new Insets(0,5,10,10));
        setHgap(10);
        setVgap(10);
        setPrefSize(600, 625);

        ColumnConstraints venstre = new ColumnConstraints();
        venstre.setPercentWidth(50);
        ColumnConstraints højre = new ColumnConstraints();
        højre.setPercentWidth(50);
        getColumnConstraints().addAll(venstre, højre);

        søgeFelt = new LabeledTextInput(søgeFeltTekst);

        liste = new LabeledListViewInput<>(listeTekst);

        VBox venstreBoks = new VBox(5,søgeFelt,liste);
        VBox.setVgrow(liste, Priority.ALWAYS);
        add(venstreBoks,0,0);

        attributVisning = new VBox(5);
        add(attributVisning,1,0);

        GridPane.setHgrow(venstreBoks, Priority.ALWAYS);
        GridPane.setHgrow(attributVisning, Priority.ALWAYS);
        GridPane.setVgrow(venstreBoks, Priority.ALWAYS);
        GridPane.setVgrow(attributVisning, Priority.ALWAYS);
    }
}
