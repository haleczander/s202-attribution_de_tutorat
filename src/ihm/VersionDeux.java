package ihm;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VersionDeux extends Application{
    //Glob

    Button sessionBt = new Button("Non connecté");
    ComboBox<String> cbMatieres = new ComboBox<>();
    Image profilPhoto = new Image ("file:res/img/jc2.jpeg");
    ComboBox<String> cbSession = new ComboBox<>();


    @Override
    public void start(Stage stage) throws Exception {        
        BorderPane root = new BorderPane();

        MenuBar menu = initMenu();
        root.setTop(menu);

        VBox main = initMain();
        root.setCenter(main);

        HBox footer = initFooter();
        root.setBottom(footer);

        stage.setScene(new Scene(root, 800, 500));
        stage.show();
        
        
    }

    VBox initMain(){
        VBox main = new VBox();

        HBox header = new HBox();
        cbMatieres.setPromptText("Choisir une matière");

        HBox session = new HBox();
        ImageView logoImgView = new ImageView(new Image("file:res/img/jc2.jpeg"));
        ImageView sessionImgView = new ImageView(profilPhoto);
        cbSession.setPromptText("Non connecté");
        session.getChildren().addAll(cbSession, sessionImgView);

        header.getChildren().addAll(cbMatieres, logoImgView, session);
        

        HBox options = new HBox();

        HBox listes = new HBox();

        main.getChildren().addAll(header, options, listes);

        return main;
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

    public static void main(String[] args) {
        Application.launch(args);
    }
    
}
