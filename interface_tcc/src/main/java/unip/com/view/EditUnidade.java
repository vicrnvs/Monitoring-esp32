package unip.com.view;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import net.miginfocom.swing.MigLayout;
import unip.com.control.exceptions.RequestException;
import unip.com.model.DefaultModels;
import unip.com.model.Esp32ConfigParamsDto;
import unip.com.model.Esp32Dto;

public class EditUnidade extends BaseGUI{
	private JTextField txtCidade;
	private JTextField txtBairro;
	private JTextField txtNumero;
	private JTextField txtUF;
	private JTextField txtRua;
	private JTextField txtCep;
	private JButton btnSalvar;
	private JButton btnCancelar;
	private DefaultModels model;
	
	private boolean registrar;
	private JTextField txtIdentificador;
	private JTextField txtAltura;
	private JTextField txtLatitude;
	private JTextField txtLongi;
	private JTable table;

	public EditUnidade(boolean registrar, Esp32Dto esp32) {
		super("Unidade");
		
		if(!registrar) {
			this.btnSalvar.setText("Salvar");
			this.txtIdentificador.setEditable(false);
			pupulateFrame(esp32);
		}
		this.registrar = registrar;

	}

	@Override
	public void createAndShowGUI() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		btnSalvar = new JButton("Registrar");
		menuBar.add(btnSalvar);
		
		model = new DefaultModels(new String[] {"id","Param", "Valor"}, new boolean[] {false,true,true}, new Class<?>[] {Integer.class, String.class, String.class});
		btnCancelar = new JButton("Cancelar");
		menuBar.add(btnCancelar);
		getContentPane().setLayout(new MigLayout("", "[grow]", "[113.00][3][grow][][grow]"));
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panel, "cell 0 0,grow");
		panel.setLayout(new MigLayout("", "[][grow][grow]", "[][][][]"));
		
		JLabel lblNewLabel_3 = new JLabel("UF");
		panel.add(lblNewLabel_3, "cell 0 0,alignx trailing");
		
		txtUF = new JTextField();
		panel.add(txtUF, "flowx,cell 1 0");
		txtUF.setColumns(4);
		
		JLabel lblNewLabel_1 = new JLabel("Bairro");
		panel.add(lblNewLabel_1, "cell 0 1,alignx trailing");
		
		txtBairro = new JTextField();
		panel.add(txtBairro, "cell 1 1,growx");
		txtBairro.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Cidade");
		panel.add(lblNewLabel, "cell 1 0,alignx trailing");
		
		txtCidade = new JTextField();
		panel.add(txtCidade, "cell 1 0 2 1,growx");
		txtCidade.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Rua");
		panel.add(lblNewLabel_4, "cell 0 2,alignx trailing");
		
		txtRua = new JTextField();
		panel.add(txtRua, "cell 1 2,growx");
		txtRua.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Numero");
		panel.add(lblNewLabel_2, "cell 0 3,alignx trailing");
		
		txtNumero = new JTextField();
		panel.add(txtNumero, "flowx,cell 1 3,alignx left");
		txtNumero.setColumns(4);
		
		JLabel lblNewLabel_5 = new JLabel("CEP");
		panel.add(lblNewLabel_5, "cell 1 3");
		
		txtCep = new JTextField();
		panel.add(txtCep, "cell 1 3");
		txtCep.setColumns(10);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(panel_1, "cell 0 2,grow");
		panel_1.setLayout(new MigLayout("", "[][grow]", "[][]"));
		
		JLabel lblNewLabel_6 = new JLabel("Identificador");
		panel_1.add(lblNewLabel_6, "flowx,cell 0 0,alignx left");
		
		JLabel lblNewLabel_7 = new JLabel("Altura");
		panel_1.add(lblNewLabel_7, "flowx,cell 1 0,alignx left");
		
		txtAltura = new JTextField();
		panel_1.add(txtAltura, "cell 1 0,alignx trailing");
		txtAltura.setColumns(5);
		
		JLabel lblNewLabel_8 = new JLabel("Latitude");
		panel_1.add(lblNewLabel_8, "flowx,cell 0 1,alignx trailing");
		
		txtLatitude = new JTextField();
		panel_1.add(txtLatitude, "cell 0 1,alignx trailing");
		txtLatitude.setColumns(10);
		
		txtIdentificador = new JTextField();
		panel_1.add(txtIdentificador, "cell 0 0,growx");
		txtIdentificador.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("Longitude");
		panel_1.add(lblNewLabel_9, "flowx,cell 1 1,alignx left");
		
		txtLongi = new JTextField();
		panel_1.add(txtLongi, "cell 1 1,alignx trailing");
		txtLongi.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("Parametros");
		getContentPane().add(lblNewLabel_10, "cell 0 3");
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "cell 0 4,grow");
		
		table = new JTable();
		table.setModel(model);
		table.setAutoCreateRowSorter(true);
		scrollPane.setViewportView(table);
		table.getColumnModel().getColumn(0).setMaxWidth(35);
		setSize(384,408);
		setVisible(true);
		setLocationRelativeTo(null);
	}

	@Override
	public void createListners() {
		btnSalvar.addActionListener(e ->{
			Esp32Dto esp = new Esp32Dto();
			esp.setIdentificador(txtIdentificador.getText());
			esp.setNomeRua(txtRua.getText());
			esp.setNumero(txtNumero.getText());
			esp.setCidade(txtCidade.getText());
			esp.setBairro(txtBairro.getText());
			esp.setCep(txtCep.getText());
			esp.setEstado(txtUF.getText());
			esp.setPais("Brasil");
			esp.setLatitude(txtLatitude.getText());
			esp.setLongitude(txtLongi.getText());
			esp.setAltura(Integer.parseInt(txtAltura.getText()));
			esp.setProximaManutencao(LocalDate.now().plusMonths(2));
			
			List<Esp32ConfigParamsDto> configs = new ArrayList<Esp32ConfigParamsDto>();
			for(int i = 0;i<table.getRowCount();i++) {
				configs.add(new Esp32ConfigParamsDto((Integer)table.getValueAt(i, 0), (String)table.getValueAt(i, 1), (String)table.getValueAt(i, 2)));
			}
			
			esp.setConfigParams(configs);
			
			try {
				if(!registrar) {
					MainInterface.monitoring.updateEsp32(esp);
				}else {
					MainInterface.monitoring.registerNewEsp32(esp);
				}
				JOptionPane.showMessageDialog(null, "Sucesso");
				Unidades.refreshTable();
				dispose();
			} catch (RequestException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
			
		
			
		});
		
		btnCancelar.addActionListener(e ->{
			dispose();
		});
	}
	
	public void pupulateFrame(Esp32Dto esp32) {
		model.removeAllRows();
		txtAltura.setText(Integer.toString(esp32.getAltura()));
		txtBairro.setText(esp32.getBairro());
		txtCep.setText(esp32.getCep());
		txtCidade.setText(esp32.getCidade());
		txtIdentificador.setText(esp32.getIdentificador());
		txtLatitude.setText(esp32.getLatitude());
		txtLongi.setText(esp32.getLongitude());
		txtNumero.setText(esp32.getNumero());
		txtRua.setText(esp32.getNomeRua());
		txtUF.setText(esp32.getEstado());
		
		esp32.getConfigParams().forEach(config -> {
			model.addRow(new Object[] {config.getId(), config.getParam(), config.getValue()});
		});
		
	}

}
