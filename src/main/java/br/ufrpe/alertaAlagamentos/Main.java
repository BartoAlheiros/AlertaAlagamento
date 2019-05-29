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
		File myFile = new File("C:\\Users\\b4rt_\\Downloads\\LinhaDoTiro.xlsx");
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
		int ultimaLinha = 0;
		

		while (rowIterator.hasNext()) {

			row = rowIterator.next();
			
			ultimaLinha = row.getSheet().getLastRowNum();

			if((row.getRowNum() > 0) && (row.getRowNum() <= ultimaLinha)) {
		
				Localidade localidade = new Localidade();

				localidade.nome = row.getCell(0).getStringCellValue();
				localidade.precip = (int)row.getCell(1).getNumericCellValue();
				localidade.nivelMareh = (int)row.getCell(2).getNumericCellValue();
				localidade.riscoAlagamento = (int)row.getCell(3).getNumericCellValue();
				localidades.add(localidade);
			}

		}

		for (int i = 0; i < localidades.size(); i++) {
			System.out.println(localidades.get(i).nome + ", " +
					localidades.get(i).precip + ", " + localidades.get(i).nivelMareh + ", " + localidades.get(i).riscoAlagamento );
		}

		myWorkBook.close();

		/* Classificador Ingênuo de Bayes. */
		calculaRisco(localidades, ultimaLinha-1);

	}

	/**
	 * Analisa os dados obtidos das localidades e classifica o risco de alagamento.
	 * 
	 * @param localidades
	 * 
	 * @return Risco Eminente de alagamento calculado. 3 - Muito Alto. 2 - Alto. 1 - Baixo.
	 * 
	 *  */
	private static int calculaRisco(ArrayList<Localidade> localidades, int totalOcorrencias) {
		int riscoAlagamentoAlto = 0;
		int riscoAlagamentoMedio = 0;
		int riscoAlagamentoBaixo = 0;
		
		double pRiscoAlagamentoAlto = 0;
		double pRiscoAlagamentoMedio = 0;
		double pRiscoAlagamentoBaixo = 0;
		
		double pRiscoAltoPluviometriaAlta = 0;
		double pRiscoMedioPluviometriaAlta = 0;
		double pRiscoBaixoPluviometriaAlta = 0;
		
		double pRiscoAltoPluviometriaMedia = 0;
		double pRiscoMedioPluviometriaMedia = 0;
		double pRiscoBaixoPluviometriaMedia = 0;

		double pRiscoAltoPluviometriaBaixa = 0;
		double pRiscoMedioPluviometriaBaixa = 0;
		double pRiscoBaixoPluviometriaBaixa = 0;
		
		/* Probabilidade não condicional
		 * Contando ocorrências. */
		for (int j = 0; j < localidades.size() - 1; j++) {
			if (localidades.get(j).riscoAlagamento == 3) {
				riscoAlagamentoAlto++;
			} else if (localidades.get(j).riscoAlagamento == 2) {
				riscoAlagamentoMedio++;
			} else if (localidades.get(j).riscoAlagamento == 1) {
				riscoAlagamentoBaixo++;
			}
		}	

		/* Probabilidade não condicional
		 * Calculando. */
		pRiscoAlagamentoAlto = (double)riscoAlagamentoAlto/totalOcorrencias;
		pRiscoAlagamentoMedio = (double)riscoAlagamentoMedio/totalOcorrencias;
		pRiscoAlagamentoBaixo = (double)riscoAlagamentoBaixo/totalOcorrencias;
		
		System.out.println("Alto: " + riscoAlagamentoAlto + " Médio: " + riscoAlagamentoMedio + " Baixo: " + riscoAlagamentoBaixo);
		System.out.printf("pRiscoAlagamentoAlto %.2f %n", pRiscoAlagamentoAlto);
		System.out.printf("pRiscoAlagamentoMedio %.2f %n", pRiscoAlagamentoMedio);
		System.out.printf("pRiscoAlagamentoBaixo %.2f ", pRiscoAlagamentoBaixo);
		
		

//		if(Integer.toString(localidades.get((localidades.size() - 1)).riscoAlagamento).equals("?")) {
//			System.out.println("Achei a interrogação.");
//		}

		return 0;

	}   
}


