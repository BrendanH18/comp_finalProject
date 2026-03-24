import java.util.ArrayList;

public class Student implements StudentIFace{
    private String id;
    private String name;
    private ArrayList<Course> enrolledCourses;

    public Student(String id,String name){
        this.id=id;
        this.name=name;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getName() {
        return this.name;
    }


    public String toString(){
        return this.id + "::" + this.name;
    }

}