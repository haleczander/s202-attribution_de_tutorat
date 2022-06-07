package ooptests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import oop.Departement;
import oop.Resource;
import oop.Student;
import oop.Teacher;
import oop.Tutor;
import oop.Tutored;

public class DepartmentTest {
	Departement departement;
	Tutored u1, u2, u3;
	Tutor t1, t2, t3;
	Teacher teacher1, teacher2, teacher3, teacher4, teacher5;

	@BeforeEach
	public void initialize() {
		departement = new Departement("Informatique");

		u1 = new Tutored("Anémone", 2, 'B');
		u1.addGrade(Resource.R101, 8.2);
		u2 = new Tutored("Béthanie", 3, 'A');
		u2.addGrade(Resource.R102, 15.3);
		u3 = new Tutored("Christian", 6, 'C');
		u3.addGrade(Resource.R103, 5.68);

		t1 = new Tutor("Adrien", 3, 2, 'A');
		t1.addGrade(Resource.R103, 16.58);
		t2 = new Tutor("Bill", 2, 7, 'A');
		t2.addGrade(Resource.R105, 12.47);
		t3 = new Tutor("Céline", 3, 0, 'C');
		t3.addGrade(Resource.R106, 13.69);

		teacher1 = new Teacher("Yann Secq", Resource.R101);
		teacher2 = new Teacher("Jean Carle", Resource.R102);
		teacher3 = new Teacher("Julien Baste", List.of(Resource.R103, Resource.R104));
		teacher4 = new Teacher("Philippe Mathieu", Resource.R105);
		teacher5 = new Teacher("Marie Deletombe", Resource.R106);
	}

	@Test
	void addStudent1Test() {
		assertTrue(departement.addStudent(u1));
		assertTrue(departement.addStudent(u2));
		assertTrue(departement.addStudent(u3));

		assertFalse(departement.addStudent(u1));
		assertFalse(departement.addStudent(u2));
		assertFalse(departement.addStudent(u3));

		assertTrue(departement.addStudent(t1));
		assertTrue(departement.addStudent(t2));
		assertTrue(departement.addStudent(t3));

		assertFalse(departement.addStudent(t1));
		assertFalse(departement.addStudent(t2));
		assertFalse(departement.addStudent(t3));
	}

	@Test
	void addStudent2Test() {
		assertTrue(departement.addStudent(List.of(u1, u2)));
		assertTrue(departement.addStudent(List.of(u3)));

		assertFalse(departement.addStudent(List.of(u1)));
		assertFalse(departement.addStudent(List.of(u1, u2, u3)));
	}

	@Test
	void addTeacher1Test() {
		assertTrue(departement.addTeacher(teacher1));
		assertTrue(departement.addTeacher(teacher2));
		assertTrue(departement.addTeacher(teacher3));

		assertFalse(departement.addTeacher(teacher1));
		assertFalse(departement.addTeacher(teacher2));
		assertFalse(departement.addTeacher(teacher3));
	}

	@Test
	void addTeacher2Test() {
		assertTrue(departement.addTeacher(List.of(teacher1, teacher2)));
		assertTrue(departement.addTeacher(List.of(teacher3)));

		assertFalse(departement.addTeacher(List.of(teacher1)));
		assertTrue(departement.addTeacher(List.of(teacher1, teacher4, teacher5)));
		assertFalse(departement.addTeacher(List.of(teacher1, teacher2, teacher3, teacher4, teacher5)));
	}

	@Test
	void addPersonTest() {
		assertTrue(departement.add(u1));
		assertTrue(departement.add(u2));
		assertTrue(departement.add(u3));

		assertTrue(departement.getStudents().contains(u1));
		assertTrue(departement.getStudents().contains(u2));
		assertTrue(departement.getStudents().contains(u3));

		assertTrue(departement.add(teacher1));
		assertTrue(departement.add(teacher2));

		assertTrue(departement.getTeachers().contains(teacher1));
		assertTrue(departement.getTeachers().contains(teacher2));
		assertFalse(departement.getTeachers().contains(teacher3));

		assertTrue(departement.add(t1));
		assertTrue(departement.add(t2));

		assertTrue(departement.getStudents().contains(t1));
		assertTrue(departement.getStudents().contains(t2));
		assertFalse(departement.getStudents().contains(t3));
	}

	@Test
	void addTutoringTest() {
		departement.newTutoring(Resource.R101);
		departement.newTutoring(Resource.R104);

		assertTrue(departement.getTutorings().containsKey(Resource.R101));
		assertTrue(departement.getTutorings().containsKey(Resource.R104));
		assertFalse(departement.getTutorings().containsKey(Resource.R103));
	}

	@Test
	void registerStudents1Test() {
		departement.add(u1);
		departement.add(u2);
		departement.add(u3);
		departement.add(t1);
		departement.add(t3);

		departement.newTutoring(Resource.R103);
		departement.registerStudent(Resource.R103);

		assertFalse(departement.getTutorings().get(Resource.R103).getTutored().contains(u1));
		assertFalse(departement.getTutorings().get(Resource.R103).getTutored().contains(u2));
		assertTrue(departement.getTutorings().get(Resource.R103).getTutored().contains(u3));

		assertTrue(departement.getTutorings().get(Resource.R103).getTutors().contains(t1));
		assertFalse(departement.getTutorings().get(Resource.R103).getTutors().contains(t2));
		assertFalse(departement.getTutorings().get(Resource.R103).getTutors().contains(t3));
	}

	@Test
	void registerStudents2Test() {
		departement.add(u2);
		departement.add(u3);
		departement.add(t2);

		departement.newTutoring(Resource.R104);
		departement.registerStudent(Resource.R104, Set.of(u2, u3, t1));

		assertTrue(departement.getTutorings().get(Resource.R104).getTutored().contains(u2));
		assertTrue(departement.getTutorings().get(Resource.R104).getTutored().contains(u3));
		assertTrue(departement.getTutorings().get(Resource.R104).getTutors().contains(t1));

	}

	@Test
	void registerStudentTest() {
		departement.add(u1);
		departement.add(u2);
		departement.add(u3);
		departement.add(t2);

		departement.newTutoring(Resource.R102);
		departement.registerStudent(Resource.R102, u1);
		departement.registerStudent(Resource.R102, u2);
		departement.registerStudent(Resource.R102, t2);

		assertTrue(departement.getTutorings().get(Resource.R102).getTutored().contains(u1));
		assertTrue(departement.getTutorings().get(Resource.R102).getTutored().contains(u2));
		assertFalse(departement.getTutorings().get(Resource.R102).getTutored().contains(u3));
		assertTrue(departement.getTutorings().get(Resource.R102).getTutors().contains(t2));

		assertTrue(departement.getTutorings().get(Resource.R102).getTutored().size() == 2);
		assertTrue(departement.getTutorings().get(Resource.R102).getTutored().get(0).getGrade(Resource.R102) == Student
				.getDefaultGrade());
		assertTrue(departement.getTutorings().get(Resource.R102).getTutored().get(1).getGrade(Resource.R102) == 15.3);
		assertTrue(departement.getTutorings().get(Resource.R102).getTutors().get(0).getGrade(Resource.R102) == Student
				.getDefaultGrade());
	}

	@Test
	void toStringTest() {
		departement.add(t3);
		departement.add(teacher4);

		assertEquals("Departement [Département Informatique, tutorings={}]", departement.toString());

		departement.newTutoring(Resource.R101);
		departement.newTutoring(Resource.R102);

		assertEquals(
				"Departement [Département Informatique, tutorings={R101: Initiation au développement=[Tuteurs: 0, Tutorés: 0, Attente: 0], R102: Développement d'interfaces web=[Tuteurs: 0, Tutorés: 0, Attente: 0]}]",
				departement.toString());

		departement.newTutoring(Resource.R103);
		departement.registerStudent(Resource.R103);

		assertEquals(
				"Departement [Département Informatique, tutorings={R101: Initiation au développement=[Tuteurs: 0, Tutorés: 0, Attente: 0], R102: Développement d'interfaces web=[Tuteurs: 0, Tutorés: 0, Attente: 0], R103: Introduction à l'architecture des ordinateurs=[Tuteurs: 0, Tutorés: 0, Attente: 0]}]",
				departement.toString());
	}
}