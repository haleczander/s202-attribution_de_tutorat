package graphs.rapport;

/**
 * Class that represents a tutor student.
 * 
 * @author Léopold V.
 */
public class Tutor extends Student {
    private int nbofTutored;

    /**
     * Instantiate a tutor.
     * 
     * @param name        tutor's name.
     * @param average     tutor's average, between 0 and 20.
     * @param level       tutor's level, between 1 and 3.
     * @param absences    tutor's absences.
     * @param motivation  tutor's motivation, letter A, B or C.
     * @param nbofTutored number of tutored student the tutor can take in charge.
     * 
     * @throws IllegalArgumentException if average is not between 0 and 20.
     * @throws IllegalArgumentException if level is not between 1 and 3.
     * @throws IllegalArgumentException if motivation is not A, B or C.
     */
    public Tutor(String name, double average, int level, int absences, char motivation, int nbofTutored)
            throws IllegalArgumentException {
        super(name, average, level, absences, motivation);
        if (level == 3) {
            this.nbofTutored = nbofTutored;
        } else {
            this.nbofTutored = 1;
        }
    }

    @Override
    public String toString() {
        if (Student.shortString) {
            return super.toString();
        } else {
            return super.toString() + " " + nbofTutored;
        }
    }

    /**
     * Compare the tutor with another one on their level, then on their average if
     * they have equal level.
     * 
     * @param student Student to compare to.
     * @return 1 if Student to compare to is better, -1 if {@code this} is better, 0
     *         otherwise.
     */
    @Override
    public int compareTo(Student student) {
        if (this.level > student.level) {
            return -1;
        } else if (this.level < student.level) {
            return 1;
        } else {
            if (this.average > student.average) {
                return -1;
            } else if (this.average < student.average) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Gets the number of tutored students the tutor can take in charge.
     * 
     * @return number of students.
     */
    public int getNbofTutored() {
        return nbofTutored;
    }

    @Override
    public double weight(double averageAvg, double absencesAvg, double averageWeighting, double levelWeighting) {
        return (averageAvg / this.average * averageWeighting + 3 / this.level * levelWeighting
                + this.absences / absencesAvg) * Tools.motivationValue(this.motivation);
    }
}
