package org.jomaveger.bookexamples.chapter2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
 
public class OuterClass3 extends JFrame {
     
    public static void main(String[] args) {
        OuterClass3 outer = new OuterClass3();
        JButton button = new JButton("Don't click me!");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.out.println("Ouch!");
            }
        });
        outer.setDefaultCloseOperation(EXIT_ON_CLOSE);
        outer.add(button);
        outer.pack();
        outer.setVisible(true);
    }
}