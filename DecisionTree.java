import java.util.*;

public class DecisionTree {
    private TreeNode root;
    private static final int MAX_DEPTH = 5;
    private static final int MIN_SIZE = 10;

    public void train(List<Patient> patients) {
        this.root = buildTree(patients, 0);
    }

    public int predict(Patient input) {
        return predictFromTree(root, input);
    }

    private int predictFromTree(TreeNode node, Patient input) {
        if (node.isLeaf()) return node.label;

        float val = node.getFeatureValue(input);
        if (val < node.threshold) {
            return predictFromTree(node.left, input);
        } else {
            return predictFromTree(node.right, input);
        }
    }

    private TreeNode buildTree(List<Patient> data, int depth) {
        if (data.size() < MIN_SIZE || depth >= MAX_DEPTH) {
            return new TreeNode(getMajorityLabel(data));
        }

        Split bestSplit = findBestSplit(data);
        if (bestSplit == null || bestSplit.gini >= 1.0) {
            return new TreeNode(getMajorityLabel(data));
        }

        TreeNode node = new TreeNode(bestSplit.featureIndex, bestSplit.threshold);
        node.left = buildTree(bestSplit.left, depth + 1);
        node.right = buildTree(bestSplit.right, depth + 1);
        return node;
    }

    private Split findBestSplit(List<Patient> data) {
        float bestGini = Float.MAX_VALUE;
        Split best = null;

        for (int i = 0; i < 3; i++) { // age, glucose, bmi
            List<Float> values = new ArrayList<>();
            for (Patient p : data) {
                float val = i == 0 ? p.getAge() : i == 1 ? p.getAverageGlucose() : p.getBMI();
                values.add(val);
            }

            for (float threshold : values) {
                List<Patient> left = new ArrayList<>();
                List<Patient> right = new ArrayList<>();
                for (Patient p : data) {
                    float val = i == 0 ? p.getAge() : i == 1 ? p.getAverageGlucose() : p.getBMI();
                    if (val < threshold) {
                        left.add(p);
                    } else {
                        right.add(p);
                    }
                }
                float gini = giniIndex(left, right);
                if (gini < bestGini) {
                    bestGini = gini;
                    best = new Split(i, threshold, left, right, gini);
                }
            }
        }
        return best;
    }

    private float giniIndex(List<Patient> left, List<Patient> right) {
        int total = left.size() + right.size();
        return (left.size() / (float) total) * giniScore(left) +
                (right.size() / (float) total) * giniScore(right);
    }

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

    private int getMajorityLabel(List<Patient> data) {
        int strokeCount = 0;
        for (Patient p : data) {
            if (Boolean.TRUE.equals(p.getStroke())) strokeCount++;
        }
        return strokeCount > data.size() / 2 ? 1 : 0;
    }

    static class TreeNode {
        int featureIndex;
        float threshold;
        int label;
        TreeNode left, right;

        TreeNode(int label) {
            this.label = label;
        }

        TreeNode(int featureIndex, float threshold) {
            this.featureIndex = featureIndex;
            this.threshold = threshold;
        }

        boolean isLeaf() {
            return left == null && right == null;
        }

        float getFeatureValue(Patient p) {
            return featureIndex == 0 ? p.getAge() : featureIndex == 1 ? p.getAverageGlucose() : p.getBMI();
        }
    }

    static class Split {
        int featureIndex;
        float threshold;
        List<Patient> left, right;
        float gini;

        Split(int featureIndex, float threshold, List<Patient> left, List<Patient> right, float gini) {
            this.featureIndex = featureIndex;
            this.threshold = threshold;
            this.left = left;
            this.right = right;
            this.gini = gini;
        }
    }
}
