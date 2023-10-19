package unip.com.view;

import net.miginfocom.swing.MigLayout;
import unip.com.model.FilterCo2;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;


import java.awt.Font;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.SwingConstants;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

public class FrameFiltroData{
	private JPanel panel = new JPanel();
	 private boolean group = false;
	 private JRadioButton rdnAgrupar = new JRadioButton("Agrupar");
	 JTextField txtCidade;
	 JTextField txtBairro;
	 public JComboBox<String> comboBox = new JComboBox<String>();

	 
    public LocalDate[] startGUIFiltroEsto() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    	JPanel pane = getPanel();
        JOptionPane.showConfirmDialog(new JFrame(),
                        pane,
                        "Data: ",
                        JOptionPane.PLAIN_MESSAGE);
        String txtDataIni = ((JTextField)pane.getComponent(7)).getText();
        String txtDataFin = ((JTextField)pane.getComponent(9)).getText();
      try {
          LocalDate[] localD = new LocalDate[] {LocalDate.parse(txtDataIni,
        		  formatter), LocalDate.parse(txtDataFin, formatter)};
          
          return localD;
      }catch (Exception e) {
    	  e.printStackTrace();
    	  JOptionPane.showMessageDialog(null, "Data Invalida");
      }
	return null;
    }
    
    private JPanel getPanel() {
		JLabel lblDataIni = new JLabel("Data Inicial");
		JLabel lblDataFin = new JLabel("Data FInal");
		JFormattedTextField txtDataIni = null;
		JFormattedTextField txtDataFin = null;
		try {
			MaskFormatter data = new MaskFormatter("##/##/####");
			data.setPlaceholderCharacter('_');
			txtDataIni = new JFormattedTextField(data);
			txtDataFin = new JFormattedTextField(data);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		panel.setVisible(true);
		panel.setLayout(new MigLayout("", "[78.00][grow]", "[][][][][][][]"));
		
		JLabel lblEstado = new JLabel("Estado");
		panel.add(lblEstado, "cell 0 0,alignx trailing");
		
		
		DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(new String[] {"SP"});
		comboBox.setModel(model);
		panel.add(comboBox, "cell 1 0,growx");
		
		JLabel lblCidade = new JLabel("Cidade");
		lblCidade.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblCidade, "cell 0 1,alignx trailing");
		
		txtCidade = new JTextField();
		panel.add(txtCidade, "cell 1 1,growx");
		txtCidade.setColumns(10);
		
		JLabel lblBairro = new JLabel("Bairro");
		panel.add(lblBairro, "cell 0 2,alignx trailing");
		
		txtBairro = new JTextField();
		panel.add(txtBairro, "cell 1 2,growx");
		txtBairro.setColumns(10);
		panel.add(lblDataIni, "cell 0 3");
		panel.add(txtDataIni, "cell 1 3,growx");
		panel.add(lblDataFin, "cell 0 4");
		panel.add(txtDataFin, "cell 1 4,growx");
		lblDataIni.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblDataFin.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtDataFin.setColumns(10);
        return panel;
    }
    public boolean getGroup() {
    	return group;
    }
    public void setGroupBtn(boolean b) {
    	if(b) {
    		panel.add(rdnAgrupar, "cell 0 2 2 1,alignx center");
    	}
    }

}
