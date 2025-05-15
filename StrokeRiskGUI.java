import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class StrokeRiskGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Stroke Risk Advisor");
        frame.setSize(450, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        String[] labels = {"Age:", "Glucose Level:", "BMI:", "Smoking Status:", "Married:", "Residence Type:", "Work Type:"};
        int y = 20;

        JTextField ageField = new JTextField();
        JTextField glucoseField = new JTextField();
        JTextField bmiField = new JTextField();
        JComboBox<String> smokeBox = new JComboBox<>(new String[]{"never smoked", "formerly smoked", "smokes"});
        JComboBox<String> marriedBox = new JComboBox<>(new String[]{"Yes", "No"});
        JComboBox<String> residenceBox = new JComboBox<>(new String[]{"Urban", "Rural"});
        JComboBox<String> workBox = new JComboBox<>(new String[]{"Private", "Self-employed", "Govt_job", "Children", "Never_worked"});

        JComponent[] fields = {ageField, glucoseField, bmiField, smokeBox, marriedBox, residenceBox, workBox};

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setBounds(20, y, 150, 25);
            fields[i].setBounds(180, y, 200, 25);
            frame.add(label);
            frame.add(fields[i]);
            y += 40;
        }

        JButton submit = new JButton("Submit");
        submit.setBounds(150, y, 120, 30);
        frame.add(submit);

        JTextArea output = new JTextArea();
        output.setBounds(20, y + 50, 390, 200);
        output.setWrapStyleWord(true);
        output.setLineWrap(true);
        output.setEditable(false);
        frame.add(output);

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String ageStr = ageField.getText().trim();
                    String glucoseStr = glucoseField.getText().trim();
                    String bmiStr = bmiField.getText().trim();

                    if (ageStr.isEmpty() || glucoseStr.isEmpty() || bmiStr.isEmpty()) {
                        output.setText("Please fill in all numeric fields.");
                        return;
                    }

                    int age = (int) Double.parseDouble(ageStr);
                    float glucose = (float) Double.parseDouble(glucoseStr);
                    float bmi = (float) Double.parseDouble(bmiStr);

                    String smoking = (String) smokeBox.getSelectedItem();
                    String married = (String) marriedBox.getSelectedItem();
                    String residence = (String) residenceBox.getSelectedItem();
                    String work = (String) workBox.getSelectedItem();

                    Map<String, String> inputData = new HashMap<>();
                    inputData.put("age", Integer.toString(age));
                    inputData.put("avg_glucose_level", Float.toString(glucose));
                    inputData.put("bmi", Float.toString(bmi));
                    inputData.put("smoking_status", smoking);
                    inputData.put("ever_married", married);
                    inputData.put("Residence_type", residence);
                    inputData.put("work_type", work);

                    String result = PredictionEngine.predictAndCompare(inputData);
                    output.setText(result);

                } catch (NumberFormatException ex) {
                    output.setText("Please enter valid numeric values (e.g., 45, 120.5, 24.3).");
                }
            }
        });

        frame.setVisible(true);
    }
}
