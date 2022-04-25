/**
 * Test hex mines class contains methods
 * for testing the hex mine manager.
 * All methods static to negate need
 * for a TestHexMines object.
 * @author Sam Cox
 * @version date 11/27/18
 */
public class TestHexMines{
    /**
     * Tests toggling a flag on a point and prints 
     * position of point.
     * @param game
     * @param num
     */

    public static void testToggleFlag(HexMineManager game, int num){
        game.toggleFlag(game.getBoard().get(num));
        System.out.println("Toggle flag at (" + game.getBoard().get(num).getX()
                            +", "+ game.getBoard().get(num).getY()+")");
    }
    /**
     * Tests uncovering a point and prints place uncovered.
     * @param game objec
     * @param num index in list
     */
    public static void testUncover(HexMineManager game, int num){
        game.uncover(game.getBoard().get(num));
        System.out.println("Uncover (" + game.getBoard().get(num).getX()
                            +", "+ game.getBoard().get(num).getY()+")");
        if(game.getBoard().get(num).isAMine()){
            System.out.println("Oops clicked on a mine!");
        }


    }
    /**
     * Prints the starting text
     * of the test.
     */
    public static void printStart(){
        System.out.println();
        System.out.println("String representation of spaces");
        System.out.println("c = covered space");
        System.out.println("f = flagged space");
        System.out.println(". = uncovered space with no neighboring mines");
        System.out.println("Numbers represent the amount" 
                            +" of mines next to a space");

    }
    /**
     * Main method starts testing by 
     * uncoverint and flagging points
     * the printing the board.
     * @param args
     */
    public static void main(String[] args){
        printStart();
        System.out.println("First Mine Manager of size" + 
                           "10x10 with 10 mines");
        HexMineManager game = new HexMineManager(10, 10, 10);
        System.out.println();
        System.out.println(game.toString());
        testToggleFlag(game, 17);
        System.out.println();
        System.out.println(game.toString());
        testUncover(game, 35);
        System.out.println();
        System.out.println(game.toString());
        testUncover(game, 78);
        System.out.println(game.toString());
        testUncover(game, 95);
        System.out.println(game.toString());

        System.out.println("Second mine manage of size" +
                           " 6x6 with 14 mines");
        HexMineManager game2 = new HexMineManager(7,5,2);
        System.out.println();
        System.out.println(game2.toString());
        testUncover(game2, 14);
        System.out.println(game2.toString());
        testToggleFlag(game2, 17);
        System.out.println(game2.toString());
        testUncover(game2, 15);
        System.out.println(game2.toString());
        System.out.println();
        System.out.println("Go back to game one and play more!");
        testUncover(game, 44);
        System.out.println(game.toString());
        testUncover(game, 38);
        System.out.println(game.toString());
        System.out.println();
        System.out.println("Testing complete!");

    }
}
