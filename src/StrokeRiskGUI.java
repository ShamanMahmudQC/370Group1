import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class StrokeRiskGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Stroke Risk Advisor");
        frame.setSize(450, 900);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        //ask user to train tree with data.csv again (incase user inputs new data or wants new trees)
        int choice = JOptionPane.showConfirmDialog(null,
            "Do you want to retrain the tree? This may take time.",
            "Retrain Tree?",
            JOptionPane.YES_NO_OPTION
        );

        PredictionEngine results;
        if (choice == JOptionPane.YES_OPTION) {
            results = new PredictionEngine(true);
        } else {
            results = new PredictionEngine(false);
        }

        // ui stuff
        String[] labels = {
                "Age:", "Gender:", "Glucose Level:", "BMI:",
                "Smoking Status:", "Married:", "Residence Type:",
                "Work Type:", "Hypertension:", "Heart Disease:", "Filter Nearest Patients?"
        };

        int y = 20;

        JTextField ageField = new JTextField();
        JTextField glucoseField = new JTextField();
        JTextField bmiField = new JTextField();
        JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other", "Unknown"});
        JComboBox<String> smokeBox = new JComboBox<>(new String[]{"Never Smoked", "Formerly Smoked", "Smokes", "Unknown"});
        JComboBox<String> marriedBox = new JComboBox<>(new String[]{"Yes", "No", "Unknown"});
        JComboBox<String> residenceBox = new JComboBox<>(new String[]{"Urban", "Rural", "Unknown"});
        JComboBox<String> workBox = new JComboBox<>(new String[]{"Private", "Self-employed", "Govt_job", "Children", "Never_worked", "Unknown"});
        JCheckBox hypertensionBox = new JCheckBox();
        JCheckBox heartBox = new JCheckBox();
        JCheckBox filterCheckBox = new JCheckBox();

        JComponent[] fields = {
                ageField, genderBox, glucoseField, bmiField,
                smokeBox, marriedBox, residenceBox,
                workBox, hypertensionBox, heartBox, filterCheckBox
        };

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setBounds(20, y, 150, 25);
            fields[i].setBounds(180, y, 200, 25);
            frame.add(label);
            frame.add(fields[i]);
            y += 30;
        }

        String[] similarLabels = {
            "Hypertension:", "Heart Disease:", "Married:",
            "Smokes:", "Residence:", "Work:"
        };

        JCheckBox similarHypertensionBox = new JCheckBox();
        JCheckBox similarHeartBox = new JCheckBox();
        JComboBox<String> similarSmokesBox = new JComboBox<>(new String[]{"Never Smoked", "Formerly Smoked", "Smokes", "Unknown"});
        JComboBox<String> similarMarriedBox = new JComboBox<>(new String[]{"Yes", "No", "Unknown"});
        JComboBox<String> similarResidenceBox = new JComboBox<>(new String[]{"Urban", "Rural", "Unknown"});
        JComboBox<String> similarWorkBox = new JComboBox<>(new String[]{"Private", "Self-employed", "Govt_job", "Children", "Never_worked", "Unknown"});

        JComponent[] similarFields = {
            similarHypertensionBox,
            similarHeartBox,
            similarMarriedBox,
            similarSmokesBox,
            similarResidenceBox,
            similarWorkBox
        };

        JLabel similarTitle = new JLabel("Nearest patient should have:");
        similarTitle.setBounds(20, y, 250, 25);
        frame.add(similarTitle);
        y += 30;

        for (int i = 0; i < similarLabels.length; i++) {
            JLabel label = new JLabel(similarLabels[i]);
            label.setBounds(20, y, 100, 25);
            frame.add(label);

            JComponent field = similarFields[i];
            field.setBounds(125, y, 200, 25); 
            frame.add(field);
            y += 30;
        }

        JButton submit = new JButton("Submit");
        submit.setBounds(150, y, 120, 30);
        frame.add(submit);
        y += 10;

        JTextArea output = new JTextArea();
        output.setWrapStyleWord(true);
        output.setLineWrap(true);
        output.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(output);
        scrollPane.setBounds(20, y + 50, 390, 200);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        frame.add(scrollPane);

        submit.addActionListener(new ActionListener() {

            //input user input into patient class and apply filters for choosing closest user
            public void actionPerformed(ActionEvent e) {
                String ageStr = ageField.getText().trim();
                String glucoseStr = glucoseField.getText().trim();
                String bmiStr = bmiField.getText().trim();


                try {

                    // extract then store in patient class
                    Integer age = (int) Double.parseDouble(ageStr);
                    String gender = (String) genderBox.getSelectedItem();
                    float glucose = (float) Double.parseDouble(glucoseStr);
                    float bmi = (float) Double.parseDouble(bmiStr);
                    String smoking = (String) smokeBox.getSelectedItem();
                    String married = (String) marriedBox.getSelectedItem();
                    String residence = (String) residenceBox.getSelectedItem();
                    String work = (String) workBox.getSelectedItem();
                    
                    Patient user = new Patient(
                        Character.toUpperCase(gender.charAt(0)),
                        age,
                        hypertensionBox.isSelected(),
                        heartBox.isSelected(),
                        married.equalsIgnoreCase("Unknown") ? null : married.equalsIgnoreCase("Yes") ? true : false,
                        Character.toUpperCase(work.charAt(0)),
                        residence.equalsIgnoreCase("Unknown") ? null : Character.toUpperCase(residence.charAt(0)),
                        glucose, bmi,
                        Character.toUpperCase(smoking.charAt(0)),
                        null
                    );

                    //filter, used to filter most similar patients
                    String similarSmoking = (String) similarSmokesBox.getSelectedItem();
                    String similarMarried = (String) similarMarriedBox.getSelectedItem();
                    String similarResidence = (String) similarResidenceBox.getSelectedItem();
                    String similarWork = (String) similarWorkBox.getSelectedItem();
                    
                    Patient filterBy = new Patient(
                        null,
                        null,
                        similarHypertensionBox.isSelected(),
                        similarHeartBox.isSelected(),
                        similarMarried.equalsIgnoreCase("Unknown") ? null : similarMarried.equalsIgnoreCase("Yes") ? true : false,
                        Character.toUpperCase(similarWork.charAt(0)),
                        similarResidence.equalsIgnoreCase("Unknown") ? null : Character.toUpperCase(similarResidence.charAt(0)),
                        null, null,
                        Character.toUpperCase(similarSmoking.charAt(0)),
                        null
                    );

                    //comparison engine uses the filter so give the filter patient to it
                    ComparisonEngine.wantedAttributes = filterBy;
                    ComparisonEngine.filterPatients = filterCheckBox.isSelected();

                    output.setText("Processing, please wait...");
                    new Thread(() -> {
                        String result = PredictionEngine.predictAndCompare(user);
                        SwingUtilities.invokeLater(() -> output.setText(result));
                    }).start();

                } catch (NumberFormatException ex) {
                    // user puts invalid input -> if value empty -> replace with default values (if unknown is chosen for other attributes it is automatically set in patient class)
                    output.setText("Please enter valid numeric values (e.g., 45, 120.5, 24.3) or empty value.");
                        if (ageStr.isEmpty() || glucoseStr.isEmpty() || bmiStr.isEmpty()) {
                            output.setText("Empty values have been normalized with data.");
                        if (ageStr.isEmpty()) {
                            ageField.setText(String.valueOf(results.dataset.defaultValue.getAge()));
                        }
                        if (glucoseStr.isEmpty()) {
                            glucoseField.setText(String.valueOf(results.dataset.defaultValue.getAverageGlucose())); 
                        }
                        if (bmiStr.isEmpty()) {
                            bmiField.setText(String.valueOf(results.dataset.defaultValue.getBMI())); 
                        }
                    }

                }
            }
        
        });
        

        frame.setVisible(true);
        
    }
}
