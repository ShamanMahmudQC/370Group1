import java.io.Serializable;
import java.util.*;

public class DecisionTree implements Serializable {
    private Node root;

    //recursively builds the tree
    public void train(List<Patient> patients) {
        this.root = buildTree(patients, 0);
    }

    public int predict(Patient input) {
        return predictFromNode(root, input);
    }

    private int predictFromNode(Node node, Patient input) {
        if (node.isLeaf()) return node.label;

        float val = node.getFeatureValue(input);
        if (val < node.threshold) {
            return predictFromNode(node.left, input);
        } else {
            return predictFromNode(node.right, input);
        }
    }

    private boolean isPure(List<Patient> data) {
        boolean first = Boolean.TRUE.equals(data.get(0).getStroke());
        for (Patient p : data) {
            if (Boolean.TRUE.equals(p.getStroke()) != first) return false;
        }
        return true;
    }

    //if uniform -> done else find best split
    private Node buildTree(List<Patient> data, int depth) {
        if (isPure(data)) {
            return new Node(getMajorityLabel(data));
        }

        float bestGini = Float.MAX_VALUE;
        int bestFeature = -1;
        float bestThreshold = 0;
        List<Patient> bestLeft = null;
        List<Patient> bestRight = null;

        float[][] featureVectors = new float[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            featureVectors[i] = data.get(i).getFeatureVector(
                ComparisonEngine.maxValues[0],
                ComparisonEngine.maxValues[1],
                ComparisonEngine.maxValues[2]
            );
        }
        //finding best split based on the 5 features we chose to use for decision tree (hypertension heart disease bmi age glucose)
        for (int featureIndex = 0; featureIndex < 5; featureIndex++) {
            Set<Float> uniqueValues = new TreeSet<>();
            for (float[] vec : featureVectors) uniqueValues.add(vec[featureIndex]);

            Float prev = null;
            List<Float> thresholds = new ArrayList<>();
            for (Float val : uniqueValues) {
                if (prev != null) thresholds.add((prev + val) / 2);
                prev = val;
            }

            for (float threshold : thresholds) {
                List<Patient> left = new ArrayList<>();
                List<Patient> right = new ArrayList<>();
                for (int j = 0; j < data.size(); j++) {
                    float val = featureVectors[j][featureIndex];
                    if (val < threshold) left.add(data.get(j));
                    else right.add(data.get(j));
                }
            //find the best split
                float gini = giniIndex(left, right);
                if (gini < bestGini) {
                    bestGini = gini;
                    bestFeature = featureIndex;
                    bestThreshold = threshold;
                    bestLeft = left;
                    bestRight = right;
                }
            }
        }

        if (bestFeature == -1 || bestLeft == null || bestRight == null || bestLeft.isEmpty() || bestRight.isEmpty()) {
            return new Node(getMajorityLabel(data));
        }

        Node node = new Node(bestFeature, bestThreshold);
        node.left = buildTree(bestLeft, depth + 1);
        node.right = buildTree(bestRight, depth + 1);
        return node;
    }
    //calculate giniindex
    private float giniIndex(List<Patient> left, List<Patient> right) {
        int total = left.size() + right.size();
        if (total == 0) return 0;

        return (left.size() / (float) total) * giniScore(left) +
               (right.size() / (float) total) * giniScore(right);
    }

    //calculate score
    private float giniScore(List<Patient> group) {
        if (group.isEmpty()) return 0;
        int count0 = 0, count1 = 0;
        for (Patient p : group) {
            if (Boolean.TRUE.equals(p.getStroke())) count1++;
            else count0++;
        }
        float p0 = count0 / (float) group.size();
        float p1 = count1 / (float) group.size();
        return 1 - (p0 * p0 + p1 * p1);
    }


    //cannot split further :(
    private int getMajorityLabel(List<Patient> data) {
        int strokeCount = 0;
        for (Patient p : data) {
            if (Boolean.TRUE.equals(p.getStroke())) strokeCount++;
        }
        return strokeCount > data.size() / 2 ? 1 : 0;
    }
}
