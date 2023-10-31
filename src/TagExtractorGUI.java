import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.Map;
import java.util.Set;
import java.nio.file.*;
import java.util.HashSet;


public class TagExtractorGUI {
    private JFrame frame;
    private JTextArea textArea;
    private JButton openTextButton;
    private JButton openStopWordsButton;
    private JButton saveButton;
    private TagExtractor tagExtractor;

    public TagExtractorGUI() {
        frame = new JFrame("Tag Extractor");
        textArea = new JTextArea();
        openTextButton = new JButton("Open Text File");
        openStopWordsButton = new JButton("Open Stopwords File");
        saveButton = new JButton("Save Tags");

        tagExtractor = new TagExtractor();

        openTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    try {
                        String content = new String(Files.readAllBytes(Paths.get(filePath)));
                        textArea.setText(content);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        openStopWordsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                    try {
                        Set<String> newStopWords = new HashSet<>();
                        BufferedReader reader = new BufferedReader(new FileReader(filePath));
                        String line;

                        while ((line = reader.readLine()) != null) {
                            newStopWords.add(line.trim().toLowerCase());
                        }

                        reader.close();

                        tagExtractor.setStopWords(newStopWords);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String content = textArea.getText();
                tagExtractor.processText(content);
                String tagContent = generateTagContent();
                FileManager.saveFile(tagContent);
            }
        });

        frame.setLayout(new FlowLayout());
        frame.add(openStopWordsButton);
        frame.add(openTextButton);
        frame.add(textArea);
        frame.add(saveButton);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private String generateTagContent() {
        StringBuilder content = new StringBuilder();
        Map<String, Integer> tagFrequencyMap = tagExtractor.getTagFrequencyMap();
        for (Map.Entry<String, Integer> entry : tagFrequencyMap.entrySet()) {
            content.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return content.toString();
    }

    public static void main(String[] args) {
        new TagExtractorGUI();
    }
}
