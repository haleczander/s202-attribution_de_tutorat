package alexsauce;

public class ATutor extends AStudent{
    private int capacity = 1, nbOfCopies=1;
    private boolean isDoublon = false;
    private static boolean polyTutor = false;

    public ATutor(String name, double grades, int level) {
        super(name, grades, level);
    }

    public ATutor(ATutor t, int nbOfDoublon){
        this(t.getName(),t.getGrades(),t.getLevel());
        this.nbOfCopies=t.getNbOfCopies();
        this.isDoublon = true;
    }

    @Override
    protected double computeWeight() {
        return (20/(this.getLevel()*this.getGrades()))/nbOfCopies;
    }  

    /**Getter & Setter de la capacité de tutorat 
     * getter : si le poly tutorat est desactivé, retournera 1
     * setter : désactivé en cas de mono tutorat
    */
    public int getCapacity() {
        return (ATutor.polyTutor) ? capacity : 1;
    }
    
    public boolean increaseCapacity() {
        if (!ATutor.polyTutor) { return false;}
        this.capacity++;
        return true;
    }

    public int getNbOfCopies(){
        return this.nbOfCopies;
    }
    public void incrementNbOfCopies(){
        this.nbOfCopies++;
    }
    
    /**Permet d'activer ou désactiver la capacité de prendre en charge plusieurs tutorés */
    public static void polyTutorToggle(){
        ATutor.polyTutor= !ATutor.polyTutor;
    }
    public static boolean polyTutorState(){
        return ATutor.polyTutor;
    }

    /**Prise en compte de l'affichage des doublons */
    @Override
    public String toString() {
        if (!isDoublon) return super.toString();
        return super.toString()+"(D)";
    }
}
