package utilities;

import javax.script.ScriptContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {

    private String propertyFilePath;
    private Properties prop;


    public PropertiesManager() {
        propertyFilePath = System.getProperty("user.dir")+"/Credentials";
    }

    private void loadData() {
        prop = new Properties();
        try {
            prop.load(new FileInputStream(propertyFilePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String propertyName) {
        loadData();
        return prop.getProperty(propertyName);
    }
}
