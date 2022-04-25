# Hex-Minesweeper
HOW TO PLAY GAME
    
    //STARTING A GAME\\
    To start the game you can either run the jar by clicking on it, 
    or you can run it on the command line with or without using arguments.
    The arguments on the command line specify the number of rows coloumns 
    and mines the board will have. So, for example if you wanted to have a
    game using 7 rows 8 coloumns and 15 mines you would type the aruments
    in the following order.

    java -jar HexMines.java 7 8 15

    Based on my testing the GUI and Manger can handle any sized board(as long 
    as if fits on your screen) and number of mines assuming that the 
    number of mines is not greater than total number of hexagons.

    The hexagons themselves dont change size, the size of the frame and panel 
    just increase
    based on the number of rows and coloumns. The largest size board I was 
    able to fit on 
    my laptop was 7 by 20.

    The default size of the board and number of mines are six rows six columns
    and six mines. So as far as difficulty goes you can make it as hard or 
    easy as you want. Making a board with one mine helped me during testing
    the win and loss conditions and I hope it helps you when grading.

    //CONTROLS\\
    To uncover a hexagon left click on it and to flag a hexagon right click on
    it. (Mouse recommended)

    //SCORE\\
    The game has a label displaying the number of mines and the number of flags
    placed. The game is won when all of the hexagons, except the hexagons which
    are hiding mines, are uncovered. Which means that the mines can be left in
    an uncovered or flagged state. So long as the mines are not Uncovered and 
    all other spaces are the player wins. If a mine is clicke on the player 
    loses and the board is uncovered.


PROGRAM INTERNALS
    
    //CLASSES\\
    The entires program contains 4 regular classes (not including TestHexMines)
    2 inner classes 1 enum and 2 anonymous inner classes. The anonymous inner classes
    are used for listeners. One for mouseReleased and one for the start of 
    the timer. One of the inner classes is used as a helper to read in an image and the other is used to create the painting of the game board. All inner classes are in HexMinesGUI.java. HexMinesGUI is used to create and display the GUI as well as the necessary listeners. The other 3 classes are used for the management of the game.

    HexMineManger manages the storage of the game board and the methods
    used to find neighbors of mines aswell as uncovering spaces on the board.
    The board is stored in a single list containing Point objects from the 
    point class that I made. From the x and y coordinates in each point
    the HexMineManager is able to find the neighbors aswell as uncover the
    spaces recursively when necessary. The HexMineManager take 3 paramerters
    in its consturctor the num of rows cols and mines to be used for the game.
    It also makes a list of pixel coordinates using the PixelCord object 
    the PixelCords have an x and a y representing the center of the hexagon 
    and the point it is associated with. So at the Point 0,0 the PixelCord is 50,60. The Pixel cords are put into a hashmap as they keys with the points the correspond to as values. This makes it easier to uncover 
    the hexagons when they are clicked on.

    Point is using to store the x and y board position(not pixel coord) 
    the State of the point a boolean determining if it is a mine and the
    number of mines next to it. The class mainly contains setters and
    getters as it is only used to store data.

    State is the only enum used which stores 5 "States", COVERED,
    UNCOVERED, IS_MINE, NEX_TO_MINE, FLAGGED. The enum just made it
    easier for me to build the game which is why I chose to use it.

    PixelCord is similar to Point however it only contains two values
    the x point in pixels and the y point in pixels. The methods are 
    just getters aside from toString(), equals(), and hashcod(). I
    Overrode toString() for debugging purposes, equals() becuase 
    equality between two PixelCords means that they are within
    40 pixels of one another, and hashcode() for the hashmap used
    in HexMineMangaer.

    HexMineGUI draws the board based on its size and has the labels
    and timer. HexMineGUI is also where the main() method is located
    primarily because I like it there.

    //ALGORITHMS\\

    Hex Coordinate logic
    The logic for finding the pixel coordinates on the board are as
    follows.
    The x point is found by multiplying 40 (half the width of 
    a hexagon) by 3/2 the x value of a point then adding 50.
    The y point is found by multiplying 40 by the square root of 3
    then multiplying that by the y value of the point + 0.5 which is
    multiplied by the x value that has used a binary operator (x&1),
    then multiplying that by 1.2 then adding 60 to the value.
    The majority of the algorithm was taken from the red blobs game
    site however I slightly modified it for my purposes.
    Then when a hexagon is clicked on if the spot where the mouse
    was released is within 40 pixels of that pixelCord determined 
    earlier it uncovers or flags the hexagon.

    Placing random mines 

    Mines are places by using a loop which stop ones the number
    of mines in that game has been reached. A random number
    generator picks a number based on the size of the board
    and if that point at that index has not already been picked
    in the board list it places a mine there.

    Uncovering Cells and Neighbors

    If the cell clicked on is a mine it uncovers the entire board
    if it is next to a mine it uncovers that one hexagon and if 
    it is not next to any mines it uncovers the points next to 
    it that are not mines and repeats this process for each 
    hexagon uncovered that is not next to any mines.
    The uncovering is done recursivly  

    Using the uncover() and recUncover() methods when uncover is
    called on a piece the piece is uncovered and recUncovered is
    called if the piece was not next to a mine. recUncover checks
    each neighbor and uncovers them if they are not mines and this
    is done until all of there are only pieces next to mines.

    End of game detection

    If a mine is clicked on the game is over and lost.
    If all the hexagons which are not mines are uncovered
    checkWin is called which then loop through the board
    and adds one to an int if the covered or flagged cell
    is a mine and then if that that int is equal to the 
    number of mines in the game the game is over but it is 
    won.

EXTRAS
    
    Becuase of time constraints I was only able to add one
    extra being the small bomb img that appears on each mine 
    when the board is uncovered.

BUGGS
    
    The toString() method in HexMineManager will only
    display the board as a string if the rows and cols
    multiply to be an even perfect square so 6x6 and 8x8
    will work but 7x5 will not.
    Didn't have time to get this method fixed.
