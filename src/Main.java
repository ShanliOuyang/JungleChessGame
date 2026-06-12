import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;
import view.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame(910,710);
            loginFrame.setVisible(true);
        });
    }
}
