package com.amazonaws.samples;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class LerExcel {
	
	public static void enviarSms(File file, String sms) throws IOException {
		
		List<Paciente> listaPacientes = new ArrayList<Paciente>();
		
		try {
			
			FileInputStream arquivo = new FileInputStream(file);
			
			XSSFWorkbook workbook = new XSSFWorkbook(arquivo);
			
			
			   
            XSSFSheet sheetAlunos = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheetAlunos.iterator();
            
            
            
            
         // import org.apache.poi.ss.usermodel.*;

            for (Row row : sheetAlunos) {
            	String nome = null, email = null, telefone = null, data = null;
                for (Cell cell : row) {
                	
                    // Alternatively, get the value and format it yourself
                	 switch (cell.getColumnIndex()) {
                     case 0:
                           nome = cell.getStringCellValue();
                           break;
                     case 1:
                           email = cell.getStringCellValue();
                           break;
                     case 2:
                           telefone = cell.getStringCellValue();
                           break;
                     case 3:
                         	//nothing here
                         break;
                     case 4:
                           //nothing here
                           break;
                     case 5:
                  	   		data = cell.getStringCellValue();
                  	   break;
                     }
                }
                Paciente paciente = new Paciente(nome, email, telefone, data);
                listaPacientes.add(paciente);
            }
            
            //elimina primeira linha do arquivo
            listaPacientes.remove(0);
         
         arquivo.close();
         workbook.close();
			
		}catch(FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Arquivo Excel não encontrado!");
            JOptionPane.showMessageDialog(null, "Arquivo Excel não encontrado!");
		} catch(IllegalArgumentException e1) {
			e1.printStackTrace();
            System.out.println("Ocorreu um erro! Arquivo Excel não encontrado!");
            JOptionPane.showMessageDialog(null, "Ocorreu um erro! Arquivo Excel não encontrado!");
		}
		
		
		//Imprime os valores do Paciente
		if (listaPacientes.size() == 0) {
            System.out.println("Nenhum paciente encontrado!");
		} else {
            for (Paciente paciente : listaPacientes) {
                   System.out.println("Paciente: " + paciente.getNome() + " Email: "
                                + paciente.getEmail() + " Telefone:" + paciente.getTelefone()
                                + " Data Nascimento:" + paciente.getData_nascimento());
            }
            System.out.println("Número total de pacientes: " + listaPacientes.size());
            JOptionPane.showMessageDialog(null, "Número total de pacientes: " + listaPacientes.size());
		}
		
		SendSmsForTopic sendSms = new SendSmsForTopic(listaPacientes,  sms);
	}
}
