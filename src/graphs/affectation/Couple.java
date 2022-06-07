package graphs.affectation;

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

    public boolean contains(Student student){
        return student.equals((Student)this.getTutor()) || student.equals((Student)this.getTutored());
    }

    @Override
    public String toString() {
        return "(" + tutored + ", " + tutor + ")";
    }    

    public boolean equals(Tutored tutored, Tutor tutor){
        return this.tutored.equals(tutored) && this.tutor.equals(tutor);
    }

    // ------------------------
    // Attribute getters & setters 
    // ------------------------   
    public Tutored getTutored() {
        return tutored;
    }

    public Tutor getTutor() {
        return tutor;
    }

}
