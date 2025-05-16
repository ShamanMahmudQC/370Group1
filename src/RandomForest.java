import java.io.Serializable;
import java.util.*;

public class RandomForest implements Serializable{
    private List<DecisionTree> forest;
    private int numTrees;
    private Random random;

  // constructor
    public RandomForest(int numTrees) {
        this.numTrees = numTrees;
        this.forest = new ArrayList<>();
        this.random = new Random();
    }
    // create tree based on how many requested, train them in the decisionTree class
    public void train(List<Patient> dataset) {
        forest.clear();

        // Separate stroke-positive and negative patients
        List<Patient> positive = new ArrayList<>();
        List<Patient> negative = new ArrayList<>();

        for (Patient p : dataset) {
            if (Boolean.TRUE.equals(p.getStroke())) {
                positive.add(p);
            } else {
                negative.add(p);
            }
        }

        // Oversample positive cases to match negative count
        List<Patient> balancedDataset = new ArrayList<>(negative);
        Random rand = new Random();
        while (balancedDataset.size() < 2 * negative.size()) {
            balancedDataset.add(positive.get(rand.nextInt(positive.size())));
        }

        for (int i = 0; i < numTrees; i++) {
            List<Patient> bootstrapSample = bootstrap(balancedDataset);
            DecisionTree tree = new DecisionTree();
            tree.train(bootstrapSample);
            forest.add(tree);
        }
    }
    
    // count random forest vote
    public int predict(Patient input) {
        Map<Integer, Integer> votes = new HashMap<>();
        for (DecisionTree tree : forest) {
            int prediction = tree.predict(input);
            votes.put(prediction, votes.getOrDefault(prediction, 0) + 1);
        }
        return votes.getOrDefault(1, 0) > votes.getOrDefault(0, 0) ? 1 : 0;
    }


    // bootstrap - pick random % that will be our % of strke victims in sample data
    // iterate through data and place in bucket stroke or no stroke

    private List<Patient> bootstrap(List<Patient> data) {
        int size = data.size();

        // randomly choose % stroke victims
        Random random = new Random();
        double strokePercent = random.nextDouble();
        int strokeCountNeeded = (int) (size * strokePercent);

        List<Patient> strokePatients = new ArrayList<>();
        List<Patient> nonStrokePatients = new ArrayList<>();
        for (Patient p : data) {
            if (Boolean.TRUE.equals(p.getStroke())) {
                strokePatients.add(p);
            } else {
                nonStrokePatients.add(p);
            }
        }

        // bootstrap collection
        List<Patient> sample = new ArrayList<>(size);

        // randomly pick stroke victim
        for (int i = 0; i < strokeCountNeeded; i++) {
            if (strokePatients.isEmpty()) break;
            sample.add(strokePatients.get(random.nextInt(strokePatients.size())));
        }

        // randomly picks non stroke
        int nonStrokeNeeded = size - sample.size();
        for (int i = 0; i < nonStrokeNeeded; i++) {
            if (nonStrokePatients.isEmpty()) break;
            sample.add(nonStrokePatients.get(random.nextInt(nonStrokePatients.size())));
        }


        return sample;
    }

}
