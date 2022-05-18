package graphs.affectation;

public abstract class Person {
    protected final String FORENAME;
    protected final String SURNAME;


    protected Person(String forename, String surname) {
        this.FORENAME = forename;
        this.SURNAME = surname;
    }


    public Person(String name) {
        this(name.split(" ")[0], name.split(" ")[1]);
    }
    
}
