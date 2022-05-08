package graphs.affectation;

public class TutorDuplicate extends Tutor {
    private static double weightModifier = 1.5;

    private TutorDuplicate(String name, double average, int level, int absences, char motivation)
            throws IllegalArgumentException {
        super(name+"(D)", average, level, absences, motivation, 1);
    }

    public TutorDuplicate(Tutor t){
        this(t.getName(), t.getAverage(), t.getLevel(), t.getAbsences(), t.getMotivation());
        this.weight=t.getWeight()*TutorDuplicate.weightModifier;
    }

    
    public static double getWeightModifier() {
        return weightModifier;
    }

    public static void setWeightModifier(double weightModifier) {
        TutorDuplicate.weightModifier = weightModifier;
    }

    public boolean isDuplicate(){return true;}
    
}
