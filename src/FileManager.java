import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class FileManager {
    private String dir;
    public String COURSES_FILE = "courses.csv";
    public String STUDENTS_FILE  = "students.csv";

    public FileManager(String dir) {
        this.dir = dir;
    }

   public ArrayList<Course> loadCourses(String file){
       ArrayList<Course> resultList = new ArrayList<Course>();

       File inFile = new File(this.dir + "/" + file);

       try {
           Scanner in = new Scanner(inFile);

           while ( in.hasNextLine() ) {
               String nextCSVLine = in.nextLine();

               String[] nextLinePieces = nextCSVLine.split(",");

               Course course = new Course(nextLinePieces[1], nextLinePieces[0], Integer.parseInt(nextLinePieces[2]));

               resultList.add(course);
           }

           return resultList;
       } catch (FileNotFoundException e) {
           System.out.println("Course loading failed");

           return resultList;
       }
   }

   public ArrayList<StudentIFace> loadStudents(String file){
       ArrayList<StudentIFace> resultList = new ArrayList<StudentIFace>();

       File inFile = new File(this.dir + "/" + file);

       try {
           Scanner in = new Scanner(inFile);

           while ( in.hasNextLine() ) {
               String nextCSVLine = in.nextLine();

               String[] nextLinePieces = nextCSVLine.split(",");

               Student nextStudent = new Student(nextLinePieces[1], nextLinePieces[0]);

               resultList.add(nextStudent);
           }

           return resultList;
       } catch (FileNotFoundException e) {
           System.out.println("Student loading failed");

           return resultList;
       }
   }

   public boolean saveCourses(ArrayList<Course> courses,  String file){
       File outFile = new File(this.dir + "/" + file);

       try {
           PrintWriter out = new PrintWriter(outFile);

           for (int i = 0; i < courses.size(); i++) {
               Course newCourse = courses.get(i);
               out.println(newCourse.toString());
           }
           out.close();
           return true;
       } catch (FileNotFoundException e) {
           System.out.println("Course saving failed");
           return false;
       }
   }

   public boolean saveStudents(ArrayList<StudentIFace> students, String file){
        File outFile = new File(this.dir + "/" + file);

        try {
            PrintWriter out = new PrintWriter(outFile);

            for (int i = 0; i < students.size(); i++) {
                StudentIFace newStudent = students.get(i);
                out.println(newStudent.toString());
            }
            out.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println("Course saving failed");
            return false;
        }
   }
}
