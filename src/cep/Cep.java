package cep;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URL;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Atxy2k.CustomTextField.RestrictedTextField;

public class Cep extends JFrame {

	private JPanel contentPane;
	private JTextField textFieldCep;
	private JTextField textFieldEndereco;
	private JTextField textFieldBairro;
	private JTextField textFieldCidade;
	private JComboBox comboBoxUF;
	private JLabel LabelStatus;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Cep frame = new Cep();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Cep() {
		setTitle("Buscar CEP");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Cep.class.getResource("/img/home.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 313);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel LabelCep = new JLabel("CEP");
		LabelCep.setFont(new Font("Tahoma", Font.BOLD, 11));
		LabelCep.setBounds(23, 35, 30, 14);
		contentPane.add(LabelCep);

		textFieldCep = new JTextField();
		textFieldCep.setBounds(86, 32, 89, 20);
		contentPane.add(textFieldCep);
		textFieldCep.setColumns(10);

		JLabel LabelEndereco = new JLabel("Endere\u00E7o");
		LabelEndereco.setFont(new Font("Tahoma", Font.BOLD, 11));
		LabelEndereco.setBounds(23, 83, 61, 14);
		contentPane.add(LabelEndereco);

		textFieldEndereco = new JTextField();
		textFieldEndereco.setColumns(10);
		textFieldEndereco.setBounds(86, 80, 320, 20);
		contentPane.add(textFieldEndereco);

		JLabel LabelBairro = new JLabel("Bairro");
		LabelBairro.setFont(new Font("Tahoma", Font.BOLD, 11));
		LabelBairro.setBounds(23, 133, 42, 14);
		contentPane.add(LabelBairro);

		textFieldBairro = new JTextField();
		textFieldBairro.setColumns(10);
		textFieldBairro.setBounds(86, 130, 320, 20);
		contentPane.add(textFieldBairro);

		JLabel LabelCidade = new JLabel("Cidade");
		LabelCidade.setFont(new Font("Tahoma", Font.BOLD, 11));
		LabelCidade.setBounds(23, 183, 50, 14);
		contentPane.add(LabelCidade);

		textFieldCidade = new JTextField();
		textFieldCidade.setColumns(10);
		textFieldCidade.setBounds(86, 180, 182, 20);
		contentPane.add(textFieldCidade);

		JLabel LabelUF = new JLabel("UF");
		LabelUF.setFont(new Font("Tahoma", Font.BOLD, 11));
		LabelUF.setBounds(306, 183, 30, 14);
		contentPane.add(LabelUF);

		comboBoxUF = new JComboBox();
		comboBoxUF.setModel(new DefaultComboBoxModel(
				new String[] { "", "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA",
						"PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
		comboBoxUF.setBounds(346, 179, 60, 22);
		contentPane.add(comboBoxUF);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpar(); //linha 229
			}
		});
		btnLimpar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLimpar.setBounds(86, 224, 89, 23);
		contentPane.add(btnLimpar);

		JButton btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (textFieldCep.getText().equals("")) {

					JOptionPane.showMessageDialog(null, "Preenchimento CEP obrigatório!");
					textFieldBairro.requestFocus();

				} else {
					buscarCep(); //linha 180
				}
			}
		});
		btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnBuscar.setBounds(225, 31, 89, 23);
		contentPane.add(btnBuscar);

		JButton btnGitHub = new JButton("");
		btnGitHub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				link("https://github.com/RodrigoD324"); //linha 240

			}
		});
		btnGitHub.setToolTipText("Sobre");
		btnGitHub.setIcon(new ImageIcon(Cep.class.getResource("/img/github.png")));
		btnGitHub.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGitHub.setAlignmentX(Component.RIGHT_ALIGNMENT);
		btnGitHub.setBorder(null);
		btnGitHub.setBackground(SystemColor.control);
		btnGitHub.setBounds(346, 21, 48, 48);
		contentPane.add(btnGitHub);

		// parte de validação
		RestrictedTextField validar = new RestrictedTextField(textFieldCep);

		LabelStatus = new JLabel("");
		LabelStatus.setBounds(185, 31, 20, 20);
		contentPane.add(LabelStatus);
		validar.setOnlyNums(true);
		validar.setLimit(8);

	}

	private void buscarCep() {
		String logradouro = "";
		String tipoLogradouro = "";
		String resultado = null;
		String cep = textFieldCep.getText();
		try {

			URL url = new URL("http://cep.republicavirtual.com.br/web_cep.php?cep=" + cep + "&formato=xml");
			SAXReader xml = new SAXReader();
			Document documento = xml.read(url);
			Element root = documento.getRootElement();

			for (Iterator<Element> it = root.elementIterator(); it.hasNext();) {

				Element element = it.next();
				if (element.getQualifiedName().equals("tipo_logradouro")) {
					tipoLogradouro = element.getText();
				}
				if (element.getQualifiedName().equals("logradouro")) {
					logradouro = element.getText();
				}
				if (element.getQualifiedName().equals("bairro")) {
					textFieldBairro.setText(element.getText());
				}
				if (element.getQualifiedName().equals("cidade")) {
					textFieldCidade.setText(element.getText());
				}
				if (element.getQualifiedName().equals("uf")) {
					comboBoxUF.setSelectedItem(element.getText());
				}
				if (element.getQualifiedName().equals("resultado")) {
					resultado = element.getText();
					if (resultado.equals("1")) {
						LabelStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/check.png")));
					} else {
						JOptionPane.showMessageDialog(null, "CEP inexistente!");
					}
				}
			}
			// setar endereço
			textFieldEndereco.setText(tipoLogradouro + " " + logradouro);

		} catch (Exception e) {

			System.out.println(e);

		}
	}

	private void limpar() {
		textFieldCep.setText(null);
		textFieldBairro.setText(null);
		textFieldEndereco.setText(null);
		textFieldCidade.setText(null);
		comboBoxUF.setSelectedItem(null);
		LabelStatus.setIcon(null);
		textFieldCep.requestFocus();
	}

	// método criado para a realização do link para o gitHub
	private void link(String site) {

		Desktop desktop = Desktop.getDesktop();
		try {

			URI uri = new URI(site);
			desktop.browse(uri);

		} catch (Exception e) {

			System.out.println(e);

		}
	}

}