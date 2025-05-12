package group1Project370;

public class Patient {
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


    public void setGender(Character gender) {
        if (gender != null && (gender == 'm' || gender == 'f')) {
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
    	if (workType != null && (workType == 'C' || workType == 'G' || workType == 'N' || workType == 'S' || workType == 'U')) {
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

    
    public Character getGender() {
        return gender;
    }

    public Integer getAge() {
        return age;
    }

    public Boolean getHypertension() {
        return hypertension;
    }

    public Boolean getHeartDisease() {
        return heartDisease;
    }

    public Boolean getEverMarried() {
        return everMarried;
    }

    public Character getWorkType() {
        return workType;
    }

    public Character getResidenceType() {
        return residenceType;
    }

    public Float getAverageGlucose() {
        return averageGlucose;
    }

    public Float getBMI() {
        return bmi;
    }

    public Character getSmokingHistory() {
        return smokingHistory;
    }

    public Boolean getStroke() {
        return stroke;
    }


    
    public void replaceNulls(Patient defaultPatient) {
        if (this.getGender() == null) {
            this.gender = defaultPatient.getGender();
        }
        if (this.getAge() == null) {
            this.age = defaultPatient.getAge();
        }
        if (this.getHypertension() == null) {
            this.hypertension = defaultPatient.getHypertension();
        }
        if (this.getHeartDisease() == null) {
            this.heartDisease = defaultPatient.getHeartDisease();
        }
        if (this.getEverMarried() == null) {
            this.everMarried = defaultPatient.getEverMarried();
        }
        if (this.getWorkType() == null) {
            this.workType = defaultPatient.getWorkType();
        }
        if (this.getResidenceType() == null) {
            this.residenceType = defaultPatient.getResidenceType();
        }
        if (this.getAverageGlucose() == null) {
            this.averageGlucose = defaultPatient.getAverageGlucose();
        }
        if (this.getBMI() == null) {
            this.bmi = defaultPatient.getBMI();
        }
        if (this.getSmokingHistory() == null) {
            this.smokingHistory = defaultPatient.getSmokingHistory();
        }
        if (this.getStroke() == null) {
            this.stroke = defaultPatient.getStroke();
        }
    }

}
