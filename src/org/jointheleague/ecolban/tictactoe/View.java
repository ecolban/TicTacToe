package org.jointheleague.ecolban.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class View extends JPanel implements Observer {

    private static final Font BIG = new Font("Helvetica", Font.PLAIN, 144);
    private final Model model;
    private JButton[] buttons = new JButton[9];
    private Controller controller;

    private View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        model.addObserver(this);
    }


    public static void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
        View view = new View(model, controller);
        SwingUtilities.invokeLater(view::buildGui);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o != model) return;
        Model.Mark[] marks = model.getMarks();
        for (int i = 0; i < marks.length; i++) {
            buttons[i].setText(marks[i].toString());
            buttons[i].setEnabled(marks[i] == Model.Mark.Blank);
        }
        if (model.isGameOver()) {
            String message = (model.isDraw() ? "It's a draw." : model.getPlayer() + " won.") + " Play again?";
            int result = JOptionPane.showConfirmDialog(this, message, "Play again?", JOptionPane.YES_NO_OPTION);
            controller.onGameOver(result == JOptionPane.YES_OPTION);
        }
    }

    private void buildGui() {
        JFrame frame = new JFrame("Tic Tac Toe");
        frame.add(this);
        setLayout(new GridLayout(3, 3));
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setPreferredSize(new Dimension(200, 200));
            buttons[i].setFont(BIG);
            add(buttons[i]);
            final int j = i;
            buttons[i].addActionListener(e -> controller.onClick(j));
        }
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
