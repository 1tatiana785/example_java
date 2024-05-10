package core.utils.XML;

import static core.coreUI.utility.AllUtilsMethods.getRandomEMail;
import static core.coreUI.utility.AllUtilsMethods.getRandomNumberInRange;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XMLRead {
	
	public static String readXML(String section, String tagName) {
		try {
			File fXmlFile = new File(System.getProperty("user.dir") + "/constantsData.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName(section);
			for (int temp = 0; temp < nList.getLength(); temp++) {
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					return eElement.getElementsByTagName(tagName).item(0).getTextContent();
				}
			}
		}
		catch (Exception ex) {
			System.out.println("Exception " +ex.getMessage());
		}
		return "";
	}



	public static String generateName() {
		ArrayList<String> email = new ArrayList<>();
		email.add("Tesla");
		email.add("Razer");
		email.add("Rentgen");
		email.add("Rayleigh");
		email.add("Lenard");
		email.add("Thomson");
		email.add("Dalen");
		email.add("Compton");
		email.add("Heisenberg");
		return email.get(getRandomNumberInRange(0, email.size() - 1));
	}
	
	private static String generateLastName() {
		ArrayList<String> email = new ArrayList<>();
		email.add("Shepard ");
		email.add("Oconnell");
		email.add("Lenard");
		email.add("Thomson");
		email.add("Michelson");
		email.add("Lippmann");
		email.add("Marconi");
		email.add("Wien");
		email.add("Dalen");
		email.add("Compton");
		email.add("Heisenberg");
		email.add("Carrillo");
		return email.get(getRandomNumberInRange(0, email.size() - 1));
	}
	
	public String getPathXLSX(String name) {
		try {
			String fileName=System.getProperty("user.dir")+File.separator+"src"+File.separator
					+"resources"+File.separator+ "downloadedPDF" +File.separator+"List of errors emails_.txt";
			File file = new File(fileName);
			file.delete();
				URL res = getClass().getClassLoader().getResource(name);
			File fXmlFile = Paths.get(Objects.requireNonNull(res).toURI()).toFile();
			return fXmlFile.getAbsoluteFile().toString();
		}
		catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
	public String updateXLSX(String name) {
		String newPAth="";
		try {
			URL res = getClass().getClassLoader().getResource(name);
			File fXmlFile = Paths.get(Objects.requireNonNull(res).toURI()).toFile();
			
			XSSFWorkbook workbook = new XSSFWorkbook(fXmlFile);
			// we get first sheet
			XSSFSheet sheet = workbook.getSheetAt(0);
			// we iterate on rows
			Iterator<Row> rowIt = sheet.iterator();
			
			while (rowIt.hasNext()) {
				Row row = rowIt.next();
				if (!row.cellIterator().next().toString().contains("Email")) {
					// iterate on cells for the current row
					Iterator<Cell> cellIterator = row.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						switch (cell.getColumnIndex()) {
							case 0: {
								cell.setCellValue(getRandomEMail().toLowerCase());
								break;
							}
							case 1: {
								cell.setCellValue(generateName().toLowerCase());
								break;
							}
							case 2: {
								cell.setCellValue(generateLastName().toLowerCase());
								break;
							}
							case 3: {
								cell.setCellValue(generateName() + " " + generateLastName());
								break;
							}
							default:
							{
								cell.setCellValue(cell.toString());
							}
						}
					}
				}
			}
			newPAth=System.getProperty("user.dir")+File.separator+"test-output"+File.separator+getRandomNumberInRange(0,500)+name;
			try (FileOutputStream fileOut = new FileOutputStream(newPAth)) {
				workbook.write(fileOut);
				fileOut.close();
				workbook.close();
			}
		}
		catch (POIXMLException | IOException | URISyntaxException | InvalidFormatException e) {
			e.printStackTrace();
		}
		return newPAth;
	}
}
