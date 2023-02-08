package com.softwareag.jsonParse;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class FromURL {

	@Autowired
	static
    RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		System.out.println("GitHub URL : "); String urlString = scanner.nextLine();
		System.out.println("Bearer token : "); String tokenString = scanner.nextLine();
		System.out.println("Path to download Zip File : "); String zipFilePath = scanner.nextLine();

		
		
		RestTemplate restTemplate = new RestTemplate();
	    restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());

	    HttpHeaders headers = new HttpHeaders();
	    
	    headers.set("Accept", "application/vnd.github+json");
	    headers.set("Authorization", "Bearer " + tokenString);
		headers.set("Content-Type", "application/zip");
		headers.set("Content-Disposition", "attachment; filename=output.zip");

	    
	    HttpEntity<String> entity = new HttpEntity<String>(headers);

	    ResponseEntity<byte[]> response = restTemplate.exchange(urlString,HttpMethod.GET, entity, byte[].class, "1");

	    if (response.getStatusCode() == HttpStatus.OK) {
	        Files.write(Paths.get(zipFilePath), response.getBody());
	    }
		
	    Path zipFile = Path.of(zipFilePath);
	    
	    unzipFile(zipFile);
	    
	    System.out.println("Provide the location of the files to be converted : "); 
	    String locString = scanner.nextLine();
	    
	    locString = "C:\\Users\\nams\\OneDrive - Software AG\\Desktop\\wM-connectors-store-1015wmio2022\\customize\\wmio\\connectors\\jsons";
	    JsonParsingJackson.JSONtoCSV(locString);
	    
	  }

	  private static void unzipFile(Path filePathToUnzip) {

	    Path parentDir = filePathToUnzip.getParent();
	    String fileName = filePathToUnzip.toFile().getName();
	    Path targetDir = parentDir.resolve(FilenameUtils.removeExtension(fileName));

	    try (ZipFile zip = new ZipFile(filePathToUnzip.toFile())) {

	      Enumeration<? extends ZipEntry> entries = zip.entries();

	      if (!targetDir.toFile().isDirectory()
	          && !targetDir.toFile().mkdirs()) {
	        throw new IOException("failed to create directory " + targetDir);
	      }

	      while (entries.hasMoreElements()) {
	        ZipEntry entry = entries.nextElement();

	        File f = new File(targetDir.resolve(Path.of(entry.getName())).toString());

	        if (entry.isDirectory()) {
	          if (!f.isDirectory() && !f.mkdirs()) {
	            throw new IOException("failed to create directory " + f);
	          }
	        }
	        
	        else {
	          File parent = f.getParentFile();
	          if (!parent.isDirectory() && !parent.mkdirs()) {
	            throw new IOException("failed to create directory " + parent);
	          }

	          try(InputStream in = zip.getInputStream(entry)) {
	            Files.copy(in, f.toPath());
	          }

	        }
	      }
	      
	      System.out.println("File extracted at : " + targetDir);
	      
	      
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	    
	  }
}
