package graphs.affectation;

import java.util.ArrayList;
import java.util.List;

import graphs.alexsauce.Resource;

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
}
