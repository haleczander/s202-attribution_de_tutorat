package graphs.rapport;

public class Tuteur extends Etudiant {
    public int nbTutores;

    public Tuteur(String name, double average, int level, int nbTutores) {
        super(name, average, level);
        this.nbTutores = nbTutores;
    }

    @Override
    public String toString() {
        return super.toString() + " " + nbTutores;
    }

    /**
     * Compare deux étudiants tuteurs en fonction de leurs années puis de leurs
     * moyennes si leurs années sont égales
     * 
     * @param etudiant l'étudiant comparant
     * @return 1 si l'étudiant comparant est meilleur, -1 si l'étudiant comparé est
     *         meilleur, 0 sinon.
     */
    @Override
    public int compareTo(Etudiant etudiant) {
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
