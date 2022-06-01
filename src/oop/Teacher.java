package oop;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Person {
    private List<Resource> resources;

    private double averageWeighting = 1;
    private double levelWeighting = 1;
    private double absenceWeighting = 1;

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

        /**
     * Sets the weighting of the average of students for weight computing. Default
     * value is 1.
     * 
     * @param levelWeighting new weighting of students level.
     */
    public void setLevelWeighting(double levelWeighting) {
        this.levelWeighting = levelWeighting;
    }

    /**
     * Sets the weighting of the level of students for weight computing. Default
     * value is 1.
     * 
     * @param averageWeighting new weighting of students average.
     */
    public void setAverageWeighting(double averageWeighting) {
        this.averageWeighting = averageWeighting;
    }

    /**
     * Sets the weighting of the level of students for weight computing. Default
     * value is 1.
     * 
     * @param absenceWeighting new weighting of students absence.
     */
    public void setAbsenceWeighting(double absenceWeighting) {
        this.absenceWeighting = absenceWeighting;
    }

    public double getLevelWeighting() {
        return levelWeighting;
    }

    public double getAverageWeighting() {
        return averageWeighting;
    }

    public double getAbsenceWeighting() {
        return absenceWeighting;
    }
}
