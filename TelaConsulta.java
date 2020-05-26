import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class TelaConsulta extends JFrame implements ActionListener, ItemListener, ListSelectionListener{

	final int LARGURA_TELA = 600;
	final int ALTURA_TELA = 300;
	final int LARGURA_SCROLL_PANE = LARGURA_TELA - 200;
	final int ALTURA_SCROLL_PANE = ALTURA_TELA - 110;
	
	private JTextField txtValor;
	private JLabel lblId, lblValor;
	private JButton btnSair, btnConsultarData;
	
	

	private Termometro termometro;

	private Connection conn;

	private Integer[] vetor ultimosDias;

	private Object[] [] temperaturas;
	private JComboBox<Integer>cbId;

	private String[] colunas = {"Id","Valor"};
	private JTable tabelaTemperaturas;
	private Container caixa;
	private JScrollPane rolagem;

	private JPanel pnlCentro;
	private DateFormat formatter;
	
	public TelaConsulta(Connection conn) {
		super("Consulta de Temperaturas");
		
		txtValor = new JTextField(20);
		formatter = new SimpleDateFormat("dd/MM/YYYY");
		
		this.conn = conn;
		
		temptela = ultimosDias(conn);
		//passando o Array
		cbId = new JComboBox(ultimosDias);
		
		lblId = new JLabel("Id");
		lblValor = new JLabel ("Valor");
		
		temperaturas = carregaDados(conn);
		
		instanciaJTableEScrollPane();
		
		btnSair = new JButton("Sair");
		btnConsultarData = new JButton("Consulta");
		
		caixa = getContentPane();
		caixa.setLayout(new BorderLayout());
		
		JPanel pnlNorte = new JPanel(new FlowLayout());
		JPanel pnlSul = new JPanel(new FlowLayout());
		pnlCentro = new JPanel(new FlowLayout());
		
		pnlNorte.add(lblId);
		pnlNorte.add(cbId);
		pnlNorte.add(lblValor);
		pnlNorte.add(txtValor);
		pnlSul.add(btnConsultarData);
		pnlSul.add(btnSair);
		
		pnlCentro.add(rolagem);
		
		btnSair.addActionListener(this);
		btnConsultarData.addActionListener(this);
		
		cbId.addItemListener(this);
		caixa.add(pnlNorte, BorderLayout.NORTH);
		caixa.add(pnlSul, BorderLayout.SOUTH);
		caixa.add(pnlCentro, BorderLayout.CENTER);
		
		setSize(LARGURA_TELA, ALTURA_TELA);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == btnSair) {
			System.exit(0);
		}else if(e.getSource() == btnConsultarData) {
			String sDataIni = JOptionPane.showInputDialog("Digite a data inicial");
			String sDataFim = JOptionPane.showInputDialog("Digite a data final");
			
			try {
				Date dataIni = (Date) formatter.parse(sDataIni);
				Date dataFim = (Date)formatter.parse(sDataFim);
				
				temperaturas = carregaDados(conn, dataIni, dataFim);
				refazTela();
			}catch(ParseException p) {
				p.printStackTrace();
				JOptionPane.showMessageDialog(this, "Consulta data invalida");
			}
		}
	}
	
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange()== ItemEvent.SELECTED)
			temperaturas = carregaDados(conn);
			refazTela();
	}
	
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting()) {
			String resultado = 
					tabelaTemperaturas.getColumnName(0) + ":" + 
							tabelaTemperaturas.getValueAt(tabelaTemperaturas.getSelectedRow(),0)+
							tabelaTemperaturas.getValueAt(tabelaTemp, column)
							
							JOptionPane.showMessageDialog(this,);
		}
	}
	
	public Integer[] carregaTemperaturas(Connection conn) {
		
		Termometro termometro = new Termometro();
		ArrayList<Temperatura>lista = temperaturas.carregaTemperaturas;
		
		Integer[] saida = new Integer[lista.size];
		
		Cliente cliente;
		for(int i = 0; i < lista.size(0);i++) {
			cliente = lista.get(i);
		
			saida[i] = cliente. getIdCliente();
		}	
		return saida;
	}
	
	public String[][] carregaDados(Connection conn){
		
		temperaturas = new Temperatura(cbId.getItemAt(cbId.getSelectedIndex()));
		
		temperatura.carregar(conn);
		txtValor.setText(temperaturas.getValor());
		
		
	
	public void instanciaJTableEScrollPane() {
		tabelaTemperaturas = new JTable(temperaturas, colunas);
		
		tabelaTemperaturas.getSelectionModel().addListSelectionListener(this);
		rolagem = new JScrollPane(tabelaTemperaturas);
		
		rolagem.setPreferredSize(new Dimension(LARGURA_SCROLL_PANE, ALTURA_SCROLL_PANE));
	}
	
	public void refazTela() {
		caixa.remove(pnlCentro);
		pnlCentro.remove(rolagem);
		
		instanciaJTableEScrollPane();
		
		pnlCentro.add(rolagem);
		caixa.add(pnlCentro, BorderLayout.CENTER);
		
		validate();
		
		repaint();		
	}
}
