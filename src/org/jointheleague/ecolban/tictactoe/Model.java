package org.jointheleague.ecolban.tictactoe;

import java.util.Observable;

public class Model extends Observable {

    private Mark[] board = new Mark[9];
    private Player player;
    private boolean isGameOver;
    private boolean isDraw;
    private int numMoves;

    Model() {
        reset(Player.X);
    }

    public Mark[] getBoard() {
        return board.clone();
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * @return true if and only if the game is over
     */
    public boolean isGameOver() {
        return isGameOver;
    }

    /**
     * @return true iff it's a draw
     */
    public boolean isDraw() {

        return isDraw;
    }

    /**
     * Adds a mark on the board for player at pos
     *
     * @param player the player
     * @param pos the position on the board where player's mark is to be put.
     * @throws IllegalStateException if it is not player's turn or pos is already marked with X or O
     * @throws IllegalArgumentException if pos is outside the permitted range
     */
    public void play(Player player, int pos) throws IllegalStateException, IllegalArgumentException {
        if (pos < 0 || pos >= board.length) {
            throw new IllegalArgumentException("pos must be between 0 (inclusive) and 9 (exclusive).");
        }
        if (this.player != player) {
            throw new IllegalStateException("It's not " + player + "'s turn.");
        }
        if (board[pos] != Mark.Blank) {
            throw new IllegalStateException("pos must be blank.");
        }
        board[pos] = this.player.mark;
        numMoves++;
        checkGameOver();
        if (!isGameOver) switchPlayer();
        setChanged();
        notifyObservers();
    }

    private void checkGameOver() {
        Mark m = player.mark;
        boolean isWin = board[0] == m && board[1] == m && board[2] == m
                || board[3] == m && board[4] == m && board[5] == m
                || board[6] == m && board[7] == m && board[8] == m
                || board[0] == m && board[3] == m && board[6] == m
                || board[1] == m && board[4] == m && board[7] == m
                || board[2] == m && board[5] == m && board[8] == m
                || board[0] == m && board[4] == m && board[8] == m
                || board[2] == m && board[4] == m && board[6] == m;

        isDraw = !isWin && numMoves == board.length;
        isGameOver = isWin || isDraw;
    }

    private void switchPlayer() {
        player = player == Player.X ? Player.O : Player.X;
    }

    /**
     * Reset the game to the initial state. After this, it's startPlayer's turn.
     * @param startPlayer the player to begin the game.
     */
    public void reset(Player startPlayer) {
        numMoves = 0;
        for (int i = 0; i < board.length; i++) {
            board[i] = Mark.Blank;
        }
        isGameOver = false;
        isDraw = false;
        player = startPlayer;
        setChanged();
        notifyObservers();
    }

    public enum Mark {

        O("O"), X("X"), Blank("");

        private final String string;

        Mark(String string) {
            this.string = string;
        }

        @Override
        public String toString() {
            return string;
        }
    }

    public enum Player {

        O(Mark.O), X(Mark.X);

        private final Mark mark;

        Player(Mark mark) {
            this.mark = mark;
        }
    }
}

