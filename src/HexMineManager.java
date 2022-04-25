import java.util.*;
/**
 * HexMineManager class contains methods 
 * and constructor for backend of a
 * hex minesweeper game.
 * @author Samuel Cox
 * @version date 11/26/18
 */

public class HexMineManager{

    private final int DEFAULT_BOARD_WIDTH = 6;
    private final int DEFAULT_BOARD_LENGTH = 6;
    private final int DEFAULT_MINE_LENGTH = 6;
    private int rows;
    private int cols;
    private List<Point> board;
    private int mineLength;
    private boolean gameOver;
    private static final String COVERED_S = "c";
    private static final String UNCOVERED_S = ".";
    private static final String FLAGGED_S = "f";
    private static final String MINE_S = "m";
    private boolean steppedOnMine = false;
    private List<PixelCord> pixelCords;
    private Map<PixelCord, Point> pointsAndPixelCords = 
                                        new HashMap<>();
    /**
     * Default constrctor for HexMineManager
     * sets member variables to default values.
     *
     */
    public HexMineManager(){
        this.rows = DEFAULT_BOARD_WIDTH;
        this.cols = DEFAULT_BOARD_LENGTH;
        mineLength = DEFAULT_MINE_LENGTH;
        board = makeBoard(this.rows, this.cols);
        this.pixelCords = makeListOfPixelCords();
        this.placeMines();
        this.findMines();
    }
    /**
     * Constructor for HexMineManager
     * sets values to the values provided 
     * when object is created.
     * @param rows
     * @param cols
     * @param minelength number of mines to be placed
     */
    public HexMineManager(int rows, int cols, int mineLength){
        this.rows = rows;
        this.cols = cols;
        this.mineLength = mineLength;
        this.board = makeBoard(this.rows, this.cols);
        this.pixelCords = makeListOfPixelCords();
        this.placeMines();
        this.findMines();

    }
    /**
     * Returns the number of mines on
     * the board. Returns Interger
     * object to be able to use 
     * toString().
     * @return this.mineLength
     */
    public Integer getMines(){
        return this.mineLength;
    }
    /**
     * Makes the game board by creating 
     * new points with a row and colounm
     * value.
     * @param rows
     * @param cols
     * @return board created
     */
    public List<Point> makeBoard(int rows, int cols){
        List<Point> board = new LinkedList<>();
        for(int i = 0; i < rows; i++){
            for(int j = 0; j< cols; j++){
                Point point = new Point(j, i, State.COVERED, 
                                            false, 0);
                    board.add(point);
            }
        }
        return board;
    }

    /**
     * Loops through the board and if 
     * the point is a mine each of the mines
     * neighbors is notified that there is 
     * a mine next it.
     */
    public void findMines(){
        for(Point p : board){
            if(p.isAMine()){
                this.makeNeighborsAware(p);
            }
        }
    }

    /**
     * Makes the neighbors of a mine
     * aware that there is a mine
     * next to them. Increases the
     * number displayed on the point
     * if there are more than 1 mines 
     * next to it.
     * @param p
     */
    public void makeNeighborsAware(Point p){
        int x = p.getX();
        int y = p.getY();
        int [] xOffSet = {0, 1, 1, 0, -1 , -1};
        int [] evenYOffSet = {-1, -1, 0, 1, 0, -1};
        int [] oddYOffSet = {-1, 0, 1, 1, 1, 0};
        if(x == 0 || x != 1 && x % 2 == 0 ){
            for(int i = 0; i < xOffSet.length; i++){
                int tempX = x;
                int tempY = y;
                tempX += xOffSet[i];
                tempY += evenYOffSet[i];
                if(this.getPoint(tempX, tempY) != null){
                    this.getPoint(tempX, tempY).setNumOfMines(
                    this.getPoint(tempX, tempY).getNumOfMines() + 1);
                }
            }
        }else if(x == 1 || x % 2 != 0){
            for(int i = 0; i < xOffSet.length; i++){
                int tempX = x;
                int tempY = y;
                tempX += xOffSet[i];
                tempY += oddYOffSet[i];
                if(this.getPoint(tempX, tempY) != null){
                    this.getPoint(tempX, tempY).setNumOfMines(
                    this.getPoint(tempX, tempY).getNumOfMines() + 1);
                }
            }
        }
    }

    /**
     * Returns the number of flags on
     * the board.
     * Returnas an Integer object to be 
     * able to use toString().
     * @return Integer flags
     */
    public Integer getFlags(){
        Integer flags = 0;
        for(Point p : this.board){
            if(p.getState() == State.FLAGGED){
                flags++;
            }
        }
        return flags;
    }

    /**
     * Returns the number of rows
     * the board has.
     * @return this.rows
     */
    public int getRows(){
        return this.rows;
    }

    /**
     * Returns the number of coloumns
     * the board has.
     * @return this.cols
     */
    public int getCols(){
        return this.cols;
    }

    /**
     * Uncovers the neighbors of the point
     * that was passed to the funciton 
     * @param point 
     *
     */
    public void recUncover(Point point){

        int x = point.getX();
        int y = point.getY();
        int [] xOffSet = {0, 1, 1, 0, -1 , -1};
        int [] evenYOffSet = {-1, -1, 0, 1, 0, -1};
        int [] oddYOffSet = {-1, 0, 1, 1, 1, 0};

        if(point.getState() == State.NEX_TO_MINE){
            return;
        }
        for(int i = 0; i < xOffSet.length; i++){
            int tempX = x;
            int tempY = y;
            tempX += xOffSet[i];
            if(tempX== 0 || tempX % 2 == 0 && tempX != 1){
                tempY += evenYOffSet[i];
            }else{
                tempY += oddYOffSet[i];
            }
            Point p = this.getPoint(tempX, tempY);
            if(p == null){
                continue;
            }
            if(p.isAMine()){
                continue;
            }
            if(!p.isAMine()){
                uncover(p); 
            }
            
            
            
        }
    }

    /**
     * Returns a list of points 
     * that represent the game board
     * @return this.board
     */
    public List<Point> getBoard(){
        return this.board;
    }


    /**
     * Returns the point at the 
     * given row coloumn value.
     * @param x
     * @param y
     * @return point
     */
    public Point getPoint(int x, int y){
        Point point = null;
        for(Point p : this.board){
            if(p.getX() == x && p.getY() == y){
                point = p;
            }
        }
        return point;
    }

    /**
     * Makes a list of the pixel coordinate
     * centers of each hexagon on the board.
     * Then places the pixel coordinate with
     * the coressponding board coordinate into
     * a hashmap.
     * @return List cords
     */
    public List<PixelCord> makeListOfPixelCords(){
        int hexSize = 40;
        List<PixelCord> cords = new LinkedList<>();
        //takes each hex cord and changes it to a pixel cord and
        //puts it in a list
        // got this from thr red blobs site
        for(Point p : this.board){
            int x = hexSize * 3/2 * p.getX() + 50;
            double y = hexSize * Math.sqrt(3) * (double)(p.getY() + 
                                    0.5 * (p.getX() &1)) *1.2 + 60;
            PixelCord pixelCord = new PixelCord(x, y);
            cords.add(pixelCord);
            this.pointsAndPixelCords.put(pixelCord, p);
        }
        return cords;
    }


    /**
     * Gets the point corresponding
     * with the given pixel corrdinate.
     * @param x
     * @param y
     * @return point 
     */
    public Point getPointForPix(int x, int y){
        PixelCord cord = new PixelCord(x,y);
        for(PixelCord j : pixelCords){
            if(j.equals(cord)){
                return pointsAndPixelCords.get(j);
            }
        }
        return null;
    }


    /**
     * Uncovers the point given. Ff it is 
     * flagged return. If it is a mine uncover 
     * the whole board. If the point is not next
     * to a mine uncover is neighbors.
     * @param point
     */
    public void uncover(Point point){
        if(point.getState() == State.FLAGGED){
            return;
        }
        if(point.isAMine()){
            for(Point p : this.board){
                if(p.isAMine()){
                    p.changeState(State.IS_MINE);
                }else if(p.getNumOfMines()> 0){
                    p.changeState(State.NEX_TO_MINE);
                }else{
                    p.changeState(State.UNCOVERED);
                }

            }
            this.steppedOnMine = true;
            return;
        }
        if(point.isAMine() == false){

            if(point.getNumOfMines() > 0){
                point.changeState(State.NEX_TO_MINE);
            }else if(point.getNumOfMines() == 0 && 
                     point.getState() != State.UNCOVERED){
                point.changeState(State.UNCOVERED);
                recUncover(point);
            }
        }
    }

    /**
     * Checks if the game has been
     * won or lost. 
     * @return true for win false for loss
     */
    public boolean checkWin(){
        if(this.steppedOnMine == true){
            this.gameOver = true;
            return false;
        }
        int coveredCells = 0;
        for(Point p : this.board){
            if(p.getState() == State.COVERED ||
               p.getState() == State.FLAGGED){
                coveredCells++;
            }
        }
        if(coveredCells == this.mineLength){
            this.gameOver = true;
            return true;
        }
        return false;
    }

    /**
     * Checks to see if game is over.
     * @return true false
     */
    public boolean isOver(){
        return this.gameOver;
    }

    
    /**
     * Places the mines on the board randomly.
     */
    public void placeMines(){
        Random rand = new Random();
        for(int i = 0; i < this.mineLength; i++){
            int random = rand.nextInt((this.rows * this.cols));
            while(this.board.get(random).isAMine()){
                random = rand.nextInt((this.rows * this.cols));
            }
            this.board.get(random).makeMine();
        }

    }


    /**
     * If the pont is not flagged flags it.
     * If it is flagged unflags it.
     * @param point
     */
    public void toggleFlag(Point point){
        if(point.getState() == State.FLAGGED){
            point.changeState(State.COVERED);
        }else if(point.getState() == State.COVERED){
            point.changeState(State.FLAGGED);
        }
    }

    /**
     * Prints a string representation of the board.
     * @return str string of board
     *
     */
    @Override
    public String toString(){  
        String str = "";
        String tempEven = "";
        String tempOdd = "";
        String tempChar = "";

        for(int i = 0; i < this.board.size(); i++){
            int x = board.get(i).getX();
            State state = this.board.get(i).getState();
            if(state == State.COVERED){
                tempChar = COVERED_S;
            }else if(state == State.UNCOVERED){
                tempChar = UNCOVERED_S;
            }else if(state == State.FLAGGED){
                tempChar = FLAGGED_S;
            }else if(state == State.IS_MINE){
                tempChar = MINE_S;
            }else if(state == State.NEX_TO_MINE){
                tempChar = board.get(i).getNumOfMines().toString();
            }
            
            

            if(x == 0 || x % 2 == 0){
                if(x == this.cols - 2){
                    
                    tempEven += tempChar + " \n";
                    str += tempEven;
                    tempEven = "";
                }else{
                    tempEven += tempChar + " ";
                }
                   
            }else if(x == 1 || x % 2 != 0){
                if(x == this.cols - 1){
                    tempOdd += " " + tempChar + "\n";
                    str += tempOdd;
                    tempOdd = "";
                }else{
                    tempOdd += " " + tempChar;    
                }
                    
            }

        }

        return str;
    }

}