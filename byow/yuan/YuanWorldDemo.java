package byow.yuan;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.yuan.de.voodoosoft.gameroots.mapgen.bsp.BspMapCreator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

public class YuanWorldDemo implements java.io.Serializable {

        private  TETile[][] world;
        private  Random rand;
        private  TERenderer ter;
        private  int WIDTH;
        private  int HEIGHT;
        private  int userX;
        private  int userY;
        private  int goalX;
        private  int goalY;

        public  void creatWorld(long seed, int width, int height) {
            WIDTH=width;
            HEIGHT=height;
            ter = new TERenderer();
            ter.initialize(width,height);
            BspMapCreator charMap = new BspMapCreator();
            charMap.setSeed(seed);
            rand = new Random(seed);
            charMap.setMapDimension(height,width);
            charMap.setMinRoomSize(5);
            charMap.setMaxIterations(1000);
            char[][] output = charMap.createMap();
            world = new TETile[width][height];
            for(int i=0;i<width;i++){
                for(int j=0;j<height;j++){
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
        public  void addPlayer(){
            userX=rand.nextInt(WIDTH);
            userY=rand.nextInt(HEIGHT);
            while(world[userX][userY]!=Tileset.FLOOR){
                userX=rand.nextInt(WIDTH);
                userY=rand.nextInt(HEIGHT);
            }
            world[userX][userY]=Tileset.AVATAR;
            ter.renderFrame(world);
        }
        public  void addGoal(){
            goalX=rand.nextInt(WIDTH);
            goalY=rand.nextInt(HEIGHT);
            while(world[goalX][goalY]!=Tileset.WALL){
                goalX=rand.nextInt(WIDTH);
                goalY=rand.nextInt(HEIGHT);
            }
            world[goalX][goalY]=Tileset.LOCKED_DOOR;
            ter.renderFrame(world);
        }
        public  boolean userUp(){
            if(userY+1>=world[userX].length) return false;
            if(world[userX][userY+1]==Tileset.FLOOR||
                    world[userX][userY+1]==Tileset.LOCKED_DOOR){
                world[userX][userY]=Tileset.FLOOR;
                userY+=1;
                if(world[userX][userY]==Tileset.FLOOR)
                    world[userX][userY]=Tileset.AVATAR;
            }
            ter.renderFrame(world);
            if(world[userX][userY]==Tileset.LOCKED_DOOR)
                return true;
            else
                return false;
        }
        public  boolean userDown(){
            if(userY-1<0) return false;
            if(world[userX][userY-1]==Tileset.FLOOR
                    ||
                    world[userX][userY-1]==Tileset.LOCKED_DOOR){
                world[userX][userY]=Tileset.FLOOR;
                userY-=1;
                if(world[userX][userY]==Tileset.FLOOR)
                    world[userX][userY]=Tileset.AVATAR;
            }
            ter.renderFrame(world);
            if(world[userX][userY]==Tileset.LOCKED_DOOR)
                return true;
            else
                return false;
        }
        public  boolean userLeft(){
            if(userX-1<0) return false;
            if(world[userX-1][userY]==Tileset.FLOOR||
                    world[userX-1][userY]==Tileset.LOCKED_DOOR){
                world[userX][userY]=Tileset.FLOOR;
                userX-=1;
                if(world[userX][userY]==Tileset.FLOOR)
                    world[userX][userY]=Tileset.AVATAR;
            }
            ter.renderFrame(world);
            if(world[userX][userY]==Tileset.LOCKED_DOOR)
                return true;
            else
                return false;
        }
        public  boolean userRight(){
            if(userX+1>=world.length) return false;
            if(world[userX+1][userY]==Tileset.FLOOR||
                    world[userX+1][userY]==Tileset.LOCKED_DOOR){
                world[userX][userY]=Tileset.FLOOR;
                userX+=1;
                if(world[userX][userY]==Tileset.FLOOR)
                    world[userX][userY]=Tileset.AVATAR;
            }
            ter.renderFrame(world);
            if(world[userX][userY]==Tileset.LOCKED_DOOR)
                return true;
            else
                return false;
        }
        public void showWorld(){
            for(int i=0;i<world.length;i++){
                for(int j=0;j<world[i].length;j++){
                    if(world[i][j].description().equals(Tileset.FLOOR.description())){
                        world[i][j]=Tileset.FLOOR;
                    }
                    if(world[i][j].description().equals(Tileset.NOTHING.description())){
                        world[i][j]=Tileset.NOTHING;
                    }
                    if(world[i][j].description().equals(Tileset.WALL.description())){
                        world[i][j]=Tileset.WALL;
                    }
                    if(world[i][j].description().equals(Tileset.LOCKED_DOOR.description())){
                        world[i][j]=Tileset.LOCKED_DOOR;
                    }
                    if(world[i][j].description().equals(Tileset.AVATAR.description())){
                        world[i][j]=Tileset.AVATAR;
                    }
                }
            }
            ter.renderFrame(world);
        }
}
