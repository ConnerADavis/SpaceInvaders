import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class SImissle extends SIthing {
    
    public SImissle(int x, int y)
    {
        super.setX(x);
        super.setY(y);
        super.setWidth(2);
        super.setHeight(10);
    }

    @Override
    public void paint(Graphics2D g2) {
         g2.setColor(Color.WHITE);
         g2.fill(new Rectangle2D.Double(super.getX(), super.getY(), super.getWidth(), super.getHeight()));
    }

}
