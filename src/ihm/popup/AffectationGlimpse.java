package ihm.popup;

import java.util.Collection;
import java.util.Optional;

import graphs.Couple;
import graphs.Tutoring;
import ihm.Interface;
import ihm.utils.TutoringUtils;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import oop.Tutor;

public class AffectationGlimpse extends PopUp {
    Tab pairsTab;
    Tab forcedTab;
    Tab forbiddenTab;

    TabPane root;

    public AffectationGlimpse(Interface parent) {
        super(parent);
        start(stage);
    }

    @Override
    public void start(Stage stage) {
        pairsTab = newTab("Calculées", parent.dpt.currentTutoring.affectations);
        forcedTab = newTab("Forcées", parent.dpt.currentTutoring.getForcedCouples());
        forbiddenTab = newTab("Interdites", parent.dpt.currentTutoring.getForbiddenCouples());
        root = new TabPane(pairsTab, forcedTab, forbiddenTab);

        root.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

        stage.setTitle("Visualiser les affectations");
        stage.setScene(new Scene(root, 300, 300));
        stage.show();

    }

    Tab newTab(String titre, Collection<Couple> liste) {
        Tab retour = new Tab(titre);

        ListView<Couple> view = new ListView<>();
        updateList(view, liste);


        Button removeBt = new Button("Supprimer");
        removeBt.disableProperty().bind(Bindings.size(view.getSelectionModel().getSelectedItems()).isEqualTo(0));
        removeBt.setOnAction(e -> predefinedAction(retour, view, liste));

        VBox root = new VBox(view, removeBt);
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.setPadding(parent.PAD_MIN);

        retour.setContent(root);

        return retour;
    }

    void updateLists() {
        updateList(((ListView<Couple>) (((VBox) pairsTab.getContent()).getChildren().get(0))),
                parent.dpt.currentTutoring.affectations);
        updateList(((ListView<Couple>) (((VBox) forcedTab.getContent()).getChildren().get(0))),
                parent.dpt.currentTutoring.getForcedCouples());
        updateList(((ListView<Couple>) (((VBox) forbiddenTab.getContent()).getChildren().get(0))),
                parent.dpt.currentTutoring.getForbiddenCouples());
    }

    void updateList(ListView<Couple> view, Collection<Couple> liste) {
        view.getItems().clear();
        view.getItems().addAll(liste);
    }

    void predefinedAction(Tab tab, ListView<Couple> view, Collection<Couple> liste) {
        Alert alert = new Alert(AlertType.WARNING, "", ButtonType.YES, ButtonType.CANCEL);
        alert.headerTextProperty().set("");

        if (tab == pairsTab) {
            alert.setContentText("Vous vous apprêtez à interdire cette  affectation. Êtes-vous certain(e)?");
            if (alert.showAndWait().get() == ButtonType.YES) {
                parent.dpt.currentTutoring.addForbiddenAssignments(view.getSelectionModel().getSelectedItem());
            }
                
        } else if (tab == forcedTab) {
            alert.setContentText("Vous vous apprêtez à ne plus forcer cette affectation. Êtes-vous certain(e)?");
            if (alert.showAndWait().get() == ButtonType.YES){
                parent.dpt.currentTutoring.getForcedCouples().remove(view.getSelectionModel().getSelectedItem());
            }
        } else if (tab == forbiddenTab) {
            alert.setContentText("Vous vous apprêtez à ne plus interdire cette affectation. Êtes-vous certain(e)?");
            if (alert.showAndWait().get() == ButtonType.YES){
                parent.dpt.currentTutoring.getForbiddenCouples().remove(view.getSelectionModel().getSelectedItem());
            }
            
        }
        updateLists();
        TutoringUtils.updateLists(parent);

    }

}
