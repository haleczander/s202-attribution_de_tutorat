package oop;

import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import graphs.affectation.Assignment;

public class Departement {
    private String name;
    private Set<Student> students;
    private Set<Teacher> teachers;
    private Map<Resource, Assignment> tutorings = new EnumMap<>(Resource.class);
    // j'ai modifié parce que EnumMap est bien pour travailler avec les enums
    // -> lire la doc (c'est presque la même chose)


    public Teacher getTeacher(Resource resource) {
        return tutorings.get(resource).getTeacher();
    }

    public void setTeacher(Resource resource, Teacher teacher) {
        if (!teachers.contains(teacher)){
            teachers.add(teacher);
        }
        tutorings.get(resource).setTeacher(teacher);
    }


    public Departement(String name) {
        this(name, new HashSet<Student>(), new HashSet<Teacher>());
    }

    public Departement(String name, Set<Student> students, Set<Teacher> teachers){
        this.name = name;
        this.students = students;
        this.teachers = teachers;
    }

    public void addStudent(Student student){
        this.students.add(student);
    }

    public void addStudent(Set<Student> students){
        this.students.addAll(students);
    }

    public void addTeacher(Teacher teacher){
        this.teachers.add(teacher);
    }

    public void addTeacher(Set<Teacher> teachers){
        this.teachers.addAll(teachers);
    }

    public void add(Person person) throws IllegalArgumentException{
        if (person.isTeacher()) {
            addTeacher((Teacher)person);
        }
        else if (person.isStudent()){
            addStudent((Student)person);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    public void addTutoring(Resource resource) {
        tutorings.put(resource, new Assignment(students));
    }

    public void registerStudents(Resource resource){
        for (Student student : students) {
            if (student.grades.containsKey(resource)){
                tutorings.get(resource).addStudent(student);
            }
        }
    }

    public void registerStudent(Resource resource, Student student){
        tutorings.get(resource).addStudent(student);
    }
    public void registerStudent(Resource resource, Set<Student> students){
        tutorings.get(resource).addStudent(students);
    }


    @Override
    public String toString() {
        return "Departement [Département " + name + ", tutorings=" + tutorings + "]";
    }

    public String getName() {
        return name;
    }

    public Set<Student> getStudents() {
        return students;
    }
}
