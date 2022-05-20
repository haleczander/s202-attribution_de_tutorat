package graphs.affectation;

/**
 * Abstract class that represents a student.
 * 
 * @author Léopold V.
 * @author Alexandre H.
 */
public abstract class Student extends Person implements Comparable<Student> {
    protected double average;
    protected int level;
    protected int absences;
    protected char motivation;
    public double weight;

    protected static boolean shortString = true;
    protected static double averageWeighting = 1;
    protected static double levelWeighting = 1;
    protected static double absenceWeighting = 1;

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
        super(name);
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
     * Method that compares the student to another.
     * 
     * @param student the student to compare to.
     * @return -1, 1 or 0 depending on the comparison.
     */
    @Override
    public int compareTo(Student student) {
        return (int) (100 * (this.weight - student.getWeight()));
    }

    /**
     * Calculates the weight of the student.
     * 
     * @param averageAvg  average of all students.
     * @param absencesAvg average of absences of all students.
     * @return the weight of the student.
     */
    protected abstract void setWeight(double averageAvg, double absencesAvg);

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
        temp = Double.doubleToLongBits(weight);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        return Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight);
    }

    @Override
    public String toString() {
        if (shortString) {
            return this.getName();
        } else {
            return "Student [nom=" + FORENAME + " " + SURNAME + ", moyenne=" + average + ", niveau=" + level + "]";
        }
    }

    /**
     * Gets the student's name.
     * 
     * @return the student's name.
     */
    public String getName() {
        return FORENAME + " " + SURNAME;
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

    /**
     * Gets the student's absence count.
     * 
     * @return number of times the student was absent.
     */
    public int getAbsences() {
        return absences;
    }

    /**
     * Gets the student's motivation.
     * 
     * @return character representing the student's motivation.
     */
    public char getMotivation() {
        return motivation;
    }

    /**
     * Gets the student's weight.
     * 
     * @return the weight of the student.
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Sets the weighting of the average of students for weight computing. Default
     * value is 1.
     * 
     * @param levelWeighting new weighting of students level.
     */
    public static void setLevelWeighting(double levelWeighting) {
        Student.levelWeighting = levelWeighting;
    }

    /**
     * Sets the weighting of the level of students for weight computing. Default
     * value is 1.
     * 
     * @param averageWeighting new weighting of students average.
     */
    public static void setAverageWeighting(double averageWeighting) {
        Student.averageWeighting = averageWeighting;
    }

    /**
     * Sets the weighting of the level of students for weight computing. Default
     * value is 1.
     * 
     * @param absenceWeighting new weighting of students absence.
     */
    public static void setAbsenceWeighting(double absenceWeighting) {
        Student.absenceWeighting = absenceWeighting;
    }

    /**
     * Manually sets a weight for the student. Used when duplicating students.
     * 
     * @param weight new weight of the student.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }
}