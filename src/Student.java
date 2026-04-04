import java.util.ArrayList;

public class Student implements StudentIFace{
    private String id;
    private String name;
    private ArrayList<String> enrolledCourses;

    public Student(String id,String name){
        this.id=id;
        this.name=name;
        this.enrolledCourses = new ArrayList<String>();
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
    public ArrayList<String> getEnrolledCourses() {
        return this.enrolledCourses;
    }

    @Override
    public boolean addCourse(String code) {
        if (this.enrolledCourses.contains(code)) {
            return false;
        } else {
            this.enrolledCourses.add(code);
            return true;
        }
    }

    public boolean dropCourse(String code) {
        if (this.enrolledCourses.contains(code)) {
            this.enrolledCourses.remove(code);
            return true;
        } else  {
            return false;
        }
    }

    public String toString(){
        return this.id + "::" + this.name;
    }

}