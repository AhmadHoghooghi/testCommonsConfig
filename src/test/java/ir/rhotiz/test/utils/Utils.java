package ir.rhotiz.test.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class Utils {
    public static void writePropertyToFile(String key, String value, String filePath) {
        try(OutputStream output = new FileOutputStream(filePath)){
            Properties properties = new Properties();
            properties.setProperty(key, value);
            properties.store(output, null);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }


    public static String readPropertyFromFile(String key,String filePath) {
        try(InputStream input = new FileInputStream(filePath)){
            Properties properties = new Properties();
            properties.load(input);
            return properties.getProperty(key);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}