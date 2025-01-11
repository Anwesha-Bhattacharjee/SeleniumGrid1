package com.avi.SeleniumGrid1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class GridManager {

	@SuppressWarnings("deprecation")
	@BeforeTest
	void gridSetup() throws IOException, InterruptedException {
		
		Runtime rt = Runtime.getRuntime();
		rt.exec("cmd /c start startGrid-standalone.bat");
		Thread.sleep(3000);
		
		long startTime = System.currentTimeMillis();
        long endTime = startTime + TimeUnit.SECONDS.toMillis(30);
        
        System.out.println(startTime + " " + endTime);
        
        boolean serverStarted = false;
        
        while ( System.currentTimeMillis() < endTime ) {
        	BufferedReader br = new BufferedReader(new FileReader("logoutput.txt"));
        	String currentLine = br.readLine();
        	
        	while (currentLine != null && !serverStarted) {
        		if (currentLine.contains("Started Selenium Standalone")) {
        			 System.out.println("Found text");
        			 serverStarted = true;
        			 break;
        		} else {
        			System.out.println("Text not found, rechecking!");
        		}
        		
        		currentLine=br.readLine();

        	}
        	
        	br.close();
        	
        	if (serverStarted) {
        		System.out.println("Server has started! Proceeding...");
        		break;
        	} else {
        		System.out.println("Server not started. Rechecking.");
        	}
        }
        
        System.out.println("came out");
        
	}
	
	@AfterTest
	void tearDown() throws IOException {
		
		// URL of the Selenium Grid server's shutdown endpoint
        //String shutdownUrl = "http://localhost:4444/selenium-server/driver/?cmd=shutDownSeleniumServer";
        String shutdownUrl = "http://localhost:4444//lifecycle-manager/LifecycleServlet?action=shutdown";

        // Create a URL object
        URL url = new URL(shutdownUrl);

        // Open a connection to the URL
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set the request method to GET
        connection.setRequestMethod("GET");

        // Get the response code
        int responseCode = connection.getResponseCode();

        if (responseCode == 200) {
            System.out.println("Selenium Grid server has been shut down successfully.");
        } else {
            System.out.println("Failed to shut down Selenium Grid server. Response code: " + responseCode);
        }

        // Close the connection
        connection.disconnect();
		
		
		File file = new File("logoutput.txt");

        // Delete the File
      if (file.delete()) {
          System.out.println("File deleted successfully");
      }
      else {
          System.out.println("Failed to delete the file");
      }
	}
}
