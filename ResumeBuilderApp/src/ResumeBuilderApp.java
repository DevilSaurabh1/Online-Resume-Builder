import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class ResumeBuilderApp extends JFrame {
    private JTextField nameField;
    private JTextArea educationTextArea;
    private JTextArea experienceTextArea;
    private JTextArea certificationsTextArea;
    private JTextField languagesField;
    private JTextArea skillsTextArea;
    private JButton generateButton;
    private JButton uploadImageButton;
    private JLabel imageLabel;

    private String imagePath;

    public ResumeBuilderApp() {
        setTitle("Resume Builder");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(9, 2, 10, 10));
        

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel educationLabel = new JLabel("Education:");
        educationTextArea = new JTextArea();
        JLabel experienceLabel = new JLabel("Experience:");
        experienceTextArea = new JTextArea();
        JLabel certificationsLabel = new JLabel("Certifications:");
        certificationsTextArea = new JTextArea();
        JLabel languagesLabel = new JLabel("Languages:");
        languagesField = new JTextField();
        JLabel skillsLabel = new JLabel("Skills:");
        skillsTextArea = new JTextArea();

        generateButton = new JButton("Generate Resume");
        uploadImageButton = new JButton("Upload Image");
        imageLabel = new JLabel();

        uploadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imagePath = selectedFile.getAbsolutePath();
                    ImageIcon imageIcon = new ImageIcon(imagePath);
                    imageLabel.setIcon(imageIcon);

                    // Debug: Print the imagePath to the console
                    System.out.println("Image Path: " + imagePath);
                }
            }
        });

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Resume content
                String name = nameField.getText();
                String education = educationTextArea.getText();
                String experience = experienceTextArea.getText();
                String certifications = certificationsTextArea.getText();
                String languages = languagesField.getText();
                String skills = skillsTextArea.getText();

                // Generate the resume content
                String resumeContent = "Name: " + name + "\n\n"
                        + "Education:\n" + education + "\n\n"
                        + "Experience:\n" + experience + "\n\n"
                        + "Certifications:\n" + certifications + "\n\n"
                        + "Languages: " + languages + "\n\n"
                        + "Skills:\n" + skills;

                // Create a PDF document
                try {
                    Document document = new Document();
                    PdfWriter.getInstance(document, new FileOutputStream("resume.pdf"));
                    document.open();

                    // Set a larger font size
                    Font font = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.NORMAL);

                    // Add image to PDF
                    if (imagePath != null) {
                        com.itextpdf.text.Image image = com.itextpdf.text.Image.getInstance(imagePath);
                        image.scaleAbsolute(100, 100);
                        document.add(image);
                    } else {
                        System.out.println("No image selected."); // Debug
                    }

                    // Add resume content to PDF with the specified font size
                    Paragraph paragraph = new Paragraph(resumeContent, font);
                    document.add(paragraph);

                    // Close the document
                    document.close();

                    JOptionPane.showMessageDialog(ResumeBuilderApp.this, "Resume saved as resume.pdf", "Resume Exported", JOptionPane.INFORMATION_MESSAGE);
                } catch (DocumentException | FileNotFoundException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ResumeBuilderApp.this, "Error exporting resume to PDF", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(ResumeBuilderApp.this, "Error adding image to PDF", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(nameLabel);
        add(nameField);
        add(educationLabel);
        add(educationTextArea);
        add(experienceLabel);
        add(experienceTextArea);
        add(certificationsLabel);
        add(certificationsTextArea);
        add(languagesLabel);
        add(languagesField);
        add(skillsLabel);
        add(skillsTextArea);
        add(uploadImageButton);
        add(imageLabel);
        add(new JLabel()); // Empty cell for spacing
        add(generateButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ResumeBuilderApp().setVisible(true);
            }
        });
    }
}
