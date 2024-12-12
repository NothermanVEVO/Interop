package gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

import javax.swing.JFrame;

public class Window extends JFrame{

    public Window(String name, int width, int height){
        super(name);

        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addKeyListener(new Input());
        setAlwaysOnTop(true);
        setLocationRelativeTo(null);
        setVisible(true);

    }

}