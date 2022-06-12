package ihm.utils;

import ihm.Interface;
import javafx.scene.control.ScrollBar;

public class DisplayUtils {
    public static void setTheme(Interface iface, String color) {   
        iface.scene.getRoot().setStyle("-fx-base:"+color);
    }

    public static void setScrollBars(Interface iface) {
        iface.scrollBarOne = (ScrollBar) iface.tutoredView.lookup(".scroll-bar:vertical");
        iface.scrollBarTwo = (ScrollBar) iface.tutorsView.lookup(".scroll-bar:vertical");
        iface.scrollBarThree = (ScrollBar) iface.couplesView.lookup(".scroll-bar:vertical");
        
        iface.couplesView.lookup(".scroll-bar").setStyle("-fx-scale:0;");
    }
}
