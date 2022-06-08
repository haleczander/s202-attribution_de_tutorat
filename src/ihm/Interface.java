package ihm;

import java.util.Optional;
import java.util.Random;

import graphs.Couple;
import graphs.Tutoring;
import ihm.events.AffectationHandler;
import ihm.events.StudentHandler;
import ihm.popup.Log;
import ihm.popup.Login;
import ihm.utils.TutoringUtils;
import ihm.utils.WidgetUtils;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import oop.Coefficient;
import oop.Resource;
import oop.Tutor;
import oop.Tutored;

public class Interface extends Application {
    // Glob
    Stage stage;
    Scene scene;
    public IHMDepartment dpt = new IHMDepartment();

    //Waiting for tutoring
    VBox etudiantsControls;
    VBox triControls;
    Button btAffect;
    MenuItem affecter;

    // Login controls
    Button sessionBt = new Button();
    final String notLogged = "Non connecté";
    final String logged = "Connecté en tant que ";
    MenuItem login = new MenuItem("Se connecter");
    MenuItem logout = new MenuItem("Se déconnecter");
    Circle sessionPhoto = new Circle(50);
   
    public ComboBox<Resource> cbMatieres = new ComboBox<>();
    ComboBox<String> cbSession = new ComboBox<>();

    //
    final double TOOLBAR_HEIGHT = 55;

    int slMin = 0;
    int slMax = 5;

    Slider slAvg = new Slider(slMin, slMax, 1);
    Slider slAbs = new Slider(slMin, slMax, 1);
    Slider slLvl = new Slider(slMin, slMax, 1);

    public ListView<Tutor> tutors = new ListView<>();
    public ListView<Tutored> tutored = new ListView<>();

    public ListView<Couple> aretes = new ListView<>();

    // Padding
    final Insets PAD_MIN = new Insets(5);
    final Insets PAD_BTN = new Insets(5, 9, 5, 9);

    private class OrderListHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Button bt = ((Button) e.getTarget());
            if (bt.getText().equals("↓")) {
                bt.setText("↑");
                bt.getTooltip().setText("Tri décroissant");
            } else {
                bt.setText("↓");
                bt.getTooltip().setText("Tri croissant");
            }
        }
    }

    private class AuthentificationHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            dispatchEvent();
        }

        private void dispatchEvent() {
            if (!Login.loggedIn) {
                new Login(Interface.this);
            } else {
                Alert alert = new Alert(AlertType.CONFIRMATION,
                        "Vous allez vous déconnecter. Êtes-vous certain(e)?",
                        ButtonType.YES, ButtonType.CANCEL);
                alert.headerTextProperty().set("");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.YES) {
                    Log.loggedIn = false;
                    Log.account = null;
                    Interface.this.updateSession();
                }
            }
        }

    }

    public class SliderListener implements ChangeListener<Number> {
        Slider slider;
        Coefficient coef;

        SliderListener(Slider slider, Coefficient coef) {
            this.slider = slider;
            this.coef = coef;
        }

        @Override
        public void changed(ObservableValue<? extends Number> obs, Number oldVal, Number newVal) {
            slider.setValue(Math.ceil((double) newVal * 2) / 2.0d);
            dpt.teacher.setWeighting(coef, (double) newVal);
        }

    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        initInterface();
        initData();

        stage.setTitle("Tutorat du département " + this.dpt.getName());
        stage.setScene(scene);
        stage.show();
    }

    void initInterface() {
        BorderPane root = new BorderPane();
        scene = new Scene(root, 850, 600);

        root.setTop(initTop());
        root.setCenter(initMain());
        root.setBottom(initFooter());
        root.setLeft(initListControls());

        waitingForTutoring(true);
    }

    VBox initMain() {
        VBox main = new VBox();
        main.getChildren().addAll(initListes());
        return main;
    }

    VBox initTop() {
        return new VBox(initMenu(), initHeader(), initOptions());
    }

    void initData() {
        initDepartment();
        updateSession();
        // setLists();
    }

    void initDepartment() {

    }

    TitledPane initEtudiantsControls() {
        etudiantsControls = new VBox();

        HBox add = WidgetUtils.labelButton("Ajouter", "+", new StudentHandler(this), "Ajouter un étudiant");
        HBox del = WidgetUtils.labelButton("Supprimer", "‒", new StudentHandler(this), "Supprimer un étudiant");
        HBox union = WidgetUtils.labelButton("Forcer", "🔗", new StudentHandler(this), "Forcer une affectation");
        HBox disUnion = WidgetUtils.labelButton("Interdire", "⦸", new StudentHandler(this), "Interdire une affectation");

        etudiantsControls.getChildren().addAll(add, del, union, disUnion);
        return new TitledPane("Etudiants", etudiantsControls);
    }

    public void waitingForTutoring(boolean bool){
        for ( Node box : etudiantsControls.getChildren()) {
            ((Button)((HBox)box).getChildren().get(2)).setDisable(bool);
        }
        for ( Node box : triControls.getChildren()) {
            ((Button)((HBox)box).getChildren().get(2)).setDisable(bool);
        }
        btAffect.setDisable(bool);
        affecter.setDisable(bool);
    }

    TitledPane initOrderControls() {
        triControls = new VBox(); 

        HBox alpha = WidgetUtils.labelButton("Nom", "↓", new OrderListHandler(), "Tri croissant");
        HBox avg = WidgetUtils.labelButton("Moyenne", "↓", new OrderListHandler(), "Tri croissant");
        HBox abs = WidgetUtils.labelButton("Absences", "↓", new OrderListHandler(), "Tri croissant");
        HBox motiv = WidgetUtils.labelButton("Motivation", "↓", new OrderListHandler(), "Tri croissant");

        triControls.getChildren().addAll(alpha, avg, abs, motiv);
        return new TitledPane("Trier par", triControls);
    }

    VBox initListControls() {
        VBox listControls = new VBox();
        listControls.setPadding(PAD_MIN);
        listControls.setPrefWidth(150);

        listControls.getChildren().addAll(initEtudiantsControls(), initOrderControls());

        return listControls;
    }

    HBox initListes() {
        HBox listes = new HBox();

        listes.getChildren().addAll(tutored, WidgetUtils.spacer(150), aretes, WidgetUtils.spacer(150), tutors);
        listes.setAlignment(Pos.CENTER);

        listes.setPadding(PAD_MIN);
        return listes;
    }

    HBox initOptions() {
        HBox options = new HBox();

        options.getChildren().addAll(initToolCoefs(), WidgetUtils.filler(), initToolAffect());
        options.setPadding(PAD_MIN);
        options.setMaxHeight(Double.MIN_VALUE);
        options.setAlignment(Pos.TOP_LEFT);
        options.setStyle(
                "-fx-background-color: #EFEFEF;-fx-effect: dropshadow(gaussian, rgba(125,125,125,0.8), 2, 0, 0, 1);");
        return options;
    }

    TitledPane initToolAffect() {
        ToolBar tb = new ToolBar();
        tb.setPrefHeight(TOOLBAR_HEIGHT);
        btAffect = new Button("Affecter !");

        btAffect.setTooltip(new Tooltip("Lancer l'affectation"));
        btAffect.setOnAction(new AffectationHandler(this));
        btAffect.setDisable(true);



        tb.setPadding(PAD_MIN);
        tb.getStyleClass().clear();
        tb.getItems().addAll(btAffect);
        return new TitledPane("Affectation", tb);
    }

    HBox initToolListes() {
        HBox tb = new HBox();
        HBox.setHgrow(tb, Priority.ALWAYS);
        Label lbOrdre = new Label("Tri des noms :");
        Button btOrder = new Button("↓");
        Button btRemoveStudent = new Button("‒");
        Button btAddStudent = new Button("+");

        tb.getChildren().addAll(lbOrdre, btOrder, new Label("\t"), btAddStudent, btRemoveStudent);
        return tb;
    }

    TitledPane initToolCoefs() {
        HBox coefs = new HBox();
        coefs.setPrefHeight(TOOLBAR_HEIGHT);
        Slider[] sliders = { slAvg, slAbs, slLvl };
        for (Slider slider : sliders) {
            slider.setMaxWidth(75);
            slider.setMajorTickUnit(1);
            slider.setShowTickLabels(true);
        }
        slAvg.valueProperty().addListener(new SliderListener(slAvg, Coefficient.GRADES));
        slAbs.valueProperty().addListener(new SliderListener(slAbs, Coefficient.ABSENCES));
        slLvl.valueProperty().addListener(new SliderListener(slLvl, Coefficient.LEVEL));

        Label labelAvg = new Label("Moyenne");
        Label labelAbs = new Label("Absences");
        Label labelLvl = new Label("Motivation");

        Button resetCoef = new Button("↺");
        resetCoef.setTooltip(new Tooltip("Réinitialiser les coefficients"));
        resetCoef.setOnAction(e-> setCoefs());

        Button btShuffle = new Button("🔀");
        btShuffle.setTooltip(new Tooltip("Randomiser les coefficients"));
        btShuffle.setOnAction(e-> setCoefs(1));

        coefs.getChildren().addAll(labelAvg, slAvg, labelAbs, slAbs, labelLvl, slLvl, resetCoef, btShuffle);
        return new TitledPane("Coefficients", coefs);
    }

    HBox initHeader() {
        HBox header = new HBox();
        header.setPadding(PAD_MIN);

        HBox logo = new HBox();
        HBox.setHgrow(logo, Priority.ALWAYS);
        logo.setAlignment(Pos.CENTER);

        HBox session = new HBox();
        session.setAlignment(Pos.CENTER_RIGHT);
        ImageView logoImgView = new ImageView(new Image("file:res/img/logo.png"));
        logoImgView.setFitHeight(75);
        logoImgView.setPreserveRatio(true);
        logo.getChildren().addAll(logoImgView);

        session.getChildren().addAll(cbSession, sessionPhoto);

        header.getChildren().addAll(TutoringUtils.initMatieres(this), logo, session);
        for (Node n : header.getChildren()) {
            n.minHeight(Double.MAX_VALUE);
        }
        return header;
    }

    HBox initFooter() {
        HBox footer = new HBox();
        sessionBt.getStyleClass().clear();
        sessionBt.setOnMouseEntered(e -> ((Button) e.getTarget()).setTextFill(Color.BLUE));
        ;
        sessionBt.setOnMouseExited(e -> ((Button) e.getTarget()).setTextFill(Color.BLACK));
        ;
        sessionBt.setOnAction(new AuthentificationHandler());
        footer.getChildren().addAll(sessionBt);
        return footer;
    }

    public void close() {
        Alert alert = new Alert(AlertType.CONFIRMATION,
                "Quitter l'application?",
                ButtonType.YES, ButtonType.CANCEL);
        alert.headerTextProperty().set("");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.YES) {
            stage.close();
        }
    }

    MenuBar initMenu() {
        return new MenuBar(initMenuFichier(), initMenuSelection(), initMenuListes(), initMenuAffectation(),
                initMenuEdition());
    }

    Menu initMenuFichier() {
        Menu fichier = new Menu("Fichier");
        MenuItem exporter = new MenuItem("Exporter");
        exporter.setDisable(true);
        MenuItem importer = new MenuItem("Importer");
        importer.setDisable(true);
        MenuItem save = new MenuItem("Sauvegarder");
        MenuItem quit = new MenuItem("Quitter");
        fichier.getItems().addAll(exporter, importer, new SeparatorMenuItem(), login, logout, new SeparatorMenuItem(),
                save, quit);

        // exporter.setOnAction(e->exporter());
        // importer.setOnAction(e->importer());
        login.setOnAction(new AuthentificationHandler());
        // logout.setOnAction(e->logout());
        // save.setOnAction(e->save());
        quit.setOnAction(e -> close());

        return fichier;
    }

    Menu initMenuSelection() {
        Menu selection = new Menu("Selection");
        MenuItem forcer = new MenuItem("Forcer l'affectation");
        MenuItem interdire = new MenuItem("Interdire l'affection");
        selection.getItems().addAll(forcer, interdire);

        // forcer.setOnAction(e->forcer());
        // interdire.setOnAction(e->interdire());

        return selection;
    }

    Menu initMenuListes() {
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

        return listes;
    }

    Menu initMenuAffectation() {
        Menu affectation = new Menu("Affectation");
        affecter = new MenuItem("Affecter");
        MenuItem coefRst = new MenuItem("Réinitialiser les coefficients");
        MenuItem coefAvg = new MenuItem("Maximiser la moyenne");
        MenuItem coefAbs = new MenuItem("Maximiser les absences");
        MenuItem coefLvl = new MenuItem("Maximiser la motivation");
        MenuItem coefRng = new MenuItem("Coefficients aléatoires");
        affectation.getItems().addAll(affecter, new SeparatorMenuItem(), coefRst, coefRng, new SeparatorMenuItem(),
                coefAvg, coefAbs, coefLvl);

        affecter.setOnAction(new AffectationHandler(this));
        affecter.setDisable(true);
        coefRst.setOnAction(e -> setCoefs());
        coefAvg.setOnAction(e -> setCoefs(2, .5, .5));
        coefAbs.setOnAction(e -> setCoefs(.5, 2, .5));
        coefLvl.setOnAction(e -> setCoefs(.5, .5, 2));
        coefRng.setOnAction(e -> setCoefs(666));

        return affectation;
    }

    void setCoefs(double avg, double abs, double lvl) {
        slAvg.setValue(avg);
        slAbs.setValue(abs);
        slLvl.setValue(lvl);
    }

    void setCoefs() {
        setCoefs(1, 1, 1);
    }

    void setCoefs(int n) {
        Random rng = new Random();
        setCoefs(rng.nextDouble(Tutoring.getMaxWeighting()), rng.nextDouble(Tutoring.getMaxWeighting()),
                rng.nextDouble(Tutoring.getMaxWeighting()));
    }

    Menu initMenuEdition() {
        Menu edition = new Menu("Edition");
        MenuItem changeMat = new MenuItem("Changer de matière");
        changeMat.setDisable(true);
        MenuItem editProfil = new MenuItem("Editer le profil");
        editProfil.setDisable(true);
        MenuItem changeProf = new MenuItem("Changer le professeur référent");
        changeProf.setDisable(true);
        edition.getItems().addAll(changeMat, new SeparatorMenuItem(), editProfil, new SeparatorMenuItem(), changeProf);

        // changeMat.setOnAction(e->changeMat());
        // editProfil.setOnAction(e->editprofil());
        // changeProf.setOnAction(e->changeProf());

        return edition;
    }

    void setProfilPhoto(String path) {
        sessionPhoto.setStroke(Color.DARKGREY);
        sessionPhoto.setFill(new ImagePattern(new Image(path)));
    }

    void setProfilPhoto() {
        setProfilPhoto("file:res/img/notlogged.jpg");
    }

    public void updateSession() {
        if (Log.loggedIn) {
            sessionBt.setText(logged + Log.account);
            login.setDisable(true);
            logout.setDisable(false);
            setProfilPhoto("file:res/img/root.jpg");
            cbSession.setPromptText(Log.account);
        } else {
            sessionBt.setText(notLogged);
            login.setDisable(false);
            logout.setDisable(true);
            setProfilPhoto();
            cbSession.setPromptText(notLogged);
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
