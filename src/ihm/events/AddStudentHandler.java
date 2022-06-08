package ihm.events;

import ihm.Interface;
import ihm.popup.AddStudent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AddStudentHandler implements EventHandler<ActionEvent> {
    Interface iface;

    public AddStudentHandler(Interface iface) {
        this.iface = iface;
    }

    @Override
    public void handle(ActionEvent e) {
        new AddStudent(iface);
        
    }
    
    
}
