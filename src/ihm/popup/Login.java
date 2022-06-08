package ihm.popup;

import ihm.Interface;
import ihm.utils.WidgetUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Login extends Log{
    
    static boolean loggingIn = false;

    PasswordField mdpTf = new PasswordField();
    TextField idTf = new TextField();
    Label erreur = new Label();

    public Login (Interface parent) {
        super (parent);   
        this.parent = parent;
        start(stage);
    }

    private class LoginChecker implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e) {
            Log.loggedIn = idTf.getText().equals("root") && mdpTf.getText().equals("root");
            if (!Log.loggedIn){
                erreur.setText("⚠ Login ou Mot de passe erroné !");
                erreur.setTextFill(Color.RED);
            }
            else {
                Log.account = idTf.getText();
                parent.updateSession();
                stage.close();
            }
        }
    }

    public void start(Stage stage) {
        stage.setOnCloseRequest(e-> parent.updateSession());
        VBox root = new VBox();
        stage.setTitle("Connexion");
        Scene scene = new Scene(root, sizeX, sizeY); 
        stage.setScene(scene); 
        stage.setResizable(false);

        Label idLb = new Label("Identifiant");
        idTf.setPromptText("prenom.nom");
        // idTf.setFocusTraversable(false);
        HBox id = new HBox(idLb, WidgetUtils.spacer(), idTf);

        Label mdpLb = new Label("Mot de passe");

        mdpTf.setPromptText("motdepasse");
        // mdpTf.setFocusTraversable(false);
        HBox mdp = new HBox(mdpLb, WidgetUtils.spacer(), mdpTf);


        Button btn = new Button("Se connecter");
        btn.setOnAction(new LoginChecker());
        root.getChildren().addAll(erreur, id, mdp, btn);

        root.requestFocus();

        stage.show();        
    }
}
