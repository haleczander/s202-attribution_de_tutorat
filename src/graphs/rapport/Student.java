package graphs.rapport;

/**
 * Abstract class that represents a student.
 */
public abstract class Student {
    private final String name;
    protected double average;
    protected int level;
    protected int absences;
    protected char motivation;
    protected static boolean shortString = true;
    public double weight;

    /**
     * Instantiate a student.
     * 
     * @param name       student's name.
     * @param average    student's average, between 0 and 20.
     * @param level      student's level, between 1 and 3.
     * @param absences   student's absences.
     * @param motivation student's motivation, letter A, B or C.
     * 
     * @throws IllegalArgumentException if average is not between 0 and 20.
     * @throws IllegalArgumentException if level is not between 1 and 3.
     * @throws IllegalArgumentException if motivation is not A, B or C.
     */
    protected Student(final String name, double average, int level, int absences, char motivation)
            throws IllegalArgumentException {
        this.name = name;
        if (average < 0 || average > 20) {
            throw new IllegalArgumentException("Average must be between 0 and 20.");
        } else {
            this.average = average;
        }
        if (level < 1 || level > 3) {
            throw new IllegalArgumentException("Level must be between 1 and 3.");
        } else {
            this.level = level;
        }
        this.absences = absences;
        if (motivation != 'A' && motivation != 'B' && motivation != 'C') {
            throw new IllegalArgumentException("Motivation must be A, B or C");
        } else {
            this.motivation = motivation;
        }
    }

    /**
     * Method that compares 2 students.
     * 
     * @param student the student to compare to.
     * @return -1, 1 or 0 depending on the comparison.
     */
    protected abstract int compareTo(Student student);

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + absences;
        long temp;
        temp = Double.doubleToLongBits(average);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + level;
        result = prime * result + motivation;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Student other = (Student) obj;
        if (absences != other.absences) {
            return false;
        }
        if (Double.doubleToLongBits(average) != Double.doubleToLongBits(other.average)) {
            return false;
        }
        if (level != other.level) {
            return false;
        }
        if (motivation != other.motivation) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /**
     * Returns a simple String representation of a student.
     * 
     * @return the student's name.
     */
    protected String simpleToString() {
        return this.getName();
    }

    @Override
    public String toString() {
        if (shortString) {
            return shortToString();
        } else {
            return "Student [nom=" + name + ", moyenne=" + average + ", niveau=" + level + "]";
        }
    }

    private String shortToString() {
        return this.name;
    }

    /**
     * Gets the student's name.
     * 
     * @return the student's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the student's average.
     * 
     * @return the student's average.
     */
    public double getAverage() {
        return average;
    }

    /**
     * Gets the student's level.
     * 
     * @return the student's level.
     */
    public int getLevel() {
        return level;
    }
}