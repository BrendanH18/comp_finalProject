import java.util.ArrayList;

public class Student implements StudentIFace{
    private String id;
    private String name;
    private ArrayList<Course> enrolledCourses;

    public Student(String id,String name){
        this.id=id;
        this.name=name;
        this.enrolledCourses = new ArrayList<Course>();
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public ArrayList<Course> getEnrolledCourses() {
        return this.enrolledCourses;
    }


    public String toString(){
        return this.id + "::" + this.name;
    }

}