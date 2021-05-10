package bgu.spl.net.impl.obj;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {
    private static Database singleton = DatabaseHolder.singleton;
    private ConcurrentHashMap<String, User> users;
    private ConcurrentHashMap<Short, Course> courses;

    //to prevent user from creating new Database
    private Database() {
        users = new ConcurrentHashMap<>();
        courses = new ConcurrentHashMap<>();
        System.out.println(initialize("./Courses.txt"));
    }

    /**
     * Retrieves the single instance of this class.
     */
    public static Database getInstance() {
        return singleton;
    }

    private static class DatabaseHolder {
        private static Database singleton = new Database();
    }

    /**
     * loads the courses from the file path specified
     * into the Database, returns true if successful.
     */
    boolean initialize(String coursesFilePath) {
        Path path = Paths.get(coursesFilePath);
        List<String> courses;
        try {
            courses = Files.readAllLines(path);
            int i = 0;
            for (String s : courses) {
                String[] currCourse = s.split("\\|");
                short courseNum = Short.parseShort(currCourse[0]);
                String courseName = currCourse[1];
                String[] kdams = currCourse[2].substring(1, currCourse[2].length() - 1).split(",");
                ArrayList<Short> kdamList = new ArrayList<>();
                for (String k : kdams) {
                    if (!k.isEmpty())
                        kdamList.add(Short.parseShort(k));
                }
                short maxStudents = Short.parseShort(currCourse[3]);
                addCourse(new Course(courseNum, courseName, kdamList, maxStudents, i));
                i++;
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public String getUserPassword(String UserName) {
        return users.get(UserName).getPassword();
    }

    public void addAdmin(String UserName, String Password) { // 1
        users.put(UserName, new Admin(UserName, Password));
    }

    public void addStudent(String UserName, String Password) { // 2
        users.put(UserName, new Student(UserName, Password));
    }

    public void addCourse(Course course) { // 5
        if (!courses.containsKey(course.courseNumber))
            courses.put(course.courseNumber, course);
    }

    public ArrayList<Short> getKdam(short CourseNumber) { // 6
        Course currCourse = courses.get(CourseNumber);
        if (courses.containsKey(CourseNumber))
            return currCourse.getKdam();
        throw new IllegalArgumentException(); // don't have this course number
    }

    public boolean RegisterStudent(short courseNum, Student student) {
        if(!student.Register(courseNum))
            return false;
        courses.get(courseNum).addStudent(student.getUserName());
        return true;
    }

    public void UnregisterStudent(short courseNum, Student student) {
        courses.get(courseNum).dropStudent(student.getUserName());
        student.deleteCourse(courseNum);
    }

    public Course getCourse(short CourseNum) {
        return courses.get(CourseNum);
    }

    public boolean hasAllKdams(User Student, short CourseNum) {
        ArrayList<Short> kdams = courses.get(CourseNum).kdam;
        for (Short currCourse : kdams) {
            if (!courses.get(currCourse).getRegistered().contains(Student.getUserName())) {
                return false;
            }
        }
        return true;
    }

    public String studentStat(String UserName) { // 8
        return "Student: " + UserName + "\nCourses: " + getUserCourses(UserName);
    }

    public String courseStat(short courseNum) {
        return courses.get(courseNum).toString();
    }

    public boolean isRegisteredToCourse(String UserName, short CourseNumber) { // 9
        return ((Student) users.get(UserName)).isRegistered(CourseNumber);
    }

    public boolean hasRegisteredToServer(String UserName) {
        return users.containsKey(UserName);
    }

    public User getUser(String userName) {
        return users.get(userName);
    }

    public boolean doesCourseExists(short courseNum) {
        return courses.containsKey(courseNum);
    }

    public void LoginUser(String UserName) {
        users.get(UserName).Login();
    }

    public void LogoutUser(String UserName) {
        users.get(UserName).LogOut();
    }

    public ArrayList<Short> getUserCourses(String UserName) { // 11
        return ((Student) users.get(UserName)).getCourses();
    }
}
