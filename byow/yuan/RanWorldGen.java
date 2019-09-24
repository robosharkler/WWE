package byow.yuan;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import byow.yuan.Rect;
public class RanWorldGen {

    private int width;
    private int height;
    private int[][] ranWorld;
    private int numRoomTries;
    private Random RANDOM;
    private List<Rect> rooms = new ArrayList<>();


    public RanWorldGen(int w, int h, int nRT,long seed){
        width = w;
        height = h;
        numRoomTries = nRT;
        ranWorld = new int[w][h];
        Random RANDOM = new Random(seed);

    }

    public int[][] WorldGen(){

        //Generate Room
        RanRoomGen();
        //Generate Maze
        RanMazeGen();
        //Connect Room
        ConnectRoom();
        //Remove SiHuTong
        RemoveSiHuTong();

        return ranWorld;
    }

    private void RanRoomGen(){
        for (int i = 0; i < numRoomTries; i++) {

            // Pick a random room size. The funny math here does two things:
            // - It makes sure rooms are odd-sized to line up with maze.
            // - It avoids creating rooms that are too rectangular: too tall and
            //   narrow or too wide and flat.
            // TODO: This isn't very flexible or tunable. Do something better here.
            int size = RANDOM.nextInt(15);
            size = size==0? 1 : size;
            int rectangularity = RANDOM.nextInt(1+size/2) * 2;
            int roomWidth = size;
            int roomHeight = size;
            if (RANDOM.nextInt(1) == 1) {
                roomWidth += rectangularity;
            } else {
                roomHeight += rectangularity;
            }

            int x = RANDOM.nextInt((width - roomWidth)/2) * 2 +1;
            int y = RANDOM.nextInt((height - roomHeight)/2) * 2 +1;

            Rect room = new Rect(x, y, roomWidth, roomHeight);
            boolean overlaps= false;
            for (Rect other : rooms) {
                if (room.isOverlaped(other) == true) { //TODO: waiting for implment
                    overlaps = true;
                    break;
                }
            }
            if (overlaps) continue;
            rooms.add(room);

            //TODO: Add room into Array
        }

    }

    private void RanMazeGen(){

    }

    private void ConnectRoom(){

    }

    private void RemoveSiHuTong(){

    }

}
