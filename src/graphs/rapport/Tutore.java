package graphs.rapport;

public class Tutore extends Etudiant {
    public Tutore(String name, double average, int level) {
        super(name, average, level);
    }

    /**
     * Compare deux étudiants tutorés en fonction de leurs moyennes
     * 
     * @param etudiant l'étudiant comparant
     * @return 1 si l'étudiant comparé est meilleur, -1 si l'étudiant comparant est
     *         meilleur, 0 sinon.
     */
    @Override
    public int compareTo(Etudiant etudiant) {
        if (this.average > etudiant.average) {
            return 1;
        }

        else if (this.average < etudiant.average) {
            return -1;
        }

        else {
            return 0;
        }

    }
}
