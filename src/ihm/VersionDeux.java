package ihm;

import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import oop.Tutor;
import oop.Tutored;

public class VersionDeux extends Application{
    //Glob
    Scene scene;
    Button sessionBt = new Button("Non connecté");
    ComboBox<String> cbMatieres = new ComboBox<>();
    Image profilPhoto;
    ComboBox<String> cbSession = new ComboBox<>();

    final double TOOLBAR_HEIGHT = 55;

    int slMin = 0;
    int slMax = 5;

    Slider slAvg = new Slider(slMin, slMax, 1);
    Slider slAbs = new Slider(slMin, slMax, 1);
    Slider slMot = new Slider(slMin, slMax, 1);

    ListView<String> tutors = new ListView<>();
    ListView<String> tutored = new ListView<>();

    //Padding
    final Insets PAD_MIN = new Insets(5);
    final Insets PAD_BTN = new Insets(5, 9, 5, 9);

    //Lists
    // List<Tutor> tutorsOrigin = loadTutors();
    // List<Tutored> tutoredOrigin = loadTutored();
    List<Tutor> tutorsList;
    List<Tutored> tutoredList;
    
    private class OrderListHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e) {
            Button bt = ((Button)e.getTarget());
            if (bt.getText().equals("↓")) {
                bt.setText("↑");
                bt.getTooltip().setText("Tri décroissant");
            }
            else {
                bt.setText("↓");                
                bt.getTooltip().setText("Tri croissant");
            }
        } 
    }

    private class StudentHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e) {
            Button bt = ((Button)e.getTarget());
            if (bt.getText().equals("+")) {
                System.out.println("J'ajoute");
            }
            else if (bt.getText().equals("-")){
                System.out.println("Je retire");
            }
            else if (bt.getText().equals("🔗")){ //⩆
                System.out.println("J'affecte'");
            }
            else{
                System.out.println("J'interdis'");
            }
        } 
    }


    @Override
    public void start(Stage stage) throws Exception {        
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 850, 600);

        // MenuBar menu = initMenu();
        // root.setTop(menu);
        VBox top = initTop();
        root.setTop(top);

        VBox main = initMain();
        root.setCenter(main);

        HBox footer = initFooter();
        root.setBottom(footer);

        VBox listControls = initListControls();
        root.setLeft(listControls);
        
        initValues();

        stage.setScene(scene);
        stage.show();
    }

    void initValues(){
        setCoefs();
        // setLists();
    }

    void setCoefs(){
        setCoefs(1, 1, 1);
    }

    void setLists(){
        // setLists(tutoredOrigin, tutorsOrigin);
    }

    void setLists(List<Tutored> tutored, List<Tutor> tutor){
        
    }

    void setCoefs(int avg, int abs, int motivation){
        slAvg.setValue(avg);
        slAbs.setValue(abs);
        slMot.setValue(motivation);
    }



    VBox initTop(){
        VBox top= new VBox();
        top.getChildren().addAll(initMenu(), initHeader(), initOptions());
        return top;
    }

    HBox initLabelButton(String label, String button, EventHandler<ActionEvent> handler, String tooltip){
        HBox box = new HBox();        
        box.setAlignment(Pos.CENTER);

        Label lb = new Label(label);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button bt = new Button(button);
        bt.setOnAction(handler);
        bt.setStyle("-fx-font-size : 15;");
        bt.setTooltip(new Tooltip(tooltip));
        bt.setPadding(new Insets(0));
        bt.setPrefSize(20, 20);

        box.getChildren().addAll(lb, spacer, bt);
        return box;
    }


    TitledPane initEtudiantsControls(){
        VBox etudiantsControls = new VBox();

        HBox add = initLabelButton("Ajouter", "+", new StudentHandler(), "Ajouter un étudiant");
        HBox del = initLabelButton("Supprimer", "‒", new StudentHandler(), "Supprimer un étudiant");
        HBox union = initLabelButton("Forcer", "🔗", new StudentHandler(), "Forcer une affectation");
        HBox disUnion = initLabelButton("Interdire", "⦸", new StudentHandler(), "Interdire une affectation");

        etudiantsControls.getChildren().addAll(add, del, union, disUnion);
        return new TitledPane("Etudiants", etudiantsControls);
    }

    TitledPane initOrderControls(){
        VBox triControls = new VBox();

        HBox alpha = initLabelButton("Nom", "↓", new OrderListHandler(), "Tri croissant");
        HBox avg = initLabelButton("Moyenne", "↓", new OrderListHandler(), "Tri croissant");
        HBox abs = initLabelButton("Absences", "↓", new OrderListHandler(), "Tri croissant");
        HBox motiv = initLabelButton("Motivation", "↓", new OrderListHandler(), "Tri croissant");

        triControls.getChildren().addAll(alpha, avg, abs, motiv);
        return new TitledPane("Trier par", triControls);
    }

    VBox initListControls(){
        VBox listControls = new VBox();
        listControls.setPadding(PAD_MIN);
        listControls.setPrefWidth(150);       

        listControls.getChildren().addAll(initEtudiantsControls(), initOrderControls());

        return listControls;
    }


    VBox initMain(){
        VBox main = new VBox();

        // HBox header = initHeader();        

        // ToolBar options = initOptions();

        HBox listes = initListes();

        // main.getChildren().addAll(header, options, listes);
        main.getChildren().addAll(listes);

        return main;
    }

    HBox initListes(){
        HBox listes = new HBox();
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        spacer.setMaxWidth(150);

        listes.getChildren().addAll(tutored, spacer, tutors);
        listes.setAlignment(Pos.CENTER);

        listes.setPadding(PAD_MIN);
        return listes;
    }

    void setSlValue(Slider s, Number newVal){
        {((Slider)s).setValue( Math.ceil((double)newVal*2) / 2.0d);}
    }
    
    HBox initOptions(){
        HBox options = new HBox();
        Region spacer = new Region();
        spacer.setMinWidth(10);
        options.getChildren().addAll(initToolCoefs(), spacer, initToolAffect());
        options.setPadding(PAD_MIN);
        options.setMaxHeight(Double.MIN_VALUE);
        options.setAlignment(Pos.TOP_LEFT);
        options.setStyle("-fx-background-color: #EFEFEF;-fx-effect: dropshadow(gaussian, rgba(125,125,125,0.8), 2, 0, 0, 1);");
        return options;
    }

    TitledPane initToolAffect(){
        ToolBar tb = new ToolBar();
        tb.setPrefHeight(TOOLBAR_HEIGHT);
        Button btAffect = new Button("Affecter !");
        btAffect.setTooltip(new Tooltip("Lancer l'affectation"));

        Region spacer = new Region();
        spacer.setPrefWidth(10);

        Button btShuffle = new Button("🔀");
        btShuffle.setTooltip(new Tooltip("Lancer une affectation avec des coefficients aléatoires"));

        tb.setPadding(PAD_MIN);
        tb.getStyleClass().clear();
        tb.getItems().addAll(btAffect, spacer, btShuffle);
        return new TitledPane("Affectation", tb);
    }

    HBox initToolListes(){
        HBox tb = new HBox();
        HBox.setHgrow(tb, Priority.ALWAYS);
        Label lbOrdre = new Label("Tri des noms :");
        Button btOrder = new Button("↓");
        Button btRemoveStudent = new Button("‒");
        Button btAddStudent = new Button("+");

        tb.getChildren().addAll(lbOrdre, btOrder, new Label("\t"),btAddStudent, btRemoveStudent);
        return tb;
    }

    TitledPane initToolCoefs(){
        HBox coefs = new HBox();        
        coefs.setPrefHeight(TOOLBAR_HEIGHT);
        Slider[] sliders = {slAvg, slAbs, slMot};
        for (Slider s : sliders) {
            s.setMaxWidth(75);
            s.setMajorTickUnit(1);
            s.setShowTickLabels(true);
        }

        slAvg.valueProperty().addListener((obs, oldval, newVal) -> setSlValue(slAvg, newVal));
        slAbs.valueProperty().addListener((obs, oldval, newVal) -> setSlValue(slAbs, newVal));
        slMot.valueProperty().addListener((obs, oldval, newVal) -> setSlValue(slMot, newVal));

        Label labelAvg = new Label("Moyenne");
        Label labelAbs = new Label("Absences");
        Label labelMot = new Label("Motivation");
        Button resetCoef = new Button("↺");
        resetCoef.setTooltip(new Tooltip("Réinitialiser les coefficients"));

        coefs.getChildren().addAll(labelAvg, slAvg, labelAbs, slAbs, labelMot, slMot, resetCoef);
        return new TitledPane("Coefficients",coefs);
    }

    HBox initHeader(){
        HBox header = new HBox();
        header.setPadding(PAD_MIN);

        HBox matieres = new HBox();
        matieres.setAlignment(Pos.CENTER_LEFT);
        cbMatieres.setPromptText("Choisir une matière");
        matieres.getChildren().addAll(cbMatieres);


        HBox logo = new HBox();
        HBox.setHgrow(logo, Priority.ALWAYS);
        logo.setAlignment(Pos.CENTER);

        HBox session = new HBox();
        session.setAlignment(Pos.CENTER_RIGHT);
        ImageView logoImgView = new ImageView(new Image("file:res/img/logo.png"));
        logoImgView.setFitHeight(75);
        logoImgView.setPreserveRatio(true);
        logo.getChildren().addAll(logoImgView);

        setProfilPhoto();
        Circle sessionPhoto = new Circle(50);
        sessionPhoto.setStroke(Color.DARKGREY);
        sessionPhoto.setFill(new ImagePattern(profilPhoto));

        cbSession.setPromptText("Non connecté");
        session.getChildren().addAll(cbSession, sessionPhoto);

        header.getChildren().addAll(matieres, logo, session);
        for (Node n : header.getChildren()){
            n.minHeight(Double.MAX_VALUE);
        }
        return header;
    }

    HBox initFooter(){
        HBox footer = new HBox();
        sessionBt.getStyleClass().clear();
        // sessionBt.setOnAction(e->login());
        footer.getChildren().addAll(sessionBt);
        return footer;
    }

    MenuBar initMenu(){
        MenuBar menu = new MenuBar();

        Menu fichier = new Menu("Fichier");
        MenuItem exporter = new MenuItem("Exporter");
        MenuItem importer = new MenuItem("Importer");
        MenuItem login = new MenuItem("Se connecter");
        MenuItem logout = new MenuItem("Se déconnecter");
        MenuItem save = new MenuItem("Sauvegarder");
        MenuItem quit = new MenuItem("Quitter");
        fichier.getItems().addAll(exporter, importer, new SeparatorMenuItem(), login, logout, new SeparatorMenuItem(), save, quit);

        // exporter.setOnAction(e->exporter());
        // importer.setOnAction(e->importer());
        // login.setOnAction(e->login());
        // logout.setOnAction(e->logout());
        // save.setOnAction(e->save());
        // quit.setOnAction(e->quit());
        

        Menu selection = new Menu("Selection");
        MenuItem forcer = new MenuItem("Forcer l'affectation");
        MenuItem interdire = new MenuItem("Interdire l'affection");
        selection.getItems().addAll(forcer, interdire);

        // forcer.setOnAction(e->forcer());
        // interdire.setOnAction(e->interdire());


        Menu listes = new Menu("Listes");
        MenuItem ajouter = new MenuItem("Exporter");
        MenuItem supprimer = new MenuItem("Importer");
        MenuItem triAlpha = new MenuItem("Tri alphabétique");
        MenuItem triNotes = new MenuItem("Tri par moyennes");
        MenuItem triAbs = new MenuItem("Tri par absences");
        listes.getItems().addAll(ajouter, supprimer, new SeparatorMenuItem(), triAlpha, triNotes, triAbs);

        // ajouter.setOnAction(e->ajouter());
        // supprimer.setOnAction(e->supprimer());
        // triAlpha.setOnAction(e->trier("alpha"));
        // triNotes.setOnAction(e->trier("avg"));
        // triAbs.setOnAction(e->trier("abs"));


        Menu affectation = new Menu("Affectation");
        MenuItem affecter = new MenuItem("Affecter");
        MenuItem coefReset = new MenuItem("Réinitialiser les coefficients");
        MenuItem coefAvg = new MenuItem("Maximiser la moyenne");
        MenuItem coefAbs = new MenuItem("Maximiser les absences");
        MenuItem coefMot = new MenuItem("Maximiser la motivation");
        MenuItem coefRng = new MenuItem("Affectation aléatoire");
        affectation.getItems().addAll(affecter, new SeparatorMenuItem(), coefReset, coefRng, new SeparatorMenuItem(), coefAvg, coefAbs, coefMot);

        // affecter.setOnAction(e->affecter());
        // coefReset.setOnAction(e->coef("reset"));
        // coefAvg.setOnAction(e->coef("avg"));
        // coefAbs.setOnAction(e->coef("abs"));
        // coefMot.setOnAction(e->coef("mot"));
        // coefRng.setOnAction(e->coef("rng"));


        Menu edition = new Menu("Edition");
        MenuItem changeMat = new MenuItem("Changer de matière");
        MenuItem editProfil = new MenuItem("Editer le profil");
        MenuItem changeProf = new MenuItem("Changer le professeur référent");
        edition.getItems().addAll(changeMat, new SeparatorMenuItem(), editProfil, new SeparatorMenuItem(), changeProf);
        
        // changeMat.setOnAction(e->changeMat());
        // editProfil.setOnAction(e->editprofil());
        // changeProf.setOnAction(e->changeProf());


        menu.getMenus().addAll(fichier, selection, listes, affectation, edition);
        return menu;
    }

    void setProfilPhoto(String path){
        profilPhoto = new Image (path);
    }
    void setProfilPhoto(){
        setProfilPhoto("file:res/img/notlogged.jpg");
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
