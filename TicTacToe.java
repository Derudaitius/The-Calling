import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe {

    public static void main(String[] args) {

        boolean endGame = false;
        initializeBoard();
        //print board with positions
        System.out.println(" 1"  + " | 2" + " | 3");
        System.out.println("---+---+---");
        System.out.println(" 4"  + " | 5" + " | 6");
        System.out.println("---+---+---");
        System.out.println(" 7"  + " | 8" + " | 9");
        System.out.println();

        while (!endGame) {
            playerMove();
            printBoard();
            endGame = endGame(); //break out of loop if player makes a winning move
            if (endGame) {
                break;
            }
            compMove();
            printBoard();
            endGame = endGame();
        }

    }


    static Character[][] board = new Character[3][3];

    public TicTacToe() {

    }

    //initialize the board with empty spaces
    public static void initializeBoard() {
        for (Character[] characters : board) {
            Arrays.fill(characters, ' ');
        }
    }

    //check all winning positions and return true if a winning move is present
    public static boolean endGame() {
        //horizontal winning positions
        if (board[0][0] == board[0][1] && board[0][1] == board[0][2] && board[0][1] != ' ') {
            if (board[0][0] == 'O') {
                System.out.println("You Win!");
            } else {
                System.out.println("You Lose!");
            }
            return true;
        } else if (board[1][0] == board[1][1] && board[1][1] == board[1][2] && board[1][0] != ' ') {
            if (board[1][0] == 'O') {
                System.out.println("You Win!");
            } else {
                System.out.println("You Lose!");
            }
            return true;
        } else if (board[2][0] == board[2][1] && board[2][1] == board[2][2] && board[2][0] != ' ') {
            if (board[2][0] == 'O') {
                System.out.println("You Win!");
            } else {
                System.out.println("You Lose!");
            }
            return true;
        }

        //vertical winning positions
        if (board[0][0] == board[1][0] && board[1][0] == board[2][0] && board[1][0] != ' ') {
            if (board[0][0] == 'O') {
                System.out.println("You Win!");
            } else {
                System.out.println("You Lose!");
            }
            return true;
        } else if (board[0][1] == board[1][1] && board[1][1] == board[2][1] && board[0][1] != ' ') {
            if (board[0][1] == 'O') {
                System.out.println("You Win!");
            } else {
                System.out.println("You Lose!");
            }
            return true;
        } else if (board[0][2] == board[1][2] && board[1][2] == board[2][2] && board[0][2] != ' ') {
            if (board[0][2] == 'O') {
                System.out.println("You Win!");
            } else {
                System.out.println("You Lose!");
            }
            return true;
        }

        //diagonal winning positions
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[1][1] != ' ') {
            if (board[0][0] == 'O') {
                System.out.println("You Win!");
            } else {
                System.out.println("You Lose!");
            }
            return true;
        } else if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[1][1] != ' ') {
            if (board[0][2] == 'O') {
                System.out.println("You Win!");
            } else {
                System.out.println("You Lose!");
            }
            return true;
        }

        //check for a draw
        if (!vacantSpace(1) && !vacantSpace(2) && !vacantSpace(3) && !vacantSpace(4) && !vacantSpace(5) && !vacantSpace(6) && !vacantSpace(7) && !vacantSpace(8) && !vacantSpace(9)) {
            System.out.println("Its a Draw!");
            return true;
        }

        return false;
    }

    public static void playerMove() {
        //initialize scanner for user input
        Scanner scan = new Scanner(System.in);
        int pos = -1; // Initialize with an invalid value
        boolean validInput = false;

        //validate user input as int 1-9, and loop while not valid
        while (!validInput) {
            System.out.println("Enter your desired position (1-9):");
            try {
                pos = scan.nextInt();
                // Check if the input is within the valid range
                if (pos >= 1 && pos <= 9 && vacantSpace(pos)) {
                    validInput = true; // Valid input, exit the loop
                } else if (pos >= 1 && pos <= 9 && !vacantSpace(pos)) {
                    System.out.println("Please enter a vacant space!");
                } else {
                    System.out.println("Please enter a number between 1 and 9.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 9.");
                scan.next(); // Clear the invalid input from the scanner
            }

        }move(pos, 'O');
    }

    //checks if a space is vacant or not
    public static boolean vacantSpace(int space) {
        switch (space) {
            case 1 -> {
                return board[0][0] == ' ';
            }
            case 2 -> {
                return board[0][1] == ' ';
            }
            case 3 -> {
                return board[0][2] == ' ';
            }
            case 4 -> {
                return board[1][0] == ' ';
            }
            case 5 -> {
                return board[1][1] == ' ';
            }
            case 6 -> {
                return board[1][2] == ' ';
            }
            case 7 -> {
                return board[2][0] == ' ';
            }
            case 8 -> {
                return board[2][1] == ' ';
            }
            case 9 -> {
                return board[2][2] == ' ';
            }
        }
        return false;
    }

    public static void move(int space, char xo) {
        switch (space) {
            case 1 -> {
                if (board[0][0] == ' ') board[0][0] = xo;
            }
            case 2 -> {
                if (board[0][1] == ' ') board[0][1] = xo;
            }
            case 3 -> {
                if (board[0][2] == ' ') board[0][2] = xo;
            }
            case 4 -> {
                if (board[1][0] == ' ') board[1][0] = xo;
            }
            case 5 -> {
                if (board[1][1] == ' ') board[1][1] = xo;
            }
            case 6 -> {
                if (board[1][2] == ' ') board[1][2] = xo;
            }
            case 7 -> {
                if (board[2][0] == ' ') board[2][0] = xo;
            }
            case 8 -> {
                if (board[2][1] == ' ') board[2][1] = xo;
            }
            case 9 -> {
                if (board[2][2] == ' ') board[2][2] = xo;
            }
        }
    }

    //print the board with current values
    public static void printBoard() {
        System.out.println(" " + board[0][0] + " | " + board[0][1] + " | " + board[0][2]);
        System.out.println("---+---+---");
        System.out.println(" " + board[1][0] + " | " + board[1][1] + " | " + board[1][2]);
        System.out.println("---+---+---");
        System.out.println(" " + board[2][0] + " | " + board[2][1] + " | " + board[2][2]);
        System.out.println();
    }

    public static void compMove() {
        // Try to make a winning move
        for (int i = 0; i < 3; i++) {
            // Check horizontal win possibilities
            if (canWin('X', i, 0, i, 1, i, 2)) return;
            // Check vertical win possibilities
            if (canWin('X', 0, i, 1, i, 2, i)) return;
        }
        // Check diagonals for winning
        if (canWin('X', 0, 0, 1, 1, 2, 2)) return;  // Left diagonal
        if (canWin('X', 0, 2, 1, 1, 2, 0)) return;  // Right diagonal

        // Try to block player from winning
        for (int i = 0; i < 3; i++) {
            if (canWin('O', i, 0, i, 1, i, 2)) return;
            if (canWin('O', 0, i, 1, i, 2, i)) return;
        }
        if (canWin('O', 0, 0, 1, 1, 2, 2)) return;
        if (canWin('O', 0, 2, 1, 1, 2, 0)) return;

        // Build in a row/column with one 'X'
        for (int i = 0; i < 3; i++) {
            if (buildLine( i, 0, i, 1, i, 2)) return;
            if (buildLine(0, i, 1, i, 2, i)) return;
        }
        if (buildLine(0, 0, 1, 1, 2, 2)) return; // Diagonal building
        if (buildLine( 0, 2, 1, 1, 2, 0)) return;

        // Make a move in an empty row/column or a random move if no other strategy works
        makeAnyMove();
    }

    //check for two X's or two O's, and one space
    private static boolean canWin(char player, int row1, int col1, int row2, int col2, int row3, int col3) {
        if (board[row1][col1] == player && board[row2][col2] == player && board[row3][col3] == ' ') {
            move(rowColToSpace(row3, col3), 'X'); // Convert to 1-9
            return true;
        }
        if (board[row1][col1] == player && board[row2][col2] == ' ' && board[row3][col3] == player) {
            move(rowColToSpace(row2, col2), 'X');
            return true;
        }
        if (board[row1][col1] == ' ' && board[row2][col2] == player && board[row3][col3] == player) {
            move(rowColToSpace(row1, col1), 'X');
            return true;
        }
        return false;
    }

    //check for one X and two spaces
    private static boolean buildLine(int row1, int col1, int row2, int col2, int row3, int col3) {
        int countX = 0, countEmpty = 0;
        if (board[row1][col1] == 'X') countX++;
        if (board[row2][col2] == 'X') countX++;
        if (board[row3][col3] == 'X') countX++;

        if (board[row1][col1] == ' ') countEmpty++;
        if (board[row2][col2] == ' ') countEmpty++;
        if (board[row3][col3] == ' ') countEmpty++;

        if (countX == 1 && countEmpty == 2) { // Move into an empty space if 1 'X' already exists
            if (board[row1][col1] == ' ') {
                move(rowColToSpace(row1, col1), 'X');
                return true;
            }
            if (board[row2][col2] == ' ') {
                move(rowColToSpace(row2, col2), 'X');
                return true;
            }
            if (board[row3][col3] == ' ') {
                move(rowColToSpace(row3, col3), 'X');
                return true;
            }
        }
        return false;
    }

    //make a random move
    private static void makeAnyMove() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    move(rowColToSpace(i, j), 'X');
                    return;
                }
            }
        }
    }

    // Helper function to convert row and column to space 1-9
    private static int rowColToSpace(int row, int col) {
        return row * 3 + col + 1; // 1-based conversion
    }

}
