import java.util.ArrayList;

public class FileManager {

    public String COURSES_FILE;
    public String STUDENTS_FILE;
    private String dir;

    public FileManager(String dir) {
        this.dir = dir;
    }

    public ArrayList<Course> loadCourses(String file){
        return new ArrayList<>();
    }


    public ArrayList<StudentIFace> loadStudents(String file){
        return new ArrayList<>();
    }

    public boolean saveCourses(String file, ArrayList<Course> courses){
        return false;
    }

    public boolean saveStudents(String file, ArrayList<StudentIFace> students){
        return false;
    }

}
