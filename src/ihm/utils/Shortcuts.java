package ihm.utils;

import ihm.Interface;
import ihm.events.Events;
import ihm.popup.AddStudent;
import oop.Tutor;

public class Shortcuts {

    public Shortcuts(Interface iface) {
        init(iface);
    }

    public static void init(Interface iface) {
        iface.scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case MINUS, SUBTRACT, D, DELETE -> Events.RemoveStudentHandler(iface);
                case ADD, PLUS, N -> Events.AddStudentHandler(iface);
                case F5, ENTER -> Events.AffectationHandler(iface);
                case F -> Events.AddForcedAffectationHandler(iface, false);
                case I -> Events.AddForcedAffectationHandler(iface, true);
                case ESCAPE -> iface.close();
                case R -> {if (!iface.selectedStudent.isTutored()) new AddStudent(iface, (Tutor)iface.selectedStudent);}
                default -> nothing();
            }
        });
    }
    private static void nothing() {
    }

}