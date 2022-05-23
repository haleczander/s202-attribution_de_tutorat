package graphs.affectation;

import java.util.ArrayList;
import java.util.List;

import org.junit.experimental.theories.internal.Assignments;

public class Test {
    public static void main(String[] args) {
        //Departement
        Student std1 = new Tutor("A", 12.1, 2, 3, 'A');
        Student std2 = new Tutored("B", 10.0, 3, 'C');
        List<Student> classe = new ArrayList<Student>();
        classe.add(std1);
        classe.add(std2);
        Departement dep = new Departement("Test");
        Assignment mat1 = new Assignment(classe);
        dep.addTutoring(Resource.R101, mat1);
        System.out.println(dep);
    }
}
