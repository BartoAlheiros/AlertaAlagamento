package br.ufrpe.alertaAlagamentos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.ufrpe.alertaAlagamentos.beans.Localidade;

public class Main {

	public static void main(String[] args) throws IOException {
		ArrayList<Localidade> localidades = new ArrayList<>();
		File myFile = new File("C:\\Users\\b4rt_\\Downloads\\Risco de Alagamentos(4).xlsx");
		FileInputStream fis = new FileInputStream(myFile);

		// Finds the workbook instance for XLSX file
		XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);

		// Return first sheet from the XLSX workbook
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);

		// Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = mySheet.iterator();

		/* Itera sobre as linhas da planilha, 
		 * salvando cada localidade no Array de localidades
		 *  */
		
		Row row = null;
		
		while (rowIterator.hasNext()) {

			row = rowIterator.next();

			if((row.getRowNum() > 0) && (row.getRowNum() <= 48)) {

				Localidade localidade = new Localidade();

				localidade.nome = row.getCell(0).getStringCellValue();
				localidade.precip = (int)row.getCell(2).getNumericCellValue();
				localidade.nivelMareh = (int)row.getCell(3).getNumericCellValue();
				localidade.riscoAlagamento = (int)row.getCell(1).getNumericCellValue();
				// localidade.riscoEminente = (int)row.getCell(4).getNumericCellValue();
				
				localidades.add(localidade);
			}
			System.out.println("");
		}
		
		for (int i = 0; i < localidades.size(); i++) {
			System.out.println(localidades.get(i).nome + ", " +
					localidades.get(i).precip + ", " + localidades.get(i).nivelMareh + ", " + localidades.get(i).riscoAlagamento );
		}
		
		// System.out.println("última linha: " + row);
		
		myWorkBook.close();
		
		/* Classificador Ingênuo de Bayes. */
		calculaRisco(localidades);

	}

	/**
	 * Analisa os dados obtidos das localidades e classifica o risco de alagamento.
	 * 
	 * @param localidades
	 * 
	 * @return Risco Eminente de alagamento calculado. 3 - Muito Alto. 2 - Alto. 1 - Baixo.
	 * 
	 *  */
	private static int calculaRisco(ArrayList<Localidade> localidades) {
		int riscoAlagamentoAlto = 0;
		int riscoAlagamentoMedio = 0;
		int riscoAlagamentoBaixo = 0;
		
		/* Probabilidade não condicional */
		for (int j = 0; j < localidades.size(); j++) {
			if (localidades.get(j).riscoAlagamento == 3) {
				riscoAlagamentoAlto++;
			} else if (localidades.get(j).riscoAlagamento == 2) {
				riscoAlagamentoMedio++;
			} else if (localidades.get(j).riscoAlagamento == 1) {
				riscoAlagamentoBaixo++;
			}
		}	
		
		
		System.out.println("Alto: " + riscoAlagamentoAlto + " Médio: " + riscoAlagamentoMedio + " Baixo: " + riscoAlagamentoBaixo);
		
		if(Integer.toString(localidades.get((localidades.size() - 1)).riscoAlagamento).equals("?")) {
			System.out.println("Achei a interrogação.");
		}
		
		return 0;
		
	}   
}


