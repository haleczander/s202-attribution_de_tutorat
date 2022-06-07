package utility;

import java.util.Set;

import graphs.affectation.Couple;
import oop.Student;
import oop.Tutor;
import oop.Tutored;

public class Couples {
    public static boolean containsCouple(Set<Couple> set, Tutored tutored, Tutor tutor){
         return Couples.get(set, tutored, tutor) != null ;
    }

    public static boolean containsStudent(Set<Couple> set, Student student){
        for (Couple couple : set) {
            if ( couple.contains(student) ){
                return true;
            }
        }
        return false;
    }


    public static boolean remove(Set<Couple> set, Tutored tutored, Tutor tutor){
        return set.remove(Couples.get(set, tutored, tutor));
    }

    public static boolean remove(Set<Couple> set, Couple couple){
        return set.remove(couple);
    }

    public static Couple get(Set<Couple> set, Tutored tutored, Tutor tutor){
        for (Couple couple : set) {
            if (couple.equals(tutored, tutor)){
                return couple;
            }
        }
        return null;
    }
}
