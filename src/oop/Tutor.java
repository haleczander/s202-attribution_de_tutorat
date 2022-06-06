package oop;

import graphs.affectation.Tools;

/**
 * Class that represents a tutor student.
 * 
 * @author Léopold V.
 * @author Alexandre H.
 */
public class Tutor extends Student {
    private int nbofTutored;

    protected static int defaultLevel = 2;
    protected static int defaultNbOfTutored = 1;
    protected static int defaultNbOfTutoredThirdLevel = 2;

    /**
     * Instantiate a tutor.
     * 
     * @param name        tutor's name.
     * @param level       tutor's level, between 1 and 3.
     * @param absences    tutor's absences.
     * @param motivation  tutor's motivation, letter A, B or C.
     * @param nbofTutored number of tutored student the tutor can take in charge. If
     *                    level is not 3, field will be set to 1 in any case.
     */
    public Tutor(String name, int level, int absences, char motivation, int nbofTutored) {
        super(name, level, absences, motivation);
        setNbOfTutored(level, nbofTutored);
    }

    /**
     * Instatiate a tutor with a default number of tutor to take in charge (1 if
     * level is 2, 2 if level is 3.)
     * 
     * @param name       tutor's name.
     * @param average    tutor's average, between 0 and 20.
     * @param level      tutor's level, between 1 and 3.
     * @param absences   tutor's absences.
     * @param motivation tutor's motivation, letter A, B or C.
     */
    public Tutor(String name, int level, int absences, char motivation) {
        this(name, level, absences, motivation, 0);
    }

    private void setNbOfTutored(int level, int nbofTutored) {
        if (level == 3) {
            if (nbofTutored == 1) {
                this.nbofTutored = 1;
            } else {
                this.nbofTutored = Tutor.defaultNbOfTutoredThirdLevel;
            }
        } else {
            setLevel(Tutor.defaultLevel);
            this.nbofTutored = Tutor.defaultNbOfTutored;
        }
    }

    public int getNbofTutored() {
        return nbofTutored;
    }

    public double getWeight(Resource resource, double gradesAverage, int absencesAverage, double gradesWeight,
            double absencesWeight, double levelWeight) {
        return ((gradesAverage / this.grades.get(resource)) * gradesWeight
                + (double) (3.0 / this.level) * levelWeight
                + Math.sqrt((1 + this.absences) / (1 + absencesAverage)) * absencesWeight)
                * Tools.motivationValue(this.motivation)
                / 3; // Le tout divisé par le nombre de paramètres pour rester autour de 1
    }

    /**
     * Returns {@code true} if the tutor is a duplicate of another (which is never
     * the case).
     * 
     * @return false.
     */
    public boolean isDuplicate() {
        return false;
    }

    /**
     * Returns a duplicate of the tutor using the TutorDuplicate class.
     * 
     * @return the duplicate of the tutor.
     * @see TutorDuplicate
     */
    public TutorDuplicate duplicate() {
        if (this.nbofTutored > 1) {
            return new TutorDuplicate(this);
        }
        return null;
    }

    @Override
    public String toString() {
        if (Person.shortName) {
            return super.toString();
        } else {
            return super.toString() + " " + nbofTutored;
        }
    }

    

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + nbofTutored;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public static int getDefaultLevel() {
        return defaultLevel;
    }

    public static void setDefaultLevel(int defaultLevel) {
        Tutor.defaultLevel = defaultLevel;
    }

    public static int getDefaultNbOfTutored() {
        return defaultNbOfTutored;
    }

    public static void setDefaultNbOfTutored(int defaultNbOfTutored) {
        Tutor.defaultNbOfTutored = defaultNbOfTutored;
    }

    public static int getDefaultNbOfTutoredThirdLevel() {
        return defaultNbOfTutoredThirdLevel;
    }

    public static void setDefaultNbOfTutoredThirdLevel(int defaultNbOfTutoredThirdLevel) {
        Tutor.defaultNbOfTutoredThirdLevel = defaultNbOfTutoredThirdLevel;
    }
}
