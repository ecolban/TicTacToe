package org.jointheleague.ecolban.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class View extends JPanel implements Observer {

    private static final Font BIG = new Font(Font.SANS_SERIF, Font.PLAIN, 36);
    private final Model model;
    private final Controller controller;
    private JButton[] buttons = new JButton[9];
    private Model.Player player;

    View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        this.player = model.getPlayer();
        model.addObserver(this);
    }


    public static void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(model, controller);
        SwingUtilities.invokeLater(view::buildGui);
    }

    @Override
    public void update(Observable observable, Object arg) {
        if (observable != model) return;
        Model.Mark[] marks = model.getBoard();
        player = model.getPlayer();
        for (int i = 0; i < marks.length; i++) {
            buttons[i].setText(marks[i].toString());
            buttons[i].setEnabled(marks[i] == Model.Mark.Blank);
        }
        if (model.isGameOver()) {
            String message = (model.isDraw() ? "It's a draw." : player + " won.") + " Play again?";
            int answer = JOptionPane.showConfirmDialog(this, message, "Play again?", JOptionPane.YES_NO_OPTION);
            controller.onGameOver(answer);
        }
    }

    void buildGui() {
        JFrame frame = new JFrame("Tic-Tac-Toe");
        frame.add(this);
        setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setPreferredSize(new Dimension(70, 50));
            buttons[i].setFont(BIG);
            add(buttons[i]);
            final int j = i;
            buttons[i].addActionListener(e -> controller.onClick(player, j));
        }
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
