package oop;

import java.util.EnumMap;
import java.util.Map;

import graphs.affectation.Tutorat;

/**
 * Abstract class that represents a student.
 * 
 * @author Léopold V.
 * @author Alexandre H.
 */
public abstract class Student extends Person {
    protected int level;
    protected int absences;
    protected char motivation;
    protected Map<Resource, Double> grades = new EnumMap<>(Resource.class);

    protected static double defaultGrade = 9.99;
    protected static char defaultMotivation = 'B';
    protected static int defaultLevel = 1;
    protected static int defaultAbsences = 0;

    /**
     * Instantiate a student.
     * 
     * @param name       student's name.
     * @param level      student's level, between 1 and 3.
     * @param absences   student's absences.
     * @param motivation student's motivation, letter A, B or C.
     */
    protected Student(final String name, int level, int absences, char motivation) {
        super(name, true);
        setLevel(level);
        setAbsences(absences);
        setMotivation(motivation);
    }


    // ------------------------
    // Class methods
    // ------------------------
    public void addGrade(Resource resource, double grade) {
        this.grades.put(resource, grade);
    }
    public void addGrade(String resource, double grade) {
        addGrade(Resource.valueOf(resource), grade);
    }

    public double getWeight(Resource resource) {
        return this.getWeight(resource, Student.defaultGrade, Student.defaultAbsences, Teacher.getDefaultWeighting(),
                Teacher.getDefaultWeighting(), Teacher.getDefaultWeighting());
    }
    public abstract double getWeight(Tutorat tutorat);
    protected abstract double getWeight(Resource resource, double gradesAverage, double absencesAverage, double gradesWeight,
            double absencesWeight, double levelWeight);

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + absences;
        result = prime * result + level;
        result = prime * result + motivation;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Student other = (Student) obj;
        if (absences != other.absences)
            return false;
        if (level != other.level)
            return false;
        if (motivation != other.motivation)
            return false;
        return true;
    }

    @Override
    public String toString() {
        if (Person.shortName) {
            return super.toString();
        }
        return super.toString().substring(0, super.toString().length() - 1) + ", level= " + this.level + ", absences= "
                + this.absences + ", notes= " + this.grades.toString() + ", motivation= " + this.motivation + "]";
    }

    // ------------------------
    // Attribute getters & setters 
    // ------------------------
    protected void setLevel(int level) {
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

    public int getLevel() {
        return level;
    }

    public int getAbsences() {
        return absences;
    }

    public char getMotivation() {
        return motivation;
    }

    public Map<Resource, Double> getGrades() {
        return this.grades;
    }

    // Custom
    public double getGrade(Resource resource) {
        return this.grades.get(resource);
    }

    public void setGrade(Resource resource, double grade){
        this.grades.replace(resource, grade);
    }

    // Static getters & setters
    public static double getDefaultGrade() {
        return defaultGrade;
    }

    public static void setDefaultGrade(double defaultAverage) {
        Student.defaultGrade = defaultAverage;
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

}