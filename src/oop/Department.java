package oop;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import graphs.Tutoring;

public class Department {
    private String name;
    private Set<Student> students;
    private Set<Teacher> teachers;
    private Map<Resource, Tutoring> tutorings = new EnumMap<>(Resource.class);

    public Department(String name) {
        this(name, new HashSet<>(), new HashSet<>());
    }

    public Department(String name, Set<Student> students, Set<Teacher> teachers) {
        this.name = name;
        this.students = students;
        this.teachers = teachers;
    }

    // ------------------------
    // Class methods
    // ------------------------

    // Size of Department
    public int getNbOfStudents() {
        return this.students.size();
    }

    public int getNbOfteachers() {
        return this.teachers.size();
    }

    public int getNbOfTutorings() {
        return this.tutorings.size();
    }

    // Adding Persons to the Department
    public boolean addStudent(Student student) {
        return this.students.add(student);
    }

    public boolean addStudent(Collection<Student> students) {
        return this.students.addAll(students);
    }

    public boolean addTeacher(Teacher teacher) {
        return this.teachers.add(teacher);
    }

    public boolean addTeacher(Collection<Teacher> teachers) {
        return this.teachers.addAll(teachers);
    }

    public boolean add(Person person) {
        if (person.isStudent()) {
            return addStudent((Student) person);
        } else {
            return addTeacher((Teacher) person);
        }
    }    
    public boolean add(Collection<Person> persons){
        boolean add = true;
        for (Person person : persons) {
            add &= add(person);
        }
        return add;
    }

    // Tutoring    
    public void newTutoring(Resource resource) {
        tutorings.put(resource, new Tutoring(resource));
    }

    public void newTutoring(Resource resource, Teacher teacher) {
        tutorings.put(resource, new Tutoring(teacher, resource));
    }

    public Tutoring getTutoring(Resource resource){
        return tutorings.get(resource);
    }

    public Teacher getTeacher(Resource resource) {
        return tutorings.get(resource).getTeacher();
    }
    
    public void setTeacher(Resource resource, Teacher teacher) {
            if (!teachers.contains(teacher)) {
                teachers.add(teacher);
            }
            tutorings.get(resource).setTeacher(teacher);
        }
    
    // Adding Students to a Tutoring
    public void registerStudent(Resource resource) {
        for (Student student : students) {
            if (student.grades.containsKey(resource)) {
                checkedRegisterStudent(resource, student);
            }
        }
    }

    public void registerStudent(Resource resource, Collection<Student> students) {
        for (Student student : students) {
            registerStudent(resource, student);
        }
    }

    public void registerStudent(Resource resource, Student student) {
        if (!student.grades.containsKey(resource)) {
            student.addGrade(resource, Student.getDefaultGrade());
        }
        checkedRegisterStudent(resource, student);
    }

    private void checkedRegisterStudent(Resource resource, Student student){
        tutorings.get(resource).addStudent(student);
    }

    public String toString() {
        return "Departement [Département " + name + ", tutorings=" + tutorings + "]";
    }

    // ------------------------
    // Attribute getters & setters 
    // ------------------------
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Teacher> getCopyOfTeachers() {
        return Set.copyOf(teachers);
    }

    public Set<Student> getStudents() {
        return students;
    }

    public Map<Resource, Tutoring> getTutorings() {
        return Map.copyOf(tutorings);
    }

}
