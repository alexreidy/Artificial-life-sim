import java.awt.Color;
import java.util.ArrayList;

public class Neuron extends Organelle { // Activated by impulses and physical contact
    
    private Color defaultColor = new Color(255, 140, 0),
            activeColor = Color.YELLOW;
    
    ArrayList<Organelle> impulseSenders = new ArrayList<Organelle>();
    
    public Neuron(double x, double y, int activationImpulses) {
        super(x, y, activationImpulses);
        setColor(defaultColor);
    }
    
    public Neuron clone() {
        return new Neuron(getPosition().x, getPosition().y,
                getActivationImpulses() + (int) Util.rsign(Util.rin(1) > 0.95 ? 1 : 0));
    }
    
    @Override
    public void activate() {
        super.activate();
        setColor(activeColor);
        setIsTouching(null);
        for (Organelle neighbor : contacts) {
            if (!impulseSenders.contains(neighbor))
                neighbor.receiveImpulse(this);
        }
        impulseSenders.clear();
    }
    
    @Override
    public void receiveImpulse(Organelle organelle) {
        super.receiveImpulse();
        impulseSenders.add(organelle);
    }
    
    @Override
    public void update() {
        if (canActivate() || touchingSomething()) {
            activate();
        } else {
            setColor(defaultColor);
        }
    }
}