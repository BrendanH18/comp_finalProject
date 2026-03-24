public class OnlineCourse extends Course{
    private String url;

    public OnlineCourse(String code, String title, int maxCap, String url ){
        super(code, title, maxCap);
        this.url = url;
    }

    @Override
    public String toString(){
        return (getCode() + getMaxCapacity()) + "//" + getTitle() + "//" + url;
    }
}