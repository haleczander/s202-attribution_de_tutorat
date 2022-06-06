package oop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class ToolsCSV {
    static String path = "res" + File.separator + "data" + File.separator;
    
    public static Set<Student> importStudents(){
        Set<Student> students = new HashSet<>();
        try(BufferedReader br = new BufferedReader(new FileReader(new File(path+"students.csv")))){
            while(br.ready()){
                students.add(newStudent( br.readLine().split(";", 13)));
            }
        }
        catch(Exception e){System.err.println("Souci a l'import des étudiants");}
        return students;
    }

    public static Set<Teacher> importTeachers(){
        Set<Teacher> teachers = new HashSet<>();
        String line[];
        try(BufferedReader br = new BufferedReader(new FileReader(new File(path+"teachers.csv")))){
            while(br.ready()){
                line = br.readLine().split(";");
                teachers.add(new Teacher(line[0]+" "+line[1], line[2]));
            }
        }
        catch(Exception e){}
        return teachers;
    }

    private static Student newStudent(String[] csv){
        String forename = csv[0];
        String surname = csv[1];
        int level = Integer.valueOf(csv[2]);
        int absences = Integer.valueOf(csv[3]);
        char motivation = csv[4].charAt(0);
        int nbofTutored = (!csv[5].equals("")) ? Integer.valueOf(csv[5]) : 0;

        Student student;
        if (level == 1){
            student = new Tutored(forename+" "+surname,  absences, motivation);
        }
        else {
            student = new Tutor(forename+" "+surname, level, absences, motivation, nbofTutored);
        }

        Resource[] resources = Resource.values();
        for (int i = 0; i < resources.length; i++) {
            if (csv[6+i].length()>0) {
                student.addGrade(resources[i], Double.valueOf(csv[6+i]));
            }
        }

        return student;

    }
}
