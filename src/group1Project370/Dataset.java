package group1Project370;

import java.util.*;
import java.io.*;

public class Dataset {

	private List<Patient> data;
	private Patient defaultValue;
	private double[][] attributeMatrix;
	
	
	
	public Dataset(String filename) {
	    List<Patient> patients = new ArrayList<>();
	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
	        String line;

	        while ((line = br.readLine()) != null) {
	            String[] attributes = line.split(",");
	            
	            	Character gender = Character.toUpperCase(attributes[1].charAt(0)); 
	            	
		            Integer age = Integer.valueOf(attributes[2]); 
		            
		            Boolean hypertension = (Integer.valueOf(attributes[3]) == 1);
		            
		            Boolean heartDisease = (Integer.valueOf(attributes[4]) == 1);
		            
		            Boolean everMarried = null;
		            if (attributes[5].equalsIgnoreCase("Yes")) {
		                everMarried = true;
		            } else if (attributes[5].equalsIgnoreCase("No")) {
		                everMarried = false;
		            }
		            
		            Character workType = Character.toUpperCase(attributes[6].charAt(0)); // private self-employed children govt never
		            
		            Character residenceType = Character.toUpperCase(attributes[7].charAt(0));
		            
		            Float averageGlucose = Float.valueOf(attributes[8]);
		            
		            Float bmi = Float.valueOf(attributes[9]);
		            
		            Character smokingHistory = Character.toUpperCase(attributes[10].charAt(0)); // f n s
		            
		            Boolean stroke = (Integer.valueOf(attributes[11]) == 1);  // 1 0
	                
	                Patient patient = new Patient(gender, age, hypertension, heartDisease, everMarried, workType, residenceType, averageGlucose, bmi, smokingHistory, stroke);

	            patients.add(patient);
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    
	    this.data = patients;
	}
	
	public void createDefault() {
		//Mode of non numerical
		//Median of all numerical
	}
        
        
}