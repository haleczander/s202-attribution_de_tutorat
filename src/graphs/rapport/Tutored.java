package graphs.rapport;

/**
 * Class that represents a tutored student.
 * 
 * @author Léopold V.
 */
public class Tutored extends Student {

    /**
     * Instantiate a tutored student.
     * 
     * @param name       tutored student's name.
     * @param average    tutored student's average, between 0 and 20.
     * @param absences   tutored student's absences.
     * @param motivation tutored student's motivation, letter A, B or C.
     * 
     * @throws IllegalArgumentException if average is not between 0 and 20.
     * @throws IllegalArgumentException if motivation is not between A, B or C.
     */
    public Tutored(String name, double average, int absences, char motivation) throws IllegalArgumentException {
        super(name, average, 1, absences, motivation);
    }

    /**
     * Compare the tutored student with another one on their average.
     * 
     * @param student Student to compare to.
     * @return 1 if {@code this} is better, -1 if Student to compare to is better, 0
     *         otherwise.
     */
    @Override
    public int compareTo(Student student) {
        if (this.average > student.average) {
            return 1;
        } else if (this.average < student.average) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public double getWeight(double averageAvg, double absencesAvg) {
        return (averageAvg / this.average * Student.averageWeighting + this.absences / absencesAvg)
                * Tools.motivationValue(this.motivation);
    }
}
