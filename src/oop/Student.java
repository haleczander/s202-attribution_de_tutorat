package oop;

import java.util.EnumMap;
import java.util.Map;

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
    protected double weight;
    protected Map<Resource, Double> grades = new EnumMap<>(Resource.class);

    

    protected static double defaultAverage = 15;
    protected static char defaultMotivation = 'B';
    protected static int defaultLevel = 1;
    protected static int defaultAbsences = 0;

    public Map<Resource, Double> getGrades() {
        return this.grades;
    }

    public static double getDefaultAverage() {
        return defaultAverage;
    }

    public static void setDefaultAverage(double defaultAverage) {
        Student.defaultAverage = defaultAverage;
    }

    public static char getDefaultMotivation() {
        return defaultMotivation;
    }

    public static void setDefaultMotivation(char defaultMotivation) {
        Student.defaultMotivation = defaultMotivation;
    }

    public static int getDefaultLevel() {
        return defaultLevel;
    }

    public static void setDefaultLevel(int defaultLevel) {
        Student.defaultLevel = defaultLevel;
    }

    public static int getDefaultAbsences() {
        return defaultAbsences;
    }

    public static void setDefaultAbsences(int defaultAbsences) {
        Student.defaultAbsences = defaultAbsences;
    }

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

    public void setAverage(double average) {
        if (average < 0 || average > 20) {
            this.average = Student.defaultAverage;
        } else {
            this.average = average;
        }
    }

    public void setLevel(int level) {
        if (level < 1 || level > 3) {
            this.level = Student.defaultLevel;
        } else {
            this.level = level;
        }
    }

    public void setAbsences(int absences) {
        if (absences >= 0) {
            this.absences = absences;
        } else {
            this.absences = Student.defaultAbsences;
        }
    }

    public void setMotivation(char motivation) {
        if (motivation != 'A' && motivation != 'B' && motivation != 'C') {
            this.motivation = defaultMotivation;
        } else {
            this.motivation = motivation;
        }
    }

    protected Student(final String name, double average, int level, int absences, char motivation) {
        super(name, true);
        setAverage(average);
        setLevel(level);
        setAbsences(absences);
        setMotivation(motivation);
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
    public abstract void setWeight(double averageAvg, double absencesAvg, Teacher teacher);

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
        if (Person.shortName) {
            return super.toString();
        }
        return super.toString().substring(0, super.toString().length()-1) + ", level= "+this.level + ", absences= "+this.absences + ", notes= " + this.grades.toString() +"]";

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
    public double getWeight(){
        return this.weight;
    }
    
    
     public double getWeight(Resource resource) {
        // return weight;
        return this.getWeight(resource, Student.defaultAverage, Student.defaultAbsences, Teacher.getDefaultWeighting(), Teacher.getDefaultWeighting(), Teacher.getDefaultWeighting());
    }

    public abstract double getWeight(Resource resource, double gradesAverage, int absencesAverage, double gradesWeight, double absencesWeight, double levelWeight);

    /**
     * Manually sets a weight for the student. Used when duplicating students.
     * 
     * @param weight new weight of the student.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }
}