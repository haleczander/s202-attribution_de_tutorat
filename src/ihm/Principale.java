package ihm;

import javax.swing.plaf.metal.MetalPopupMenuSeparatorUI;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Principale extends Application{

    public static void main(String[] arg0){
        Application.launch(arg0);
        
    }
    /**Listes d'étudiants */
    ListView<String> tutors = new ListView<>();
    ListView<String> tutored = new ListView<>();
    /**Sliders des coefs */
    Slider slAvg = new Slider();
    Slider slAbs = new Slider();
    Slider slLvl = new Slider();

    public void start(Stage stage) throws Exception {
        BorderPane root = new BorderPane();

        HBox header = (HBox) initHeader();
        root.setTop(header);        
        VBox main = (VBox) initMain();
        root.setCenter(main);
        HBox footer = (HBox) initFooter();
        root.setBottom(footer);




        stage.setScene(new Scene(root, 200, 200));
        stage.show();
    }

    private Node initHeader(){
        HBox header = new HBox();
        ComboBox<String> cbMatieres = new ComboBox<>();

        header.getChildren().addAll(cbMatieres);
        return header;
    }

    private Node initMain(){
        VBox main = new VBox();
        ToolBar tools = initToolBar();
        HBox sliders = new HBox(slAvg, slLvl, slAbs);
        HBox actions = new HBox(sliders, tools);
        HBox lists = new HBox(tutors, tutored);

        main.getChildren().addAll(actions, lists);
        return main;
    }
    private Node initFooter(){
        HBox footer = new HBox();
        footer.getChildren().add(new Label("Créé et entretenu avec une passion pour Jean Carle !"));
        return footer;
    }

    private ToolBar initToolBar(){
        ToolBar tb = new ToolBar();
        Button btReset = new Button("Reset");
        Button btShuffle = new Button("Affectation aléatoire");
        Button btOrder = new Button("Tri : Alphabétique");
        Button btAffect = new Button("Affecter !");

        tb.getItems().addAll(btReset, btShuffle, btOrder, btAffect);
        return tb;
    }

    
}
