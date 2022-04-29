package graphs.rapport;

public class Tutored extends Student {
    public Tutored(String name, double average, int level) {
        super(name, average, level);
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
