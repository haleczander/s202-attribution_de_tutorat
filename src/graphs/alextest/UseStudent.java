package alextest;

import java.util.ArrayList;
import java.util.Collections;

import alexsauce.AStudent;
import alexsauce.ATutor;
import alexsauce.ATutored;


public class UseStudent {
    public static void main(String[] args) {
        ArrayList<AStudent> pool = new ArrayList<>();
        ATutor t1 = new ATutor("Paul", 13.56, 2);
        ATutor t2 = new ATutor("Pierre", 11,3); 

        ATutored e1 = new ATutored("Jacques",6.74,1);
        ATutored e2 = new ATutored("Kévin", 10.02, 1);

        pool.add(t1);
        pool.add(t2);
        pool.add(e1);
        pool.add(e2);

        System.out.println(pool);
        Collections.sort(pool);
        System.out.println(pool);

        pool.add(t1.duplicate());
        System.out.println(pool);

        AStudent.toggleToStringShort();
        System.out.println(pool);        
    }
}
