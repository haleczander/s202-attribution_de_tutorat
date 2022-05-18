package graphs.affectation;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {
    private List<Resource> resources;

    public Teacher(String name, List<Resource> resources) {
        super(name);
        this.resources = resources;
    }

    public Teacher(String name) {
        this(name, new ArrayList<>());
    }

    public List<Resource> getResources() {
        return List.copyOf(resources);
    }

    public boolean addResource(Resource resource) {
        return this.resources.add(resource);
    }

    @Override
    public String toString() {
        return this.FORENAME + " " + this.SURNAME;
    }
}
