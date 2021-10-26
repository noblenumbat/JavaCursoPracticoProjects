package org.jomaveger.bookexamples.chapter7.swing.examples;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Example1 extends JFrame {
	
	public Example1() {
		initUI();	
	}
	
	private void initUI() {
        setTitle("Example 1");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {

            var ex = new Example1();
            ex.setVisible(true);
        });
	}
}
