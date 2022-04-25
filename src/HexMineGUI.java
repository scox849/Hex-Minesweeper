import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;
import java.awt.event.*;
import java.io.*;


/**
 * HexMineGUI class runs the 
 * HexMine minesweeper GUI.
 * Contains several methods, inner
 * classes, and anonymous classes
 * used to run the GUI.
 * Main method for game in this class.
 * @author Sam Cox
 * @version date 12/7/18
 */
public class HexMineGUI{

    private static HexMineManager hexGame;
    private static Timer time;

    /**
     * ImageHelper class used
     * to load image as input stream.
     * Class heavliy influenced from lecture
     * in class.
     * @author Sam Cox
     */
    public static class ImageHelper{
        private BufferedImage image;

        /**
         * ImageHelper constructor
         * loads image from the given
         * file name.
         * @param String filename
         */
        public ImageHelper(String fileName){
            InputStream is = getClass().getClassLoader(
                        ).getResourceAsStream(fileName);
            try{
                this.image = ImageIO.read(is);
                    
            }catch(IOException e){
                e.printStackTrace();
            }  
        }

        /**
         * Returns the image as
         * a BufferedImage.
         * @return this.image
         */
        public BufferedImage getImage(){
            return this.image;
        }
    }

    /** 
     * Hexboard class contains methods 
     * and constructor for making the game
     * board. Made static so reference to object
     * is not necessary.
     * @author Sam Cox
     */
    public static class HexBoard extends JPanel{

        private static HexMineManager game = getGame();
        private static final int[] xPoints = {10, 30, 70, 90,70, 30};
        private static final int[] yPoints = {60, 20, 20, 60, 100, 100};
        private static int numPoints = 6;
        private static int pixelX;
        private static int pixelY;
        private static ImageHelper image = new ImageHelper("bomb.png");

        /**
         * HexBoard constructor creates
         * backgroud for hexagons to go
         * on top of and creates a mouse listener
         * for mouse released event.
         */
        public HexBoard(){
            setPreferredSize(new Dimension(70 * game.getCols(),
                             80 * game.getRows() + 100));
            setBackground(Color.RED);
            //Syntax for mouse listener found at
            // stackoverflow.com/questions/2668718/java-mouselistener
            addMouseListener(new MouseAdapter() {

                /**
                 * Gets the s and y position 
                 * of where the mouse was released.
                 * Uncovers the hexagon that was clicked on
                 * or flags if right clicked.
                 * Checks if game has been won or lost and 
                 * if so displays a dialog box,
                 * Repaints the board based on what button was
                 * clicked.
                 * @param MouseEvent e
                 */
                public void mouseReleased(MouseEvent e){
                    getTimer().start();
                    pixelX = e.getX();
                    pixelY = e.getY();
                    Point pointToUncover = game.getPointForPix(pixelX, pixelY);
                    if(pointToUncover != null){
                        if(e.getButton() == MouseEvent.BUTTON1 && !game.isOver(
                                                                            )){
                            game.uncover(pointToUncover);
                            repaint();
                            if(game.checkWin()){
                                JOptionPane.showMessageDialog(null, 
                                                        "You Win!");
                            }else if(game.isOver() && game.checkWin() == false){
                                JOptionPane.showMessageDialog(null,
                                                                "You Lose!");
                                
                            }   
                        }
                        if(e.getButton() == MouseEvent.BUTTON3
                             && !game.isOver()){
                            game.toggleFlag(pointToUncover);
                            repaint();

                        }
                    }
                }
            });

        }

        /**
         * Paints the board by calling paint board
         * with Graphics object.
         * @param Graphics g
         */
        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            paintBoard(g);   
        }

           

        
        /**
         * Paints each hexagon on the 
         * board according to its State.
         * If COVERED paints black hexagon.
         * If UNCOVERED paints red hexagon.
         * If FLAGGED paints # on top of black
         * hexagon.
         * If NEX_TO_MINE paints the number of
         * mines it is next to on top of a red
         * hexagon.
         * If IS_MINE paints image of a bomb on a red
         * hexagon.
         * @param Graphics g
         */
        public static void paintBoard(Graphics g){

            int[] tempXPoints = new int[xPoints.length];
            int[] tempYPoints = new int[yPoints.length];
            for(int i = 0; i < xPoints.length; i++){
                tempXPoints[i] = xPoints[i];
                tempYPoints[i] = yPoints[i];
            }
            int yOffset = 80;
            
            for(int i = 0; i < game.getCols(); i++){
                
                for(int j = 0; j< game.getRows(); j++){
                    
                    Polygon poly = new Polygon(tempXPoints, 
                                    tempYPoints, numPoints);
                    Point point = game.getPoint(i, j);
                    g.setFont(new Font("Arial", Font.PLAIN, 35));
                    if(game.getPoint(i, j).getState() == State.COVERED){
                        g.setColor(Color.BLACK);
                        g.fillPolygon(poly);
                        g.setColor(Color.WHITE);
                        g.drawPolygon(poly);

                    }else if(game.getPoint(i, j).getState() == State.UNCOVERED){
                        g.setColor(Color.RED);
                        g.fillPolygon(poly);
                        g.setColor(Color.WHITE);
                        g.drawPolygon(poly);
                        
                    }else if(game.getPoint(i, j).getState() == State.FLAGGED){
                        g.setColor(Color.BLACK);
                        g.fillPolygon(poly);
                        g.setColor(Color.WHITE);
                        g.drawPolygon(poly);
                        g.drawString("#", tempXPoints[0] + 30, 
                                            tempYPoints[0] + 15);       
                    }else if(game.getPoint(i, j).getState() == State.IS_MINE){
                        g.setColor(Color.RED);
                        g.fillPolygon(poly);
                        g.setColor(Color.WHITE);
                        g.drawPolygon(poly);
                        g.drawImage(image.getImage(), tempXPoints[0]+30, 
                                                    tempYPoints[0] - 10, 
                                                            30, 30, null);    
                    }else if(game.getPoint(i, j).getState() == State.NEX_TO_MINE){
                        g.setColor(Color.RED);
                        g.fillPolygon(poly);
                        g.setColor(Color.WHITE);
                        g.drawPolygon(poly);
                        g.drawString(game.getPoint(i, j).getNumOfMines(
                                                            ).toString(), 
                                     tempXPoints[0]+30, tempYPoints[0]);    
                    }
                    
                    for(int k = 0; k < yPoints.length; k++){
                        tempYPoints[k] += yOffset; 
                    }
                    
                }
                adjustYPoints(i, tempYPoints);
                  
                for(int k = 0; k < xPoints.length; k++){
                    tempXPoints[k] += 60;
                }
                
                
            }
        }

        /**
         * Offsets the y points according
         * to coloumn being even or odd.
         * @param int colounm 
         * @param int[] ypoints
         */
        public static void adjustYPoints(int i, int[] tempYPoints){
            if(i == 0){
                tempYPoints[0] = 100;
                tempYPoints[1] = 60; 
                tempYPoints[2] = 60;
                tempYPoints[3] = 100;
                tempYPoints[4] = 140;
                tempYPoints[5] = 140;
            }else if(i == 1){
                tempYPoints[0] = 60;
                tempYPoints[1] = 20; 
                tempYPoints[2] = 20;
                tempYPoints[3] = 60;
                tempYPoints[4] = 100;
                tempYPoints[5] = 100;
            }else if(i % 2 == 0){
                tempYPoints[0] = 100;
                tempYPoints[1] = 60; 
                tempYPoints[2] = 60;
                tempYPoints[3] = 100;
                tempYPoints[4] = 140;
                tempYPoints[5] = 140;
            }else if(i % 2 != 0){
                tempYPoints[0] = 60;
                tempYPoints[1] = 20; 
                tempYPoints[2] = 20;
                tempYPoints[3] = 60;
                tempYPoints[4] = 100;
                tempYPoints[5] = 100;
            }
        }
    }

    /**
     * Returns the HexMineManager
     * used to run the current game.
     * @return HexMineManger hexGame
     */
    public static HexMineManager getGame(){
        return hexGame;        
    }


    /**
     * Returns the timer being used 
     * by the game that keep track of 
     * how long the game has been played.
     * @return Timer time
     */
    public static Timer getTimer(){
        return time;
    }

    /**
     * Creates and displays the GUI
     * Which consists of a frame, two
     * panels one containing the game board,
     * the other containing the score board.
     * the scoreboard has labels for the Timer
     * and the number of mines and flags.
     * Both panels use border layout.
     */
    public static void createAndShowGUI(){
        final JFrame frame = new JFrame("Hex Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        HexBoard game = new HexBoard();
        JPanel board = new JPanel(new BorderLayout());
        board.add(game, BorderLayout.LINE_START);
        JPanel scoreBoard = new JPanel(new BorderLayout());
        scoreBoard.setPreferredSize(new Dimension(hexGame.getCols() * 70, 50));
        JLabel timer = new JLabel();
        JLabel score = new JLabel();
        timer.setText("Time 0:0");
        score.setText("Number of mines: "+ hexGame.getMines().toString() 
                           + " Number of flags: 0 ");
        time = new Timer(1000, new ActionListener(){
            private Integer s = 0;
            private Integer m = 0;
            /**
             * Waits for the start() method 
             * to be called then starts timer.
             * Timer is updated every second.
             * sets the text of the labels as it is 
             * easy to update them if values are checked 
             * every second. start() is called when the mouse
             * is released the first time.
             * @param ActionEvent e
             */
            public void actionPerformed(ActionEvent e){
                
                if(s == 60){
                    m++;
                    s = 0;
                }
                if(!hexGame.isOver()){
                    s++;
                }
                timer.setText("Time " + m.toString() + ":" + s.toString());
                score.setText("Number of mines: " + hexGame.getMines().toString()+
                              " Number of flags: "+hexGame.getFlags().toString()+" ");
                hexGame.checkWin();
            }
        });
        
        
   
        scoreBoard.add(timer, BorderLayout.LINE_START);
        scoreBoard.add(score, BorderLayout.LINE_END);
        board.add(scoreBoard, BorderLayout.PAGE_END);

        frame.getContentPane().add(board);
        frame.pack();
        frame.setVisible(true);

    }

    /**
     * Main method starts the game.
     * Comand line arguments are used to 
     * determine the number of rows cols
     * and mines the HexMineManager has.
     * If no args are given uses default values 
     * in HexMineManager class.
     * @param String[] args
     */
    public static void main(String[] args){
        if(args.length > 0){
            hexGame = new HexMineManager(Integer.parseInt(args[0]), 
                                                     Integer.parseInt(args[1]), 
                                                     Integer.parseInt(args[2]));
        }else{
            hexGame = new HexMineManager();
        }
        SwingUtilities.invokeLater(new Runnable(){

            public void run(){
                createAndShowGUI();
            }
        });

    }

}