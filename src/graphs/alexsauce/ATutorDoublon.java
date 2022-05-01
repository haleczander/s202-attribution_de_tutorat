package graphs.alexsauce;

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
        if (toStringShort) { return super.toString()+"(D)"; }
        return super.toString();
        
    }
}
