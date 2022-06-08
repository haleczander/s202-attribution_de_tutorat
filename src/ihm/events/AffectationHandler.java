package ihm.events;

import ihm.Interface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AffectationHandler implements EventHandler<ActionEvent> {
    Interface iface;

    public AffectationHandler(Interface iface){
        this.iface = iface;
    }

    @Override
    public void handle(ActionEvent e) {
        iface.aretes.getItems().clear();
        iface.dpt.currentTutoring.affectations();
        System.out.println(iface.dpt.currentTutoring.affectations);
        iface.aretes.getItems().addAll(iface.dpt.currentTutoring.affectations);
         
    }
    
}
