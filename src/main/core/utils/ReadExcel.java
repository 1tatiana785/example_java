package core.utils;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ReadExcel {

    public  List<List<String>> readExcel(String path) {
        List<List<String>> rows = new ArrayList<>();
        try {
//            URL res = getClass().getClassLoader().getResource(path);
//            File file = Paths.get(Objects.requireNonNull(res).toURI()).toFile();
//            String absolutePath = file.getAbsolutePath();
            FileInputStream fis = new FileInputStream(path);
            // we create an XSSF Workbook object for our XLSX Excel File
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            // we get first sheet
            XSSFSheet sheet = workbook.getSheetAt(0);

            // we iterate on rows
            for (Row row : sheet) {
                // iterate on cells for the current row
                Iterator<Cell> cellIterator = row.cellIterator();

                List<String> objectExcel = new ArrayList<>();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    if (cell.getCellType() == CellType.NUMERIC)
                        objectExcel.add(String.valueOf((long) cell.getNumericCellValue()));
                    else
                        objectExcel.add(cell.toString());
                }
                rows.add(objectExcel);
            }

            workbook.close();
            fis.close();
    } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }



    public static List<String> readFile(String FILENAME) {
        BufferedReader br = null;
        FileReader fr = null;
        List<String> list=new  ArrayList<String>();
        try {
            String file= System.getProperty("user.dir")+File.separator+"src"+File.separator
                    +"resources"+File.separator+ "downloadedPDF" +File.separator+FILENAME;
            String sCurrentLine;
            br = new BufferedReader(new FileReader(file));

            while ((sCurrentLine = br.readLine()) != null) {
                list.add(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            try {

                if (br != null)
                    br.close();

                if (fr != null)
                    fr.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
            return list;
        }

    }
}