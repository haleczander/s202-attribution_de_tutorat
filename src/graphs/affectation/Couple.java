package graphs.affectation;

import oop.Student;
import oop.Tutor;
import oop.Tutored;

public class Couple {
    private Tutored tutored;
    private Tutor tutor;

    public Couple(Tutored tutored, Tutor tutor) {
        this.tutored = tutored;
        this.tutor = tutor;
    }

    public boolean contains(Student student) {
        return student.equals(this.getTutor()) || student.equals(this.getTutored());
    }

    @Override
    public String toString() {
        return "(" + tutored + ", " + tutor + ")";
    }

    public boolean equals(Tutored tutored, Tutor tutor) {
        return this.tutored.equals(tutored) && this.tutor.equals(tutor);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((tutor == null) ? 0 : tutor.hashCode());
        result = prime * result + ((tutored == null) ? 0 : tutored.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Couple other = (Couple) obj;
        if (tutor == null) {
            if (other.tutor != null)
                return false;
        } else if (!tutor.equals(other.tutor))
            return false;
        if (tutored == null) {
            if (other.tutored != null)
                return false;
        } else if (!tutored.equals(other.tutored))
            return false;
        return true;
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
