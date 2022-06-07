package utility;

import java.util.Set;

import graphs.affectation.Couple;
import oop.Student;
import oop.Tutor;
import oop.Tutored;

public class Couples {
    public static boolean exists(Set<Couple> set, Tutored tutored, Tutor tutor){
        for (Couple couple : set) {
            // System.out.println(couple +" --> " + tutored +" & " + tutor + " : " + (tutored.equals(couple.getTutored()) && tutor.equals(couple.getTutor())));
            if (tutored.equals(couple.getTutored()) && tutor.equals(couple.getTutor())) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsStudent(Set<Couple> set, Student student){
        for (Couple couple : set) {
            if ( student.equals((Student)couple.getTutor()) || student.equals((Student)couple.getTutored()) ){
                return true;
            }
        }
        return false;
    }

    public static boolean remove(Set<Couple> set, Tutored tutored, Tutor tutor){
        Couple toRemove = null;
        for (Couple couple : set) {
            if (tutored.equals(couple.getTutored()) && tutor.equals(couple.getTutor())){
                toRemove = couple;
                break;
            }
        }
        return set.remove(toRemove);
    }
}
