import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {

    public String COURSES_FILE;
    public String STUDENTS_FILE;
    private String dir;

    public FileManager(String dir) {
        this.dir = dir;
    }

    public ArrayList<Course> loadCourses(String file) {
        ArrayList<Course> courselist = new ArrayList<>();

        File inputFile = new File(this.dir + "/" + file);

        try {
            Scanner input = new Scanner(inputFile);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] data = line.split(",");
                Course nextCourse = new Course(data[1], data[0], data[2]);
                courselist.add(nextCourse);
            } return  courselist;
        }  catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            return courselist;
        }
    }


    public ArrayList<StudentIFace> loadStudents(String file) {
        ArrayList<StudentIFace> resultList = new ArrayList<StudentIFace>();
        File inputFile = new File(this.dir + "/" + file);

        try {
            Scanner input = new Scanner(inputFile);
            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] data = line.split(",");
                Student nextStudent = new Student(data[1], data[0]);
                resultList.add(nextStudent);
            } return  resultList;
        }  catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            return resultList;
        }
    }

    public boolean saveCourses(String file, ArrayList<Course> courses){
        return false;
    }

    public boolean saveStudents(String file, ArrayList<StudentIFace> students){

    }

}
