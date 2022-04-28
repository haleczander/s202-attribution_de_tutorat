package graphs.rapport;

public class Tutor extends Student {
    public int nbofTutored;

    public Tutor(String name, double average, int level, int nbofTutored) {
        super(name, average, level);
        this.nbofTutored = nbofTutored;
    }

    @Override
    public String toString() {
        return super.toString() + " " + nbofTutored;
    }

    /**
     * Compare two tutor students on their level, then on their average if they have
     * equal level.
     * 
     * @param etudiant Student to compare to.
     * @return 1 if Student to compare to is better, -1 if {@code this} is better, 0
     *         otherwise.
     */
    @Override
    public int compareTo(Student etudiant) {
        if (this.level > etudiant.level) {
            return -1;
        } else if (this.level < etudiant.level) {
            return 1;
        } else {
            if (this.average > etudiant.average) {
                return -1;
            } else if (this.average < etudiant.average) {
                return 1;
            } else {
                return 0;
            }
        }
    }
}
