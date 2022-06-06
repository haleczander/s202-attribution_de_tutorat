package graphs.affectation;

import oop.Tutor;
import oop.Tutored;

public class Couple{
    private Tutored tutored;
    private Tutor tutor;

    public Couple(Tutored tutored, Tutor tutor) {
        this.tutored = tutored;
        this.tutor = tutor;
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
