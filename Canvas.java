import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.Map;
import java.util.LinkedHashMap;

public class Canvas extends JPanel {

    private Map<Object, Drawable> drawables = new LinkedHashMap<Object, Drawable>();
    
    private JFrame window;
    
    public Canvas(int width, int height) {
        super();
        setSize(width, height);
    }
    
    public JFrame displayInWindow(String title) {
        if (window != null) return window;
        
        window = new JFrame(title);
        window.setSize(getWidth(), getHeight());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.add(this);
        window.setVisible(true);
        
        return window;
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        synchronized(this) {
            for (Drawable drawable : drawables.values()) {
                g2.setColor(drawable.getColor());
                g2.fill(drawable.getShape());
            }
        }
    }
    
    public synchronized void add(Drawable drawable) {
        drawables.put(drawable, drawable);
    }
    
    public synchronized void remove(Drawable drawable) {
        drawables.remove(drawable);
    }
    
    public void render() { repaint(); }
    
    public boolean contains(Vector2 point) {
        return point.x > 0 && point.x < getWidth() &&
            point.y > 0 && point.y < getHeight();
    }
    
}