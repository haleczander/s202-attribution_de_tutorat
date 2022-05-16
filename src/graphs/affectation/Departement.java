package graphs.affectation;

import java.util.HashMap;
import java.util.Map;

public class Departement {
    String name;
    Map<Resource, Assignment> tutorings= new HashMap<>();

    public Departement(String name){
        this.name = name;
    }
    
    void addTutoring(Resource resource, Assignment assignment){
        tutorings.put(resource, assignment);
    }

    void addStudent(Resource resource, Student s){
        this.tutorings.get(resource).addStudent(s);
    }

    @Override
    public String toString() {
        return "Departement [Département " + name + ", tutorings=" + tutorings + "]";
    }

    
}
