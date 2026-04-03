import java.util.ArrayList;

public interface StudentIFace {
    public String getId();
    public String getName();
    public ArrayList<Course> getEnrolledCourses();
    public boolean addCourse(String code);
    public boolean dropCourse(String code);
}