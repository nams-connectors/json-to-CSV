package com.softwareag.jsonParse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwareag.jsonParse.model.FileStructure;

public class JsonParsingJackson {
	public static void JSONtoCSV(String folderPath) throws IOException{
		

		Scanner scanner = new Scanner(System.in);

		System.out.println("Type = "); String typeString = scanner.nextLine();

		System.out.println("allowCustomOperation = "); String acoString = scanner.nextLine();
 
		System.out.println("certified = "); String certifiedString = scanner.nextLine();

		ObjectMapper objectMapper = new ObjectMapper();
		JSONArray array = new JSONArray();           
		String jsonString;
		JSONObject jsonObject;
		File folder = new File(folderPath);

		File[] Fileslist = folder.listFiles();

		for (File file : Fileslist) {
			if (file.isFile()) {
				FileStructure fileStructure;
				try {	

					fileStructure = objectMapper.readValue(new File(file.getAbsolutePath()), FileStructure.class);

					if(!typeString.isBlank()) {

						if (!acoString.isBlank()) {

							if (!certifiedString.isBlank()) {

								if (fileStructure.getType().equalsIgnoreCase(typeString) && fileStructure.getAllowCustomOperations().equalsIgnoreCase(acoString) && fileStructure.getCertified() != null && fileStructure.getCertified().equalsIgnoreCase(certifiedString)) {
									jsonString = new String(
											Files.readAllBytes(Paths.get(file.getAbsolutePath())));
									jsonObject = new JSONObject(jsonString);
									array.put(jsonObject);
								}

							}
							else {

								if (fileStructure.getType().equalsIgnoreCase(typeString) && fileStructure.getAllowCustomOperations().equalsIgnoreCase(acoString)) {
									jsonString = new String(
											Files.readAllBytes(Paths.get(file.getAbsolutePath())));
									jsonObject = new JSONObject(jsonString);
									array.put(jsonObject);
								}

							}

						}
						else {
							if (!certifiedString.isBlank()) {

								if (fileStructure.getType().equalsIgnoreCase(typeString) && fileStructure.getCertified() != null && fileStructure.getCertified().equalsIgnoreCase(certifiedString)) {
									jsonString = new String(
											Files.readAllBytes(Paths.get(file.getAbsolutePath())));
									jsonObject = new JSONObject(jsonString);
									array.put(jsonObject);
								}

							}
							else {

								if (fileStructure.getType().equalsIgnoreCase(typeString)) {
									jsonString = new String(
											Files.readAllBytes(Paths.get(file.getAbsolutePath())));
									jsonObject = new JSONObject(jsonString);
									array.put(jsonObject);
								}

							}

						}

					}
					else {

						if (!acoString.isBlank()) {

							if (!certifiedString.isBlank()) {

								if (fileStructure.getAllowCustomOperations().equalsIgnoreCase(acoString) && fileStructure.getCertified().equalsIgnoreCase(certifiedString)) {
									jsonString = new String(
											Files.readAllBytes(Paths.get(file.getAbsolutePath())));
									jsonObject = new JSONObject(jsonString);
									array.put(jsonObject);
								}

							}
							else {

								if (fileStructure.getAllowCustomOperations().equalsIgnoreCase(acoString)) {
									jsonString = new String(
											Files.readAllBytes(Paths.get(file.getAbsolutePath())));
									jsonObject = new JSONObject(jsonString);
									array.put(jsonObject);
								}

							}

						}
						else {
							if (!certifiedString.isBlank()) {

								if (fileStructure.getCertified().equalsIgnoreCase(certifiedString)) {
									jsonString = new String(
											Files.readAllBytes(Paths.get(file.getAbsolutePath())));
									jsonObject = new JSONObject(jsonString);
									array.put(jsonObject);
								}

							}
							else {

								jsonString = new String(
										Files.readAllBytes(Paths.get(file.getAbsolutePath())));
								jsonObject = new JSONObject(jsonString);
								array.put(jsonObject);

							}

						}

					}

				} catch (Exception e) {
					Map<String, Object> map = new HashMap<String, Object>();
					jsonString = new String(
							Files.readAllBytes(Paths.get(file.getAbsolutePath())));
					map = objectMapper.readValue(jsonString, new TypeReference<Map<String, Object>>(){});
					jsonObject = new JSONObject(map);
					array.put(jsonObject);
				}			
			}	
		} 
		
		String csvString = CDL.toString(array);
		try {
			File path = new File("C:\\Users\\nams\\OneDrive - Software AG\\Desktop\\testcsvfiles\\Test.csv");

			FileWriter wr = new FileWriter(path);

			wr.write(csvString);
			wr.flush();   
			wr.close();
			System.out.println("File Created... C:\\Users\nams\\OneDrive - Software AG\\Desktop\\testcsvfiles\\Test.csv" );
		} catch (NullPointerException e) {
			System.out.println("There are no such file exist with the given attributes");
		}
		
	}

}




