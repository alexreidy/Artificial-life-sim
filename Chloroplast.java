import java.awt.Color;

public class Chloroplast extends Organelle {
	
	public Chloroplast(double x, double y, int activationImpulses) {
		super(x, y, activationImpulses);
		setDefaultChangeInEnergy(0.5);
		setChangeInEnergyOnActivation(0.8);
		setColor(Color.GREEN);
	}
	
	public Chloroplast clone() {
		return new Chloroplast(getPosition().x, getPosition().y, getActivationImpulses());
	}
	
	@Override
	public void receiveImpulse() {}
	
	@Override
	public void activate() {}
}