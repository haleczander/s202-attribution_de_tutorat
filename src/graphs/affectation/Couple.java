package graphs.affectation;

import java.util.Set;

import oop.Student;
import oop.Tutor;
import oop.Tutored;

public class Couple{
    private Tutored tutored;
    private Tutor tutor;

    public Couple(Tutored tutored, Tutor tutor) {
        this.tutored = tutored;
        this.tutor = tutor;
    }

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

    public Tutored getTutored() {
        return tutored;
    }

    public void setTutored(Tutored tutored) {
        this.tutored = tutored;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    @Override
    public String toString() {
        return "(" + tutored + ", " + tutor + ")";
    }    

    
}
