package graphs.rapport;

public abstract class Etudiant {
    public String name;
    public double average;
    public int level;

    public Etudiant(String name, double average, int level) {
        this.name = name;
        this.average = average;
        this.level = level;
    }

    public abstract int compareTo(Etudiant etudiant);

    public String simpleToString() {
        return this.name;
    }

    @Override
    public String toString() {
        return "Etudiant [nom=" + name + ", moyenne=" + average + ", niveau=" + level + "]";
    }
}