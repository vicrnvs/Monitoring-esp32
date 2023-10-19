package unip.com.control;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;

import unip.com.view.MainInterface;

public class Main {
	
	
	public static void main(String args[]) {
		
	try {
		FlatLightLaf.setup();
		setFlatLaf();
		SwingUtilities.invokeLater(new Runnable() {
			
			public void run() {
				new MainInterface();
			}
		});
	} catch (Exception e) {
		e.printStackTrace();
		JOptionPane.showMessageDialog(null, "Falha ao Abrir Conexão");
	}
		
		
	}
	
	
	private static void setFlatLaf() {
		UIManager.put( "Table.showHorizontalLines", true );
		UIManager.put( "Table.showVerticalLines", true );
		UIManager.put( "Table.gridColor", "darken" );
		UIManager.put( "TableHeader.separatorColor", "darken($TableHeader.background,10%)" );
		UIManager.put( "TabbedPane.showTabSeparators", true );
		UIManager.put( "TabbedPane.tabSeparatorsFullHeight", true );
		UIManager.put( "TabbedPane.showTabSeparators", true );


	}
}
