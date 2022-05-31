package oop;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {
    private List<Resource> resources;

    /**
     * Constructs a Teacher from their name and the resource(s) they teach.
     * 
     * @param name teacher's name.
     * @param resources resources the teacher teaches.
     */
    public Teacher(String name, List<Resource> resources) {
        super(name);
        this.resources = resources;
    }

    /**
     * Constructs a Teacher from their name only.
     * 
     * @param name teacher's name.
     */
    public Teacher(String name) {
        this(name, new ArrayList<>());
    }

    /**
     * Construct a Teacher from their name and one resource they teach.
     * 
     * @param name teacher's name.
     * @param resource resource the teacher teaches.
     */
    public Teacher(String name, Resource resource) {
        this(name, List.of(resource));
    }

    /**
     * Construct a Teacher from their name and the name of one resource they teach.
     * 
     * @param name teacher's name.
     * @param resourceName name of a resource the teacher teaches.
     */
    public Teacher(String name, String resourceName) {
        this(name, Resource.valueOf(resourceName));
    }

    /**
     * Gets an immutable copy of the list of resources the teacher teaches.
     * 
     * @return a list of resources.
     */
    public List<Resource> getResources() {
        return List.copyOf(resources);
    }

    /**
     * Add a resource to the list of resources a teacher can teach.
     * 
     * @param resource resource to add.
     * @return true if resource could be added to the list, false otherwise.
     */
    public boolean addResource(Resource resource) {
        return this.resources.add(resource);
    }

    @Override
    public String toString() {
        return this.FORENAME + " " + this.SURNAME;
    }

    @Override
    boolean isTeacher() {
        return true;
    }

    @Override
    boolean isStudent() {
        return false;
    }
}
