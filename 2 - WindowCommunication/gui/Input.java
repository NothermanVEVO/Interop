package gui;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class Input implements KeyListener{

    public Input(){}

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

}
