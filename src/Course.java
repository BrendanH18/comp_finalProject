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

    public ArrayList<StudentIFace> getEnrolledStudents(){
        return this.enrolledStudents;
    }

    public boolean isFull() {
        if (this.enrolledStudents.size() >= this.maxCapacity) {
            return true;
        } else  {
            return false;
        }
    }

    public boolean addStudent(String id, RegistrationSystem system) throws Exception {
        StudentIFace student = system.findStudent(id);
        if (this.enrolledStudents.contains(student)) {
            return false;
        } else {
            this.enrolledStudents.add(system.findStudent(id));
            return true;
        }
    }

    public boolean removeStudent(String id, RegistrationSystem system) throws Exception {
        StudentIFace student = system.findStudent(id);
        if (this.enrolledStudents.contains(student)) {
            this.enrolledStudents.remove(system.findStudent(id));
            return true;
        } else  {
            return false;
        }
    }

    public String toString(){
        return (this.code + this.maxCapacity) + "//" + title;
    }
}