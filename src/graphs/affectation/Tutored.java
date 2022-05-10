package graphs.affectation;

/**
 * Class that represents a tutored student.
 * 
 * @author Léopold V.
 * @author Alexandre H.
 */
public class Tutored extends Student {

    /**
     * Instantiate a tutored student.
     * 
     * @param name       tutored student's name.
     * @param average    tutored student's average, between 0 and 20.
     * @param absences   tutored student's absences.
     * @param motivation tutored student's motivation, letter A, B or C.
     * 
     * @throws IllegalArgumentException if average is not between 0 and 20.
     * @throws IllegalArgumentException if motivation is not between A, B or C.
     */
    public Tutored(String name, double average, int absences, char motivation) throws IllegalArgumentException {
        super(name, average, 1, absences, motivation);
    }

    @Override
    public void setWeight(double averageAvg, double absencesAvg) {
        // System.out.println((this.average / averageAvg) * averageWeighting);
        // System.out.println(Math.sqrt((1 + this.absences) / (1 + absencesAvg)) * absenceWeighting);
        // System.out.println();
        this.weight = ((this.average / averageAvg) * averageWeighting
                + Math.sqrt((1 + this.absences) / (1 + absencesAvg)) * absenceWeighting) / 2
                * Tools.motivationValue(this.motivation);
    }
}