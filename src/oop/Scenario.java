package oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import graphs.affectation.Assignment;

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
        
        bienvenueALIUT();
    }

    static void bienvenueALIUT(){
        /*
        Bienvenue à l'IUT, 
        Cette année nous ne nous intéressons qu'au département informatique !
        */
        Departement dptInfo = new Departement("Informatique");
        IUT.add(dptInfo);


        /*        
        La liste des étudiants ne contient que 57 inscrits, 
        apparemment Parcoursup a encore causé des ennuis...
        */
        dptInfo.addStudent(ToolsCSV.importStudents());
        System.out.println("Inscription des " + dptInfo.getNbOfStudents() +" étudiants");        
        // System.out.println(dptInfo.getStudents().toString());

        /*
        Monsieur Carle se propose d'organiser du tutorat le samedi matin
        pour celles et ceux en difficulté en R102.
        */
        Teacher jeanCarle = new Teacher("Jean carle", Resource.R102);
        dptInfo.add(jeanCarle);
        System.out.println("Il y a désormais " + dptInfo.getNbOfteachers() + " enseignant dans le département informatique !");
        System.out.println(dptInfo.getTeachers());
        System.err.println();

        Resource web = Resource.R102;
        dptInfo.addTutoring(web, jeanCarle);
        Assignment webTutorat = dptInfo.getTutoring(web);
        System.out.println("Le premier tutorat a été créé !");
        System.out.println(webTutorat);
        System.out.println();

        /*
        Il faut maintenant inscrire les étudiants éligibles à ce tutorat,
        Tuteurs comme Tutorés.
         */
        dptInfo.registerStudents(web);
        System.out.println("Inscription des étudiants au tutorat de web !");
        System.out.println(webTutorat);
        System.out.println();
        System.out.println("Voici les tutorés : ");
        System.out.println(webTutorat.getTutored());
        System.out.println();
        System.out.println("Et voici les tuteurs : ");
        System.out.println(webTutorat.getTutors());
        System.out.println();

        /*
        Nous sommes prêts à lancer une première affectation !
         */
        webTutorat.getAssignment();
        /*
        Thérèse et Madeleine s'entendent vraiment bien, 
        mais si Lucas et Martin se trouvent dans la même pièce,
        il vaut mieux avoir une bonne assurance.
        */


        /* */





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
