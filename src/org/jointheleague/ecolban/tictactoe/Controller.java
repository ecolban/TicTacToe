package org.jointheleague.ecolban.tictactoe;

public class Controller {

    private final Model model;

    Controller(Model model) {
        super();
        this.model = model;
    }

    public void onClick(int pos) {
        model.mark(pos);
    }

    public void onGameOver(boolean playAgain) {
        if (playAgain) {
            model.reset();
        } else {
            System.exit(0);
        }
    }
}
