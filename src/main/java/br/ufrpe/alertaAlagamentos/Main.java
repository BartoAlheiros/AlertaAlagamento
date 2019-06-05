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
		File myFile = new File("D:\\Downloads\\LinhaDoTiro.xlsx");
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
				localidade.pluviometria = (int)row.getCell(1).getNumericCellValue();
				localidade.mareh = (int)row.getCell(2).getNumericCellValue();
				localidade.risco = (int)row.getCell(3).getNumericCellValue();
				localidades.add(localidade);
			}

		}

		for (int i = 0; i < localidades.size(); i++) {
			System.out.println(localidades.get(i).nome + ", " +
					localidades.get(i).pluviometria + ", " + localidades.get(i).mareh + ", " + localidades.get(i).risco );
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
		
		// double tempoInicial = (double)System.currentTimeMillis();
		
		int riscoAlto = 0;
		int riscoMedio = 0;
		int riscoBaixo = 0;
		
		double pRiscoAlto = 0;
		double pRiscoMedio = 0;
		double pRiscoBaixo = 0;
		
		int riscoAltoPluviometriaAlta = 0;
		int riscoMedioPluviometriaAlta = 0;
		int riscoBaixoPluviometriaAlta = 0;
		
		int riscoAltoPluviometriaMedia = 0;
		int riscoMedioPluviometriaMedia = 0;
		int riscoBaixoPluviometriaMedia = 0;

		int riscoAltoPluviometriaBaixa = 0;
		int riscoMedioPluviometriaBaixa = 0;
		int riscoBaixoPluviometriaBaixa = 0;
		
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
			if (localidades.get(j).risco == 3) {
				riscoAlto++;
			} else if (localidades.get(j).risco == 2) {
				riscoMedio++;
			} else if (localidades.get(j).risco == 1) {
				riscoBaixo++;
			}
		}	

		/* Probabilidade não condicional
		 * Calculando. */
		pRiscoAlto = (double)riscoAlto/totalOcorrencias;
		pRiscoMedio = (double)riscoMedio/totalOcorrencias;
		pRiscoBaixo = (double)riscoBaixo/totalOcorrencias;
		
		System.out.printf("%nAlto: " + riscoAlto + " Médio: " + riscoMedio + " Baixo: " + riscoBaixo + "%n");
		System.out.printf("pRiscoAlagamentoAlto %.2f %n", pRiscoAlto);
		System.out.printf("pRiscoAlagamentoMedio %.2f %n", pRiscoMedio);
		System.out.printf("pRiscoAlagamentoBaixo %.2f %n%n", pRiscoBaixo);
		
		/* Elimina os riscos zerados. */
		if (riscoAlto == 0) {
			riscoAlto++;
		}
		
		if (riscoMedio == 0) {
			riscoMedio++;
		} 
		
		if (riscoBaixo == 0) {
			riscoBaixo++;
		}
		
		/* Probabilidade condicional. Contabilizando Ocorrências. */
		for (int j = 0; j < localidades.size() - 1; j++) {
			
			/* (Risco Alto ^ Pluviometria Alta) || (Risco Alto ^ Pluviometria Média) ||
			 * (Risco Alto ^ Pluviometria Baixa)  */
			
			if(localidades.get(j).risco == 3) {
				if (localidades.get(j).pluviometria == 3) {
					riscoAltoPluviometriaAlta++;
				} else if (localidades.get(j).pluviometria == 2) {
					riscoAltoPluviometriaMedia++;
				} else if (localidades.get(j).pluviometria == 1) {
					riscoAltoPluviometriaBaixa++;
				}
				
			}
		
			
			/* (Risco Médio ^ Pluviometria Alta) || (Risco Médio ^ Pluviometria Média) ||
			 * (Risco Médio ^ Pluviometria Baixa)  */
			if (localidades.get(j).risco == 2) {
				if (localidades.get(j).pluviometria == 3) {
					riscoMedioPluviometriaAlta++;
				} else if (localidades.get(j).pluviometria == 2) {
					riscoMedioPluviometriaMedia++;
				} else if (localidades.get(j).pluviometria == 1) {
					riscoMedioPluviometriaBaixa++;
				}
			}
			
			
			/* (Risco Baixo ^ Pluviometria Alta) || (Risco Baixo ^ Pluviometria Média) ||
			 * (Risco Baixo ^ Pluviometria Baixa)  */
			if (localidades.get(j).risco == 1) {
				if (localidades.get(j).pluviometria == 3) {
					riscoBaixoPluviometriaAlta++;
				} else if (localidades.get(j).pluviometria == 2) {
					riscoBaixoPluviometriaMedia++;
				} else if (localidades.get(j).pluviometria == 1) {
					riscoBaixoPluviometriaBaixa++;
				}
				
			}
		
			
		}
		
		/* Probabilidade condicional. Calculando. */
		pRiscoAltoPluviometriaAlta = (double)riscoAltoPluviometriaAlta/riscoAlto;
		pRiscoAltoPluviometriaMedia = (double)riscoAltoPluviometriaMedia/riscoAlto;
		pRiscoAltoPluviometriaBaixa = (double)riscoAltoPluviometriaBaixa/riscoAlto;
		
		pRiscoMedioPluviometriaAlta = (double)riscoMedioPluviometriaAlta/riscoMedio;
		pRiscoMedioPluviometriaMedia = (double)riscoMedioPluviometriaMedia/riscoMedio;
		pRiscoMedioPluviometriaBaixa = (double)riscoMedioPluviometriaBaixa/riscoMedio;
		
		pRiscoBaixoPluviometriaAlta = (double)riscoBaixoPluviometriaAlta/riscoBaixo;
		pRiscoBaixoPluviometriaMedia = (double)riscoBaixoPluviometriaMedia/riscoBaixo;
		pRiscoBaixoPluviometriaBaixa = (double)riscoBaixoPluviometriaBaixa/riscoBaixo;
		
		System.out.printf("pRiscoAltoPluviometriaAlta %.5f %n", pRiscoAltoPluviometriaAlta);
		System.out.printf("pRiscoAltoPluviometriaMedia %.5f %n", pRiscoAltoPluviometriaMedia);
		System.out.printf("pRiscoAltoPluviometriaBaixa %.5f %n", pRiscoAltoPluviometriaBaixa);
		
		System.out.printf("%n%n");
		System.out.printf("pRiscoMedioPluviometriaAlta %.5f %n", pRiscoMedioPluviometriaAlta);
		System.out.printf("pRiscoMedioPluviometriaMedia %.5f %n", pRiscoMedioPluviometriaMedia);
		System.out.printf("pRiscoMedioPluviometriaBaixa %.5f %n", pRiscoMedioPluviometriaBaixa);
		
		System.out.printf("%n%n");
		System.out.printf("pRiscoBaixoPluviometriaAlta %.5f %n", pRiscoBaixoPluviometriaAlta);		
		System.out.printf("pRiscoBaixoPluviometriaMedia %.5f %n", pRiscoBaixoPluviometriaMedia);
		System.out.printf("pRiscoBaixoPluviometriaBaixa %.5f %n", pRiscoBaixoPluviometriaBaixa);
		
		// System.out.printf("o metodo executou em %.50f", ((double)System.currentTimeMillis() - tempoInicial));

//		if(Integer.toString(localidades.get((localidades.size() - 1)).riscoAlagamento).equals("?")) {
//			System.out.println("Achei a interrogação.");
//		}

		return 0;

	}   
}


