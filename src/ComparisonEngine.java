import java.util.*;

public class ComparisonEngine {
    private static List<Patient> dataset;
    public static float maxAge = 0, maxGlucose = 0, maxBMI = 0;

    static {
        float[] maxValues = new float[3];
        dataset = DatasetLoader.load("data.csv", maxValues);
        maxAge = maxValues[0];
        maxGlucose = maxValues[1];
        maxBMI = maxValues[2];
    }

    public static String compareWithDataset(Patient inputData) {
        float[] userVector = normalize(inputData);

        ClosestPoint closestHealthy = new ClosestPoint(null, Float.MAX_VALUE);
        ClosestPoint closestStroke = new ClosestPoint(null, Float.MAX_VALUE);

        for (Patient p : dataset) {
            if (p.getStroke() == null || p.getAge() == null || p.getBMI() == null || p.getAverageGlucose() == null)
                continue;

            float[] otherVec = normalize(p);
            float dist = euclideanDistance(userVector, otherVec);

            if (Boolean.TRUE.equals(p.getStroke()) && dist < closestStroke.distance) {
                closestStroke = new ClosestPoint(p, dist);
            } else if (Boolean.FALSE.equals(p.getStroke()) && dist < closestHealthy.distance) {
                closestHealthy = new ClosestPoint(p, dist);
            }
        }

        return buildExplanation(inputData, closestHealthy, closestStroke);
    }

    private static float[] normalize(Patient p) {
        return new float[]{
                p.getAge() / maxAge,
                p.getAverageGlucose() / maxGlucose,
                p.getBMI() / maxBMI
        };
    }

    private static float euclideanDistance(float[] a, float[] b) {
        float sum = 0;
        for (int i = 0; i < a.length; i++) {
            float diff = a[i] - b[i];
            sum += diff * diff;
        }
        return (float) Math.sqrt(sum);
    }

    private static String buildExplanation(Patient input, ClosestPoint healthy, ClosestPoint stroke) {
        StringBuilder sb = new StringBuilder();
        sb.append("Closest Healthy Patient (distance: ").append(healthy.distance).append(")\n");
        sb.append(compareAttributes(input, healthy.patient)).append("\n\n");

        sb.append("Closest Stroke Patient (distance: ").append(stroke.distance).append(")\n");
        sb.append(compareAttributes(input, stroke.patient));

        return sb.toString();
    }

    private static String compareAttributes(Patient input, Patient other) {
        StringBuilder sb = new StringBuilder();

        if (other == null) {
            sb.append("No similar patient found for comparison.\n");
            return sb.toString();
        }

        if (!Objects.equals(input.getSmokingHistory(), other.getSmokingHistory())) {
            sb.append("- Smoking history differs. You: ").append(input.getSmokingHistory()).append(", Other: ")
                    .append(other.getSmokingHistory()).append(".\n");
        }
        if (!Objects.equals(input.getEverMarried(), other.getEverMarried())) {
            sb.append("- Marital status differs. You: ").append(input.getEverMarried()).append(", Other: ")
                    .append(other.getEverMarried()).append(".\n");
        }
        if (!Objects.equals(input.getResidenceType(), other.getResidenceType())) {
            sb.append("- Residence type differs. You: ").append(input.getResidenceType()).append(", Other: ")
                    .append(other.getResidenceType()).append(".\n");
        }
        if (!Objects.equals(input.getWorkType(), other.getWorkType())) {
            sb.append("- Work type differs. You: ").append(input.getWorkType()).append(", Other: ")
                    .append(other.getWorkType()).append(".\n");
        }
        return sb.toString();
    }

    public static Character mapSmoking(String input) {
        switch (input.toLowerCase()) {
            case "never smoked":
                return 'N';
            case "formerly smoked":
                return 'F';
            case "smokes":
                return 'S';
            default:
                return null;
        }
    }

    public static Character mapWorkType(String input) {
        switch (input) {
            case "children":
                return 'C';
            case "Govt_job":
                return 'G';
            case "Never_worked":
                return 'N';
            case "Self-employed":
                return 'S';
            case "Private":
                return 'U';
            default:
                return null;
        }
    }
}
