import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Gato extends JFrame implements ActionListener {
    private JButton[] buttons = new JButton[9];
    private String currentPlayer = "X";
    private JLabel statusLabel;
    private String player1, player2;
    private ImageIcon backgroundImage;

    public Gato() {
        player1 = JOptionPane.showInputDialog("Ingresa el nombre del Jugador 1 (X):");
        player2 = JOptionPane.showInputDialog("Ingresa el nombre del Jugador 2 (O):");

        setTitle("Juego de Gato");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        backgroundImage = new ImageIcon();

        JPanel board = new JPanel(new GridLayout(3, 3)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 60));
            buttons[i].setOpaque(false);
            buttons[i].setContentAreaFilled(false);
            buttons[i].setBorderPainted(false);
            buttons[i].addActionListener(this);
            board.add(buttons[i]);
        }
        JPanel statusPanel = new JPanel();
        statusLabel = new JLabel("Turno de " + currentPlayer + " (" + (currentPlayer.equals("X") ? player1 : player2) + ")");
        statusPanel.add(statusLabel);

        add(board, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton buttonClicked = (JButton) e.getSource();

        if (!buttonClicked.getText().equals("")) {
            return;
        }

        buttonClicked.setText(currentPlayer);

        if (checkWin()) {
            JOptionPane.showMessageDialog(this, currentPlayer + " (" + (currentPlayer.equals("X") ? player1 : player2) + ") ha ganado!");
            resetBoard();
        } else if (isBoardFull()) {
            JOptionPane.showMessageDialog(this, "Es un empate y SOPORTA PANZONA!");
            resetBoard();
        } else {
            switchPlayer();
        }
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
        statusLabel.setText("Turno de " + currentPlayer + " (" + (currentPlayer.equals("X") ? player1 : player2) + ")");
    }

    private boolean checkWin() {
        int[][] winPositions = {
            {0, 1, 2}, // fila 1
            {3, 4, 5}, // fila 2
            {6, 7, 8}, // fila 3
            {0, 3, 6}, // columna 1
            {1, 4, 7}, // columna 2
            {2, 5, 8}, // columna 3
            {0, 4, 8}, // diagonal
            {2, 4, 6}  // diagonal
        };

        for (int[] pos : winPositions) {
            if (buttons[pos[0]].getText().equals(currentPlayer) &&
                buttons[pos[1]].getText().equals(currentPlayer) &&
                buttons[pos[2]].getText().equals(currentPlayer)) {
                return true;
            }
        }
        return false;
    }

    private boolean isBoardFull() {
        for (JButton button : buttons) {
            if (button.getText().equals("")) {
                return false;
            }
        }
        return true;
    }

    private void resetBoard() {
        for (JButton button : buttons) {
            button.setText("");
        }
    }

    public static void main(String[] args) {
        new Gato();
    }
}

