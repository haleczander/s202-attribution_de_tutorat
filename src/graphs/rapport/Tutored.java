package graphs.rapport;

/**
 * Class that represents a tutored student.
 */
public class Tutored extends Student {

    /**
     * Instantiate a tutored student.
     * 
     * @param name    tutored student's name.
     * @param average tutored student's average.
     */
    public Tutored(String name, double average) {
        super(name, average, 1);
    }

    /**
     * Compare two tutored students on their average.
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
}
