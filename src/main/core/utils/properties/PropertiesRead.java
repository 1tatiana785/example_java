package core.utils.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesRead {


	public  String getPropValues(String name, String propertiesFileName)  {
		String result="";
		String myProject=System.getProperty("user.dir");
		try {
			InputStream  input = new FileInputStream(myProject+"/src/resources/"+propertiesFileName);
			Properties prop = new Properties();
			prop.load(input);
			result=prop.getProperty(name);
		}
		catch (IOException ex)
		{
			System.out.println("Exception " +ex.getMessage());
		}
		return result;
	}
}
