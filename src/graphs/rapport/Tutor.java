package graphs.rapport;

/**
 * Class that represents a tutor student.
 * 
 * @author Léopold V.
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
    public Tutor(String name, double average, int level, int absences, char motivation, int nbofTutored)
            throws IllegalArgumentException {
        super(name, average, level, absences, motivation);
        if (level == 1) {
            throw new IllegalArgumentException("Tutor students cannot have a level of 1.");
        } else if (level == 3) {
            this.nbofTutored = nbofTutored;
        } else {
            this.nbofTutored = 1;
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
        this(name, average, level, absences, motivation, 2);
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
        this.weight =  (
            (averageAvg / this.average) * averageWeighting                              //poids de la moyenne
            + (double)(3.0 / this.level) * levelWeighting                               //poids du niveau
            + Math.sqrt((1 + this.absences) / (1 + absencesAvg)) * absenceWeighting     //poids des absences
            ) / 3
            * Tools.motivationValue(this.motivation);                                   // motivation
    }

    public double getWeight(){
        return this.weight;
    }

    public Tutor copyOf(char toAppend) {
        return new Tutor(this.getName() + "(" + toAppend + ")", average, level, absences, motivation, 1);
    }

    public boolean isDuplicate(){
        return false;
    }

    protected TutorDuplicate duplicate(){
        return new TutorDuplicate(this);
    }
}
