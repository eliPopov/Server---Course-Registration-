package bgu.spl.net.impl.obj;

import java.util.*;

public class Student extends User {

    private ArrayList<Short> courses;

    public Student(){this.courses = new ArrayList<>();}

    public Student(String userName) {
        super(userName);
        this.courses = new ArrayList<>();
        this.courses.add((short)7);
    }

    public Student(String userName, String password) {
        super(userName, password);
        this.courses = new ArrayList<>();
    }

    public ArrayList<Short> getCourses() {
        courses.sort(new Comparator<Short>() {
        @Override
        public int compare(Short o1, Short o2) {
            return Database.getInstance().getCourse(o1).getLine()-Database.getInstance().getCourse(o2).getLine();
        }
    });
        return courses;
    }

    public boolean isRegistered(short CourseNumber) { // 9
        return courses.contains(CourseNumber);
    }

    public boolean Register(short courseNumber){
        if(courses.contains(courseNumber))
            return false;
        courses.add(courseNumber);
        return true;
    }

    public void deleteCourse(short CourseNumber) { // 10
        if (!courses.contains(CourseNumber))
            throw new IllegalArgumentException(); // can't unregister from an unregistered course
        courses.remove(courses.indexOf(CourseNumber));
    }
}
