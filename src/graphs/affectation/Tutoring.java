package graphs.affectation;

import java.util.ArrayList;
import java.util.List;

public class Tutoring {
    private Teacher teacher;

    private List<Tutor> tutors;
    private List<Tutored> tutored;

    private Assignment assignement;


    public Tutoring(Teacher teacher, List<Tutor> tutors,
            List<Tutored> tutored) {
        this.teacher = teacher;
        this.tutors = tutors;
        this.tutored = tutored;
    }

    public Tutoring(Teacher teacher, List<Student> students) {
        this(teacher);
        dispatchStudents(students);
    }

    public Tutoring(Teacher teacher) {
        this(teacher, new ArrayList<Tutor>(), new ArrayList<Tutored>());
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Tutor> getTutors() {
        return tutors;
    }

    public void setTutors(List<Tutor> tutors) {
        this.tutors = tutors;
    }

    public List<Tutored> getTutored() {
        return tutored;
    }

    public void setTutored(List<Tutored> tutored) {
        this.tutored = tutored;
    }

    public Assignment getAssignement() {
        return assignement;
    }

    public void setAssignement() {
        this.assignement = new Assignment(tutored, tutors);
    }

    private void dispatchStudents(List<Student> students) {
        for (Student s : students) {
            addStudent(s);
        }
    }

    public boolean addStudent(Student s) {
        if (this.tutored.contains(s) || this.tutors.contains(s)) {
            return false;
        }
        if (s.getLevel() == 1) {
            return this.tutored.add((Tutored) s);
        } else {
            return this.tutors.add((Tutor) s);
        }
    }


    @Override
    public String toString() {
        return "[Enseignant: "+ this.teacher.toString() + ", Assignation: " +  this.assignement + "]";
    }

}
