package oop;

import java.util.Comparator;

public class StudentComparator implements Comparator<Student> {
    Resource resource;

    public StudentComparator(Resource resource){
        this.resource= resource;
    }

    @Override
    public int compare(Student o1, Student o2) {
       return (int) (100* (o1.getWeight(resource) - o2.getWeight(resource)));
    }
    
}
