package byow.yuan;

public class Rect {

    private  int x;
    private int y;
    private int width;
    private int height;

    public Rect(int x, int y, int width, int height){
        this.x = x;
        this.y=y;
        this.width=width;
        this.height=height;
    }

    public boolean isOverlaped(Rect other){
        return true;
    }
}
