import java.text.DecimalFormat;

/****************************************************************************
 *  Command: HexBoardStats N0 N1 T
 *
 *  This program takes the board sizes N0,N1 and game count T as a command-line
 *  arguments. Then, the program runs T games for each board size N where
 *  N0 <= N <= N1 and where each play randomly chooses an unset tile to set in
 *  order to estimate the probability that player 1 will win.
 *  @author Ryan Jones
 ****************************************************************************/


public class HexBoardStats {
    public static void main(String[] args) {
        int low = Integer.parseInt(args[0]);
        int high = Integer.parseInt(args[1]);
        int numGames = Integer.parseInt(args[2]);
        runStats(low, high, numGames);
    }

    /**
     * Method runs through T games on boards ranging in size from N0 to N1. Method
     * also prints out win percentages for both players and finally prints the average
     * win percentage for each player overall.
     * <p>
     * Note: I have not implemented the getP1WinProbabilityEstimates(int N) method.
     * The reasoning for this is described in the readme.html file
     *
     * @param N0 - Minimum board size
     * @param N1 - Maximum board size
     * @param T  - Number of games to run
     */
    public static void runStats(int N0, int N1, int T) {

        //Check that values will work
        validate(N0, N1, T);
        //print out number of games to play
        StdOut.println("T = " + T);
        //local variable for T
        int numGames = T;
        //variables to keep track of p1 win percentage
        //per game and overall for each player
        double[] p1Percentages = new double[N1 - N0];
        double percentage,
                totalP1WinPercentages = 0.0,
                totalP2WinPercentages = 0.0;
        //DecimalFormat for printing decimal percentage values
        DecimalFormat df = new DecimalFormat("#.###");
        double totalTime = 0.0;

        //loop runs through board sizes
        for (int i = N0; i <= N1; ++i) {
            //track number of p1 wins for each board size
            int player1Wins = 0;
            int moves[] = new int[i * i];
            for (int a = 0; a < moves.length; a++) {
                moves[a] = a;
            }
            Stopwatch sw = new Stopwatch();
            //loop for number of games for board
            for (int j = 0; j < numGames; ++j) {
                // sets a size for the board between N0 and N1
                HexBoard h = new HexBoard(i);
                int player = 1;

                StdRandom.shuffle(moves);
                for (int k = 0; k < moves.length; k++) {
                    int yCoordinate = moves[k] / i;
                    int xCoordinate = moves[k] % i;
                    if (!h.isSet(xCoordinate, yCoordinate)) {
                        h.setTile(xCoordinate, yCoordinate, player);
                        //check p1 win status
                        if (h.hasPlayer1Won()) {
                            ++player1Wins;
                            break;
                        }
                        //swap player if turn is over
                        player = (player == 1) ? 2 : 1;
                    } else { //continue to top of while loop if coordinate is invalid
                        continue;
                    }
                }
            }
            double time = sw.elapsedTime();
            totalTime += time;
            //win percentage for p1
            percentage = (double) player1Wins / (double) numGames;
            //Add running win percentage for each player
            totalP1WinPercentages += percentage;
            totalP2WinPercentages += (1 - percentage);
            //print out
            StdOut.println("N = " + i + ", P1 = " + df.format(percentage) + " (" + player1Wins +
                    "), P2 = " + df.format((1 - percentage)) + " (" + (numGames - player1Wins) + ")");
        }

        //finally, calculate and print average win percentage for each player
        StdOut.println("Total time: " + df.format(totalTime));
    }

    /**
     * Method to determine if values given are valid for the method.
     *
     * @param N0
     * @param N1
     * @param T
     */
    private static void validate(int N0, int N1, int T) {
        if (N0 <= 0 || N1 <= 0 || T <= 0 || N1 < N0) {
            throw new IllegalArgumentException("The variables " + N0 + ", " + N1 + ", " + T + " will not work.");
        }
    }
}
