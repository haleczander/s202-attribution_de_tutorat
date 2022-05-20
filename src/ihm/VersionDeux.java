package ihm;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.Slider;
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

public class VersionDeux extends Application{
    //Glob
    Scene scene;
    Button sessionBt = new Button("Non connecté");
    ComboBox<String> cbMatieres = new ComboBox<>();
    Image profilPhoto;
    ComboBox<String> cbSession = new ComboBox<>();

    int slMin = 0;
    int slMax = 5;

    Slider slAvg = new Slider(slMin, slMax, 1);
    Slider slAbs = new Slider(slMin, slMax, 1);
    Slider slMot = new Slider(slMin, slMax, 1);

    //Padding
    Insets PAD_MIN = new Insets(5);


    @Override
    public void start(Stage stage) throws Exception {        
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 500);

        MenuBar menu = initMenu();
        root.setTop(menu);

        VBox main = initMain();
        root.setCenter(main);

        HBox footer = initFooter();
        root.setBottom(footer);

        stage.setScene(scene);
        stage.show();
        
        
    }

    VBox initMain(){
        VBox main = new VBox();

        HBox header = initHeader();        

        HBox options = initOptions();

        HBox listes = new HBox();

        main.getChildren().addAll(header, options, listes);

        return main;
    }

    void setSlValue(Slider s, Number newVal){
        {((Slider)s).setValue( Math.ceil((double)newVal*2) / 2.0d);}
    }
    
    HBox initOptions(){
        HBox options = new HBox();
        options.setAlignment(Pos.CENTER);

        HBox coefs = new HBox();
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

        coefs.getChildren().addAll(labelAvg, slAvg, labelAbs, slAbs, labelMot, slMot, resetCoef);


        options.getChildren().addAll(coefs);
        return options;
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
