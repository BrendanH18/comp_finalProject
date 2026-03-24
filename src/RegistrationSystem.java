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

        Student newStudent = new Student(newName, newID);

        this.students.add(newStudent);
        System.out.println(this.students.size());
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

        Course newCourse = new Course(newName, newCode, Integer.parseInt(newMax));

        this.courses.add(newCourse);
        System.out.println(this.courses.size());
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
