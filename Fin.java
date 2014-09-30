import java.awt.geom.Rectangle2D;
import java.awt.Color;

public class Fin extends Organelle {
        
        private Color defaultColor = new Color(0, 0, 255), activeColor = new Color(0, 200, 255);
        
        public Fin(double x, double y, int activationImpulses,
                        Vector2 defaultChangeInPosition, Vector2 changeInPositionOnActivation) {
                super(x, y, activationImpulses);
                setDefaultChangeInPosition(defaultChangeInPosition);
                setChangeInPositionOnActivation(changeInPositionOnActivation);
                setDefaultChangeInEnergy(-0.1);
                setColor(Color.BLUE);
        }
        
        public Fin clone() {
                Fin fin = new Fin(getPosition().x, getPosition().y, getActivationImpulses(),
                                getDefaultChangeInPosition(), getChangeInPositionOnActivation());
                return fin;
        }
        
        @Override
        public void activate() {
                super.activate();
                setColor(activeColor);
        }
        
        @Override
        public void update() {
                setColor(defaultColor);
                super.update();
        }
        

}