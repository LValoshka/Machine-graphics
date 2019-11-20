import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class CircleDrawing extends JPanel {
    private BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
    private int prevX, prevY;
    private boolean paintMode;
    private Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public CircleDrawing() {
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        setOpaque(true);
        paintMode = false;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                Graphics gr = image.getGraphics();
                gr.setColor(color);

                if (paintMode) drawBresenhamCircle(prevX, prevY, e.getX(), e.getY(), gr);
                prevX = e.getX();
                prevY = e.getY();
                repaint();
                paintMode = true;
            }
        });
    }

    private void drawBresenhamCircle(int xstart, int ystart, int xend, int yend, Graphics g) {
        int sx = 0;
        double r = (Math.sqrt(Math.pow(Math.abs(xend - xstart), 2) + Math.pow(Math.abs(yend - ystart), 2)));
        int sy = (int) r;
        int d = (int) (3 - 2 * r);
        while (sx <= sy) {
            g.drawLine(xstart + sx, ystart - sy, xstart + sx, ystart - sy);
            g.drawLine(xstart + sx, ystart + sy, xstart + sx, ystart + sy);
            g.drawLine(xstart - sx, ystart - sy, xstart - sx, ystart - sy);
            g.drawLine(xstart - sx, ystart + sy, xstart - sx, ystart + sy);

            g.drawLine(xstart + sy, ystart + sx, xstart + sy, ystart + sx);
            g.drawLine(xstart - sy, ystart + sx, xstart - sy, ystart + sx);
            g.drawLine(xstart + sy, ystart - sx, xstart + sy, ystart - sx);
            g.drawLine(xstart - sy, ystart - sx, xstart - sy, ystart - sx);

            if (d < 0) {
                d = d + 4 * sx + 6;
            } else {
                d = d + 4 * (sx - sy) + 10;
                sy = sy - 1;
            }
            sx += 1;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        g.drawImage(image, 0, 0, this);
    }
}
