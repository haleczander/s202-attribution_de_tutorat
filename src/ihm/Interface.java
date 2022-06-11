package ihm;

import java.util.Optional;
import java.util.Random;

import graphs.Couple;
import graphs.Tutoring;
import ihm.events.Events;
import ihm.events.SelectedStudentListener;
import ihm.events.SliderListener;
import ihm.events.SortListHandler;
import ihm.popup.AddStudent;
import ihm.popup.Login;
import ihm.utils.CoupleCellFactory;
import ihm.utils.StudentCellFactory;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import oop.Coefficient;
import oop.Resource;
import oop.Student;
import oop.Teacher;
import oop.Tutor;

public class Interface extends Application {
    // Glob
    public BorderPane root;
    Stage stage;
    Scene scene;
    public IHMDepartment dpt = new IHMDepartment();

    // list filter
    public boolean filterTutored = true;

    public ScrollBar scrollBarOne;
    public ScrollBar scrollBarTwo;
    public ScrollBar scrollBarThree;

    MenuBar menuBar;

    // Waiting for tutoring
    VBox etudiantsControls;
    VBox triControls;
    Button btAffect;
    MenuItem affecter;
    MenuItem ajouter;
    MenuItem supprimer;
    public CheckBox polyTutoring;
    Button coefResetBtn;
    Button coefShuffleBtn;

    // Tutoring infos;
    public Label tutoringTeacherLb = new Label();
    public Label tutoringAffectedLb = new Label();
    public Label tutoringForcedLb = new Label();
    public Label tutoringForbiddenLb = new Label();
    public Label tutoringAwaitingLb = new Label();
    public Label tutoringTutorNbLb = new Label();
    public Label tutoringTutoredNbLb = new Label();

    // Toolbar
    HBox affectationContainer = new HBox();
    HBox coefficientContainer = new HBox();
    VBox tutoringContainer = new VBox();

    // Login controls
    Label sessionBt = new Label();
    final String notLogged = "Non connecté";
    final String logged = "Connecté en tant que ";
    MenuItem login = new MenuItem("Se connecter");
    MenuItem logout = new MenuItem("Se déconnecter");
    Circle sessionPhoto = new Circle(50);

    // RightClickMenu
    ContextMenu rightClickMenu;
    public MenuItem rightClickMenuInfo = new MenuItem("Informations");
    public MenuItem rightClickMenuForce = new MenuItem("Forcer une affectation");
    public MenuItem rightClickMenuForbid = new MenuItem("Interdire une affectation");  
    public MenuItem rightClickMenuRemove = new MenuItem("Retirer du tutorat");
    public MenuItem rightClickMenuAdd = new MenuItem("Ajouter un étudiant");
    public MenuItem rightClickMenuReplace = new MenuItem("Supprimer et remplacer");

    public ComboBox<Resource> cbMatieres = new ComboBox<>();
    ComboBox<String> cbSession = new ComboBox<>();

    public Student selectedStudent;
    public boolean dragging;
    public boolean draggingTarget = false;

    //
    final double TOOLBAR_HEIGHT = 55;
    final String ASC = "Tri croissant";
    final String DESC = "Tri décroissant";

    int slMin = 0;
    int slMax = 5;

    // Undo redo coefs
    public Button redoCoef;
    public Button undoCoef;

    Slider slAvg = new Slider(slMin, slMax, 1);
    Slider slAbs = new Slider(slMin, slMax, 1);
    Slider slLvl = new Slider(slMin, slMax, 1);

    public ListView<Student> tutors = new ListView<>();
    public ListView<Student> tutored = new ListView<>();
    public ListView<Couple> couples = new ListView<>();

    public boolean affectationInterdite;


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
        
        synchronizeScrollBars();
    }

    public void setTheme(String color) {   
        scene.getRoot().setStyle("-fx-base:"+color);
        couples.setBackground(affectationContainer.getBackground());
        couples.setPadding(Insets.EMPTY);
        couples.getStyleClass().clear();
    }

    void synchronizeScrollBars() {
        scrollBarOne = (ScrollBar) tutored.lookup(".scroll-bar:vertical");
        scrollBarTwo = (ScrollBar) tutors.lookup(".scroll-bar:vertical");
        scrollBarThree = (ScrollBar) couples.lookup(".scroll-bar:vertical");
        
        couples.lookup(".scroll-bar").setStyle("-fx-scale:0;");
    }

    void initInterface() {
        root = new BorderPane();
        scene = new Scene(root, 850, 800);

        root.setTop(initTop());
        root.setCenter(initMain());
        root.setBottom(initFooter());
        root.setLeft(initListControls());

        keyBoardShortcuts();
        

        waitingForTutoring(true);
    }

    void keyBoardShortcuts() {
        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case MINUS, SUBTRACT, D, DELETE -> Events.RemoveStudentHandler(this);
                case ADD, PLUS, N -> Events.AddStudentHandler(this);
                case F5, ENTER -> Events.AffectationHandler(this);
                case F -> Events.AddForcedAffectationHandler(this, false);
                case I -> Events.AddForcedAffectationHandler(this, true);
                case ESCAPE -> close();
                case R -> {if (!selectedStudent.isTutored()) new AddStudent(this, (Tutor)selectedStudent);}
                default -> nothing();
            }

        });
    }

    void nothing() {
    }

    VBox initMain() {
        HBox titres = new HBox(
                WidgetUtils.spacer(200),
                new HBox(new Label("Tutorés ("), tutoringTutoredNbLb, new Label(")")),
                WidgetUtils.spacer(),
                new HBox(new Label("Tuteurs ("), tutoringTutorNbLb, new Label(")")),
                WidgetUtils.spacer(200));
        HBox lists = studentListsWidget();
        VBox main = new VBox(titres, lists);
        main.setPadding(PAD_MIN);
        VBox.setVgrow(lists, Priority.ALWAYS);
        

        return main;

    }

    ContextMenu rightClickMenu (){
        ContextMenu menu = new ContextMenu(
            rightClickMenuInfo,
            new SeparatorMenuItem(),
            rightClickMenuForce,
            rightClickMenuForbid,            
            new SeparatorMenuItem(),
            rightClickMenuReplace,
            rightClickMenuRemove,
            rightClickMenuAdd
            );

        // rightClickMenuInfo.setOnAction(e -> StudentInformationHandler(this));
        rightClickMenuForce.setOnAction(e -> Events.AddForcedAffectationHandler(this, false));
        rightClickMenuForbid.setOnAction(e -> Events.AddForcedAffectationHandler(this, true));
        rightClickMenuRemove.setOnAction(e -> Events.RemoveStudentHandler(this));
        rightClickMenuAdd.setOnAction(e -> Events.AddStudentHandler(this));
        rightClickMenuReplace.setOnAction(e -> new AddStudent(this, (Tutor)selectedStudent));
        return menu;
    }


    VBox initTop() {
        VBox retour = new VBox(initMenu(), initHeader(), horizontalToolbar());
        retour.setBorder(new Border(new BorderStroke(null, null, Color.DARKGRAY, null, null, null, BorderStrokeStyle.SOLID, null, null, null, Insets.EMPTY)));
        
        // retour.setBackground(MENU_BG_DARK);

        
        return retour;
    }

    void initData() {
        initDepartment();
        updateSession();
        // setLists();
    }

    void initDepartment() {

    }

    TitledPane listStudentControls() {
        etudiantsControls = new VBox();

        HBox add = WidgetUtils.labelButton("Ajouter", "+", e -> Events.AddStudentHandler(this), "Ajouter un étudiant");
        HBox del = WidgetUtils.labelButton("Supprimer (D)", "‒", e -> Events.RemoveStudentHandler(this),
                "Supprimer un étudiant");
        HBox union = WidgetUtils.labelButton("Forcer (F)", "🔗", e -> Events.AddForcedAffectationHandler(this, false), "Forcer une affectation");
        HBox disUnion = WidgetUtils.labelButton("Interdire", "⦸", e -> Events.AddForcedAffectationHandler(this, true),
                "Interdire une affectation");

        etudiantsControls.getChildren().addAll(add, del, union, disUnion);
        
        etudiantsControls.setPadding(PAD_MIN);
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
        for (MenuItem item : rightClickMenu.getItems()){
            item.setDisable(bool);
        }
        btAffect.setDisable(bool);
        affecter.setDisable(bool);
        ajouter.setDisable(bool);
        supprimer.setDisable(bool);
        polyTutoring.setDisable(bool);
        coefResetBtn.setDisable(bool);
        coefShuffleBtn.setDisable(bool);
        slAbs.setDisable(bool);
        slAvg.setDisable(bool);
        slLvl.setDisable(bool);
    }

    TitledPane listSortingControls() {

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
        triGroup.setPadding(PAD_MIN);
        return new TitledPane("Trier par", triGroup);
    }

    VBox initListControls() {
        VBox retour = new VBox();
        retour.setPadding(PAD_MIN);
        retour.setMaxWidth(300);

        retour.getChildren().addAll(listStudentControls(), WidgetUtils.filler(), listSortingControls());

        // retour.setBackground(MENU_BG_LIGHT);

        return retour;
    }

    HBox studentListsWidget() {
        HBox retour = new HBox();

        rightClickMenu = rightClickMenu();

        tutors.getSelectionModel().getSelectedItems().addListener(new
        SelectedStudentListener(this));
        tutored.getSelectionModel().getSelectedItems().addListener(new
        SelectedStudentListener(this));


        couples.setBackground(null);
        HBox.setHgrow(couples, Priority.ALWAYS);
        couples.setMaxWidth(175);
        couples.setDisable(true);
        
        couples.setPadding(Insets.EMPTY);


        VBox.setVgrow(tutors, Priority.ALWAYS);
        VBox.setVgrow(tutored, Priority.ALWAYS);
        VBox.setVgrow(couples, Priority.ALWAYS);
        
        // tutored.setStyle("-fx-base:"+WidgetUtils.getRgb(Color.LIGHTGRAY));

        tutored.setContextMenu(rightClickMenu);
        tutors.setContextMenu(rightClickMenu);

        tutored.setCellFactory(new StudentCellFactory(this));
        tutors.setCellFactory(new StudentCellFactory(this));
        couples.setCellFactory(new CoupleCellFactory(this));

        retour.getChildren().addAll(WidgetUtils.spacer(), tutored, WidgetUtils.spacer(50),couples, WidgetUtils.spacer(50), tutors, WidgetUtils.spacer());
        retour.setAlignment(Pos.CENTER);
        retour.setPadding(PAD_MIN);


        return retour;
    }

    HBox horizontalToolbar() {
        HBox retour = new HBox();
        // retour.maxWidthProperty().bind(root.prefWidthProperty());
        retour.getChildren().addAll(coefficientWidgets(), WidgetUtils.spacer(), affectationWidgets(), WidgetUtils.spacer(), tutoringWidgets());

        for (Node n : retour.getChildren()) {
            if (n instanceof TitledPane){
                Pane p = ((Pane) ((TitledPane) n).getContent());
                p.setMinHeight(TOOLBAR_HEIGHT);
                p.setPadding(PAD_MIN);

            }
        }

        retour.setPadding(PAD_MIN);
        // retour.setBackground(MENU_BG_LIGHT);
        return retour;
    }

    TitledPane tutoringWidgets() {

        TutoringUtils.updateTutoringInfos(this);

        polyTutoring = new CheckBox("Polytutorat");
        polyTutoring.setTooltip(new Tooltip("Les tuteurs s'occupent de plusieurs tutorés."));
        polyTutoring.selectedProperty().addListener((a, o, n) -> dpt.currentTutoring.setPolyTutor(n));

        VBox affectations = new VBox(
                new Label("Affectations : "),
                new HBox(WidgetUtils.filler(15), new Label("Affectés"), WidgetUtils.spacer(), tutoringAffectedLb),
                new HBox(WidgetUtils.filler(15), new Label("En attente"), WidgetUtils.spacer(), tutoringAwaitingLb),
                new HBox(WidgetUtils.filler(15), new Label("Forcées"), WidgetUtils.spacer(), tutoringForcedLb),
                new HBox(WidgetUtils.filler(15), new Label("Interdites"), WidgetUtils.spacer(), tutoringForbiddenLb));
        tutoringContainer.getChildren().addAll(
                new HBox(new Label("Responsable : "), tutoringTeacherLb),
                polyTutoring,
                affectations);
        tutoringContainer.setSpacing(10);
        TitledPane retour = new TitledPane("Informations sur le tutorat", tutoringContainer);
        retour.setExpanded(false);
        return retour;
    }

    TitledPane affectationWidgets() {
        btAffect = new Button("Affecter !");
        btAffect.setTooltip(new Tooltip("Lancer l'affectation"));
        btAffect.setOnAction(e -> Events.AffectationHandler(this));
        btAffect.setDisable(true);

        affectationContainer.setPadding(PAD_MIN);
        affectationContainer.getStyleClass().clear();
        affectationContainer.getChildren().addAll(btAffect);

        affectationContainer.setAlignment(Pos.CENTER);

        return new TitledPane("Affectation", affectationContainer);
    }

    TitledPane coefficientWidgets() {

        Slider[] sliders = { slAvg, slAbs, slLvl };
        for (Slider slider : sliders) {
            slider.setMaxWidth(75);
            slider.setMajorTickUnit(1);
            slider.setShowTickLabels(true);
        }
        slAvg.valueProperty().addListener(new SliderListener(this, slAvg, Coefficient.GRADES));
        slAbs.valueProperty().addListener(new SliderListener(this, slAbs, Coefficient.ABSENCES));
        slLvl.valueProperty().addListener(new SliderListener(this, slLvl, Coefficient.LEVEL));

        Label slAvgLb = new Label("Moyenne");
        Label slAbsLb = new Label("Absences");
        Label slLblLb = new Label("Motivation");

        coefResetBtn = new Button("↺");
        coefResetBtn.setTooltip(new Tooltip("Réinitialiser les coefficients"));
        coefResetBtn.setOnAction(e -> setCoefs());

        coefShuffleBtn = new Button("🔀");
        coefShuffleBtn.setTooltip(new Tooltip("Randomiser les coefficients"));
        coefShuffleBtn.setOnAction(e -> setCoefs(1));

        undoCoef = new Button("Undo");
        undoCoef.setOnAction(e -> SliderListener.restoreCoefs(this, dpt.currentTutoring.getTeacher()));
        undoCoef.setDisable(true);

        redoCoef = new Button("Redo");
        redoCoef.setOnAction(e -> SliderListener.reEditCoefs(this, dpt.currentTutoring.getTeacher()));
        redoCoef.setDisable(true);

        coefficientContainer.getChildren().addAll(slAvgLb, slAvg, slAbsLb, slAbs, slLblLb, slLvl, coefResetBtn,
                coefShuffleBtn);//, undoCoef, redoCoef);

        coefficientContainer.setAlignment(Pos.CENTER);
        return new TitledPane("Coefficients", coefficientContainer);
    }

    HBox initHeader() {
        HBox retour = new HBox();
        retour.setPadding(PAD_MIN);

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

        retour.getChildren().addAll(TutoringUtils.initMatieres(this), logo, session);
        for (Node n : retour.getChildren()) {
            n.minHeight(Double.MAX_VALUE);
        }

        
        retour.setBorder(new Border(new BorderStroke(null, null, Color.DARKGRAY, null, null, null, BorderStrokeStyle.SOLID, null, null, null, Insets.EMPTY)));

        return retour;
    }

    HBox initFooter() {
        HBox retour = new HBox();
        ToggleButton nightMode = new ToggleButton("🌙");
        ToggleButton dayMode = new ToggleButton("☀");
        ToggleGroup tGroup = new ToggleGroup();
        nightMode.setToggleGroup(tGroup);
        nightMode.setTooltip(new Tooltip("Passer en thème sombre"));
        dayMode.setToggleGroup(tGroup);
        dayMode.setTooltip(new Tooltip("Passer en thème jour"));
        nightMode.setOnAction(e -> setTheme("black"));
        dayMode.setOnAction(e ->setTheme("#ececec"));
       
        retour.setOnMouseClicked(e -> Events.AuthentificationHandler(this));
        retour.getChildren().addAll(sessionBt, WidgetUtils.spacer(), nightMode, dayMode);

        retour.setPadding(PAD_MIN);
        retour.getStyleClass().addAll(cbMatieres.getStyleClass());
        return retour;
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
        menuBar =new MenuBar(initMenuFichier(), initMenuSelection(), initMenuListes(), initMenuAffectation(),
                initMenuEdition());
        return menuBar;
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
        login.setOnAction(e -> Events.AuthentificationHandler(this));
        logout.setOnAction(e -> Events.AuthentificationHandler(this));
        // save.setOnAction(e->save());
        quit.setOnAction(e -> close());

        return fichier;
    }

    Menu initMenuSelection() {
        Menu selection = new Menu("Selection");
        MenuItem forcer = new MenuItem("Forcer l'affectation");
        MenuItem interdire = new MenuItem("Interdire l'affection");
        selection.getItems().addAll(forcer, interdire);

        forcer.setOnAction(e-> Events.AddForcedAffectationHandler(this, false));
        interdire.setOnAction(e->Events.AddForcedAffectationHandler(this, true));

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
        affecter = new MenuItem("Lancer l'affectation");
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
        if (!(avg == 1 && avg == abs && avg == lvl)){
            SliderListener.saveCoefs(dpt.currentTutoring.getTeacher());
        }
    }

    void setCoefs() {
        setCoefs(1, 1, 1);
    }

    public void setCoefs(Teacher teacher){
        setCoefs(teacher.getAverageWeighting(), teacher.getAbsenceWeighting(), teacher.getLevelWeighting());
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
