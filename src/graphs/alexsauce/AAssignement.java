package graphs.alexsauce;

import java.util.ArrayList;
import java.util.Map;

public class AAssignement {
    private Map<ATutor,AStudent> pairs;

    private ArrayList<AStudent> tutors;
    private ArrayList<AStudent> tutored;


    public AAssignement(ArrayList<AStudent> tutors, ArrayList<AStudent> tutored){
        if (tutors.size() == tutored.size()){
            this.tutors=tutors;
            this.tutored=tutored;
        }
        else if ( tutors.size() > tutored.size() ) {
            this.tutors = (ArrayList<AStudent>) tutors.subList(0, tutored.size());}
        else { 
            // if (ATutor.polyTutorState()) {
            //      if (!polyTutoring(tutors, tutored.size())){
            //         whoIsWaiting(tutored,tutors.size());
            //      }
            // } else {
            //     whoIsWaiting(tutored,tutors.size());
            // }
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
        for (int i = 0; i < tutors.size(); i++) {
            
        }
        return false;
    }
}
