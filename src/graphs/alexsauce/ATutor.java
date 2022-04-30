package graphs.alexsauce;

public class ATutor extends AStudent{
    private int capacity = 1;
    private static boolean polyTutor = false;

    public ATutor(String name, double grades, int level) {
        super(name, grades, level);
    }

    public ATutor(ATutor t){
        this(t.getName(),t.getGrades(),t.getLevel());
    }

    @Override
    protected double computeWeight() {
        return 20 / ( this.getLevel() * this.getGrades() );
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


    public ATutor duplicate(){
        this.setWeight(this.getWeight() * 2);
        return new ATutorDoublon(this);
    }
    public boolean isDoublon(){
        return false;
    }
    
    /**Permet d'activer ou désactiver la capacité de prendre en charge plusieurs tutorés */
    public static void polyTutorToggle(){
        ATutor.polyTutor= !ATutor.polyTutor;
    }
    public static boolean polyTutorState(){
        return ATutor.polyTutor;
    }

}
