
// DrawableEntity is the base class for visual entities in the simulation.

import javax.swing.*;
import java.awt.geom.RectangularShape;
import java.awt.*;

public abstract class DrawableEntity {
        
        private double x, y;
        private double direction; // degrees
        
        private RectangularShape shape;
        private Color color;
        
        public DrawableEntity(double x, double y) {
                this.x = x;
                this.y = y;
        }
        
        public DrawableEntity(double x, double y, RectangularShape shape, Color color) {
                this(x, y);
                this.color = color;
                this.shape = shape;
        }
        
        public RectangularShape getShape() { return shape; }
        
        public void setShape(RectangularShape shape) {
                if (this.shape == null) this.shape = shape;
        }
        
        public void setColor(Color color) { this.color = color; }
        public Color getColor() { return color; }
        
        public void setDirection(double degrees) { direction = degrees; }
        
        // Rotates direction, not shape
        public void rotate(double degrees) { direction += degrees; }
        
        private void updateShape() {
                if (shape != null) {
                        shape.setFrame(x, y, shape.getWidth(), shape.getHeight());
                }
        }
        
        public void setPosition(double x, double y) {
                this.x = x; this.y = y;
                updateShape();
        }
        
        public Vector2 getPosition() { return new Vector2(x, y); }
        
        public void move(double xOffset, double yOffset) {
                setPosition(x + xOffset, y + yOffset);
        }
        
        // Offsets entity by distance according to direction
        public void move(double distance) {
                double xOffset = Math.cos(Math.toRadians(direction)) * distance;
                double yOffset = Math.sin(Math.toRadians(direction)) * distance;
                move(xOffset, yOffset);
        }
        
        public abstract boolean canBeDeleted();
        
        public abstract void update();
        
}
