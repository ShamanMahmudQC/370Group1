
import java.io.Serializable;

public class Node implements Serializable {
    int featureIndex;
    float threshold;
    int label;  
    Node left;
    Node right;

    // leaf
    public Node(int label) {
        this.label = label;
        this.left = null;
        this.right = null;
    }

    // parent
    public Node(int featureIndex, float threshold) {
        this.featureIndex = featureIndex;
        this.threshold = threshold;
        this.left = null;
        this.right = null;
    }

    public boolean isLeaf() {
        return (left == null && right == null);
    }

    public float getFeatureValue(Patient p) {
        float[] features = p.getFeatureVector(
            ComparisonEngine.maxValues[0],
            ComparisonEngine.maxValues[1],
            ComparisonEngine.maxValues[2]
        );
        return features[featureIndex];
    }
}
