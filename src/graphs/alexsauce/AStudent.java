package graphs.alexsauce;

/**Classe abstraite student comprenant
 * un nom
 * une moyenne
 * une année
 */
public abstract class AStudent implements Comparable<AStudent> {
    private String name;
    private double grades;
    private int level;
    private double weight;

    protected static boolean toStringShort=false;

    
    
    public AStudent(String name, double grades, int level) {
        this.name = name;
        this.grades = grades;
        this.level = level;
        this.weight = computeWeight();
    }

    /**Getters seulement */
    public String getName() {
        return name;
    }

    public double getGrades() {
        return grades;
    }

    public int getLevel() {
        return level;
    }

    public double getWeight() {
        return weight;
    }

    /**Setter du poids, pour forcer une affectation */
    public void setWeight(double weight) {
        this.weight = weight;
    }
    /**Setter de la fonction d'affichage réduite */
    public void setToStringShort(){
        AStudent.toStringShort=true;
    }

    
    /** toString() avec 
     * un mode court : retourne this.name
     * un mode long : nom + level + notes
     */
    @Override
    public String toString() {
        if (AStudent.toStringShort) { return this.getName(); }
        return "Student [name=" + this.name + ", level=" + this.level + ", grades=" + this.grades  + "]";
    }
    
    /**Fonction du calcul du poids, 
     * à définir selon tuteur ou tutoré  
     */
    protected abstract double computeWeight();

    /**Compare deux étudiants en fonction de leurs poids respectifs */
    public int compareTo(AStudent o){
        return (int)((this.weight-o.getWeight())*100);
    }

}
