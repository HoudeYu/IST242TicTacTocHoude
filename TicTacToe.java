import java.util.Scanner;

public class TicTacToe {
    private static char[][][] board; // 三维数组来表示游戏棋盘
    private static int boardSize; // 棋盘大小
    private static char currentPlayer = 'X'; // 当前玩家，'X' 或 'O'

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the dimension (n): "); // 提示输入棋盘维度
        int n = scanner.nextInt(); // 获取用户输入的维度
        boardSize = n;
        board = new char[n][n][n]; // 初始化棋盘
        initializeBoard(); // 初始化棋盘状态

        char winner = '\0'; // 获胜者，初始为空
        while (winner == '\0') { // 当没有获胜者时继续游戏
            displayBoards(); // 显示当前棋盘状态
            getPlayerMove(); // 玩家落子
            winner = checkWinner(); // 检查是否有获胜者
            if (winner != '\0') break; // 如果有获胜者，结束游戏
            computerMove(); // 电脑落子
            winner = checkWinner(); // 检查是否有获胜者
        }

        displayBoards(); // 显示最终棋盘状态
        if (winner == 'D') {
            System.out.println("It's a draw!"); // 如果是平局
        } else {
            System.out.println("Player " + winner + " wins!"); // 如果有玩家获胜
        }
    }

    // 初始化棋盘，将所有位置初始化为空格
    private static void initializeBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                for (int k = 0; k < boardSize; k++) {
                    board[i][j][k] = ' '; // 将每个位置初始化为空格
                }
            }
        }
    }

    // 显示当前棋盘状态
    private static void displayBoards() {
        for (int i = 0; i < boardSize; i++) {
            System.out.println("Dimension " + i + ":");
            displayBoard(i); // 显示当前维度的棋盘
            System.out.println();
        }
    }

    // 显示指定维度的棋盘
    private static void displayBoard(int dimension) {
        System.out.print("  ");
        for (int i = 0; i < boardSize; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 0; i < boardSize; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < boardSize; j++) {
                System.out.print(board[dimension][i][j]); // 打印棋盘上每个位置的值
                if (j < boardSize - 1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i < boardSize - 1) {
                for (int j = 0; j < boardSize; j++) {
                    System.out.print("--");
                }
                System.out.println();
            }
        }
    }

    // 获取玩家的落子
    private static void getPlayerMove() {
        Scanner scanner = new Scanner(System.in);
        int row, col, depth;
        do {
            System.out.print("Player " + currentPlayer + ", enter row (0 to " + (boardSize - 1) + ") for dimension 0: ");
            row = scanner.nextInt(); // 获取玩家输入的行数
            System.out.print("Player " + currentPlayer + ", enter column (0 to " + (boardSize - 1) + ") for dimension 0: ");
            col = scanner.nextInt(); // 获取玩家输入的列数
            System.out.print("Player " + currentPlayer + ", enter depth (0 to " + (boardSize - 1) + ") for dimension 0: ");
            depth = scanner.nextInt(); // 获取玩家输入的深度
        } while (!isValidMove(row, col, depth)); // 重复直到玩家输入有效落子位置

        board[row][col][depth] = currentPlayer; // 在棋盘上标记玩家的落子
    }

    // 检查落子是否有效
    private static boolean isValidMove(int row, int col, int depth) {
        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize || depth < 0 || depth >= boardSize || board[row][col][depth] != ' ') {
            System.out.println("Invalid move! Try again."); // 显示无效落子的错误消息
            return false;
        }
        return true;
    }

    // 检查是否有玩家获胜或平局
    private static char checkWinner() {
        // 检查行是否有获胜组合
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                boolean win = true;
                for (int k = 1; k < boardSize; k++) {
                    if (board[i][j][k] != board[i][j][k - 1]) {
                        win = false;
                        break;
                    }
                }
                if (win && board[i][j][0] != ' ') {
                    return board[i][j][0]; // 返回获胜玩家的标志
                }
            }
        }

        // 检查列是否有获胜组合
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                boolean win = true;
                for (int k = 1; k < boardSize; k++) {
                    if (board[i][k][j] != board[i][k - 1][j]) {
                        win = false;
                        break;
                    }
                }
                if (win && board[i][0][j] != ' ') {
                    return board[i][0][j]; // 返回获胜玩家的标志
                }
            }
        }

        // 检查对角线是否有获胜组合
        for (int i = 0; i < boardSize; i++) {
            boolean win1 = true;
            boolean win2 = true;
            for (int j = 1; j < boardSize; j++) {
                if (board[i][j][j] != board[i][j - 1][j - 1]) {
                    win1 = false;
                }
                if (board[i][j][boardSize - 1 - j] != board[i][j - 1][boardSize - j]) {
                    win2 = false;
                }
            }
            if (win1 && board[i][0][0] != ' ') {
                return board[i][0][0]; // 返回获胜玩家的标志
            }
            if (win2 && board[i][0][boardSize - 1] != ' ') {
                return board[i][0][boardSize - 1]; // 返回获胜玩家的标志
            }
        }

        // 检查立方体对角线是否有获胜组合
        boolean win1 = true;
        boolean win2 = true;
        for (int i = 1; i < boardSize; i++) {
            if (board[i][i][i] != board[i - 1][i - 1][i - 1]) {
                win1 = false;
            }
            if (board[i][i][boardSize - 1 - i] != board[i - 1][i - 1][boardSize - i]) {
                win2 = false;
            }
        }
        if (win1 && board[0][0][0] != ' ') {
            return board[0][0][0]; // 返回获胜玩家的标志
        }
        if (win2 && board[0][0][boardSize - 1] != ' ') {
            return board[0][0][boardSize - 1]; // 返回获胜玩家的标志
        }

        // 检查平面对角线是否有获胜组合
        for (int i = 0; i < boardSize; i++) {
            win1 = true;
            win2 = true;
            for (int j = 1; j < boardSize; j++) {
                if (board[j][i][j] != board[j - 1][i][j - 1]) {
                    win1 = false;
                }
                if (board[j][i][boardSize - 1 - j] != board[j - 1][i][boardSize - j]) {
                    win2 = false;
                }
            }
            if (win1 && board[0][i][0] != ' ') {
                return board[0][i][0]; // 返回获胜玩家的标志
            }
            if (win2 && board[0][i][boardSize - 1] != ' ') {
                return board[0][i][boardSize - 1]; // 返回获胜玩家的标志
            }
        }

        // 检查高维度是否有获胜组合
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                boolean win = true;
                for (int k = 1; k < boardSize; k++) {
                    if (board[k][i][j] != board[k - 1][i][j]) {
                        win = false;
                        break;
                    }
                }
                if (win && board[0][i][j] != ' ') {
                    return board[0][i][j]; // 返回获胜玩家的标志
                }
            }
        }

        // 检查是否平局
        boolean isBoardFull = true;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                for (int k = 0; k < boardSize; k++) {
                    if (board[i][j][k] == ' ') {
                        isBoardFull = false;
                        break;
                    }
                }
            }
        }

        return isBoardFull ? 'D' : '\0'; // 返回平局标志'D'或无获胜者标志'\0'
    }


    // 切换当前玩家
    private static void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X'; // 在'X'和'O'之间切换当前玩家
    }

    // 让电脑落子
    private static void computerMove() {
        int row, col, depth;
        do {
            row = (int) (Math.random() * boardSize); // 生成随机行数
            col = (int) (Math.random() * boardSize); // 生成随机列数
            depth = (int) (Math.random() * boardSize); // 生成随机深度
        } while (!isValidMove(row, col, depth)); // 重复直到生成有效落子位置

        System.out.println("Computer chooses row: " + row + ", column: " + col + ", depth: " + depth);
        board[row][col][depth] = 'O'; // 在棋盘上标记电脑的落子
    }
}