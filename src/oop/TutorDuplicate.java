package oop;

public class TutorDuplicate extends Tutor {
    private static double weightModifier = 1.5;
    private static char tutorDuplicateIdentifier = 'D';

    private TutorDuplicate(String name, double average, int level, int absences, char motivation)
            throws IllegalArgumentException {
        super(name + "("+TutorDuplicate.tutorDuplicateIdentifier+")", average, level, absences, motivation, 1);
    }

    public TutorDuplicate(Tutor t) {
        this(t.getName(), t.getAverage(), t.getLevel(), t.getAbsences(), t.getMotivation());
        this.weight = t.getWeight() * TutorDuplicate.weightModifier;
    }

    public double getWeight(Resource resource, double gradesAverage, int absencesAverage, double gradesWeight, double absencesWeight, double levelWeight){
        return super.getWeight(resource, gradesAverage, absencesAverage, gradesWeight, absencesWeight, levelWeight) * TutorDuplicate.weightModifier;
    }

    public static double getWeightModifier() {
        return weightModifier;
    }

    public static void setWeightModifier(double weightModifier) {
        TutorDuplicate.weightModifier = weightModifier;
    }

    @Override
    public boolean isDuplicate() {
        return true;
    }

    public static char getTutorDuplicateIdentifier() {
        return tutorDuplicateIdentifier;
    }

    public static void setTutorDuplicateIdentifier(char tutorDuplicateIdentifier) {
        TutorDuplicate.tutorDuplicateIdentifier = tutorDuplicateIdentifier;
    }

    
}
