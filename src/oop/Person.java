package oop;

public abstract class Person {
    protected String forename;
    protected String surname;
    /**
     * A Person is either a Student or a Teacher
     */
    private boolean isStudent;
    protected static boolean shortName = true;

    /**
     * Constructs a Person from a forename and a surname.
     * 
     * @param forename forename of the person.
     * @param surname  surname of the person.
     * @param isStudent defines if the person is a student or not
     */
    protected Person(String forename, String surname, boolean isStudent) {
        this(forename + " " + surname, isStudent);
    }

    /**
     * Constructs a Person from a full name (forename and surname separated by a
     * space).
     * 
     * @param name full name in the form : "forename surname".
     */
    protected Person(String name, boolean isStudent) {

        String[] names = name.split(" ");

        if (names.length == 1) {
            this.forename = name;
            this.surname = null;
        }
        else {
            this.forename = names[0];
            this.surname = names[1];
        }
        this.isStudent = isStudent;
    }


    public boolean isStudent(){
        return this.isStudent;
    }

        /**
     * Gets the person's name.
     * 
     * @return the person's name.
     */
    public String getName() {
        if (this.surname == null) {
            return forename;
        }
        return forename + " " + surname;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        if (Person.shortName) {
            return this.getName();
        }
        return this.getClass().getSimpleName()+" ["+ this.getName() +"]";
    }

    

    
}
