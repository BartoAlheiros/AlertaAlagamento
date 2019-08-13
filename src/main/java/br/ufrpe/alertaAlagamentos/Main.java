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
		File myFile = new File("localidades\\LinhaDoTiro.xlsx");
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
	@SuppressWarnings("unlikely-arg-type")
	private static int calculaRisco(ArrayList<Localidade> localidades, int totalOcorrencias) {

		// double tempoInicial = (double)System.currentTimeMillis();

		int totalRiscoAlto = 0;
		int totalRiscoMedio = 0;
		int totalRiscoBaixo = 0;

		double pRiscoNCondicAlto = 0;
		double pRiscoNCondicMedio = 0;
		double pRiscoNCondicBaixo = 0;

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

		int riscoAltoMarehAlta = 0;
		int riscoMedioMarehAlta = 0;
		int riscoBaixoMarehAlta = 0;

		int riscoAltoMarehMedia = 0;
		int riscoMedioMarehMedia = 0;
		int riscoBaixoMarehMedia = 0;

		int riscoAltoMarehBaixa = 0;
		int riscoMedioMarehBaixa = 0;
		int riscoBaixoMarehBaixa = 0;

		double pRiscoAltoMarehAlta = 0;
		double pRiscoMedioMarehAlta = 0;
		double pRiscoBaixoMarehAlta = 0;

		double pRiscoAltoMarehMedia = 0;
		double pRiscoMedioMarehMedia = 0;
		double pRiscoBaixoMarehMedia = 0;

		double pRiscoAltoMarehBaixa = 0;
		double pRiscoMedioMarehBaixa = 0;
		double pRiscoBaixoMarehBaixa = 0;

		Float pluviometriaEntrada = 0.0f;
		Float mareEntrada = 0.0f;
		int ultimaLinha = localidades.size();

		pluviometriaEntrada = localidades.get(ultimaLinha-1).pluviometria;
		mareEntrada = localidades.get(ultimaLinha-1).mareh;

//		double pluvioEntradaERiscAlto = 0;
//		double pluvioEntradaERiscMedio = 0;
//		double pluvioEntradaERiscBaixo = 0;
//
//		double pluvioAltaRiscoAlto = 0;
//		double pluvioAltaRiscoMedio = 0;
//		double pluvioAltaRiscoBaixo = 0;
//
//		double pluvioMediaRiscoAlto = 0;
//		double pluvioMediaRiscoMedio = 0;
//		double pluvioMediaRiscoBaixo = 0;
//
//		double pluvioBaixaRiscoAlto = 0;
//		double pluvioBaixaRiscoMedio = 0;
//		double pluvioBaixaRiscoBaixo = 0;
//
//		double mareAltaRiscoAlto = 0;
//		double mareAltaRiscoMedio = 0;
//		double mareAltaRiscoBaixo = 0;
//
//		double mareMediaRiscoAlto = 0;
//		double mareMediaRiscoMedio = 0;
//		double mareMediaRiscoBaixo = 0;
//
//		double mareBaixaRiscoAlto = 0;
//		double mareBaixaRiscoMedio = 0;
//		double mareBaixaRiscoBaixo = 0;
//
//		for (int j = 0; j < localidades.size() - 1; j++) {
//
//			if (localidades.get(j).risco == 3) {
//				totalRiscoAlto++;
//			} else if (localidades.get(j).risco == 2) {
//				totalRiscoMedio++;
//			} else if (localidades.get(j).risco == 1) {
//				totalRiscoBaixo++;
//			}
//
//			if (pluviometriaEntrada.equals(localidades.get(j).pluviometria)) {
//				if (pluviometriaEntrada.equals(3)) {
//					if (localidades.get(j).risco == 3) {
//						pluvioAltaRiscoAlto++;
//					} else if (localidades.get(j).risco == 2) {
//						pluvioAltaRiscoMedio++;
//					} else if (localidades.get(j).risco == 1) {
//						pluvioAltaRiscoBaixo++;
//					}
//				} else if(pluviometriaEntrada.equals(2)) {
//					if (localidades.get(j).risco == 3) {
//						pluvioMediaRiscoAlto++;
//					} else if (localidades.get(j).risco == 2) {
//						pluvioMediaRiscoMedio++;
//					} else if (localidades.get(j).risco == 1) {
//						pluvioMediaRiscoBaixo++;
//					}
//				} else {
//					if (localidades.get(j).risco == 3) {
//						pluvioBaixaRiscoAlto++;
//					} else if (localidades.get(j).risco == 2) {
//						pluvioBaixaRiscoMedio++;
//					} else if (localidades.get(j).risco == 1) {
//						pluvioBaixaRiscoBaixo++;
//					}
//				} 
//
//			}
//
//			if (mareEntrada.equals(localidades.get(j).mareh)) {
//				if (pluviometriaEntrada.equals(3)) {
//					if (localidades.get(j).risco == 3) {
//						mareAltaRiscoAlto++;
//					} else if (localidades.get(j).risco == 2) {
//						mareAltaRiscoMedio++;
//					} else if (localidades.get(j).risco == 1) {
//						mareAltaRiscoBaixo++;
//					}
//				} else if(pluviometriaEntrada.equals(2)) {
//					if (localidades.get(j).risco == 3) {
//						mareMediaRiscoAlto++;
//					} else if (localidades.get(j).risco == 2) {
//						mareMediaRiscoMedio++;
//					} else if (localidades.get(j).risco == 1) {
//						mareMediaRiscoBaixo++;
//					}
//				} else {
//					if (localidades.get(j).risco == 3) {
//						mareBaixaRiscoAlto++;
//					} else if (localidades.get(j).risco == 2) {
//						mareBaixaRiscoMedio++;
//					} else if (localidades.get(j).risco == 1) {
//						mareBaixaRiscoBaixo++;
//					}
//				} 
//			}
//		}

		// OK
		// System.out.println("Total ocorrencias " + totalOcorrencias);

		/* Probabilidade não condicional
		 * Calculando. */
		pRiscoNCondicAlto = (double)totalRiscoAlto/totalOcorrencias;
		pRiscoNCondicMedio = (double)totalRiscoMedio/totalOcorrencias;
		pRiscoNCondicBaixo = (double)totalRiscoBaixo/totalOcorrencias;

		// OK
		/* Probabilidade não condicional
		 * Mostrando. */
		System.out.println();
		System.out.printf("pRiscoAlto %.2f %n", pRiscoNCondicAlto);
		System.out.printf("pRiscoMedio %.2f %n", pRiscoNCondicMedio);
		System.out.printf("pRiscoBaixo %.2f %n%n", pRiscoNCondicBaixo);

		if (pRiscoNCondicAlto == 0) {
			pRiscoNCondicAlto++;
		}

		if (pRiscoNCondicMedio == 0) {
			pRiscoNCondicMedio++;
		}

		if (pRiscoNCondicBaixo == 0) {
			pRiscoNCondicBaixo++;
		}

//		double pPluvioAltaRiscoAlto = (double)pluvioAltaRiscoAlto/totalOcorrencias;
//		double pPluvioAltaRiscoMedio = (double)pluvioAltaRiscoMedio/totalOcorrencias;
//		double pPluvioAltaRiscoBaixo = (double)pluvioAltaRiscoBaixo/totalOcorrencias;
//
//		double pPluvioMediaRiscoAlto = (double)pluvioMediaRiscoAlto/totalOcorrencias;
//		double pPluvioMediaRiscoMedio = (double)pluvioMediaRiscoMedio/totalOcorrencias;
//		double pPluvioMediaRiscoBaixo = (double)pluvioMediaRiscoBaixo/totalOcorrencias;
//
//		double pPluvioBaixaRiscoAlto = (double)pluvioBaixaRiscoAlto/totalOcorrencias;
//		double pPluvioBaixaRiscoMedio = (double)pluvioBaixaRiscoMedio/totalOcorrencias;
//		double pPluvioBaixaRiscoBaixo = (double)pluvioBaixaRiscoBaixo/totalOcorrencias;
//
//		double pMareAltaRiscoAlto = (double)mareAltaRiscoAlto/totalOcorrencias;
//		double pMareAltaRiscoMedio = (double)mareAltaRiscoMedio/totalOcorrencias;
//		double pMareAltaRiscoBaixo = (double)mareAltaRiscoBaixo/totalOcorrencias;
//
//		double pMareMediaRiscoAlto = (double)mareMediaRiscoAlto/totalOcorrencias;
//		double pMareMediaRiscoMedio = (double)mareMediaRiscoMedio/totalOcorrencias;
//		double pMareMediaRiscoBaixo = (double)mareMediaRiscoBaixo/totalOcorrencias;
//
//		double pMareBaixaRiscoAlto = (double)mareBaixaRiscoAlto/totalOcorrencias;
//		double pMareBaixaRiscoMedio = (double)mareBaixaRiscoMedio/totalOcorrencias;
//		double pMareBaixaRiscoBaixo = (double)mareBaixaRiscoBaixo/totalOcorrencias;
//
//		if (pPluvioAltaRiscoAlto == 0) {
//			pPluvioAltaRiscoAlto++;
//		}
//
//		if (pPluvioAltaRiscoMedio == 0) {
//			pPluvioAltaRiscoMedio++;
//		}
//
//		if (pPluvioAltaRiscoBaixo == 0) {
//			pPluvioAltaRiscoBaixo++;
//		}
//
//		if (pPluvioMediaRiscoAlto == 0) {
//			pPluvioMediaRiscoAlto++;
//		}
//
//		if (pPluvioMediaRiscoMedio == 0) {
//			pPluvioMediaRiscoMedio++;
//		}
//
//		if (pPluvioMediaRiscoBaixo == 0) {
//			pPluvioMediaRiscoBaixo++;
//		}
//
//		if (pPluvioBaixaRiscoAlto == 0) {
//			pPluvioBaixaRiscoAlto++;
//		}
//
//		if (pPluvioBaixaRiscoMedio == 0) {
//			pPluvioBaixaRiscoMedio++;
//		}
//
//		if (pPluvioBaixaRiscoBaixo == 0) {
//			pPluvioBaixaRiscoBaixo++;
//		}
//
//		if (pMareAltaRiscoAlto == 0) {
//			pMareAltaRiscoAlto++;
//		}
//
//		if (pMareAltaRiscoMedio == 0) {
//			pMareAltaRiscoMedio++;
//		}
//
//		if (pMareAltaRiscoBaixo == 0) {
//			pMareAltaRiscoBaixo++;
//		}
//		
//		if (pMareMediaRiscoAlto == 0) {
//			pMareMediaRiscoAlto++;
//		}
//
//		if (pMareMediaRiscoMedio == 0) {
//			pMareMediaRiscoMedio++;
//		}
//
//		if (pMareMediaRiscoBaixo == 0) {
//			pMareMediaRiscoBaixo++;
//			
//		}
//		
//		if (pMareBaixaRiscoAlto == 0) {
//			pMareBaixaRiscoAlto++;
//		}
//
//		if (pMareBaixaRiscoMedio == 0) {
//			pMareBaixaRiscoMedio++;
//		}
//
//		if (pMareBaixaRiscoBaixo == 0) {
//			pMareBaixaRiscoBaixo++;
//		}

//		double pFimRiscAlto = pRiscoNCondicAlto * pPluvioAltaRiscoAlto * pPluvioMediaRiscoAlto * pPluvioBaixaRiscoAlto 
//				* pMareAltaRiscoAlto * pMareMediaRiscoAlto * pMareBaixaRiscoAlto;
//		double pFimRiscMedio = pRiscoNCondicMedio *  pPluvioAltaRiscoMedio * pPluvioMediaRiscoMedio * pPluvioBaixaRiscoMedio 
//				* pMareAltaRiscoAlto * pMareMediaRiscoAlto * pMareBaixaRiscoAlto; 
//		double pFimRiscBaixo = pRiscoNCondicBaixo * pPluvioAltaRiscoBaixo * pPluvioMediaRiscoBaixo * pPluvioBaixaRiscoBaixo 
//				* pMareAltaRiscoBaixo * pMareMediaRiscoBaixo * pMareBaixaRiscoBaixo;
		// System.out.printf("%nAlto: " + riscoAlto + " Médio: " + riscoMedio + " Baixo: " + riscoBaixo + "%n");

		//		System.out.println("Risco Alto " + riscoAlto);
		//		System.out.println("Risco Médio " + riscoMedio);
		//		System.out.println("Risco Baixo " + riscoBaixo);



		// elimina os riscos zerados
		if (totalRiscoAlto == 0) {
			totalRiscoAlto++;
		}

		if (totalRiscoMedio == 0) {
			totalRiscoMedio++;
		} 

		if (totalRiscoBaixo == 0) {
			totalRiscoBaixo++;
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

		/*****OK*******/

		//		System.out.printf("RiscoAltoPluviometriaAlta %d %n", riscoAltoPluviometriaAlta);
		//		System.out.printf("RiscoAltoPluviometriaMedia %d %n", riscoAltoPluviometriaMedia);
		//		System.out.printf("RiscoAltoPluviometriaBaixa %d %n", riscoAltoPluviometriaBaixa);
		//
		//		System.out.printf("%n%n");
		//		System.out.printf("RiscoMedioPluviometriaAlta %d %n", riscoMedioPluviometriaAlta);
		//		System.out.printf("RiscoMedioPluviometriaMedia %d %n", riscoMedioPluviometriaMedia);
		//		System.out.printf("RiscoMedioPluviometriaBaixa %d %n", riscoMedioPluviometriaBaixa);
		//
		//		System.out.printf("%n%n");
		//		System.out.printf("RiscoBaixoPluviometriaAlta %d %n", riscoBaixoPluviometriaAlta);		
		//		System.out.printf("RiscoBaixoPluviometriaMedia %d %n", riscoBaixoPluviometriaMedia);
		//		System.out.printf("RiscoBaixoPluviometriaBaixa %d %n", riscoBaixoPluviometriaBaixa);


		//dividindo pelo Total de Registros ->> (localidades.size()-1)
		pRiscoAltoPluviometriaAlta = (double)riscoAltoPluviometriaAlta/totalOcorrencias;
		pRiscoAltoPluviometriaMedia = (double)riscoAltoPluviometriaMedia/totalOcorrencias;
		pRiscoAltoPluviometriaBaixa = (double)riscoAltoPluviometriaBaixa/totalOcorrencias;

		pRiscoMedioPluviometriaAlta = (double)riscoMedioPluviometriaAlta/totalOcorrencias;
		pRiscoMedioPluviometriaMedia = (double)riscoMedioPluviometriaMedia/totalOcorrencias;
		pRiscoMedioPluviometriaBaixa = (double)riscoMedioPluviometriaBaixa/totalOcorrencias;

		pRiscoBaixoPluviometriaAlta = (double)riscoBaixoPluviometriaAlta/totalOcorrencias;
		pRiscoBaixoPluviometriaMedia = (double)riscoBaixoPluviometriaMedia/totalOcorrencias;
		pRiscoBaixoPluviometriaBaixa = (double)riscoBaixoPluviometriaBaixa/totalOcorrencias;


		/* Probabilidade condicional. MAREH. Contabilizando Ocorrências. */
		for (int j = 0; j < localidades.size(); j++) {

			/* (Risco Alto ^ Pluviometria Alta) || (Risco Alto ^ Pluviometria Média) ||
			 * (Risco Alto ^ Pluviometria Baixa)  */

			if(localidades.get(j).risco == 3) {
				if (localidades.get(j).mareh == 3) {
					riscoAltoMarehAlta++;
				} else if (localidades.get(j).mareh == 2) {
					riscoAltoMarehMedia++;
				} else if (localidades.get(j).mareh == 1) {
					riscoAltoMarehBaixa++;
				}

			}


			/* (Risco Médio ^ Pluviometria Alta) || (Risco Médio ^ Pluviometria Média) ||
			 * (Risco Médio ^ Pluviometria Baixa)  */
			if(localidades.get(j).risco == 2) {
				if (localidades.get(j).mareh == 3) {
					riscoMedioMarehAlta++;
				} else if (localidades.get(j).mareh == 2) {
					riscoMedioMarehMedia++;
				} else if (localidades.get(j).mareh == 1) {
					riscoMedioMarehBaixa++;
				}

			}

			/* (Risco Baixo ^ Pluviometria Alta) || (Risco Baixo ^ Pluviometria Média) ||
			 * (Risco Baixo ^ Pluviometria Baixa)  */
			if(localidades.get(j).risco == 1) {
				if (localidades.get(j).mareh == 3) {
					riscoBaixoMarehAlta++;
				} else if (localidades.get(j).mareh == 2) {
					riscoBaixoMarehMedia++;
				} else if (localidades.get(j).mareh == 1) {
					riscoBaixoMarehBaixa++;
				}

			}


			/******* OK **********/

			//			System.out.printf("RiscoAltoMarehAlta %d %n", riscoAltoMarehAlta);
			//			System.out.printf("RiscoAltoMarehMedia %d %n", riscoAltoMarehMedia);
			//			System.out.printf("RiscoAltoMarehBaixa %d %n", riscoAltoMarehBaixa);
			//
			//			System.out.printf("%n%n");
			//			System.out.printf("RiscoMedioMarehAlta %d %n", riscoMedioMarehAlta);
			//			System.out.printf("RiscoMedioMarehMedia %d %n", riscoMedioMarehMedia);
			//			System.out.printf("RiscoMedioMarehBaixa %d %n", riscoMedioMarehBaixa);
			//
			//			System.out.printf("%n%n");
			//			System.out.printf("RiscoBaixoMarehAlta %d %n", riscoBaixoMarehAlta);		
			//			System.out.printf("RiscoBaixoMarehMedia %d %n", riscoBaixoMarehMedia);
			//			System.out.printf("RiscoBaixoMarehBaixa %d %n", riscoBaixoMarehBaixa);


		}


		System.out.println("Pluviometria entrada " + pluviometriaEntrada);
		System.out.println("Mareh entrada " + mareEntrada);


		/* Probabilidade condicional -> Mareh ^ Risco. -- Calculando. */		
		pRiscoAltoMarehAlta = (double)riscoAltoMarehAlta/(localidades.size()-1);
		pRiscoAltoMarehMedia = (double)riscoAltoMarehMedia/(localidades.size()-1);
		pRiscoAltoMarehBaixa = (double)riscoAltoMarehBaixa/(localidades.size()-1);

		pRiscoMedioMarehAlta = (double)riscoMedioMarehAlta/(localidades.size()-1);
		pRiscoMedioMarehMedia = (double)riscoMedioMarehMedia/(localidades.size()-1);
		pRiscoMedioMarehBaixa = (double)riscoMedioMarehBaixa/(localidades.size()-1);

		System.out.println("RiscoBaixoMarehMedia " + riscoBaixoMarehMedia + " , riscoBaixo " + totalRiscoBaixo);
		System.out.printf(" **** NÚMERO de linhas de entrada %d %n", (localidades.size()-1));
		pRiscoBaixoMarehAlta = (double)riscoBaixoMarehAlta/(localidades.size()-1);
		pRiscoBaixoMarehMedia = (double)riscoBaixoMarehMedia/(localidades.size()-1);
		pRiscoBaixoMarehBaixa = (double)riscoBaixoMarehBaixa/(localidades.size()-1);

		/* Printando pRisco ^ Pluviometria */
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

		/* Printando pRisco ^ Mareh */
		System.out.printf("%n%n");
		System.out.printf("pRiscoAltoMarehAlta %.5f %n", pRiscoAltoMarehAlta);
		System.out.printf("pRiscoAltoMarehMedia %.5f %n", pRiscoAltoMarehMedia);
		System.out.printf("pRiscoAltoMarehBaixa %.5f %n", pRiscoAltoMarehBaixa);

		System.out.printf("%n%n");
		System.out.printf("pRiscoMedioMarehAlta %.5f %n", pRiscoMedioMarehAlta);
		System.out.printf("pRiscoMedioMarehMedia %.5f %n", pRiscoMedioMarehMedia);
		System.out.printf("pRiscoMedioMarehBaixa %.5f %n", pRiscoMedioMarehBaixa);

		System.out.printf("%n%n");
		System.out.printf("pRiscoBaixoMarehAlta %.5f %n", pRiscoBaixoMarehAlta);		
		System.out.printf("pRiscoBaixoMarehMedia %.5f %n", pRiscoBaixoMarehMedia);
		System.out.printf("pRiscoBaixoMarehBaixa %.5f %n", pRiscoBaixoMarehBaixa);


		if (pRiscoAltoPluviometriaAlta == 0) {
			pRiscoAltoPluviometriaAlta++;
		}

		if (pRiscoAltoPluviometriaMedia == 0) {
			pRiscoAltoPluviometriaMedia++;
		}

		if (pRiscoAltoPluviometriaBaixa == 0) {
			pRiscoAltoPluviometriaBaixa++;
		}

		if (pRiscoMedioPluviometriaAlta == 0) {
			pRiscoMedioPluviometriaAlta++;
		}

		if (pRiscoMedioPluviometriaMedia == 0) {
			pRiscoMedioPluviometriaMedia++;
		}

		if (pRiscoMedioPluviometriaBaixa == 0) {
			pRiscoMedioPluviometriaBaixa++;
		}

		if (pRiscoBaixoPluviometriaAlta == 0) {
			pRiscoBaixoPluviometriaAlta++;
		}

		if (pRiscoBaixoPluviometriaMedia == 0) {
			pRiscoBaixoPluviometriaMedia++;
		}

		if (pRiscoBaixoPluviometriaBaixa == 0) {
			pRiscoBaixoPluviometriaBaixa++;
		}

		if (pRiscoAltoMarehAlta == 0) {
			pRiscoAltoMarehAlta++;
		}

		if (pRiscoAltoMarehMedia == 0) {
			pRiscoAltoMarehMedia++;
		}

		if (pRiscoAltoMarehBaixa == 0) {
			pRiscoAltoMarehBaixa++;
		}

		if (pRiscoMedioMarehAlta == 0) {
			pRiscoMedioMarehAlta++;
		}

		if (pRiscoMedioMarehMedia == 0) {
			pRiscoMedioMarehMedia++;
		}

		if (pRiscoMedioMarehBaixa == 0) {
			pRiscoMedioMarehBaixa++;
		}

		if (pRiscoBaixoMarehAlta == 0) {
			pRiscoBaixoMarehAlta++;
		}

		if (pRiscoBaixoMarehMedia == 0) {
			pRiscoBaixoMarehMedia++;
		}

		if (pRiscoBaixoMarehBaixa == 0) {
			pRiscoBaixoMarehBaixa++;
		}


		//		/* Elimina os riscos zerados. */
		//		if (pRiscoAlto == 0) {
		//			pRiscoAlto++;
		//		}
		//		
		//		if (pRiscoMedio == 0) {
		//			pRiscoMedio++;
		//		} 
		//		
		//		if (pRiscoBaixo == 0) {
		//			pRiscoBaixo++;
		//		}


		/* Risco */

		double pRiscoAlto = 1, pRiscoMedio = 1, pRiscoBaixo = 1;

		/* Cálculo das porcentagens de cada Risco. */

		/* (Pluviometria Alta ^ Maré Alta) */
		//		if (pluviometriaEntrada.equals(3) && marehEntrada.equals(3)) {
		//			riscCalcAlto = pRiscoAlto * pRiscoAltoPluviometriaAlta * pRiscoAltoMarehAlta;
		//			riscCalcMedio = pRiscoMedio * pRiscoMedioPluviometriaAlta * pRiscoMedioMarehAlta;
		//			riscCalcBaixo = pRiscoBaixo * pRiscoBaixoPluviometriaAlta * pRiscoBaixoMarehAlta;
		//		}
		//
		//		/* (Pluviometria Alta ^ Maré Média) */
		//		if (pluviometriaEntrada.equals(3) && marehEntrada.equals(2)) {
		//			riscCalcAlto = pRiscoAlto * pRiscoAltoPluviometriaAlta * pRiscoAltoMarehMedia;
		//			riscCalcMedio = pRiscoMedio * pRiscoMedioPluviometriaAlta * pRiscoMedioMarehMedia;
		//			riscCalcBaixo = pRiscoBaixo * pRiscoBaixoPluviometriaAlta * pRiscoBaixoMarehMedia;
		//		}
		//
		//		/* (Pluviometria Alta ^ Maré Baixa) */
		//		if (pluviometriaEntrada.equals(3) && marehEntrada.equals(1)) {
		//			riscCalcAlto = pRiscoAlto * pRiscoAltoPluviometriaAlta * pRiscoAltoMarehBaixa;
		//			riscCalcMedio = pRiscoMedio * pRiscoMedioPluviometriaAlta * pRiscoMedioMarehBaixa;
		//			riscCalcBaixo = pRiscoBaixo * pRiscoBaixoPluviometriaAlta * pRiscoBaixoMarehBaixa;
		//		}
		//
		//		/* (Pluviometria Média ^ Maré Alta) */
		//		if (pluviometriaEntrada.equals(2) && marehEntrada.equals(3)) {
		//			riscCalcAlto = pRiscoAlto * pRiscoAltoPluviometriaMedia * pRiscoAltoMarehAlta;
		//			riscCalcMedio = pRiscoMedio * pRiscoMedioPluviometriaMedia * pRiscoMedioMarehAlta;
		//			riscCalcBaixo = pRiscoBaixo * pRiscoBaixoPluviometriaMedia * pRiscoBaixoMarehAlta;
		//		}
		//
		//		/* (Pluviometria Média ^ Maré Média) */
		//		if (pluviometriaEntrada.equals(2) && marehEntrada.equals(2)) {
		//			riscCalcAlto = pRiscoAlto * pRiscoAltoPluviometriaMedia * pRiscoAltoMarehMedia;
		//			riscCalcMedio = pRiscoMedio * pRiscoMedioPluviometriaMedia * pRiscoMedioMarehMedia;
		//			riscCalcBaixo = pRiscoBaixo * pRiscoBaixoPluviometriaMedia * pRiscoBaixoMarehMedia;
		//		}
		//
		//		/* (Risco Alto ^ Pluviometria Média ^ Maré Baixa) */
		//		if (pluviometriaEntrada.equals(2) && marehEntrada.equals(1)) {
		//			riscCalcAlto = pRiscoAlto * pRiscoAltoPluviometriaMedia * pRiscoAltoMarehBaixa;
		//			riscCalcMedio = pRiscoMedio * pRiscoMedioPluviometriaMedia * pRiscoMedioMarehBaixa;
		//			riscCalcBaixo = pRiscoBaixo * pRiscoBaixoPluviometriaMedia * pRiscoAltoMarehBaixa;
		//		}
		//
		//		/* (Risco Alto ^ Pluviometria Baixa ^ Maré Alta) */
		//		if (pluviometriaEntrada.equals(1) && marehEntrada.equals(3)) {
		//			riscCalcAlto = pRiscoAlto * pRiscoAltoPluviometriaBaixa * pRiscoAltoMarehAlta;
		//			riscCalcMedio = pRiscoMedio * pRiscoMedioPluviometriaBaixa * pRiscoMedioMarehAlta;
		//			riscCalcBaixo = pRiscoBaixo * pRiscoBaixoPluviometriaBaixa * pRiscoBaixoMarehAlta;
		//		}
		//
		//		/* (Risco Alto ^ Pluviometria Baixa ^ Maré Média) */
		//		if (pluviometriaEntrada.equals(1) && marehEntrada.equals(2)) {
		//			riscCalcAlto = pRiscoAlto * pRiscoAltoPluviometriaBaixa * pRiscoAltoMarehMedia;
		//			riscCalcMedio = pRiscoMedio * pRiscoMedioPluviometriaBaixa * pRiscoMedioMarehMedia;
		//			riscCalcBaixo = pRiscoBaixo * pRiscoBaixoPluviometriaBaixa * pRiscoBaixoMarehMedia;
		//		}
		//
		//		/* (Risco Alto ^ Pluviometria Baixa ^ Maré Baixa) */
		//		if (pluviometriaEntrada.equals(1) && marehEntrada.equals(1)) {
		//			riscCalcAlto = pRiscoAlto * pRiscoAltoPluviometriaBaixa * pRiscoAltoMarehBaixa;
		//			riscCalcMedio = pRiscoMedio * pRiscoMedioPluviometriaBaixa * pRiscoMedioMarehBaixa;
		//			riscCalcBaixo = pRiscoBaixo * pRiscoBaixoPluviometriaBaixa * pRiscoBaixoMarehBaixa;
		//		}

		
		System.out.println("Pluviometria entrada 2 " + pluviometriaEntrada);
		
		//Pluviometria Baixa
		if (pluviometriaEntrada.equals(1.0f)) {
			boolean resultado = verifica(localidades, 1, 1);
			//OK
			// System.out.println("Resultado1 "+ resultado);
			if (resultado) {
				pRiscoAlto = pRiscoAlto * pRiscoAltoPluviometriaBaixa;
				pRiscoMedio = pRiscoMedio * pRiscoMedioPluviometriaBaixa;
				pRiscoBaixo = pRiscoBaixo * pRiscoBaixoPluviometriaBaixa; 
			}
		}

		//Pluviometria Média
		if (pluviometriaEntrada.equals(2.0f)) {
			boolean resultado = verifica(localidades, 1, 2);
			//OK
			// System.out.println("Resultado1 "+ resultado);
			if (resultado) {
				pRiscoAlto = pRiscoAlto * pRiscoAltoPluviometriaMedia;
				pRiscoMedio = pRiscoMedio * pRiscoMedioPluviometriaMedia;
				pRiscoBaixo = pRiscoBaixo * pRiscoBaixoPluviometriaMedia; 
			}
		}

		//Pluviometria Alta
		if (pluviometriaEntrada.equals(3.0f)) {
			boolean resultado = verifica(localidades, 1, 3);
			//OK
			// System.out.println("Resultado1 "+ resultado);
			if (resultado) {
				pRiscoAlto = pRiscoAlto * pRiscoAltoPluviometriaAlta;
				pRiscoMedio = pRiscoMedio * pRiscoMedioPluviometriaAlta;
				pRiscoBaixo = pRiscoBaixo * pRiscoBaixoPluviometriaAlta; 
			}
		}



		System.out.println("Risco Alto Intermed " + pRiscoAlto);

		//Maré Baixa
		if (mareEntrada.equals(1.0f)) {
			boolean resultado = verifica(localidades, 2, 1);
			//System.out.println("resultado mareh" + resultado);
			if (resultado) {
				pRiscoAlto = pRiscoAlto * pRiscoAltoMarehBaixa;
				pRiscoMedio = pRiscoMedio * pRiscoMedioMarehBaixa;
				pRiscoBaixo = pRiscoBaixo * pRiscoBaixoMarehBaixa; 
			}
		}

		// Maré Média
		if (mareEntrada.equals(2.0f)) {
			boolean resultado = verifica(localidades, 2, 2);
			//System.out.println("resultado mareh" + resultado);
			if (resultado) {
				pRiscoAlto = pRiscoAlto * pRiscoAltoMarehMedia;
				pRiscoMedio = pRiscoMedio * pRiscoMedioMarehMedia;
				pRiscoBaixo = pRiscoBaixo * pRiscoBaixoMarehMedia; 
			}
		}

		//Maré Alta
		if (mareEntrada.equals(3.0f)) {
			boolean resultado = verifica(localidades, 2, 3);
			//System.out.println("resultado mareh" + resultado);
			if (resultado) {
				pRiscoAlto = pRiscoAlto * pRiscoAltoMarehAlta;
				pRiscoMedio = pRiscoMedio * pRiscoMedioMarehAlta;
				pRiscoBaixo = pRiscoBaixo * pRiscoBaixoMarehAlta; 
			}
		}
		
		if (pRiscoAlto==1) {
			pRiscoAlto--;
		}
		
		if(pRiscoMedio==1) {
			pRiscoMedio--;
		}
		
		if(pRiscoBaixo==1) {
			pRiscoBaixo--;
		}

		System.out.printf("%n%n");
		System.out.println("Risco Alto Calculado " + pRiscoAlto);
		System.out.println("Risco Médio Calculado " + pRiscoMedio);
		System.out.println("Risco Baixo Calculado " + pRiscoBaixo);

		double maior = 0;

		if (pRiscoAlto > maior) {
			maior = pRiscoAlto;
		}

		if (pRiscoMedio >= maior){
			maior = pRiscoMedio;
		}

		if (pRiscoBaixo >= maior) {
			maior = pRiscoBaixo;
		}

		System.out.printf("%n%n");
		System.out.println("Resultado " + maior);

		if (maior>0 && maior == pRiscoAlto && pRiscoAlto!=pRiscoMedio && pRiscoAlto!=pRiscoBaixo) {
			System.out.println("O Risco de alagamento é Alto.");
		}
		
		if (maior>0 && maior == pRiscoMedio && pRiscoMedio!=pRiscoAlto && pRiscoMedio!=pRiscoBaixo) {
			System.out.println("O Risco de alagamento é Médio.");
		} 
		
		if (maior>0 && maior == pRiscoBaixo && pRiscoBaixo!=pRiscoMedio && pRiscoBaixo!=pRiscoAlto) {
			System.out.println("O Risco de alagamento é Baixo.");
		}

		return 0;

	}

	/** 
	 * Verifica no Array de Localidades se existe ao menos uma ocorrencia do valor
	 * passado
	 * 
	 *  @param coluna
	 *  		valor da coluna
	 *  			1 - pluviometria
	 *  			2 - maré
	 *  
	 *  @param valor
	 *  		valor para ser comparado com o atributo em questão
	 *  			1 - Baixa
	 *  			2 - Media
	 *  			3 - Alta
	 *  
	 *  @param localidades
	 *  		ArrayList com as localidades 	*/
	public static boolean verifica(ArrayList<Localidade> localidades, int coluna, int valor) {
		boolean resultado = false;

		switch (coluna) {
		case 1:
			for (int i = 0; i < localidades.size() - 1; i++) {
				if(localidades.get(i).pluviometria == valor) {
					resultado = true;
					break;
				}
			}
			break;

		case 2:
			for (int i = 0; i < localidades.size() - 1; i++) {
				if(localidades.get(i).mareh == valor) {
					resultado = true;
					break;
				}
			}
			break;	

		default:
			break;
		}


		return resultado;
	}
}


