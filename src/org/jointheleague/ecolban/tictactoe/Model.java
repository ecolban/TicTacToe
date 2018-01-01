package org.jointheleague.ecolban.tictactoe;

import java.util.Observable;

public class Model extends Observable {

    private Mark[] marks = new Mark[9];
    private Mark player = Mark.X;
    private boolean isGameOver = false;
    private boolean isDraw = false;
    private int numMoves = 0;

    Model() {
        for (int i = 0; i < marks.length; i++) {
            marks[i] = Mark.Blank;
        }
    }

    public Mark[] getMarks() {
        return marks.clone();
    }

    public Mark getPlayer() {
        return player;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isDraw() {

        return isDraw;
    }

    public void mark(int pos) {
        if (pos < 0 || pos >= marks.length) {
            throw new IllegalArgumentException("pos must be between 0 (inclusive) and 9 (exclusive).");
        }
        if (marks[pos] != Mark.Blank) {
            throw new IllegalArgumentException("pos must be blank.");
        }
        marks[pos] = player;
        numMoves++;
        checkGameOver();
        if (!isGameOver) switchPlayer();
        setChanged();
        notifyObservers();
    }

    private void checkGameOver() {
        boolean isWin = marks[0] == player && marks[1] == player && marks[2] == player
                || marks[3] == player && marks[4] == player && marks[5] == player
                || marks[6] == player && marks[7] == player && marks[8] == player
                || marks[0] == player && marks[3] == player && marks[6] == player
                || marks[1] == player && marks[4] == player && marks[7] == player
                || marks[2] == player && marks[5] == player && marks[8] == player
                || marks[0] == player && marks[4] == player && marks[8] == player
                || marks[2] == player && marks[4] == player && marks[6] == player;

        isDraw = !isWin && numMoves == marks.length;
        isGameOver = isWin || isDraw;
    }

    private void switchPlayer() {
        player = player == Mark.X ? Mark.O : Mark.X;
    }

    public void reset() {
        numMoves = 0;
        for (int i = 0; i < marks.length; i++) {
            marks[i] = Mark.Blank;
        }
        isGameOver = false;
        isDraw = false;
        player = Mark.X;
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

}

