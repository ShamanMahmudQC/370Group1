import java.util.*;

public class ComparisonEngine {
    public static List<Patient> patientList;
    public static float[] maxValues = new float[3];
    public static Patient wantedAttributes;
    public static boolean filterPatients;


    public static String compareWithDataset(Patient inputData) {
        float[] userVector = normalize(inputData);

        ClosestPoint closestHealthy = new ClosestPoint(null, Float.MAX_VALUE);
        ClosestPoint closestStroke = new ClosestPoint(null, Float.MAX_VALUE);
        for (Patient p : patientList) {
        if (filterPatients) {
            //if filter != null, do not add data to vector 
            //if filter != value, do not add data to vector
            if (wantedAttributes.getEverMarried() != null &&
                !Objects.equals(p.getEverMarried(), wantedAttributes.getEverMarried())) {
                continue;
            }

            if (wantedAttributes.getHeartDisease() != null &&
                !Objects.equals(p.getHeartDisease(), wantedAttributes.getHeartDisease())) {
                continue;
            }

            if (wantedAttributes.getHypertension() != null &&
                !Objects.equals(p.getHypertension(), wantedAttributes.getHypertension())) {
                continue;
            }
            //U is valid char for this attribute so use U instead of null (have null just in case)
            if (wantedAttributes.getSmokingHistory() != null &&
                wantedAttributes.getSmokingHistory() != 'U' &&  
                !Objects.equals(p.getSmokingHistory(), wantedAttributes.getSmokingHistory())) {
                continue;
            }
            if (wantedAttributes.getResidenceType() != null &&
                !Objects.equals(p.getResidenceType(), wantedAttributes.getResidenceType())) {
                continue;
            }
            if (wantedAttributes.getWorkType() != null &&
                wantedAttributes.getWorkType() != 'U' &&  
                !Objects.equals(p.getWorkType(), wantedAttributes.getWorkType())) {
                continue;
            }
        }

            //normalized numerical attributes by dividing by max
            float[] otherVec = normalize(p);

            float dist = euclideanDistance(userVector, otherVec);

            if (Boolean.TRUE.equals(p.getStroke()) && dist < closestStroke.distance) {
                closestStroke = new ClosestPoint(p, dist);
            } else if (Boolean.FALSE.equals(p.getStroke()) && dist < closestHealthy.distance) {
                closestHealthy = new ClosestPoint(p, dist);
            }
        }

        return explanation(inputData, closestHealthy, closestStroke);
    }

    private static float[] normalize(Patient p) {
        //now between 0-1 
        //initially wanted to add hypertension and heart disease but it would weight 
        //plus we dont know how much it is supposed to impact the distance by (not doctors)
        return new float[]{
                p.getAge() / maxValues[0],
                p.getAverageGlucose() / maxValues[1],
                p.getBMI() / maxValues[2]
        };
    }

    private static float euclideanDistance(float[] a, float[] b) {
        float sum = 0;
        //for all corresponding elements
        for (int i = 0; i < a.length; i++) {
            //subtract component
            float diff = a[i] - b[i];
            //square values
            sum += diff * diff;
        }
        //square root the total
        return (float) Math.sqrt(sum);
    }

    //output patient differences and information gathered
    private static String explanation(Patient input, ClosestPoint healthy, ClosestPoint stroke) {
        StringBuilder sb = new StringBuilder();
        sb.append("Closest Healthy Patient\n");
        if(healthy.patient != null){
            sb.append(healthy.patient);
        }
        sb.append(compareAttributes(input, healthy.patient)).append("\n\n");

        sb.append("Closest Stroke Patient \n");
        if(stroke.patient != null){
            sb.append(stroke.patient);
        }
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
            sb.append("- Smoking history differs.\n");
        }
        if (!Objects.equals(input.getEverMarried(), other.getEverMarried())) {
            sb.append("- Marital status differs.\n");
        }
        if (!Objects.equals(input.getResidenceType(), other.getResidenceType())) {
            sb.append("- Residence type differs.\n");
        }
        if (!Objects.equals(input.getWorkType(), other.getWorkType())) {
            sb.append("- Work type differs.\n");
        }
        if (!Objects.equals(input.getHypertension(), other.getHypertension())) {
            sb.append("- Difference in Hypertension.\n");
        }
        if (!Objects.equals(input.getHeartDisease(), other.getHeartDisease())) {
            sb.append("- Difference in Heart Disease.\n");
        }
        return sb.toString();
    }

}
