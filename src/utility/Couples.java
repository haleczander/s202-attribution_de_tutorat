package utility;

import java.util.Collection;

import graphs.Couple;
import oop.Student;
import oop.Tutor;
import oop.Tutored;

public final class Couples {

    private Couples() {
        throw new UnsupportedOperationException("Cannot instantiate utility class.");
    }

    public static boolean containsCouple(Collection<Couple> sets, Tutored tutored, Tutor tutor) {
        return Couples.get(sets, tutored, tutor) != null;
    }

    public static boolean containsStudent(Collection<Couple> couples, Student student) {
        for (Couple couple : couples) {
            if (couple.contains(student)) {
                return true;
            }
        }
        return false;
    }

    public static boolean remove(Collection<Couple> couples, Tutored tutored, Tutor tutor) {
        return couples.remove(Couples.get(couples, tutored, tutor));
    }

    public static boolean remove(Collection<Couple> couples, Couple couple) {
        return couples.remove(couple);
    }

    public static Couple get(Collection<Couple> couples, Tutored tutored, Tutor tutor) {
        for (Couple couple : couples) {
            if (couple.equals(tutored, tutor)) {
                return couple;
            }
        }
        return null;
    }
}
