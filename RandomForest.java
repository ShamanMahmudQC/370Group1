import java.util.*;

public class RandomForest {
    private List<DecisionTree> forest;
    private int numTrees;
    private Random random;

    public RandomForest(int numTrees) {
        this.numTrees = numTrees;
        this.forest = new ArrayList<>();
        this.random = new Random();
    }

    public void train(List<Patient> dataset) {
        forest.clear();
        for (int i = 0; i < numTrees; i++) {
            List<Patient> bootstrapSample = bootstrap(dataset);
            DecisionTree tree = new DecisionTree();
            tree.train(bootstrapSample);
            forest.add(tree);
        }
    }

    public int predict(Patient input) {
        Map<Integer, Integer> votes = new HashMap<>();
        for (DecisionTree tree : forest) {
            int prediction = tree.predict(input);
            votes.put(prediction, votes.getOrDefault(prediction, 0) + 1);
        }
        return votes.getOrDefault(1, 0) > votes.getOrDefault(0, 0) ? 1 : 0;
    }

    private List<Patient> bootstrap(List<Patient> data) {
        List<Patient> sample = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            int index = random.nextInt(data.size());
            sample.add(data.get(index));
        }
        return sample;
    }
}
