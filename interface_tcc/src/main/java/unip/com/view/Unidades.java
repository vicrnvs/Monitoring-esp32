package unip.com.view;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.swing.JTable;

import unip.com.control.exceptions.RequestException;
import unip.com.model.DefaultModels;
import unip.com.model.Esp32Dto;

public class Unidades extends BaseGUI{
	private static final long serialVersionUID = 1L;
	
	private JButton btnNewButton;
	private JTable table;
	private JButton btnEditar;
	private static DefaultModels model;
	private static List<Esp32Dto> esps;
	
	
	public Unidades() {
		super("Unidades");

	}

	@Override
	public void createAndShowGUI() {
		
		model = new DefaultModels(new String[] {"Identificador", "Estado", "Cidade", "Bairro", "Rua"}, new boolean[] {false,false,false,false,false}, new Class<?>[] {String.class, String.class, String.class, String.class, String.class});
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		btnNewButton = new JButton("Adicionar");
		menuBar.add(btnNewButton);
		
		btnEditar = new JButton("Editar");
		menuBar.add(btnEditar);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		table.setModel(model);
		scrollPane.setViewportView(table);
		table.getColumnModel().getColumn(0).setMaxWidth(80);
		table.getColumnModel().getColumn(1).setMaxWidth(50);

		setSize(732,319);
		setVisible(true);
		setLocationRelativeTo(null);
		refreshTable();
	}

	@Override
	public void createListners() {
		btnNewButton.addActionListener(e ->{
			new EditUnidade(true, null);
		});
		
		btnEditar.addActionListener(e ->{
			int selectRow = table.getSelectedRow();
			if(selectRow > -1) {
				String iden = (String)table.getValueAt(selectRow, 0);
				Esp32Dto esp = esps.stream().filter(find -> Objects.equals(find.getIdentificador(), iden)).collect(Collectors.toList()).get(0);
				new EditUnidade(false, esp);
			}else {
				JOptionPane.showMessageDialog(null, "Nenhuma unidade selecionada");
			}
		});
	}
	
	public static void refreshTable() {
		try {
			model.removeAllRows();
			List<Esp32Dto> esps =  MainInterface.monitoring.listAllEsp32();
			Unidades.esps = esps;
			esps.forEach(esp ->{
				model.addRow(new Object[] {esp.getIdentificador(), esp.getEstado(), esp.getCidade(), esp.getBairro(), esp.getNomeRua()});
			});
		} catch (RequestException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
