package graphs.affectation;

public class UseDepartement {
    public static void main(String[] args) {
        Departement dpt = new Departement("INFO");

        Tutoring tutorat = new Tutoring(new Teacher("Jean Carle"));
        Tutoring tutorat2 = new Tutoring(new Teacher("Yann Secq"));

        Tutor t1 = new Tutor("Vincent", 9.3, 2, 0, 'A');
        Tutor t2 = new Tutor("Jacqueline", 13.2, 2, 1, 'B');
        Tutor t3 = new Tutor("Pénélope", 13.2, 2, 3, 'A');
        Tutor t4 = new Tutor("Édouard", 16.2, 3, 0, 'C', 1);
        Tutor t5 = new Tutor("Olivier", 11.3, 3, 2, 'B');

        // Tutorés : forment l'ensemble U
        Tutored u1 = new Tutored("Claude", 9.8, 0, 'A');
        Tutored u2 = new Tutored("Madeleine", 6.9, 8, 'A');
        Tutored u3 = new Tutored("Sabine", 12.7, 0, 'C');
        Tutored u4 = new Tutored("Hugues", 0.2, 2, 'B');
        Tutored u5 = new Tutored("Lucas", 17.3, 5, 'C');
        Tutored u6 = new Tutored("Alexandria", 12.5, 0, 'A');
        Tutored u7 = new Tutored("Anouk", 10.5, 1, 'B');

        dpt.addTutoring(Resource.R101, tutorat);
        dpt.addTutoring(Resource.R102, tutorat2);

        dpt.addStudent(Resource.R101, t1);
        dpt.addStudent(Resource.R101, t3);
        dpt.addStudent(Resource.R101, t4);

        dpt.addStudent(Resource.R101, u1);
        dpt.addStudent(Resource.R101, u3);
        dpt.addStudent(Resource.R101, u5);
        dpt.addStudent(Resource.R101, u7);


        System.out.println(dpt.toString());
    }
}
