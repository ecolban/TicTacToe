package org.jointheleague.ecolban.tictactoe;

import javax.swing.*;
import java.util.concurrent.ThreadLocalRandom;

public class Controller {

    private final Model model;

    Controller(Model model) {
        super();
        this.model = model;
    }

    public void onClick(Model.Player player, int pos) {
        model.play(player, pos);
    }

    public void onGameOver(int playAgainAnswer) {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        if (playAgainAnswer == JOptionPane.YES_OPTION) {
            final Model.Player[] players = Model.Player.values();
            model.reset(players[random.nextInt(players.length)]);
        } else {
            System.exit(0);
        }
    }
}
