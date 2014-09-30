import java.awt.Color;
import java.util.ArrayList;

public class Mouth extends Organelle {
    
    public Mouth(double x, double y, int activationImpulses) {
        super(x, y, activationImpulses);
        
        setDefaultChangeInEnergy(0);
        setChangeInEnergyOnActivation(10);
        
        setColor(new Color(255, 100, 100));
    }
    
    public Mouth clone() {
        return new Mouth(getPosition().x, getPosition().y,
                getActivationImpulses());
    }
    
    @Override
    public void activate() {
        super.activate();
        objectsInContact.clear();
        setColor(Color.RED);
    }
    
    @Override
    public void setIsTouching(Object object) {
        objectsInContact.add(object);
    }
    
    @Override
    public void update() {
        for (Object obj : objectsInContact) {
            if (obj instanceof Chloroplast) {
                Chloroplast c = (Chloroplast) obj;
                c.setDefaultChangeInEnergy(-5);
                c.setChangeInEnergyOnActivation(0);
                setChangeInEnergyOnActivation(1);
                activate();
                return;
            }
            if (obj instanceof Organelle) {
                Organelle o = (Organelle) obj;
                o.setDefaultChangeInEnergy(-5);
                o.setChangeInEnergyOnActivation(-5);
                setChangeInEnergyOnActivation(0.1);
                activate();
                return;
            }
        }
        
        setColor(Color.PINK);
    }
    
    
}
