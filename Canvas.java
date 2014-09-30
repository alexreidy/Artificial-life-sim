import java.awt.*; import javax.swing.*;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;

public class Canvas {
	
	private int width, height;
	private JFrame frame;
	private Painter painter;
	
	private ArrayList<DrawableEntity> entities = new ArrayList<DrawableEntity>();
	
	private class Painter extends Component {
		@Override
		public void paint(Graphics g) {
			Graphics2D g2D = (Graphics2D) g;
			for (int i = 0; i < entities.size(); i++) {
				DrawableEntity entity = entities.get(i);
				g2D.setColor(entity.getColor());
				g2D.fill(entity.getShape());
			}
		}
		
	}
	
	public Canvas(String title, int width, int height) {
		frame = new JFrame(title);
		frame.setSize(this.width = width, this.height = height);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(painter = new Painter());
		frame.setVisible(true);
	}
	
	public void addEntity(DrawableEntity entity) {
		entities.add(entity);
	}
	
	public void removeEntity(DrawableEntity entity) {
		entities.remove(entity);
	}
	
	public void render() { painter.repaint(); }
	
	public int getHeight() { return height; }
	public int getWidth() { return width; }
	
	public boolean isOnScreen(Vector2 position) {
		return position.x > 0 && position.x < width
			&& position.y > 0 && position.y < height;
	}
	
}