package com.amazonaws.view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.amazonaws.controller.Controller;

import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class View extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton btnEnviar;
	private String smsText;
	private File choosedFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View frame = new View();
					frame.setVisible(true);
					frame.setTitle("Programa feito por Gadiel Xavier");
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(null, "Nenhum arquivo selecionado");
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public View() throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(3, 3, 0, 0));
		
		textField = new JTextField();
		contentPane.add(textField);
		textField.setColumns(10);
		
				JButton btnSelecionar = new JButton("Selecionar");
				btnSelecionar.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {

						JFileChooser file = new JFileChooser();
						file.setApproveButtonText("Abrir");
						int result = file.showSaveDialog(null);
						// if the user click on save in Jfilechooser

						if (result == JFileChooser.APPROVE_OPTION) {
							File selectedFile = file.getSelectedFile();
							String path = selectedFile.getAbsolutePath();
							setChoosedFile(selectedFile);
						}
						// if the user click on save in Jfilechooser

						else if (result == JFileChooser.CANCEL_OPTION) {
							System.out.println("No File Select");
							JOptionPane.showMessageDialog(null, "Nenhum arquivo selecionado");
						}
					}
				});
				contentPane.add(btnSelecionar);
		
		btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setSmsText(textField.getText());
				try {
					Controller controller = new Controller(getChoosedFile(), getSmsText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "Ocorreu algum erro");
					
				}
			}
		});
		contentPane.add(btnEnviar);
	}
	
	public String getSmsText() {
		return this.smsText;
	}
	
	public File getChoosedFile() {
		return this.choosedFile;
	}
	
	public void setSmsText(String msg) {
		this.smsText= msg;
	}
	
	public void setChoosedFile(File file) {
		this.choosedFile = file;
	}
}
