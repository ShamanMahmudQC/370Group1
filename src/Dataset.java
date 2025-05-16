import java.io.*;
import java.util.*;

public class Dataset implements Serializable{
	public List<Patient> data;
	public Patient defaultValue;
	//max values is used to normalize age, bmi, sugarlevel
	public float[] maxValues = new float[3]; 


	public Dataset(String filename) {
	    List<Patient> patients = new ArrayList<>();
	    try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line = br.readLine(); 
			while ((line = br.readLine()) != null) {
	            String[] attributes = line.split(",");
				patients.add(new Patient(
					Character.toUpperCase(attributes[1].charAt(0)),
					Integer.valueOf(Math.round(Float.valueOf(attributes[2]))),
					Float.valueOf(attributes[3]) == 1,
					Float.valueOf(attributes[4]) == 1,
					attributes[5].equalsIgnoreCase("Yes") ? true : attributes[5].equalsIgnoreCase("No") ? false : null,
					Character.toUpperCase(attributes[6].charAt(0)),
					Character.toUpperCase(attributes[7].charAt(0)),
					attributes[8].equalsIgnoreCase("N/A") || attributes[8].isBlank() ? null : Float.valueOf(attributes[8]),
					attributes[9].equalsIgnoreCase("N/A") || attributes[9].isBlank() ? null : Float.valueOf(attributes[9]),
					Character.toUpperCase(attributes[10].charAt(0)),
					Integer.valueOf(attributes[11]) == 1
				));
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    this.data = patients;
		this.defaultValue = createDefault();
        ReplaceNullsData(this.data, this.defaultValue);
	}

	//create the default patient who has all non numerical values as mode and all numerical values as median
	public Patient createDefault() {
		if (this.data == null || this.data.isEmpty()) {
			this.defaultValue = null;
			throw new IllegalStateException("No dataset found: Add a data.csv with values.");
		}

		//count the count of attributes (to get the mode)
		Map<Character, Integer> genderCount = new HashMap<>();
		Map<Boolean, Integer> hypertensionCount = new HashMap<>();
		Map<Boolean, Integer> heartDiseaseCount = new HashMap<>();
		Map<Boolean, Integer> everMarriedCount = new HashMap<>();
		Map<Character, Integer> workTypeCount = new HashMap<>();
		Map<Character, Integer> residenceTypeCount = new HashMap<>();
		Map<Character, Integer> smokingHistoryCount = new HashMap<>();
		Map<Boolean, Integer> strokeCount = new HashMap<>();

		//numerical values track (just find middle)
		List<Integer> ages = new ArrayList<>();
		List<Float> averageGlucose = new ArrayList<>();
		List<Float> bmis = new ArrayList<>();

		for (Patient p : this.data) {
			if (p.getGender() != null) {
				genderCount.put(p.getGender(), genderCount.getOrDefault(p.getGender(), 0) + 1);
			}
			if (p.getHypertension() != null) {
				hypertensionCount.put(p.getHypertension(), hypertensionCount.getOrDefault(p.getHypertension(), 0) + 1);
			}
			if (p.getHeartDisease() != null) {
				heartDiseaseCount.put(p.getHeartDisease(), heartDiseaseCount.getOrDefault(p.getHeartDisease(), 0) + 1);
			}
			if (p.getEverMarried() != null) {
				everMarriedCount.put(p.getEverMarried(), everMarriedCount.getOrDefault(p.getEverMarried(), 0) + 1);
			}
			if (p.getWorkType() != null) {
				workTypeCount.put(p.getWorkType(), workTypeCount.getOrDefault(p.getWorkType(), 0) + 1);
			}
			if (p.getResidenceType() != null) {
				residenceTypeCount.put(p.getResidenceType(), residenceTypeCount.getOrDefault(p.getResidenceType(), 0) + 1);
			}
			if (p.getSmokingHistory() != null) {
				smokingHistoryCount.put(p.getSmokingHistory(), smokingHistoryCount.getOrDefault(p.getSmokingHistory(), 0) + 1);
			}
			if (p.getStroke() != null) {
				strokeCount.put(p.getStroke(), strokeCount.getOrDefault(p.getStroke(), 0) + 1);
			}
			if (p.getAge() != null) {
				ages.add(p.getAge());
				if (p.getAge() > maxValues[0]) {
					maxValues[0] = p.getAge();
				}
			}
			if (p.getAverageGlucose() != null) {
				averageGlucose.add(p.getAverageGlucose());
				if (p.getAverageGlucose() > maxValues[1]) {
					maxValues[1] = p.getAverageGlucose();
				}
			}
			if (p.getBMI() != null) {
				bmis.add(p.getBMI());
				if (p.getBMI() > maxValues[2]) {
					maxValues[2] = p.getBMI();
				}
			}
		}

		//determine value and assign it
		Character modeGender = null;
		int maxGenderCount = -1;
		for (Map.Entry<Character, Integer> e : genderCount.entrySet()) {
			if (e.getValue() > maxGenderCount) {
				maxGenderCount = e.getValue();
				modeGender = e.getKey();
			}
		}

		Boolean modeHypertension = null;
		int maxHyperCount = -1;
		for (Map.Entry<Boolean, Integer> e : hypertensionCount.entrySet()) {
			if (e.getValue() > maxHyperCount) {
				maxHyperCount = e.getValue();
				modeHypertension = e.getKey();
			}
		}

		Boolean modeHeartDisease = null;
		int maxHeartCount = -1;
		for (Map.Entry<Boolean, Integer> e : heartDiseaseCount.entrySet()) {
			if (e.getValue() > maxHeartCount) {
				maxHeartCount = e.getValue();
				modeHeartDisease = e.getKey();
			}
		}

		Boolean modeEverMarried = null;
		int maxMarriedCount = -1;
		for (Map.Entry<Boolean, Integer> e : everMarriedCount.entrySet()) {
			if (e.getValue() > maxMarriedCount) {
				maxMarriedCount = e.getValue();
				modeEverMarried = e.getKey();
			}
		}

		Character modeWorkType = null;
		int maxWorkCount = -1;
		for (Map.Entry<Character, Integer> e : workTypeCount.entrySet()) {
			if (e.getValue() > maxWorkCount) {
				maxWorkCount = e.getValue();
				modeWorkType = e.getKey();
			}
		}

		Character modeResidenceType = null;
		int maxResidenceCount = -1;
		for (Map.Entry<Character, Integer> e : residenceTypeCount.entrySet()) {
			if (e.getValue() > maxResidenceCount) {
				maxResidenceCount = e.getValue();
				modeResidenceType = e.getKey();
			}
		}

		Character modeSmokingHistory = null;
		int maxSmokeCount = -1;
		for (Map.Entry<Character, Integer> e : smokingHistoryCount.entrySet()) {
			if (e.getValue() > maxSmokeCount) {
				maxSmokeCount = e.getValue();
				modeSmokingHistory = e.getKey();
			}
		}

		//might as well have for all attributes
		Boolean modeStroke = null;
		int maxStrokeCount = -1;
		for (Map.Entry<Boolean, Integer> e : strokeCount.entrySet()) {
			if (e.getValue() > maxStrokeCount) {
				maxStrokeCount = e.getValue();
				modeStroke = e.getKey();
			}
		}

		Integer medianAge = null;
		if (!ages.isEmpty()) {
			Collections.sort(ages);
			int mid = ages.size() / 2;
			if (ages.size() % 2 == 1) {
				medianAge = ages.get(mid);
			} else {
				medianAge = (ages.get(mid - 1) + ages.get(mid)) / 2;
			}
		}

		Float medianGlucose = null;
		if (!averageGlucose.isEmpty()) {
			Collections.sort(averageGlucose);
			int mid = averageGlucose.size() / 2;
			if (averageGlucose.size() % 2 == 1) {
				medianGlucose = averageGlucose.get(mid);
			} else {
				medianGlucose = (averageGlucose.get(mid - 1) + averageGlucose.get(mid)) / 2;
			}
		}

		Float medianBMI = null;
		if (!bmis.isEmpty()) {
			Collections.sort(bmis);
			int mid = bmis.size() / 2;
			if (bmis.size() % 2 == 1) {
				medianBMI = bmis.get(mid);
			} else {
				medianBMI = (bmis.get(mid - 1) + bmis.get(mid)) / 2;
			}
		}

		Patient averagedPatient = new Patient(
			modeGender,
			medianAge,
			modeHypertension,
			modeHeartDisease,
			modeEverMarried,
			modeWorkType,
			modeResidenceType,
			medianGlucose,
			medianBMI,
			modeSmokingHistory,
			modeStroke
		);
		this.defaultValue = averagedPatient;
		return averagedPatient;
	}

	//compare and iterate through data replace if value is null
	private void ReplaceNullsData(List<Patient> data, Patient defaultValue){
			for (int i = 0; i < data.size(); i++) {
				Patient p = data.get(i);
				if(p.getGender() == null){
					p.setGender(defaultValue.getGender());
				}
				if (p.getAge() == null) {
					p.setAge(defaultValue.getAge());
				}
				if (p.getHypertension() == null) {
					p.setHypertension(defaultValue.getHypertension());
				}
				if (p.getHeartDisease() == null) {
					p.setHeartDisease(defaultValue.getHeartDisease());
				}
				if (p.getEverMarried() == null) {
					p.setEverMarried(defaultValue.getEverMarried());
				}
				if (p.getWorkType() == null) {
					p.setWorkType(defaultValue.getWorkType());
				}
				if (p.getResidenceType() == null) {
					p.setResidenceType(defaultValue.getResidenceType());
				}
				if (p.getAverageGlucose() == null) {
					p.setAverageGlucose(defaultValue.getAverageGlucose());
				}
				if (p.getBMI() == null) {
					p.setBMI(defaultValue.getBMI());
				}
				if (p.getSmokingHistory() == null) {
					p.setSmokingHistory(defaultValue.getSmokingHistory());
				}
				if (p.getStroke() == null) {
					p.setStroke(defaultValue.getStroke());
				}
			}
	}


//print all patients for testing purposes
	public void printAllPatients() {
		for (Patient patient : data) {
			System.out.println(patient);
		}
	}

}