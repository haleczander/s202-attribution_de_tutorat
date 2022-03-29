package graphs.tests;

public class Etudiant {
    public String nom;
    public double moyenne;
    public int niveau;

    /**
     * Instancie un étudiant
     * @param nom
     * @param moyenne
     * @param niveau
     */
    public Etudiant(String nom, double moyenne, int niveau) {
        this.nom = nom;
        this.moyenne = moyenne;
        this.niveau = niveau;
    }

    /**
     * Compare deux étudiants en fonction de leurs années puis de leurs moyennes si leurs années sont égales
     * Cette méthode ne fonctionne qu'avec des étudiants tutorants et sera à terme dans la classe correspondante.
     * @param etudiant l'étudiant comparant
     * @return 1 si l'étudiant comparant est meilleur, -1 si l'étudiant comparé est meilleur, 0 sinon.
     */
    public int compareToF(Etudiant etudiant) {
        if (this.niveau > etudiant.niveau)
            return -1;
        else if (this.niveau < etudiant.niveau)
            return 1;
        else {
            if (this.moyenne > etudiant.moyenne)
                return -1;
            else if (this.moyenne < etudiant.moyenne)
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
        if (this.niveau > etudiant.niveau)
            return 1;
        else if (this.niveau < etudiant.niveau)
            return -1;
        else {
            if (this.moyenne > etudiant.moyenne)
                return 1;
            else if (this.moyenne < etudiant.moyenne)
                return -1;
            else
                return 0;
        }
    }

    // pour l'instant c'est dégueulasse et ça se répète mais c'est pas grave c'est du test

    @Override
    public String toString() {
        return "Etudiant [moyenne=" + moyenne + ", niveau=" + niveau + ", nom=" + nom + "]";
    }
    
    
}
