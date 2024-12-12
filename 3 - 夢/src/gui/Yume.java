package src.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JPanel;
import javax.swing.JTextField;

import src.util.Command;

public class Yume extends JPanel{

    private JTextField enterText = new JTextField();

    public Yume(int width, int height){
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);

        enterText.setBounds(0, 0, width, height);
        enterText.addActionListener(l -> actionPerfomed(l));
        add(enterText);

        setLayout(null);

    }

    private void actionPerfomed(ActionEvent l){
        if(l.getSource() == enterText && !enterText.getText().isBlank()){
            Command.read(enterText.getText());
            enterText.setText("");
        }
    }

}
