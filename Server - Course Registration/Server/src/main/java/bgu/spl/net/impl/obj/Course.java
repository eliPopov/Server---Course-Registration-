package bgu.spl.net.impl.obj;

import java.util.*;

public class Course {

    short courseNumber;
    String courseName;
    ArrayList<Short> kdam;
    int maxStudents;
    ArrayList<String> registered;
    int line;

    public Course(short courseNumber, String courseName, ArrayList<Short> kdam, int maxStudents, int line) {
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.kdam = kdam;
        this.maxStudents = maxStudents;
        this.registered = new ArrayList<>();
        this.line = line;
    }

    public ArrayList<Short> getKdam() {
        return kdam;
    } //needs to be according to the file order

    public ArrayList<String> getRegistered() {
        return registered;
    }

    public int freeSeats() {
        return maxStudents - getRegistered().size();
    }

    public void addStudent(String userName) {
        if (registered.size() >= maxStudents)
            throw new IllegalArgumentException("course is full"); // can't have more students in this course
        registered.add(userName);
        registered.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return (o1.compareTo(o2));
            }
        });
    }

    public int getLine() {
        return line;
    }

    public void dropStudent(String userName) {
        registered.remove(userName);
    }

    @Override
    public String toString() {
        return "Course: (" + courseNumber + ") " + courseName +
                "\n Seats Available: " + freeSeats() + "/" + maxStudents +
                "\n Students Registered: " + registered;
    }
}
