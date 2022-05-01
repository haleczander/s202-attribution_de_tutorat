package alexsauce;

public class ATutored extends AStudent{

    public ATutored(String name, double grades, int level) {
        super(name, grades, level);
    }

    @Override
    protected double computeWeight() {
        return this.getGrades()/20;
    }
    
}
