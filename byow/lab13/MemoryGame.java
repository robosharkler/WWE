package byow.lab13;

import byow.refer.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private static final long SEED = 1;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};
    private int roundNum=1;

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40);
        game.startGame();
    }

    public MemoryGame(int width, int height) {
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
        rand = new Random(SEED);
        //TODO: Initialize random number generator
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = rand.nextInt(26);
            // add Character one by one in end of sb
            sb.append(CHARACTERS[index]);
        }
        return sb.toString();
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(5,height-2,"Round:"+roundNum);
        String encourage=ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)];
        StdDraw.text(width-encourage.length()/2,height-2,encourage);
        StdDraw.text(width/2,height/2,s);
        StdDraw.show();
    }

    public void flashSequence(String letters) throws InterruptedException {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for(int i=0;i<letters.length();i++){
            drawFrame(letters.substring(i,i+1));
            Thread.currentThread().sleep(1000);
            drawFrame("");
            Thread.currentThread().sleep(500);
        }

    }

    public String solicitNCharsInput(int n) {
        //TODO: Read n letters of player input
        StringBuilder sb = new StringBuilder(n);
        while(sb.length()<n){
            if(StdDraw.hasNextKeyTyped()){
                char c = StdDraw.nextKeyTyped();
                sb.append(c);
                drawFrame(sb.toString());
            }
        }
        return sb.toString();
    }

    public void startGame() throws InterruptedException {
        //TODO: Set any relevant variables before the game starts

        //TODO: Establish Engine loop

        boolean userContinue=true;
        while(userContinue){
            String startInfo = "Round: ";
            drawFrame(startInfo+roundNum);
            String randS = generateRandomString(roundNum);
            flashSequence(randS);
            String ans = solicitNCharsInput(roundNum);
            if(ans.equals(randS)){
                roundNum++;
                drawFrame("Success! Next round!");
                Thread.currentThread().sleep(500);
            }else{
                userContinue=false;
                drawFrame("Game Over! You made it to round:"+roundNum);
                Thread.currentThread().sleep(500);
            }
        }
    }

}
