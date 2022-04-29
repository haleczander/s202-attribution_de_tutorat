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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(average);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + level;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Student other = (Student) obj;
        if (Double.doubleToLongBits(average) != Double.doubleToLongBits(other.average)) {
            return false;
        }
        if (level != other.level) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

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

    public String getName() {
        return name;
    }
}