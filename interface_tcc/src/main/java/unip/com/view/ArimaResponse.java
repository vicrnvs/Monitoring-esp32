package unip.com.view;
import net.miginfocom.swing.MigLayout;
import unip.com.model.ArimaForecastResponse;
import unip.com.model.DefaultModels;

import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ArimaResponse extends BaseGUI{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTable table;
	private DefaultModels model;
	private JLabel lblNewLabel;

	public ArimaResponse(ArimaForecastResponse response) {
		super("Predição");
		
		textField.setText(Double.toString(response.getRmse()));
		Arrays.stream(response.getForecastResult()).forEach(r -> {
			model.addRow(new Object[] {r});
		});
	}

	@Override
	public void createAndShowGUI() {
		getContentPane().setLayout(new MigLayout("", "[][212.00][grow]", "[][][][grow]"));
		model = new DefaultModels(new String[] {"Predição"}, new boolean[] {false}, new Class<?>[] {Double.class});
		
		lblNewLabel = new JLabel("RMSE");
		getContentPane().add(lblNewLabel, "cell 0 2,alignx trailing");
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "cell 0 3 3 1,grow");
		
		table = new JTable();
		table.setModel(model);
		scrollPane.setViewportView(table);
		
		textField = new JTextField();
		getContentPane().add(textField, "cell 1 2 2 1,growx");
		textField.setColumns(10);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(221,260);
		setVisible(true);
		setResizable(true);
		setLocationRelativeTo(null);
		
	}

	@Override
	public void createListners() {
		
	}

}
