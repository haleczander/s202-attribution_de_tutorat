package graphs.tests;

public class Etudiant {
    public String name;
    public double average;
    public int level;

    /**
     * Instancie un étudiant
     * @param name
     * @param average
     * @param level
     */
    public Etudiant(String name, double average, int level) {
        this.name = name;
        this.average = average;
        this.level = level;
    }

    /**
     * Compare deux étudiants en fonction de leurs années puis de leurs moyennes si leurs années sont égales
     * Cette méthode ne fonctionne qu'avec des étudiants tutorants et sera à terme dans la classe correspondante.
     * @param etudiant l'étudiant comparant
     * @return 1 si l'étudiant comparant est meilleur, -1 si l'étudiant comparé est meilleur, 0 sinon.
     */
    public int compareToF(Etudiant etudiant) {
        if (this.level > etudiant.level)
            return -1;
        else if (this.level < etudiant.level)
            return 1;
        else {
            if (this.average > etudiant.average)
                return -1;
            else if (this.average < etudiant.average)
                return 1;
            else
                return 0;
        }
    }

    /**
     * Compare deux étudiants en fonction de leurs années puis de leurs moyennes si leurs années sont égales
     * Cette méthode ne fonctionne qu'avec des étudiants tutorés et sera à terme dans la classe correspondante. 
     * @param etudiant l'étudiant comparant
     * @return 1 si l'étudiant comparé est meilleur, -1 si l'étudiant comparant est meilleur, 0 sinon.
     */
    public int compareToN(Etudiant etudiant) {
        if (this.level > etudiant.level)
            return 1;
        else if (this.level < etudiant.level)
            return -1;
        else {
            if (this.average > etudiant.average)
                return 1;
            else if (this.average < etudiant.average)
                return -1;
            else
                return 0;
        }
    }

    // pour l'instant c'est dégueulasse et ça se répète mais c'est pas grave c'est du test

    @Override
    public String toString() {
        return "Etudiant [moyenne=" + average + ", niveau=" + level + ", nom=" + name + "]";
    }
    
    
}
