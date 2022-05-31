package oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Scenario {
    static List<Departement> IUT = new ArrayList<>();
    public static void main(String[] args) {
        /* 
        L'I.U.T. de Lille veut mettre en place un système de tutorat
        Chaque Département dispose d'une liste d'élèves et de professeurs
        Ces élèves disposent ou non de notes pour une matière
        Cette note est le critère d'éligibilité au tutorat pour cette matière
        Chaque tutorat dispose d'un professeur référent
        */
       
        /*
        Scenario 1:
            Situation par défaut, aucun tutorat pour la matière n'existe
            Nous ne disposons que de la liste des élèves et celle des enseignants
        */
        
        scenario1();
        scenario2();
        scenario3();
    }

    static void scenario1(){
        Departement info = new Departement("Info");
        IUT.add(info);

        Set<Student> students = ToolsCSV.importStudents();
        info.addStudent(students);
        Set<Teacher> teachers = ToolsCSV.importTeachers();
        info.addTeacher(teachers);

        Resource bdd = Resource.R104;        
        info.addTutoring(bdd);
        info.registerStudents(bdd);

        /*
        Il y a pénurie d'enseignants, de jeunes doctorants sont mis à contribution
         */
        Teacher corwyn = new Teacher("Corwyn Fèvre");
        info.setTeacher(bdd, corwyn);
    };

    static void scenario2(){
    };

    static void scenario3(){
    };
}
