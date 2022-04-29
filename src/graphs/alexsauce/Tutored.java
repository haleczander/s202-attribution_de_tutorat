package graphs.alexsauce;

public class Tutored extends Student{

    public Tutored(String name, double grades, int level) {
        super(name, grades, level);
    }

    @Override
    protected double computeWeight() {
        return this.getGrades()/20;
    }
    
}
