package ihm.popup;

import ihm.Interface;
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;

public abstract class PopUp extends Application {
    Stage stage;
    Interface parent;
    int sizeX;
    int sizeY;

    public PopUp(Interface parent) {        
        this.stage = new Stage();
        this.parent = parent;
        stage.initModality(Modality.APPLICATION_MODAL);
    }
    
    abstract public void start(Stage stage);

}
