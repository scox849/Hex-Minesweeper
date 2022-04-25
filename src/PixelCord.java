/**
 * Class for pixel coordinate object.
 * Contains methods and consructor
 * needed for object.
 * @author Samuel Cox
 * @version date 12/7/18
 */
public class PixelCord{
    private int x;
    private double y;

    /**
     * PixelCord constructor 
     * makes new object with 
     * given x and y pixel coords.
     * @param x
     * @param y
     */
    public PixelCord(int x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * Returns x value of the
     * coordinate.
     * @return this.x
     */
    public int getX(){
        return this.x;
    }

    /**
     * Returns y value of the
     * coordinate.
     * @return this.y
     */
    public double getY(){
        return this.y;
    }

    /**
     * Returns string representation 
     * of coordinate.
     * @return String s
     */
    @Override
    public String toString(){
        String s = "";
        s = this.x + " " + this.y;
        return s;
    }

    /**
     * Tests to see if two PixelCords
     * are equal. If p.x and p.y is within 40 
     * pixels of this.x and this.y returns true 
     * @param p PixelCord
     * @return true/ false
     */

    public boolean equals(PixelCord p){
        int hexSize = 40;
        if(p.getX() > this.x - hexSize && p.getX() < this.x + hexSize){
            if(p.getY() > this.y - hexSize && p.getY() < 
                                       this.y + hexSize){
                return true;
            }
        }
        return false;
    }

    /**
     * Overrides hascode for hasmap
     * usage.
     * @return int this.x * 31 + (int)this.y
     */
    public int hascode(){
        return this.x * 31 + (int)this.y;
    }


}