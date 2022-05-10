package graphs.affectation;

import java.util.HashMap;
import java.util.Map;

public class Departement {
    String name;
    Map<Resource, Tutoring> tutorings= new HashMap<>();

    public Departement(String name){
        this.name = name;
    }
    
    void addTutoring(Resource resource, Tutoring tutoring){
        tutorings.put(resource, tutoring);
    }

    void addStudent(Resource resource, Student s){
        this.tutorings.get(resource).addStudent(s);
    }

    @Override
    public String toString() {
        return "Departement [Département " + name + ", tutorings=" + tutorings + "]";
    }

    
}
