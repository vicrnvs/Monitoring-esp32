package unip.com.view;


import javax.swing.JFrame;
import net.miginfocom.swing.MigLayout;
import unip.com.control.MonitoringPort;
import unip.com.control.exceptions.RequestException;
import unip.com.model.Co2DataDto;
import unip.com.model.DefaultModels;

import javax.swing.JButton;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MainInterface extends BaseGUI{
	private static final long serialVersionUID = 1L;
	public static MonitoringPort monitoring = new MonitoringPort();
	
	private JTable table;
	private DefaultModels model;
	private JButton btnBuscar;
	private JButton btnArima;
	private JButton btnUnidades;
	
	
	public MainInterface() {
		super("Monitoring");
	}
	
	@Override
	public void createAndShowGUI() {
		setSize(713,381);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "cell 0 0,grow");
		model = new DefaultModels(new String[] {"id", "identificador", "Umidade", "Temperatura", "Umidade Ar", "Data"}, new boolean[] {false,false,false,false,false,false}, new Class<?>[] {Integer.class, Integer.class,Integer.class, Double.class, Integer.class, LocalDateTime.class});
		table = new JTable();
		table.setModel(model);
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);
		btnBuscar = new JButton("Buscar");
		btnArima = new JButton("Arima");
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuBar.add(btnBuscar);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuBar.add(btnArima);
		
		btnUnidades = new JButton("Unidades");
		menuBar.add(btnUnidades);
		setVisible(true);
		setLocationRelativeTo(null);
	}
	
	@Override
	public void createListners() {
		btnBuscar.addActionListener(e ->{
			FrameFiltroData fD = new FrameFiltroData();
			LocalDate[] dates = fD.startGUIFiltroEsto();
			LocalDateTime dI = LocalDateTime.of(dates[0], LocalTime.MIDNIGHT);
			LocalDateTime dF = LocalDateTime.of(dates[1], LocalTime.MIDNIGHT);
			try {
				List<Co2DataDto> co2List =  monitoring.listCo2ByDateAndFullAddress(dI, dF, (String)fD.comboBox.getSelectedItem(), fD.txtCidade.getText(), fD.txtBairro.getText());
				model.removeAllRows();
				co2List.forEach(line -> {
					model.addRow(new Object[] {line.getId(), line.getIdentificador(), line.getSensorData().getMoisture(), line.getSensorData().getTemperatura(), line.getSensorData().getUmidade(), line.getDateTime()});
				});
			} catch (RequestException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		});
		
		btnArima.addActionListener(e ->{
			int rows[] = table.getSelectedRows();
			List<Integer> dados = new LinkedList<>();
			
			for(int row : rows) {
				dados.add((Integer)table.getValueAt(row, 2));
			}
			
			new ArimaRequest(dados.toArray(new Integer[dados.size()]));
		});
		
		btnUnidades.addActionListener(e ->{
			new Unidades();
		});
	}

}
