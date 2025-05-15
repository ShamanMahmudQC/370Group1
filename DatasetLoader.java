// DatasetLoader.java
import java.io.*;
import java.util.*;

public class DatasetLoader {
    public static List<Patient> load(String path, float[] maxValues) {
        List<Patient> dataset = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String header = br.readLine(); // skip header
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 12) continue;

                Integer age = (int) Float.parseFloat(parts[2].trim());
                Float glucose = Float.parseFloat(parts[8].trim());
                String bmiStr = parts[9].trim();
                Float bmi = (bmiStr.isEmpty() || bmiStr.equalsIgnoreCase("N/A")) ? 26.0f : Float.parseFloat(bmiStr);


                maxValues[0] = Math.max(maxValues[0], age);
                maxValues[1] = Math.max(maxValues[1], glucose);
                maxValues[2] = Math.max(maxValues[2], bmi);

                Patient p = new Patient(
                        null,
                        age,
                        parts[3].trim().equals("1"),
                        parts[4].trim().equals("1"),
                        parts[5].trim().equalsIgnoreCase("Yes"),
                        ComparisonEngine.mapWorkType(parts[6].trim()),
                        parts[7].trim().equalsIgnoreCase("Urban") ? 'U' : 'R',
                        glucose,
                        bmi,
                        ComparisonEngine.mapSmoking(parts[10].trim()),
                        parts[11].trim().equals("1")
                );
                dataset.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataset;
    }
}
