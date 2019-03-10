package dataprovider;

import java.io.File;

import java.io.FileInputStream;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDataProvider {
	XSSFWorkbook wb;
	XSSFSheet sh1;

	public ExcelDataProvider(String excelPath, String sheetName) {
		try {

			File src = new File(excelPath);

			FileInputStream fis = new FileInputStream(src);

			wb = new XSSFWorkbook(fis);
			sh1 = wb.getSheet(sheetName);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public String readData(int sheetRowNo, int sheetColNo) {

		return sh1.getRow(sheetRowNo).getCell(sheetColNo).getStringCellValue();
	}

	public Object[][] getTableArray() throws Exception {

		Object[][] table = new String[sh1.getLastRowNum() + 1][2];

		for (int i = 0; i <= sh1.getLastRowNum(); i++) {

			for (int j = 0; j <= 1; j++) {
				table[i][j] = readData(i, j);
				StringBuilder pp = new StringBuilder();
				// pp.append(table[i][j]).append(" ").append(i).append(" ").append(j);
				System.out.print(pp);
			}
		}
		return table;
	}

}