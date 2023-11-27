package com.r3.datareader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileReader {
	public static  String getProperty(String propertyKey) {
		final String propertyFilePath = System.getProperty("user.dir") +"\\src\\test\\propertyfiles\\ConfigProperty.properties";
		Properties properties;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(propertyFilePath));
			properties = new Properties();
			try {
				properties.load(reader);
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("ConfigProperty.properties is not found at " + propertyFilePath);
		}
		return properties.getProperty(propertyKey);
	}
}
