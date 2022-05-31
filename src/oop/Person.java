package oop;

public abstract class Person {
    protected final String FORENAME;
    protected final String SURNAME;

    /**
     * Constructs a Person from a forename and a surname.
     * 
     * @param forename forename of the student.
     * @param surname  surname of the student.
     */
    protected Person(String forename, String surname) {
        this.FORENAME = forename;
        this.SURNAME = surname;
    }

    abstract boolean isTeacher();
    abstract boolean isStudent();

    /**
     * Constructs a Person from a full name (forename and surname separated by a
     * space).
     * 
     * @param name full name in the form : "forename surname".
     */
    protected Person(String name) {
        // je retire le chainage temporairement ça fait tout niquer si on a pas
        // d'espaces.
        // String fname;
        // String sname;
        String[] names = name.split(" ");

        if (names.length == 1) {
            this.FORENAME = name;
            this.SURNAME = null;
        }
        else {
            this.FORENAME = names[0];
            this.SURNAME = names[1];
        }
        // try {
        //     fname = name.split(" ")[0];
        //     sname = name.split(" ")[1];
        // } catch (ArrayIndexOutOfBoundsException e) {
        //     fname = name;
        //     sname = "";
        // }
        // this.FORENAME = fname;
        // this.SURNAME = sname;
    }

        /**
     * Gets the person's name.
     * 
     * @return the person's name.
     */
    public String getName() {
        if (this.SURNAME == null) {
            return FORENAME;
        }
        return FORENAME + " " + SURNAME;
    }
}
