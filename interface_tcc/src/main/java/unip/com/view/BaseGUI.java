package unip.com.view;

import javax.swing.JFrame;

public abstract class BaseGUI extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public BaseGUI(String nomeFrame) {
		super(nomeFrame);
		createAndShowGUI();
		createListners();
	}

	public abstract void createAndShowGUI();
	
	public abstract void createListners();
}
