package graphs.rapport;

public abstract class Student {
    public String name;
    public double average;
    public int level;
    public static boolean shortString = true;

    public Student(String name, double average, int level) {
        this.name = name;
        this.average = average;
        this.level = level;
    }

    public abstract int compareTo(Student etudiant);

    public String simpleToString() {
        return this.name;
    }

    @Override
    public String toString() {
        if (shortString) {
            return shortToString();
        } else {
            return "Etudiant [nom=" + name + ", moyenne=" + average + ", niveau=" + level + "]";
        }
    }

    private String shortToString() {
        return this.name;
    }
}