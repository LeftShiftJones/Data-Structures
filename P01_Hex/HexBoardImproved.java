/****************************************************************************
 *  This class manages an N-by-N hex game board .
 *  @author Ryan Jones, Dr. Jon Denning
 ****************************************************************************/

public class HexBoardImproved {
    /**
     * Global Quick Union object(s)
     */
    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF boardPaths;
    //private QuickUnionUF uf;
    //private QuickUnionUF boardPaths;

    /**
     * Global integer array to hold player values for tiles
     */
    private int[] board;

    /**
     * Global integer to track size of board
     */
    private final int boardSize;

    /**
     * Global integer to track number of unset tiles
     */
    private int unsetTiles, winningTile;

    //private Stopwatch sw;

    //private double elapsedTime;

    /**
     * Primary constructor for the HexBoard class. Initializes fields.
     *
     * @param N - Size of board
     */
    public HexBoardImproved(int N) {
        //sw = new Stopwatch();
        //elapsedTime = 0.0;
        winningTile = -1;

        boardSize = N;
        board = new int[N * N];
        unsetTiles = board.length;
        //new Quick Union object with 4 "ghost" nodes for win conditions
        uf = new WeightedQuickUnionUF(N * N + 4);
        boardPaths = new WeightedQuickUnionUF(N * N);
        //Non-wighted QuickUnion object
        //uf = new QuickUnionUF(N * N + (N * 4));
    }

    /**
     * Returns player at given index.
     *
     * @param row Row to check
     * @param col Column to check
     * @return Player at index
     */
    public int getPlayer(int row, int col) {
        return board[singleArrayValue(row, col)];
    }

    /**
     * Checks to see if given tile is set
     *
     * @param row Row to check
     * @param col Col to check
     * @return Player at index
     */
    public boolean isSet(int row, int col) {
        if (getPlayer(row, col) != 0) {
            return true;
        }
        return false;
    }

    /**
     * Checks through tiles to see if tile at index is on the winning path.
     *
     * @param row Row to check
     * @param col Column to check
     * @return Boolean value of tile being on winning path
     */
    public boolean isOnWinningPath(int row, int col) {
        //case for player 1
        if (hasPlayer1Won()
                && (boardPaths.connected(singleArrayValue(row, col), winningTile))
                && (boardPaths.connected(singleArrayValue(row, col), winningTile))) {
            return true;
        }
        //case for player 2
        if (hasPlayer2Won()
                && (boardPaths.connected(singleArrayValue(row, col), winningTile))
                && (boardPaths.connected(singleArrayValue(row, col), winningTile))) {
            return true;
        }
        return false;
    }

    /**
     * Method checks if desired tile is occupied. If not, sets tile to current player.
     *
     * @param row    Row of board to be played
     * @param col    Column of board to be player
     * @param player Current player
     */
    public void setTile(int row, int col, int player) {
        //check to see if tile is taken
        if (!isSet(row, col)) {
            //StdOut.println(row + ", " + col + ", " + player);
            //set player to tile
            board[singleArrayValue(row, col)] = player;
            //decrement unset tiles
            --unsetTiles;
            //see if adjacent tiles can be connected
            connectAdjacent(row, col, player);
            if (hasPlayer1Won()) {
                winningTile = singleArrayValue(row, col);
            }
            if (hasPlayer2Won()) {
                winningTile = singleArrayValue(row, col);
            }
        }
    }

    /**
     * Checks to see if the ghost nodes added
     * for the Quick Union object for P1 are connected.
     *
     * @return boolean value of connected ghost nodes
     */
    public boolean hasPlayer1Won() {
        int s = boardSize;
        if (uf.connected(singleArrayValue(0, boardSize), singleArrayValue(1, boardSize))) {
            return true;
        }
        return false;
    }

    /**
     * Checks to see if the ghost nodes added
     * for the Quick Union object for P2 are connected.
     *
     * @return boolean value of connected ghost nodes
     */
    public boolean hasPlayer2Won() {
        int s = boardSize;
        if (uf.connected(singleArrayValue(2, boardSize), singleArrayValue(3, boardSize))) {
            return true;
        }
        return false;
    }

    /**
     * @return Number of unset tiles
     */
    public int numberOfUnsetTiles() {
        return unsetTiles;
    }

    /**
     * Converts 2D array index to 1D array index
     *
     * @param - row Row on board
     * @param - col Column on board
     * @return int value of 1D index
     */
    private int singleArrayValue(int row, int col) {
        return (boardSize * col) + row;
    }

    /**
     * Connects tiles
     *
     * @param row1 Current tile row
     * @param col1 Current tile column
     * @param row2 Connecting tile row
     * @param col2 Connecting tile column
     */
    private void unionTiles(int row1, int col1, int row2, int col2) {

        uf.union(singleArrayValue(row1, col1), singleArrayValue(row2, col2));
        //StdOut.println("Connected " + singleArrayValue(row1, col1) + " and " + singleArrayValue(row2, col2));
        if (col2 < boardSize) {
            boardPaths.union(singleArrayValue(row1, col1), singleArrayValue(row2, col2));
        }
    }

    /**
     * Looks at adjacent tiles to see if they are owned by the player.
     * Also handles edge cases, connecting to "ghost" nodes
     *
     * @param row    Row on board
     * @param col    Column on board
     * @param player Current Player
     */
    private void connectAdjacent(int row, int col, int player) {
        //Check edge cases
        if (col == 0 || col == boardSize - 1 || row == 0 || row == boardSize - 1) {
            //P1
            if (col == 0 && player == 1) {
                unionTiles(row, col, 0, boardSize);
            }
            if (col == boardSize - 1 && player == 1) {
                unionTiles(row, col, 1, boardSize);
            }
            //P2
            if (row == 0 && player == 2) {
                unionTiles(row, col, 2, boardSize);
            }
            if (row == boardSize - 1 && player == 2) {
                unionTiles(row, col, 3, boardSize);
            }
        }

        //Check adjacent tiles to see if any connections are valid
        //Skips if outside of range (connected to "ghost" nodes incorrectly)
        //k, j-1
        if (col - 1 > -1 && getPlayer(row, col - 1) == player) {
            unionTiles(row, col, row, col - 1);
        }
        //k, j+1
        if (col + 1 < boardSize && getPlayer(row, col + 1) == player) {
            unionTiles(row, col, row, col + 1);
        }
        //k-1, j
        if (row - 1 > -1 && getPlayer(row - 1, col) == player) {
            unionTiles(row, col, row - 1, col);
        }
        //k-1, j+1
        if (row - 1 > -1 && col + 1 < boardSize && getPlayer(row - 1, col + 1) == player) {
            unionTiles(row, col, row - 1, col + 1);
        }
        //k+1, j-1
        if (row + 1 < boardSize && col - 1 > -1 && getPlayer(row + 1, col - 1) == player) {
            unionTiles(row, col, row + 1, col - 1);
        }
        //k+1, j
        if (row + 1 < boardSize && getPlayer(row + 1, col) == player) {
            unionTiles(row, col, row + 1, col);
        }
    }
}
