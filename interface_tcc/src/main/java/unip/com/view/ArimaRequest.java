package unip.com.view;
import net.miginfocom.swing.MigLayout;
import unip.com.control.exceptions.RequestException;
import unip.com.model.ArimaForecastRequest;
import unip.com.model.ArimaForecastResponse;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.BevelBorder;
import java.util.Arrays;

import javax.swing.border.SoftBevelBorder;
import javax.swing.JButton;


public class ArimaRequest extends BaseGUI{
	
	private static final long serialVersionUID = 1L;
	private JTextField txtNP;
	private JTextField txtNQ;
	private JTextField txtSD;
	private JTextField txtND;
	private JTextField txtSP;
	private JTextField txtSQ;
	private JTextField txtM;
	private JTextField txtQuanti;
	private JButton btnConfirmar;
	private JTextArea txtAreaDados;
	private String dadosS = "";
	

	public ArimaRequest(Integer[] dados) {
		super("Arima Params");
		
		Arrays.stream(dados).forEach(dado -> {
			dadosS += (String.format("%s,", dado));
		});
		txtAreaDados.append(dadosS.substring(0, dadosS.length()-1));
	}
	
	@Override
	public void createAndShowGUI() {
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new MigLayout("", "[234.00][grow]", "[114.00][grow]"));
		btnConfirmar  = new JButton("Confirmar");
		JPanel panel_1 = new JPanel();
		txtAreaDados  = new JTextArea();
		panel_1.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(panel_1, "cell 0 0,grow");
		panel_1.setLayout(new MigLayout("", "[][grow][][grow]", "[][][][]"));
		
		JLabel lblNewLabel = new JLabel("NP");
		panel_1.add(lblNewLabel, "cell 0 0,alignx trailing");
		
		txtNP = new JTextField();
		panel_1.add(txtNP, "cell 1 0,growx");
		txtNP.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("ND");
		panel_1.add(lblNewLabel_3, "cell 2 0,alignx trailing");
		
		txtND = new JTextField();
		panel_1.add(txtND, "cell 3 0,growx");
		txtND.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("NQ");
		panel_1.add(lblNewLabel_1, "cell 0 1,alignx trailing");
		
		txtNQ = new JTextField();
		panel_1.add(txtNQ, "cell 1 1,growx");
		txtNQ.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("SP");
		panel_1.add(lblNewLabel_4, "cell 2 1,alignx trailing");
		
		txtSP = new JTextField();
		panel_1.add(txtSP, "cell 3 1,growx");
		txtSP.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("SD");
		panel_1.add(lblNewLabel_2, "cell 0 2,alignx trailing");
		
		txtSD = new JTextField();
		panel_1.add(txtSD, "cell 1 2,growx");
		txtSD.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("SQ");
		panel_1.add(lblNewLabel_5, "cell 2 2,alignx trailing");
		
		txtSQ = new JTextField();
		panel_1.add(txtSQ, "cell 3 2,growx");
		txtSQ.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("M");
		panel_1.add(lblNewLabel_6, "cell 0 3,alignx trailing");
		
		txtM = new JTextField();
		panel_1.add(txtM, "cell 1 3,growx");
		txtM.setColumns(10);
		
		JLabel lblNewLabel_7 = new JLabel("Quantidade");
		panel_1.add(lblNewLabel_7, "cell 2 3,alignx trailing");
		
		txtQuanti = new JTextField();
		panel_1.add(txtQuanti, "cell 3 3,growx");
		txtQuanti.setColumns(10);
		
		
		panel.add(btnConfirmar, "cell 1 0,alignx center,aligny center");
		txtAreaDados.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, "cell 0 1 2 1,grow");
		
		
		scrollPane.setViewportView(txtAreaDados);
		setResizable(false);
		setSize(395,363);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	@Override
	public void createListners() {
		btnConfirmar.addActionListener(a ->{
			ArimaForecastRequest params = new ArimaForecastRequest();
			params.setNp(Integer.parseInt(txtNP.getText()));
			params.setNd(Integer.parseInt(txtND.getText()));
			params.setNq(Integer.parseInt(txtNQ.getText()));
			params.setSp(Integer.parseInt(txtSP.getText()));
			params.setSd(Integer.parseInt(txtSD.getText()));
			params.setSq(Integer.parseInt(txtSQ.getText()));
			params.setM(Integer.parseInt(txtM.getText()));
			params.setTamanhoPredicao(Integer.parseInt(txtQuanti.getText()));
			
			String sData = txtAreaDados.getText();
			
			String[] split = sData.split(",");
			double[] finalData = new double[split.length];
			
			for(int i = 0;i<split.length;i++) {
				finalData[i] =  (double) Integer.parseInt(split[i]);
			}
			
			params.setData(finalData);
			try {
				ArimaForecastResponse res =  MainInterface.monitoring.getArimaPrediction(params);
				new ArimaResponse(res);
			} catch (RequestException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			
		});
	}
	
	

}
