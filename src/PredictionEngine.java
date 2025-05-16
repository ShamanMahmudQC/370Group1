import java.io.*;
public class PredictionEngine {
    private static RandomForest forest;
    public Dataset dataset;

    //Create trees statistics
    public PredictionEngine(boolean shouldWeRemakeData) {
        //reuse data previously created and stored in forest.model
        if (!shouldWeRemakeData) {
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("forest.model"))) {
                PredictionEngine.forest = (RandomForest) in.readObject();  
                this.dataset = (Dataset) in.readObject();      
                ComparisonEngine.patientList = dataset.data;
                ComparisonEngine.maxValues = dataset.maxValues;
                System.out.println("Data loaded");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                shouldWeRemakeData = true;
            }
        } 
        //remake the data (given input to do so or if reusing failed) then save it
        if(shouldWeRemakeData) {
            this.dataset = new Dataset("data.csv");
            PredictionEngine.forest = new RandomForest(50);
            PredictionEngine.forest.train(this.dataset);
            ComparisonEngine.patientList = dataset.data;
            ComparisonEngine.maxValues = dataset.maxValues;
            saveModel();
            System.out.println("Data created");
        }
    }


    public static String predictAndCompare(Patient patient) {

        //predicts outcome of patient
        int prediction = forest.predict(patient);

        //displays output
        String result = "Prediction: You are " +
                (prediction == 1 ? "at risk of stroke." : "not predicted to have a stroke.") + "\n\n";

        result += ComparisonEngine.compareWithDataset(patient);
        return result;
    }

    //try to save the data for future fast reuse
    public void saveModel() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("forest.model"))) {
            out.writeObject(PredictionEngine.forest);  
            out.writeObject(this.dataset); 
            System.out.println("Forest and dataset saved.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
