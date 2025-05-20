package gui.opretMaltBatch;

import application.model.Malt;
import application.model.MaltBatch;
import gui.component.LabeledButton;
import gui.component.LabeledTextInput;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static gui.component.InputValidering.visDialog;

public class OpretMaltPane extends Stage {
    private final LabeledTextInput kornSortInput = new LabeledTextInput("Kornsort");
    private final LabeledTextInput markNavnInput = new LabeledTextInput("Marknavn");
    private final LabeledTextInput mængdeInput = new LabeledTextInput("Mængde i kg");
    private final LabeledButton opretMaltButton = new LabeledButton("Opret malt", "Opret");

    private Malt oprettetMalt;
    private final MaltBatch maltBatch;

    public OpretMaltPane(MaltBatch maltBatch) {
        this.maltBatch = maltBatch;
        this.setTitle("Opret ny malt");

        VBox root = new VBox(5);
        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(0, 5, 10, 10));

        Scene scene = new Scene(root, 200, 300);
        this.setScene(scene);

        root.getChildren().addAll(kornSortInput, markNavnInput, mængdeInput, opretMaltButton);

        opretMaltButton.getButton().setOnAction(e -> {
                    try {
                        String kornSort = kornSortInput.getInputValue();
                        String markNavn = markNavnInput.getInputValue();
                        double mængde = Double.parseDouble(mængdeInput.getInputValue());

                        oprettetMalt = maltBatch.createMalt(kornSort, markNavn, mængde);

                        this.close();
                    } catch (Exception exception) {
                        visDialog(
                                Alert.AlertType.ERROR,
                                "Fejl ved oprettelse",
                                "Kunne ikke oprette ny malt: " + exception.getMessage());
                    }
                }
        );
    }
    public Malt getOprettetMalt() {
        return oprettetMalt;
    }
}
