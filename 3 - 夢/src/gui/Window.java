package src.gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;

public class Window extends JFrame {

    private static Window window;

    private Yume yume;

    public Window(){
        super("私の夢");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        yume = new Yume(250, 75);

        add(yume);
        adjustToScreen();

        setVisible(true);
    }

    public static Window getInstance(){
        return window == null ? window = new Window() : window;
    }

    public void adjustToScreen(){
        pack();
        setLocationRelativeTo(null);
    }

}
