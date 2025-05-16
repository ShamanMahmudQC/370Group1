    
public class Test{
    public static void main(String[] args) {

        //test setters 
        Patient p1 = new Patient('M', 45, true, false, true, 'P', 'U', 120.5f, 25.0f, 'N', false);
        System.out.println("Test 1: Valid inputs");
        System.out.println(p1);
        System.out.println("Gender: " + p1.getGender());
        System.out.println("Age: " + p1.getAge());
        System.out.println("Hypertension: " + p1.getHypertension());
        System.out.println("Heart Disease: " + p1.getHeartDisease());
        System.out.println("Ever Married: " + p1.getEverMarried());
        System.out.println("Work Type: " + p1.getWorkType());
        System.out.println("Residence Type: " + p1.getResidenceType());
        System.out.println("Average Glucose: " + p1.getAverageGlucose());
        System.out.println("BMI: " + p1.getBMI());
        System.out.println("Smoking History: " + p1.getSmokingHistory());
        System.out.println("Stroke: " + p1.getStroke());
        System.out.println();

        //test garbage values get set to null
        Patient p2 = new Patient('X', -5, null, null, null, 'Z', 'Q', -10.0f, 150.0f, 'A', null);
        System.out.println("Test 2: Invalid inputs");
        System.out.println(p2);
        System.out.println();

        //set all values to null
        Patient p3 = new Patient(null, null, null, null, null, null, null, null, null, null, null);
        System.out.println("Test 3: Null inputs");
        System.out.println(p3);
        System.out.println();

        //test if feature vector properly normalizes
        float maxAge = 100f;
        float maxGlucose = 200f;
        float maxBMI = 50f;
        System.out.println("Test 4: Feature vector for p1");
        float[] features = p1.getFeatureVector(maxAge, maxGlucose, maxBMI);
        for (int i = 0; i < features.length; i++) {
            System.out.printf("Feature %d: %.3f\n", i, features[i]);
        }
        System.out.println();

        //Dataset
        //input data
        Dataset dataset = new Dataset("data.csv");

        //dataset.printAllPatients();

        System.out.println("\nDefault patient:");
        System.out.println(dataset.defaultValue);

        System.out.print("\nMax values (age, glucose, BMI):" + dataset.maxValues[0] + dataset.maxValues[1] + dataset.maxValues[2]);


        //RandomForest

        //DecisionTree

    }
}
