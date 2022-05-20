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
        // je retire le chainage temporairement ça fait tout niquer si on a pas d'espaces.
        String fname;
        String sname;
        try {
            fname = name.split(" ")[0];
            sname = name.split(" ")[1];
        } catch (ArrayIndexOutOfBoundsException e) {
            fname = name;
            sname = "";
        }
        this.FORENAME = fname;
        this.SURNAME = sname;
    }
}
