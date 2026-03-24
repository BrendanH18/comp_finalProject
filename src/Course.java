import java.util.ArrayList;

public class Course {
    private String code;
    private String title;
    private int maxCapacity;
    private ArrayList<StudentIFace> enrolledStudents;


    public Course(String code, String title, int maxCapacity) {
        this.code = code;
        this.title = title;
        this.maxCapacity = maxCapacity;
        this.enrolledStudents = new ArrayList<StudentIFace>();
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public ArrayList<String> getEnrolledStudents(){
        return getEnrolledStudents();
    }

    public String toString(){
        return (this.code + this.maxCapacity) + "//" + title;
    }
}