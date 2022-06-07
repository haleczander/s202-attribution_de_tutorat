package utility;

import java.util.List;

import oop.Person;

public class Persons {
    public static Person getPerson(String name, List<? extends Person> persons){
        for (Person p : persons) {
            if (p.getName().equalsIgnoreCase(name)){
                return p;
            }
        }
        return null;
    }
}
