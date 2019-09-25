package byow.yuan;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.yuan.de.voodoosoft.gameroots.mapgen.bsp.BspMapCreator;

import java.util.Random;

public class YuanWorldDemo {
    private static final int WIDTH = 40;
    private static final int HEIGHT = 80;
    private static final long SEED = 23123;
    private static final Random RANDOM = new Random(SEED);

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(HEIGHT,WIDTH);
        BspMapCreator charMap = new BspMapCreator();
        charMap.setSeed(SEED);
        charMap.setMapDimension(WIDTH,HEIGHT);
        charMap.setMinRoomSize(5);
        charMap.setMaxIterations(1000);
        char[][] output = charMap.createMap();
        TETile[][] world = new TETile[HEIGHT][WIDTH];

        for(int i=0;i<HEIGHT;i++){
            for(int j=0;j<WIDTH;j++){
                switch (output[i][j]){
                    case '#':
                        world[i][j]= Tileset.WALL;
                        break;
                    case '.':
                        world[i][j]= Tileset.FLOOR;
                        break;
                    case ',':
                        world[i][j]= Tileset.FLOOR;
                        break;
                    case '|':
                        world[i][j]= Tileset.UNLOCKED_DOOR;
                        break;
                    case '-':
                        world[i][j]= Tileset.UNLOCKED_DOOR;
                        break;
                    default:
                        world[i][j]= Tileset.NOTHING;
                        break;
                }
                System.out.print(output[i][j]);
            }
            System.out.println("");
        }
        ter.renderFrame(world);

    }
}
