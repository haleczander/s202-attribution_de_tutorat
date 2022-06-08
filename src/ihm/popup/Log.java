package ihm.popup;

import ihm.Interface;
import javafx.stage.Stage;

public abstract class Log extends PopUp{
    public static boolean loggedIn = false;
    public static String account;

    public static int sizeX = 300;
    public static int sizeY = 100;


    Log(Interface parent){
        super(parent);
        sizeX = Log.sizeX;
        sizeY = Log.sizeY;
    }


    abstract public void start(Stage s);
}
