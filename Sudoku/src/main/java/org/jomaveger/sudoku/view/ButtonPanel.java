package org.jomaveger.sudoku.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import org.jomaveger.sudoku.controller.ButtonController;
import org.jomaveger.sudoku.model.UpdateAction;

public class ButtonPanel extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;

	private JButton btnNew, btnViewSol;
	private ButtonGroup bgNumbers;
	private JToggleButton[] btnNumbers;

	public ButtonPanel() {
		super(new BorderLayout());

		JPanel pnlAlign = new JPanel();
		pnlAlign.setLayout(new BoxLayout(pnlAlign, BoxLayout.PAGE_AXIS));
		add(pnlAlign, BorderLayout.NORTH);

		JPanel pnlOptions = new JPanel(new FlowLayout(FlowLayout.LEADING));
		pnlOptions.setBorder(BorderFactory.createTitledBorder(" Options "));
		pnlAlign.add(pnlOptions);

		btnNew = new JButton("New");
		btnNew.setFocusable(false);
		pnlOptions.add(btnNew);

		btnViewSol = new JButton("View Solution");
		btnViewSol.setFocusable(false);
		pnlOptions.add(btnViewSol);

		JPanel pnlNumbers = new JPanel();
		pnlNumbers.setLayout(new BoxLayout(pnlNumbers, BoxLayout.PAGE_AXIS));
		pnlNumbers.setBorder(BorderFactory.createTitledBorder(" Numbers "));
		pnlAlign.add(pnlNumbers);

		JPanel pnlNumbersHelp = new JPanel(new FlowLayout(FlowLayout.LEADING));
		pnlNumbers.add(pnlNumbersHelp);

		JPanel pnlNumbersNumbers = new JPanel(new FlowLayout(FlowLayout.LEADING));
		pnlNumbers.add(pnlNumbersNumbers);

		bgNumbers = new ButtonGroup();
		btnNumbers = new JToggleButton[9];
		for (int i = 0; i < 9; i++) {
			btnNumbers[i] = new JToggleButton("" + (i + 1));
			btnNumbers[i].setPreferredSize(new Dimension(40, 40));
			btnNumbers[i].setFocusable(false);
			bgNumbers.add(btnNumbers[i]);
			pnlNumbersNumbers.add(btnNumbers[i]);
		}
	}

	public void update(Observable o, Object arg) {
		switch ((UpdateAction) arg) {
		case NEW_GAME:
		case VIEW_SOLUTION:
			bgNumbers.clearSelection();
			break;
		}
	}

	public void setController(ButtonController buttonController) {
		btnNew.addActionListener(buttonController);
		btnViewSol.addActionListener(buttonController);
		for (int i = 0; i < 9; i++)
			btnNumbers[i].addActionListener(buttonController);
	}
}