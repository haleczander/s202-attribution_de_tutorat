package oop;

import java.util.Collection;
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

    public Departement(String name) {
        this(name, new HashSet<>(), new HashSet<>());
    }

    public Departement(String name, Set<Student> students, Set<Teacher> teachers) {
        this.name = name;
        this.students = students;
        this.teachers = teachers;
    }

    public Assignment getTutoring(Resource resource){
        return tutorings.get(resource);
    }

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

    public void addTutoring(Resource resource) {
        tutorings.put(resource, new Assignment(resource));
    }
    public void addTutoring(Resource resource, Teacher teacher) {
        tutorings.put(resource, new Assignment(teacher, resource));
    }

    public void registerStudents(Resource resource) {
        for (Student student : students) {
            if (student.grades.containsKey(resource)) {
                registerStudent(resource, student);
            }
        }
    }

    public void registerStudents(Resource resource, Collection<Student> students) {
        for (Student student : students) {
            registerStudent(resource, student);
        }
    }

    public void registerStudent(Resource resource, Student student) {
        if (!student.grades.containsKey(resource)) {
            student.addGrade(resource, Student.getDefaultGrade());
        }
        tutorings.get(resource).addStudent(student);
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

    public int getNbOfStudents() {
        return this.students.size();
    }

    public int getNbOfteachers() {
        return this.teachers.size();
    }

    public int getNbOfTutorings() {
        return this.tutorings.size();
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


    public Set<Teacher> getTeachers() {
        return Set.copyOf(teachers);
    }

    public Map<Resource, Assignment> getTutorings() {
        return Map.copyOf(tutorings);
    }
}
