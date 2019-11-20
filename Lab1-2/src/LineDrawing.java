import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class LineDrawing extends JPanel {
    private BufferedImage image = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
    private int prevX, prevY;
    private boolean paintMode;
    private Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public LineDrawing() {
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        setOpaque(true);
        paintMode = false;
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                Graphics gr = image.getGraphics();
                gr.setColor(color);

                if (paintMode) drawBresenhamLine(prevX, prevY, e.getX(), e.getY(), gr);
                prevX = e.getX();
                prevY = e.getY();
                repaint();
                paintMode = true;
            }
        });
    }

    private int sign(int x) {
        return (x > 0) ? 1 : (x < 0) ? -1 : 0;
    }

    private void drawBresenhamLine(int xstart, int ystart, int xend, int yend, Graphics g) {
        /*
          xstart, ystart - начало;
          xend, yend - конец;
          "g.drawLine (x, y, x, y);" используем в качестве "setPixel (x, y);"
         */

        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;

        dx = xend - xstart;//проекция на ось икс
        dy = yend - ystart;//проекция на ось игрек

        incx = sign(dx);
//        Определяем, в какую сторону нужно будет сдвигаться. Если dx < 0, т.е. отрезок идёт
//        справа налево по иксу, то incx будет равен -1.
//          Это будет использоваться в цикле постороения.
        incy = sign(dy);

//         Аналогично. Если рисуем отрезок снизу вверх -
//         это будет отрицательный сдвиг для y (иначе - положительный).


        if (dx < 0) dx = -dx;//далее мы будем сравнивать: "if (dx < dy)"
        if (dy < 0) dy = -dy;//поэтому необходимо сделать dx = |dx|; dy = |dy|
        //эти две строчки можно записать и так: dx = Math.abs(dx); dy = Math.abs(dy);

        if (dx > dy) {
            //определяем наклон отрезка:


//            Если dx > dy, то значит отрезок "вытянут" вдоль оси икс, т.е. он скорее длинный, чем высокий.
//            Значит в цикле нужно будет идти по икс (строчка el = dx;), значит "протягивать" прямую по иксу
//            надо в соответствии с тем, слева направо и справа налево она идёт (pdx = incx;), при этом
//            по y сдвиг такой отсутствует.

            pdx = incx;
            pdy = 0;
            es = dy;
            el = dx;
        } else {//случай, когда прямая скорее "высокая", чем длинная, т.е. вытянута по оси y

            pdx = 0;
            pdy = incy;
            es = dx;
            el = dy;//тогда в цикле будем двигаться по y
        }

        x = xstart;
        y = ystart;
        err = el / 2;
        g.drawLine(x, y, x, y);//ставим первую точку
        //все последующие точки возможно надо сдвигать, поэтому первую ставим вне цикла

        for (int t = 0; t < el; t++) {//идём по всем точкам, начиная со второй и до последней

            err -= es;
            if (err < 0) {
                err += el;
                x += incx;//сдвинуть прямую (сместить вверх или вниз, если цикл проходит по иксам)
                y += incy;//или сместить влево-вправо, если цикл проходит по y
            } else {
                x += pdx;//продолжить тянуть прямую дальше, т.е. сдвинуть влево или вправо, если
                y += pdy;//цикл идёт по иксу; сдвинуть вверх или вниз, если по y
            }

            g.drawLine(x, y, x, y);
        }
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        g.drawImage(image, 0, 0, this);
    }
}
