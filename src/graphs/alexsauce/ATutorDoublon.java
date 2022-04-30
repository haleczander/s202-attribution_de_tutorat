package alexsauce;

public class ATutorDoublon extends ATutor{
    private final ATutor REFERENCE;

    public ATutorDoublon(ATutor t) {
        super(t);
        this.REFERENCE=t;
    }

    public boolean isDoublon(){
        return true;
    }
    
    /**Indiciatif de doublon "(D)" */
    @Override
    public String toString() {
        return super.toString()+"(D)";
    }
}
