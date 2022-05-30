package graphs.affectation;

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

    public Tutor(String name, double average, int level, int absences, char motivation, int nbofTutored)
            throws IllegalArgumentException {
        super(name, average, level, absences, motivation);
        setNbOfTutored(level, nbofTutored);
    }

    private void setNbOfTutored(int level, int nbofTutored) throws IllegalArgumentException{

        if (level != 2 && level != 3) {
            this.level = Tutor.defaultLevel;
            this.nbofTutored = Tutor.defaultNbOfTutored;
            throw new IllegalArgumentException("Tutor students must be of level 2 or 3, number of tutorees set to default (" +Tutor.defaultNbOfTutored+").");
        } else if (level ==2 && nbofTutored ==2){
            this.nbofTutored = Tutor.defaultNbOfTutored;
            throw new IllegalArgumentException("Too much tutorees for  a level 2, number of tutorees set to default (" +Tutor.defaultNbOfTutored+").");
        }        
        else if (nbofTutored ==0){
            this.nbofTutored = getDefaultNbOfTutored(level);
        }
        else{
            this.nbofTutored = nbofTutored;
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
    public Tutor(String name, double average, int level, int absences, char motivation)
            throws IllegalArgumentException {
        this(name, average, level, absences, motivation, 0);
    }

    private int getDefaultNbOfTutored(int level){
        if (level == 3) {
            return Tutor.defaultNbOfTutoredThirdLevel;
        }
        return Tutor.defaultNbOfTutored;
    }

    @Override
    public String toString() {
        if (Student.shortString) {
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
    public void setWeight(double averageAvg, double absencesAvg) {
        this.weight = ((averageAvg / this.average) * averageWeighting // poids de la moyenne
                + (double) (3.0 / this.level) * levelWeighting // poids du niveau
                + Math.sqrt((1 + this.absences) / (1 + absencesAvg)) * absenceWeighting // poids des absences
        ) / 3
                * Tools.motivationValue(this.motivation); // motivation
    }

    
    public double getWeight() {
        return this.weight;
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
    protected TutorDuplicate duplicate() {
        return new TutorDuplicate(this);
    }
}
