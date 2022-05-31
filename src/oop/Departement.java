package oop;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import graphs.affectation.Assignment;

public class Departement {
    private String name;
    private Set<Student> students;
    private Map<Resource, Assignment> tutorings = new EnumMap<>(Resource.class);
    // j'ai modifié parce que EnumMap est bien pour travailler avec les enums
    // -> lire la doc (c'est presque la même chose)

    public Departement(String name) {
        this(name, new HashSet<Student>());
    }

    public Departement(String name, Set<Student> students){
        this.name = name;
        this.students = students;
    }

    public void addStudent(Student student){
        this.students.add(student);
    }

    public void addStudent(Set<Student> students){
        this.students.addAll(students);
    }

    public void addTutoring(Resource resource) {
        tutorings.put(resource, new Assignment(students));
    }

    public void initTutoring()

    public void addTutoringStudent(Resource resource, Student s) {
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
