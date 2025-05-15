import java.util.Map;

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
                   Character workType, Character residenceType, Float averageGlucose, Float bmi,
                   Character smokingHistory, Boolean stroke) {
        this.gender = gender;
        this.age = age;
        this.hypertension = hypertension;
        this.heartDisease = heartDisease;
        this.everMarried = everMarried;
        this.workType = workType;
        this.residenceType = residenceType;
        this.averageGlucose = averageGlucose;
        this.bmi = bmi;
        this.smokingHistory = smokingHistory;
        this.stroke = stroke;
    }

    public static Patient fromInputMap(Map<String, String> inputData) {
        Integer age = Integer.parseInt(inputData.get("age"));
        Float glucose = Float.parseFloat(inputData.get("avg_glucose_level"));
        Float bmi = Float.parseFloat(inputData.get("bmi"));
        Boolean married = inputData.get("ever_married").equalsIgnoreCase("Yes");
        Character residence = inputData.get("Residence_type").equalsIgnoreCase("Urban") ? 'U' : 'R';
        Character smoke = mapSmoking(inputData.get("smoking_status"));
        Character work = mapWorkType(inputData.get("work_type"));

        return new Patient(null, age, false, false, married, work, residence, glucose, bmi, smoke, null);
    }

    public Integer getAge() {
        return age;
    }

    public Float getAverageGlucose() {
        return averageGlucose;
    }

    public Float getBMI() {
        return bmi;
    }

    public Boolean getStroke() {
        return stroke;
    }

    public Character getSmokingHistory() {
        return smokingHistory;
    }

    public Boolean getEverMarried() {
        return everMarried;
    }

    public Character getResidenceType() {
        return residenceType;
    }

    public Character getWorkType() {
        return workType;
    }

    public static Character mapSmoking(String input) {
        switch (input.toLowerCase()) {
            case "never smoked": return 'N';
            case "formerly smoked": return 'F';
            case "smokes": return 'S';
            default: return null;
        }
    }

    public static Character mapWorkType(String input) {
        switch (input) {
            case "Children": return 'C';
            case "Govt_job": return 'G';
            case "Never_worked": return 'N';
            case "Self-employed": return 'S';
            case "Private": return 'U';
            default: return null;
        }
    }
}