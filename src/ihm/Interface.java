package ihm;

import java.util.Optional;
import java.util.Random;

import graphs.Couple;
import graphs.Tutoring;
import ihm.events.AuthentificationHandler;
import ihm.events.Events;
import ihm.events.SelectedStudentListener;
import ihm.events.SliderListener;
import ihm.events.SortListHandler;
import ihm.popup.Login;
import ihm.utils.TutoringUtils;
import ihm.utils.WidgetUtils;
import javafx.application.Application;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
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
import oop.Student;

public class Interface extends Application {
    // Glob
    Stage stage;
    Scene scene;
    public IHMDepartment dpt = new IHMDepartment();

    // list filter
    public boolean filterTutored = true;

    // Waiting for tutoring
    VBox etudiantsControls;
    VBox triControls;
    Button btAffect;
    MenuItem affecter;
    MenuItem ajouter;
    MenuItem supprimer;

    // Login controls
    Button sessionBt = new Button();
    final String notLogged = "Non connecté";
    final String logged = "Connecté en tant que ";
    MenuItem login = new MenuItem("Se connecter");
    MenuItem logout = new MenuItem("Se déconnecter");
    Circle sessionPhoto = new Circle(50);

    public ComboBox<Resource> cbMatieres = new ComboBox<>();
    ComboBox<String> cbSession = new ComboBox<>();


    public Student draggedStudent;
    public boolean dragging;
    public boolean draggingTarget = false;

    //
    final double TOOLBAR_HEIGHT = 55;
    final String ASC = "Tri croissant";
    final String DESC = "Tri décroissant";

    int slMin = 0;
    int slMax = 5;

    Slider slAvg = new Slider(slMin, slMax, 1);
    Slider slAbs = new Slider(slMin, slMax, 1);
    Slider slLvl = new Slider(slMin, slMax, 1);

    public ListView<Student> tutors = new ListView<>();
    public ListView<Student> tutored = new ListView<>();
    public Student selectedStudent;

    public ListView<Couple> aretes = new ListView<>();

    // Padding
    final Insets PAD_MIN = new Insets(5);
    final Insets PAD_BTN = new Insets(5, 9, 5, 9);

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

        keyBoardShortcuts();
        // scene.setOnMouseReleased(e->System.out.println("REEASED"));
        // scene.setOnMouseMoved(e->System.out.println("MOOVIT"));

        waitingForTutoring(true);
    }

    void keyBoardShortcuts() {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case MINUS, SUBTRACT, D, DELETE -> Events.RemoveStudentHandler(this);
                case PLUS, N -> Events.AddStudentHandler(this);
                case F5 -> Events.AffectationHandler(this);
                case default -> nothing();
            }

        });
    }

    void nothing() {
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

        HBox add = WidgetUtils.labelButton("Ajouter", "+", e -> Events.AddStudentHandler(this), "Ajouter un étudiant");
        HBox del = WidgetUtils.labelButton("Supprimer", "‒", e -> Events.RemoveStudentHandler(this),
                "Supprimer un étudiant");
        HBox union = WidgetUtils.labelButton("Forcer", "🔗", null, "Forcer une affectation");
        HBox disUnion = WidgetUtils.labelButton("Interdire", "⦸", null,
                "Interdire une affectation");

        etudiantsControls.getChildren().addAll(add, del, union, disUnion);
        return new TitledPane("Etudiants", etudiantsControls);
    }

    public void waitingForTutoring(boolean bool) {
        for (Node box : etudiantsControls.getChildren()) {
            ((Button) ((HBox) box).getChildren().get(2)).setDisable(bool);
        }
        for (Node box : triControls.getChildren()) {
            for (Node bt : ((VBox) ((HBox) box).getChildren().get(2)).getChildren()) {

                ((Button) bt).setDisable(bool);
            }
        }
        btAffect.setDisable(bool);
        affecter.setDisable(bool);
        ajouter.setDisable(bool);
        supprimer.setDisable(bool);
    }

    TitledPane initSortingControls() {

        ToggleGroup tg = new ToggleGroup();
        tg.selectedToggleProperty()
                .addListener((obs, old, newv) -> filterTutored = ((RadioButton) newv).getText().equals("Tutorés"));
        RadioButton tutorFilter = new RadioButton("Tuteurs");
        tutorFilter.setToggleGroup(tg);
        RadioButton tutoredFilter = new RadioButton("Tutorés");
        tutoredFilter.setToggleGroup(tg);
        tutoredFilter.setSelected(true);

        HBox filterGroup = new HBox(tutoredFilter, WidgetUtils.filler(), tutorFilter);

        triControls = new VBox();
        HBox nom = WidgetUtils.labelButton("Nom", "▼", "▲", new SortListHandler(this, "nom"), ASC, DESC);
        HBox prenom = WidgetUtils.labelButton("Prénom", "▼", "▲", new SortListHandler(this, "pre"), ASC, DESC);
        HBox avg = WidgetUtils.labelButton("Moyenne", "▼", "▲", new SortListHandler(this, "avg"), ASC, DESC);
        HBox abs = WidgetUtils.labelButton("Absences", "▼", "▲", new SortListHandler(this, "abs"), ASC, DESC);
        HBox motiv = WidgetUtils.labelButton("Motivation", "▼", "▲", new SortListHandler(this, "mot"), ASC, DESC);

        triControls.getChildren().addAll(nom, prenom, avg, abs, motiv);
        triControls.setSpacing(5);

        VBox triGroup = new VBox(filterGroup, WidgetUtils.filler(), triControls);
        return new TitledPane("Trier par", triGroup);
    }

    VBox initListControls() {
        VBox listControls = new VBox();
        listControls.setPadding(PAD_MIN);
        listControls.setPrefWidth(175);

        listControls.getChildren().addAll(initEtudiantsControls(), WidgetUtils.filler(), initSortingControls());

        return listControls;
    }

    HBox initListes() {
        HBox listes = new HBox();

        tutors.getSelectionModel().getSelectedItems().addListener(new SelectedStudentListener(this));
        tutored.getSelectionModel().getSelectedItems().addListener(new SelectedStudentListener(this));
        

        listes.getChildren().addAll(new VBox(new Label("Tutorés"), tutored), WidgetUtils.spacer(150), aretes,
                WidgetUtils.spacer(150), new VBox(new Label("Tuteurs"), tutors));
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
        btAffect.setOnAction(e -> Events.AffectationHandler(this));
        btAffect.setDisable(true);

        tb.setPadding(PAD_MIN);
        tb.getStyleClass().clear();
        tb.getItems().addAll(btAffect);
        return new TitledPane("Affectation", tb);
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
        slAvg.valueProperty().addListener(new SliderListener(this, slAvg, Coefficient.GRADES));
        slAbs.valueProperty().addListener(new SliderListener(this, slAbs, Coefficient.ABSENCES));
        slLvl.valueProperty().addListener(new SliderListener(this, slLvl, Coefficient.LEVEL));

        Label labelAvg = new Label("Moyenne");
        Label labelAbs = new Label("Absences");
        Label labelLvl = new Label("Motivation");

        Button resetCoef = new Button("↺");
        resetCoef.setTooltip(new Tooltip("Réinitialiser les coefficients"));
        resetCoef.setOnAction(e -> setCoefs());

        Button btShuffle = new Button("🔀");
        btShuffle.setTooltip(new Tooltip("Randomiser les coefficients"));
        btShuffle.setOnAction(e -> setCoefs(1));

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
        sessionBt.setOnAction(new AuthentificationHandler(this));
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
        save.setDisable(true);
        MenuItem quit = new MenuItem("Quitter");
        fichier.getItems().addAll(exporter, importer, new SeparatorMenuItem(), login, logout, new SeparatorMenuItem(),
                save, quit);

        // exporter.setOnAction(e->exporter());
        // importer.setOnAction(e->importer());
        login.setOnAction(new AuthentificationHandler(this));
        logout.setOnAction(new AuthentificationHandler(this));
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
        ajouter = new MenuItem("Ajouter un étudiant");
        supprimer = new MenuItem("Supprimer l'étudiant");
        MenuItem separator = new MenuItem("Trier par :");
        separator.setDisable(true);
        MenuItem triNom = new MenuItem("\tnom");
        MenuItem triPrenom = new MenuItem("\tprénom");
        MenuItem triNotes = new MenuItem("\tmoyennes");
        MenuItem triAbs = new MenuItem("\tabsences");
        MenuItem triMot = new MenuItem("\tmotivation");
        listes.getItems().addAll(ajouter, supprimer, new SeparatorMenuItem(), separator, triNom, triPrenom, triNotes,
                triAbs, triMot);

        ajouter.setOnAction(e -> Events.AddStudentHandler(this));
        supprimer.setOnAction(e -> Events.RemoveStudentHandler(this));
        triNom.setOnAction(new SortListHandler(this, "nom"));
        triPrenom.setOnAction(new SortListHandler(this, "pre"));
        triNotes.setOnAction(new SortListHandler(this, "avg"));
        triAbs.setOnAction(new SortListHandler(this, "abs"));
        triMot.setOnAction(new SortListHandler(this, "mot"));

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

        affecter.setOnAction(e -> Events.AffectationHandler(this));
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
        if (Login.loggedIn) {
            sessionBt.setText(logged + Login.account);
            login.setDisable(true);
            logout.setDisable(false);
            setProfilPhoto("file:res/img/root.jpg");
            cbSession.setPromptText(Login.account);
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
