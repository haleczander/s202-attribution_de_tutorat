package graphs.affectation;

public abstract class Person {
    protected final String FORENAME;
    protected final String SURNAME;

    /**
     * Constructs a Person from a forename and a surname.
     * 
     * @param forename forename of the student.
     * @param surname surname of the student.
     */
    protected Person(String forename, String surname) {
        this.FORENAME = forename;
        this.SURNAME = surname;
    }

    /**
     * Constructs a Person from a full name (forename and surname separated by a space).
     * 
     * @param name full name in the form : "forename surname".
     */
    public Person(String name) {
        this(name.split(" ")[0], name.split(" ")[1]);
    }
    
}
