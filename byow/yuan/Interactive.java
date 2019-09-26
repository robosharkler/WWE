package byow.yuan;

import byow.lab13.MemoryGame;
import byow.refer.StdDraw;
import byow.yuan.YuanWorldDemo;
import java.awt.*;
import java.io.*;
import java.util.Random;

public class Interactive {

    private int width;
    private int height;
    private YuanWorldDemo newWorld;
    public Interactive(int width, int height) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
    }


    public void drawFrame(String s,int x, int y) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(x,y,s);
        StdDraw.show();
    }


    public long inputSeed() {
        //TODO: Read n letters of player input
        StringBuilder sb = new StringBuilder();
        drawFrame("Seed: ",width/2,height/2);
        while(true){
            if(StdDraw.hasNextKeyTyped()){
                char c = StdDraw.nextKeyTyped();
                if(c=='S'||c=='s')
                    break;
                if(Character.isDigit(c)) {
                    sb.append(c);
                    drawFrame("Seed: " + sb.toString(), width / 2, height / 2);
                }
            }
        }
        return Long.parseLong(sb.toString());
    }
    public void showWin() throws InterruptedException {
        drawFrame("You Win!!! Congradulations!!!",width/2,height/2);
        Thread.currentThread().sleep(1000);
    }
    public void showStartMenu(){
        StdDraw.clear(Color.BLACK);
        Font font = new Font("Monaco", Font.BOLD, 60);
        StdDraw.setFont(font);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(width/2,2*height/3,"World Exploration Engine");
        Font font2 = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font2);
        StdDraw.text(width/2,height/3,"New Game (N)");
        StdDraw.text(width/2,height/3-3,"Load Game (L)");
        StdDraw.text(width/2,height/3-6,"Quit (Q)");
        StdDraw.show();
    }

    public void startGame() throws InterruptedException, IOException, ClassNotFoundException {
        boolean firstInput = false;
        boolean continueOpeation=true;
        boolean win=false;
        showStartMenu();
        while(!firstInput){
            if(StdDraw.hasNextKeyTyped()){
                char c = StdDraw.nextKeyTyped();
                if(c =='n' || c=='N'){
                    long seed = inputSeed();
                    newWorld = new YuanWorldDemo();
                    newWorld.creatWorld(seed,width,height);
                    newWorld.addPlayer();
                    newWorld.addGoal();
                    firstInput=true;
                    continueOpeation=true;
                }
                if(c=='q'||c=='Q'){
                    firstInput=true;
                    continueOpeation=false;
                }
                if(c =='l' || c=='L'){
                    load();
                    newWorld.showWorld();
                    firstInput=true;
                    continueOpeation=true;
                }
            }
        }
        while(continueOpeation){
            if(StdDraw.hasNextKeyTyped()){
                char c = StdDraw.nextKeyTyped();
                if(c =='w' || c=='W'){
                    if(newWorld.userUp()){
                        win=true;
                        break;
                    }
                }
                if(c =='S' || c=='s'){
                    if(newWorld.userDown()) {
                        win=true;
                        break;
                    }
                }
                if(c =='a' || c=='A'){
                    if(newWorld.userLeft()){
                        win=true;
                        break;
                    }
                }
                if(c =='d' || c=='D'){
                    if(newWorld.userRight()) {
                        win=true;
                        break;
                    }
                }
                if(c =='q' || c=='Q'){
                    save(newWorld);
                    break;
                }
            }
        }
        if(win){
            showWin();
        }
    }
    private void save(YuanWorldDemo newWorld) throws IOException {
        FileOutputStream fileOut = new FileOutputStream("./tmp/saveWorld.ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(newWorld);
        out.close();
        fileOut.close();
    }
    private void load() throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream("./tmp/saveWorld.ser");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        newWorld = (YuanWorldDemo) in.readObject();
        in.close();
        fileIn.close();
    }
    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        Interactive newGame = new Interactive(80,40);
        newGame.startGame();
        System.exit(0);
    }
}
