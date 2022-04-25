import java.util.*;

/**
 * Point class contains methods
 * and constructor for point object.
 * @author Samuel Cox
 * @version date 11/26/18
 */
public class Point{
    private int xCord;
    private int yCord;
    private State state;
    private boolean isMine;
    private int numOfMines;

    /**
     * Constructor for point sets the values
     * of the member variables with the values 
     * provided.
     * @param xCord @param yCord @param state
     * @param isMine @param numOfMines
     */
    public Point(int xCord, int yCord, State state, 
                 boolean isMine, int numOfMines){
        this.xCord = xCord;
        this.yCord = yCord;
        this.state = state;
        this.isMine = false;
        this.numOfMines = numOfMines;
    }

    /**
     * Returns the x value of the point.
     * @return this.xCord
     *
     */
    public int getX(){
        return this.xCord;
    }
    /**
     * Returns the y value of the point.
     * @return this.yCord
     *
     */
    public int getY(){
        return this.yCord;
    }
    /**
     * Returns the state of the point.
     * @return this.state
     *
     */
    public State getState(){
        return this.state;
    }
    /**
     * Changes the state of the point 
     * to the given state.
     * @param state
     */
    public void changeState(State state){
        this.state = state;
    }

    /**
     * Returns the boolean value of whether
     * point is a mine.
     * @return this.isMine
     *
     */
    public boolean isAMine(){
        return this.isMine;
    }

    /**
     * Turns the boolean value of isMine to
     * true.
     *
     */
    public void makeMine(){
        this.isMine = true;
    }
    /**
     * Returns the number of mines the
     * point is next to.
     * @return this.numOfMines
     *
     */
    public Integer getNumOfMines(){
        return this.numOfMines;
    }

    /**
     * Sets the number of mines the point
     * is next to.
     * @param mines
     */
    public void setNumOfMines(int mines){
        this.numOfMines = mines;
    }  
}
        
        
