import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class RegistrationSystem {
    private ArrayList<StudentIFace> students;
    private ArrayList<Course> courses;
    private FileManager fileManager;
    private InputStream userInput;
    private OutputStream userOutput;
    private PrintWriter writer;
    private Scanner scanner;

    public RegistrationSystem(InputStream in, OutputStream out){
        this.students = new ArrayList<StudentIFace>();
        this.courses = new ArrayList<Course>();
        this.fileManager = new FileManager("data");
        this.students = fileManager.loadStudents(fileManager.STUDENTS_FILE);
        this.courses = fileManager.loadCourses(fileManager.COURSES_FILE);
        this.userInput = in;
        this.userOutput = out;
        this.writer = new PrintWriter(out, true);
        this.scanner = new Scanner(in);
    }

    public RegistrationSystem() {
        this(System.in, System.out);
    }

    public void run(){
        String userSelect = "";

        while (! userSelect.equals("7")){
            menu();
            userSelect = scanner.nextLine();

            switch (userSelect){
                case "1":
                    this.addStudent();
                    break;
                case "2":
                    this.addCourse();
                    break;
                case "3":
                    this.registerStudentMenu();
                    break;
                case "4":
                    this.dropStudentMenu();
                    break;
                case "5":
                    this.studentScheduleMenu();
                    break;
                case "6":
                    this.courseRosterMenu();
                    break;
                case "7":
                    saveData();
                    break;
            }
        }
    }

    public boolean setInputStream(InputStream in){
        if (in == null) {
            return false;
        }
        this.userInput = in;
        return true;
    }

    public boolean setOutputStream(OutputStream out){
        if (out == null){
            return false;
        }
        this.userOutput = out;
        this.writer = new PrintWriter(out, true);
        return true;
    }



    public void addStudent(){
        String newName;
        String newID;

        writer.println("Please enter the name of the student you want to add: ");
        newName = scanner.nextLine();

        writer.println("Please enter the ID of the student you want to add: ");
        newID = scanner.nextLine();

        Student newStudent = new Student(newName, newID);

        this.students.add(newStudent);
        saveData();
    }

    public void addCourse(){
        String newName;
        String newCode;
        String newMax;

        writer.println("Please enter the name of the course you want to add: ");
        newName = scanner.nextLine();

        writer.println("Please enter the code of the course you want to add: ");
        newCode = scanner.nextLine();

        writer.println("Please enter the maximum number of students for the course: ");
        newMax = scanner.nextLine();

        Course newCourse = new Course(newCode, newName, Integer.parseInt(newMax));

        this.courses.add(newCourse);
        saveData();
    }

    public void registerStudentMenu() {
        writer.println("Please enter the student's ID: ");
        String id = scanner.nextLine();
        writer.println("Please enter the course code: ");
        String code = scanner.nextLine();
        try {
            this.registerStudent(id, code);
            writer.println("Student registered successfully!");
        } catch (Exception e) {
            writer.println(e.getMessage());
        }
    }

    public void dropStudentMenu() {
        writer.println("Please enter the student's ID: ");
        String id = scanner.nextLine();
        writer.println("Please enter the course code: ");
        String code = scanner.nextLine();
        try {
            this.dropStudent(id, code);
            writer.println("Student dropped successfully!");
        } catch (Exception e) {
            writer.println(e.getMessage());
        }
    }

    public void studentScheduleMenu() {
        writer.println("Please enter the student's ID: ");
        String id = scanner.nextLine();
        try{
            StudentIFace student = this.findStudent(id);
            writer.println(student.getEnrolledCourses());
        } catch(Exception e){
            writer.println(e.getMessage());
        }
    }

    public void courseRosterMenu() {
        writer.println("Please enter the course code: ");
        String code = scanner.nextLine();
        try{
            Course course = this.findCourse(code);
            writer.println(course.getEnrolledStudents());
        } catch(Exception e){
            writer.println(e.getMessage());
        }
    }

    public StudentIFace findStudent(String id) throws Exception {
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).getId().equals(id)) {
                return students.get(i);
            }
        }
        throw new Exception("Could not find student " + id);
    }


    public Course findCourse(String code) throws Exception {
        for (int i = 0; i < courses.size(); i++) {
            if (courses.get(i).getCode().equals(code)) {
                return courses.get(i);
            }
        }
        throw new Exception("Could not find course " + code);
    }


    private void registerStudent(String id, String code) throws Exception {
        StudentIFace student = findStudent(id);
        Course course = findCourse(code);

        course.addStudent(id, this);
        student.addCourse(code);
        saveData();
    }


    private void dropStudent(String id, String code) throws Exception {
        StudentIFace student = findStudent(id);
        Course course = findCourse(code);

        course.removeStudent(id, this);
        student.dropCourse(code);
        saveData();
    }

    public void saveData(){
        fileManager.saveStudents(students, fileManager.STUDENTS_FILE);
        fileManager.saveCourses(courses,  fileManager.COURSES_FILE);
    }

    private void menu(){
        writer.println("1. Add Student");
        writer.println("2. Add Course");
        writer.println("3. Register Student in a Course");
        writer.println("4. Drop Student from a Course");
        writer.println("5. View Student Schedule");
        writer.println("6. View Course Roster");
        writer.println("7. Save and Exit");
    }
}
