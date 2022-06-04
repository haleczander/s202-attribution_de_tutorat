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

    /**
     * Instantiate a tutor.
     * 
     * @param name        tutor's name.
     * @param average     tutor's average, between 0 and 20.
     * @param level       tutor's level, between 1 and 3.
     * @param absences    tutor's absences.
     * @param motivation  tutor's motivation, letter A, B or C.
     * @param nbofTutored number of tutored student the tutor can take in charge. If
     *                    level is not 3, field will be set to 1 in any case.
     * 
     * @throws IllegalArgumentException if average is not between 0 and 20.
     * @throws IllegalArgumentException if level is not between 2 and 3.
     * @throws IllegalArgumentException if motivation is not A, B or C.
     */

    protected static int defaultLevel = 2;
    protected static int defaultNbOfTutored = 1;
    protected static int defaultNbOfTutoredThirdLevel = 2;

    

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



    public Tutor(String name, double average, int level, int absences, char motivation, int nbofTutored){//            throws IllegalArgumentException {
        super(name, average, level, absences, motivation);
        setNbOfTutored(level, nbofTutored);
    }


    private void setNbOfTutored(int level, int nbofTutored){// throws IllegalArgumentException{
        if (level == 3) {
            if (nbofTutored == 1) {
                this.nbofTutored = 1;
            }
            else{
                this.nbofTutored = Tutor.defaultNbOfTutoredThirdLevel;
            }
        }
        else{
            setLevel(Tutor.defaultLevel);
            this.nbofTutored = Tutor.defaultNbOfTutored;
        }
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
     * 
     * @throws IllegalArgumentException if average is not between 0 and 20.
     * @throws IllegalArgumentException if level is not between 2 and 3.
     * @throws IllegalArgumentException if motivation is not A, B or C.
     */
    public Tutor(String name, double average, int level, int absences, char motivation){//           throws IllegalArgumentException {
        this(name, average, level, absences, motivation, 0);
    }

    @Override
    public String toString() {
        if (Person.shortName) {
            return super.toString();
        } else {
            return super.toString() + " " + nbofTutored;
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
    public void setWeight(double averageAvg, double absencesAvg, Teacher teacher) {
        this.weight = ((averageAvg / this.average) * teacher.getAverageWeighting() // poids de la moyenne
                + (double) (3.0 / this.level) * teacher.getLevelWeighting() // poids du niveau
                + Math.sqrt((1 + this.absences) / (1 + absencesAvg)) * teacher.getAbsenceWeighting() // poids des absences
                ) / 3
                * Tools.motivationValue(this.motivation); // motivation
    }
    
    /**
     * Returns a copy of a tutor, using the same Tutor class.
     * 
     * @param toAppend character appended to the name of the new tutor to differenciate them.
     * @return a new tutor.
     * 
     * @see #duplicate()
     * @see TutorDuplicate
     */
    public Tutor copyOf(char toAppend) {
        return new Tutor(this.getName() + "(" + toAppend + ")", average, level, absences, motivation, 1);
    }
    public Tutor copyOf(){
        return copyOf('D');
    }


    /**
     * Returns {@code true} if the tutor is a duplicate of another (which is never the case).
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
        return new TutorDuplicate(this);
    }
}
