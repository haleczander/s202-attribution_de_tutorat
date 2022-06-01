package ooptests;

import java.util.ArrayList;
import java.util.List;

import oop.Departement;
import oop.Student;
import oop.Tutor;
import oop.Tutored;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DepartmentTest {
	Tutor tutor1 = new Tutor("A", 12.1, 2, 3, 'A');
	Tutor tutor2 = new Tutor("Jean", 13, 2, 3, 'C');
	Tutor tutor3 = new Tutor("Pierre", 18, 2, 3, 'A');
	
	Tutored tutored1 = new Tutored("Axel", 5, 2, 'C');
	Tutored tutored2 = new Tutored("Tim", 15, 2, 'C');
	Tutored tutored3 = new Tutored("Harry", 3, 2, 'C');
	Tutored tutored4 = new Tutored("Paul", 12, 2, 'C');
	
	Departement dep = new Departement("Test");
	
	List<Tutor> tuteur = new ArrayList<Tutor>();
	List<Tutored> tutoré = new ArrayList<Tutored>();
	List<Student> student = new ArrayList<Student>();
	
	public static void main(String[] args) {
		DepartmentTest test = new DepartmentTest();
		test.depTest();
	}
	
	void depTest() {
		addTest();
	}
	
	void addTest() {
		student.add(tutor1);
		student.add(tutor2);
		student.add(tutor3);
      
		student.add(tutored1);
		student.add(tutored2);
		student.add(tutored3);
		student.add(tutored4);
		
		 assertEquals(dep.getStudents(), student);
	}
	
}
