package oop;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import graphs.affectation.Assignment;
import graphs.affectation.Student;

public class Departement {
    private String name;
    private List<Student> students;
    private Map<Resource, Assignment> tutorings = new EnumMap<>(Resource.class);
    // j'ai modifié parce que EnumMap est bien pour travailler avec les enums
    // -> lire la doc (c'est presque la même chose)

    public Departement(String name) {
        this.name = name;
    }

    public void addTutoring(Resource resource, Assignment assignment) {
        tutorings.put(resource, assignment);
    }

    public void addStudent(Resource resource, Student s) {
        this.tutorings.get(resource).addStudent(s);
    }

    @Override
    public String toString() {
        return "Departement [Département " + name + ", tutorings=" + tutorings + "]";
    }

    public String getName() {
        return name;
    }

    public List<Student> getStudents() {
        return students;
    }
}
