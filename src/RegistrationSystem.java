import java.util.ArrayList;
import java.util.Scanner;

public class RegistrationSystem {
    private ArrayList<StudentIFace> students;
    private ArrayList<Course> courses;
    private FileManager fileManager;

    public RegistrationSystem(){
        this.students = new ArrayList<StudentIFace>();
        this.courses = new ArrayList<Course>();
        this.fileManager = new FileManager("data");
        this.students = fileManager.loadStudents(fileManager.STUDENTS_FILE);
        this.courses = fileManager.loadCourses(fileManager.COURSES_FILE);

    }

    public void run(){
        Scanner input = new Scanner(System.in);

        String userSelect = "";

        while (! userSelect.equals("7")){
            menu();
            userSelect = input.nextLine();

            switch (userSelect){
                case "1":
                    this.addStudent(input);
                    break;
                case "2":
                    this.addCourse(input);
                    break;
                case "3":
                    this.registerStudentMenu(input);
                    break;
                case "4":
                    this.dropStudentMenu(input);
                    break;
                case "5":
                    this.studentScheduleMenu(input);
                    break;
                case "6":
                    this.courseRosterMenu(input);
                    break;
                case "7":
                    saveData();
                    break;
            }
        }
    }



    public void addStudent(Scanner add){
        String newName;
        String newID;

        System.out.println("Please enter the name of the student you want to add: ");
        newName = add.nextLine();

        System.out.println("Please enter the ID of the student you want to add: ");
        newID = add.nextLine();

        Student newStudent = new Student(newID, newName);

        this.students.add(newStudent);
        saveData();
    }

    public void addCourse(Scanner add){
        String newName;
        String newCode;
        String newMax;

        System.out.println("Please enter the name of the course you want to add: ");
        newName = add.nextLine();

        System.out.println("Please enter the code of the course you want to add: ");
        newCode = add.nextLine();

        System.out.println("Please enter the maximum number of students for the course: ");
        newMax = add.nextLine();

        Course newCourse = new Course(newCode, newName, Integer.parseInt(newMax));

        this.courses.add(newCourse);
        saveData();
    }

    public void registerStudentMenu(Scanner input) {
        System.out.println("Please enter the student's ID: ");
        String id = input.nextLine();
        System.out.println("Please enter the course code: ");
        String code = input.nextLine();
        try {
            this.registerStudent(id, code);
            System.out.println("Student registered successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void dropStudentMenu(Scanner input) {
        System.out.println("Please enter the student's ID: ");
        String id = input.nextLine();
        System.out.println("Please enter the course code: ");
        String code = input.nextLine();
        try {
            this.dropStudent(id, code);
            System.out.println("Student dropped successfully!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void studentScheduleMenu(Scanner input) {
        System.out.println("Please enter the student's ID: ");
        String id = input.nextLine();
        try{
            StudentIFace student = this.findStudent(id);
            System.out.println(student.getEnrolledCourses());
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void courseRosterMenu(Scanner input) {
        System.out.println("Please enter the course code: ");
        String code = input.nextLine();
        try{
            Course course = this.findCourse(code);
            System.out.println(course.getEnrolledStudents());
        } catch(Exception e){
            System.out.println(e.getMessage());
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


    public void registerStudent(String id, String code) throws Exception {
        StudentIFace student = findStudent(id);
        Course course = findCourse(code);

        course.addStudent(id, this);
        student.addCourse(code);
        saveData();
    }


    public void dropStudent(String id, String code) throws Exception {
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
        System.out.println("1. Add Student");
        System.out.println("2. Add Course");
        System.out.println("3. Register Student in a Course");
        System.out.println("4. Drop Student from a Course");
        System.out.println("5. View Student Schedule");
        System.out.println("6. View Course Roster");
        System.out.println("7. Save and Exit");
    }
}
