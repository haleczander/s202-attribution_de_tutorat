package oop;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class ToolsCSV {
    static String path = "lib" + File.separator + "data" + File.separator;
    
    public static Set<Student> importStudents(){
        String[] line;
        Set<Student> students = new HashSet<>();

        try(BufferedReader br = new BufferedReader(new FileReader(new File(path+"students.csv")))){
            while(br.ready()){
                line = br.readLine().split(";");

            }
        }
        catch(Exception e){}
        return students;
    }
}
