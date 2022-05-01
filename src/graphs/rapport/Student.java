package graphs.rapport;

/**
 * Abstract class that represents a student.
 */
public abstract class Student {
    private final String name;
    protected double average;
    protected int level;
    protected static boolean shortString = true;

    /**
     * Instantiate a student.
     * 
     * @param name    student's name.
     * @param average student's average.
     * @param level   student's level.
     */
    protected Student(final String name, final double average, final int level) {
        this.name = name;
        this.average = average;
        this.level = level;
    }

    /**
     * Method that compares 2 students.
     * 
     * @param student the student to compare to.
     * @return -1, 1 or 0 depending on the comparision.
     */
    public abstract int compareTo(Student student);

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(average);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + level;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Student other = (Student) obj;
        if (Double.doubleToLongBits(average) != Double.doubleToLongBits(other.average)) {
            return false;
        }
        if (level != other.level) {
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