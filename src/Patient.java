
import java.io.Serializable;


public class Patient implements Serializable{
    private Character gender;
    private Integer age;
    private Boolean hypertension;
    private Boolean heartDisease;
    private Boolean everMarried;
    private Character workType;
    private Character residenceType;
    private Float averageGlucose;
    private Float bmi;
    private Character smokingHistory;
    private Boolean stroke;

        //contstructor
    public Patient(Character gender, Integer age, Boolean hypertension, Boolean heartDisease, Boolean everMarried,
            Character workType, Character residenceType, Float averageGlucose, Float bmi, Character smokingHistory, Boolean stroke) {
		 setGender(gender);
		 setAge(age);
		 setHypertension(hypertension);
		 setHeartDisease(heartDisease);
		 setEverMarried(everMarried);
		 setWorkType(workType);
		 setResidenceType(residenceType);
		 setAverageGlucose(averageGlucose);
		 setBMI(bmi);
		 setSmokingHistory(smokingHistory);
		 setStroke(stroke);
    }

    //used in normalization of values when calculating euclidian distance and decision tree splits
    //could use other factors for prediction but decided to only use age glucose bmi hypertension and heartdisease and others for find nearest patient
    public float[] getFeatureVector(float maxAge, float maxGlucose, float maxBMI) {
        return new float[]{
                (age != null ? age / maxAge : 0f),
                (averageGlucose != null ? averageGlucose / maxGlucose : 0f),
                (bmi != null ? bmi / maxBMI : 0f),
                (hypertension != null && hypertension ? 1f : 0f),
                (heartDisease != null && heartDisease ? 1f : 0f)
        };
    }

    //getters
    public Character getGender() { return gender; }
    public Integer getAge() { return age; }
    public Float getAverageGlucose() { return averageGlucose; }
    public Float getBMI() { return bmi; }
    public Boolean getHypertension() { return hypertension; }
    public Boolean getHeartDisease() { return heartDisease; }
    public Boolean getStroke() { return stroke; }
    public Character getSmokingHistory() { return smokingHistory; }
    public Boolean getEverMarried() { return everMarried; }
    public Character getResidenceType() { return residenceType; }
    public Character getWorkType() { return workType; }


    //setters main purpose is to deal with invalid input
    public void setGender(Character gender) {
        if (gender != null && (gender == 'M' || gender == 'F')) {
            this.gender = gender;
        } else {
            this.gender = null;
        }
    }

    public void setAge(Integer age) {
        if (age != null && age >= 0 && age <= 150) {
            this.age = age;
        } else {
            this.age = null;
        }
    }

    public void setHypertension(Boolean hypertension) {
        if (hypertension != null) {
            this.hypertension = hypertension;
        } else {
            this.hypertension = null;
        }
    }

    public void setHeartDisease(Boolean heartDisease) {
        if (heartDisease != null) {
            this.heartDisease = heartDisease;
        } else {
            this.heartDisease = null;
        }
    }

    public void setEverMarried(Boolean everMarried) {
        if (everMarried != null) {
            this.everMarried = everMarried;
        } else {
            this.everMarried = null;
        }
    }

    public void setWorkType(Character workType) {
    	if (workType != null && (workType == 'C' || workType == 'G' || workType == 'N' || workType == 'S' || workType == 'U' || workType == 'P')) {
            this.workType = workType;
        } else {
            this.workType = null;
        }
    }

    public void setResidenceType(Character residenceType) {
        if (residenceType != null && (residenceType == 'U' || residenceType == 'R')) {
            this.residenceType = residenceType;
        } else {
            this.residenceType = null;
        }
    }

    public void setAverageGlucose(Float averageGlucose) {
        if (averageGlucose != null && averageGlucose >= 0) {
            this.averageGlucose = averageGlucose;
        } else {
            this.averageGlucose = null;
        }
    }

    public void setBMI(Float bmi) {
        if (bmi != null && bmi >= 0 && bmi <= 100) {
            this.bmi = bmi;
        } else {
            this.bmi = null;
        }
    }

    public void setSmokingHistory(Character smokingHistory) {
        if (smokingHistory != null && (smokingHistory == 'N' || smokingHistory == 'F' || smokingHistory == 'S')) {
            this.smokingHistory = smokingHistory;
        } else {
            this.smokingHistory = null;
        }
    }

    public void setStroke(Boolean stroke) {
        if (stroke != null) {
            this.stroke = stroke;
        } else {
            this.stroke = null;
        }
    }

    //useful for tostring override

    public static String reverseWorkType(Character c) {
        if (c == null) return "Unknown";
        switch (c) {
            case 'C': return "children";
            case 'G': return "Govt_job";
            case 'N': return "Never_worked";
            case 'S': return "Self-employed";
            case 'P': return "Private"; 
            default: return "Unknown";
        }
    }

    public static String reverseResidenceType(Character c) {
        if (c == null) return "Unknown";
        switch (c) {
            case 'U': return "Urban";
            case 'R': return "Rural";
            default: return "Unknown";
        }
    }

    public static String reverseSmokingHistory(Character c) {
        if (c == null) return "Unknown";
        switch (c) {
            case 'N': return "Never smoked";
            case 'F': return "Formerly smoked";
            case 'S': return "Smokes";
            default: return "Unknown";
        }
    }


    //for pritning in output and testing
    @Override
    public String toString() {
        return "Patient Attributes: \n" +
           "gender = " + gender +
           ", age = " + age +
           ", hypertension = " + hypertension +
           ", heartDisease = " + heartDisease +
           ", everMarried = " + everMarried +
           ", workType = " + reverseWorkType(workType) +
           ", residenceType = " + reverseResidenceType(residenceType) +
           ", averageGlucose = " + averageGlucose +
           ", bmi = " + bmi +
           ", smokingHistory = " + reverseSmokingHistory(smokingHistory) + '\n';
    }

}
