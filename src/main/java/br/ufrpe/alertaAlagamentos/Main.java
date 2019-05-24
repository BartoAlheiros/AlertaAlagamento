package br.ufrpe.alertaAlagamentos;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import br.ufrpe.alertaAlagamentos.beans.Localidade;

public class Main {

	public static void main(String[] args) throws IOException {
		File myFile = new File("C:\\Users\\b4rt_\\Downloads\\Risco de Alagamentos(4).xlsx");
		FileInputStream fis = new FileInputStream(myFile);

		// Finds the workbook instance for XLSX file
		XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);

		// Return first sheet from the XLSX workbook
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);

		// Get iterator to all the rows in current sheet
		Iterator<Row> rowIterator = mySheet.iterator();

		// Traversing over each row of XLSX file
		while (rowIterator.hasNext()) {

			Row row = rowIterator.next();

			if(row.getRowNum() != 0) {

				Localidade local = new Localidade();

				local.setNome(row.getCell(0).getStringCellValue());
				local.setRiscoAlagamento((int)row.getCell(1).getNumericCellValue());
				
				
			}
			System.out.println("");
		}

	}   
}


