import javax.swing.*;
import java.awt.*;

import static javax.swing.JFrame.*;

public class DrawDemo extends JPanel {
    private LineDrawing lineDrawing = new LineDrawing();
    private CircleDrawing circleDrawing = new CircleDrawing();
    private JFrame frame = new JFrame("Drawing panel");
    private JButton chooseColor, drawLine;
    private Color currentColor;
    private JPanel buttonPanel = new JPanel();
    private ScrollPane scrollPane = new ScrollPane();

    private void initPanel() {
        setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

        frame.setSize(600, 400);
        frame.setVisible(true);
        //  scrollPane.add(lineDrawing);
        scrollPane.add(circleDrawing);
        frame.add(scrollPane);
    }

    private void initButtons() {
        chooseColor = new JButton("Color ");
        currentColor = Color.black;
        chooseColor.addActionListener(e -> {
            Color chosenColor = JColorChooser.showDialog(null, "Choose color", currentColor);
            if (chosenColor != null) {
                circleDrawing.setColor(chosenColor);
                lineDrawing.setColor(chosenColor);
                currentColor = chosenColor;
            }
        });
        buttonPanel.add(chooseColor);

        drawLine = new JButton("Draw line");

        frame.add(buttonPanel, BorderLayout.NORTH);
    }

    private DrawDemo() {
        initPanel();
        initButtons();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DrawDemo());
    }
}

