package graphs.alexsauce;

public class Tutor extends Student{
    private int capacity=1;

    public Tutor(String name, double grades, int level) {
        super(name, grades, level);
    }

    @Override
    protected double computeWeight() {
        return 20/(this.getLevel()*this.getGrades());
    }  

    /**Getter & Setter de la capacité de tutorat */
    public int getCapacity() {
        return capacity;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
