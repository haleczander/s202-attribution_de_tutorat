package alexsauce;

import java.util.ArrayList;
import java.util.Map;

import fr.ulille.but.sae2_02.graphes.Arete;
import fr.ulille.but.sae2_02.graphes.CalculAffectation;
import fr.ulille.but.sae2_02.graphes.GrapheNonOrienteValue;

public class AAssignement {
    private Map<ATutor,AStudent> pairs;

    private ArrayList<AStudent> tutors;
    private ArrayList<AStudent> tutored;

    private ArrayList<AStudent> waitingTutors = new ArrayList<>();
    private ArrayList<AStudent> waitingTutored = new ArrayList<>();

    public AAssignement(ArrayList<AStudent> tutors, ArrayList<AStudent> tutored){
        this.tutors=tutors;
        this.tutored=tutored;

        
        
        if ( tutors.size() > tutored.size() ) { whoIsWaiting(tutors,tutored.size(),waitingTutors);}
        if ( tutors.size() < tutored.size() ) { 
            if (ATutor.polyTutorState()) {
                 if (!polyTutoring(tutors, tutored.size())){
                    whoIsWaiting(tutored,tutors.size(),waitingTutored);
                 }
            } else {
                whoIsWaiting(tutored,tutors.size(),waitingTutored);
            }
        }
        

    }

    private void whoIsWaiting(ArrayList<AStudent> origin, int targetSize, ArrayList<AStudent> destination){
        for (int i = origin.size(); i < targetSize; i++){
            destination.add(origin.get(i));
        }
        origin.removeAll(destination);
    }

    /**Retourne TRUE s'il y a assez de doublons tuteurs par rapport aux tutorés */
    private boolean polyTutoring(ArrayList<AStudent> tutors, int targetSize){
        for (ATutor t : tutors) {
            
        }
        return false;
    }
}
