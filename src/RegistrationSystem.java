import java.util.ArrayList;
import java.util.Scanner;

public class RegistrationSystem {
    private ArrayList<StudentIFace> students;
    private ArrayList<Course> courses;

    public RegistrationSystem(){
        this.students = new ArrayList<StudentIFace>();
        this.courses = new ArrayList<Course>();
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
                    this.scheduleStudentMenu(input);
                    break;
                case "6":
                    this.courseRoster(input);
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
    }

    public void registerStudentMenu(Scanner input){
        String id;
        String code;

        System.out.println("Please enter the ID of the student you want to add: ");
        id = input.nextLine();

        System.out.println("Please enter the code of the course: ");
        code = input.nextLine();

        boolean result = this.registerStudent(id, code);

        if (result){
            System.out.println("Student successfully registered!");
        } else  {
            System.out.println("Student Registration Failed!");
        }
    }

    public void dropStudentMenu(Scanner input){
        String id;
        String code;

        System.out.println("Please enter the ID of the student you want to drop: ");
        id = input.nextLine();

        System.out.println("Please enter the code of the course: ");
        code = input.nextLine();

        boolean result = this.dropStudent(id, code);
        if (result){
            System.out.println("Student successfully dropped!");
        }  else  {
            System.out.println("Student Drop Failed!");
        }
    }

    public void scheduleStudentMenu(Scanner input){
        String id;

        System.out.println("Please enter the ID of the student you want the schedule for: ");
        id = input.nextLine();

        try{
            ArrayList<Course> schedule = findStudent(id).getEnrolledCourses();
            if (schedule.isEmpty()) {
            System.out.println("This student isn't enrolled in any courses!.");
            } else {
                for (Course course : schedule) {
                System.out.println(course.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("Student does not exist");
        }
    }

    public void courseRoster(Scanner input){
        String code;

        System.out.println("Please enter the code of the course you want the students roster for: ");
        code = input.nextLine();

        try{
            ArrayList<StudentIFace> roster = findCourse(code).getEnrolledStudents();
            if (roster.isEmpty()) {
                System.out.println("There isn't any students enrolled in this course.");
            } else {
                for (StudentIFace student : roster) {
                    System.out.println(student.toString());
                }
            }
        } catch (Exception e) {
            System.out.println("Course does not exist");
        }
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

    public StudentIFace findStudent(String id) throws Exception{
        for ( int i = 0; i < this.students.size(); i ++ ) {
            if ( this.students.get(i).getId().equals(id) ) {
                return this.students.get(i);
            }
        }
        throw new Exception("Could not find student with id: " + id);
    }

    public Course findCourse(String code) throws Exception {
        for ( int i = 0; i < this.courses.size(); i ++ ) {
            if ( this.courses.get(i).getCode().equals(code) ) {
                return this.courses.get(i);
            }
        }
        throw new Exception("Could not find course with that code: " + code);
    }

    private boolean registerStudent(String id, String code){
        try {
            StudentIFace student = findStudent(id);

            Course course = findCourse(code);

            if (course.isFull()) {
                return false;
            }

            boolean addedToCourse = course.addStudent(id, this);
            if (addedToCourse) {
                if (student.addCourse(code)){
                    student.getEnrolledCourses().add(course);
                }
            }
            return addedToCourse;
        } catch (Exception e){
            return false;
        }
    }

    private boolean dropStudent(String id, String code){
        try {
            StudentIFace student = findStudent(id);
            Course course = findCourse(code);

            boolean removedFromCourse = course.removeStudent(id, this);
            if (removedFromCourse) {
                student.dropCourse(code);
            }
            return removedFromCourse;
        } catch (Exception e) {
            return false;
        }
    }

    public void loadData() {
        FileManager standardfm = new FileManager("data");
        standardfm.COURSES_FILE = "courses.csv";
        standardfm.STUDENTS_FILE = "students.csv";
        this.courses = standardfm.loadCourses(standardfm.COURSES_FILE);
        this.students = standardfm.loadStudents(standardfm.STUDENTS_FILE);
    }

    public void saveData(){
        FileManager standardfm = new FileManager("data");
        standardfm.STUDENTS_FILE = "students.csv";
        standardfm.COURSES_FILE = "courses.csv";
        standardfm.saveCourses(standardfm.COURSES_FILE, this.courses);
        standardfm.saveStudents(standardfm.STUDENTS_FILE, this.students);
    }
}
