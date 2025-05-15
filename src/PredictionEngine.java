import java.util.List;
import java.util.Map;

public class PredictionEngine {
    private static RandomForest forest;
    private static List<Patient> dataset;
    private static float[] maxValues;

    static {
        maxValues = new float[3]; // age, glucose, bmi
        dataset = DatasetLoader.load("data.csv", maxValues);
        forest = new RandomForest(50);
        forest.train(dataset);
    }

    public static String predictAndCompare(Map<String, String> inputData) {
        Patient input = Patient.fromInputMap(inputData);

        int prediction = forest.predict(input);

        String result = "Prediction: You are " +
                (prediction == 1 ? "at risk of stroke." : "not predicted to have a stroke.") + "\n\n";

        result += ComparisonEngine.compareWithDataset(input);
        return result;
    }

    public static float[] getMaxValues() {
        return maxValues;
    }
}
