package ihm.events;

import ihm.Interface;
import ihm.popup.AffectationGlimpse;

public class AffectationGlimpseManager {
    Interface iface;

    public AffectationGlimpseManager(Interface iface) {
        this.iface = iface;
        if (iface.dpt.currentTutoring != null){
            new AffectationGlimpse(iface);
        }
        else {
            // Utils.bleep();
        }
        
    }

    
}
