import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public abstract class Organelle extends DrawableEntity {
        
        public static double WIDTH = 7, HEIGHT = 7;
        
        // Negative if net consumer of energy
        private double changeInEnergyOnActivation = 0,
                        defaultChangeInEnergy = 0,
                        changeInEnergy = 0;
        
        private Vector2 changeInPositionOnActivation = new Vector2(),
                        defaultChangeInPosition = new Vector2(),
                        changeInPosition = new Vector2();
        
        private int impulses = 0;
        private int activationImpulses = 1; // (# required to activate)
        
        // Contacts are usually neighboring organelles
        public ArrayList<Organelle> contacts = new ArrayList<Organelle>();
        
        // List of foreign objects organelle is overlapping
        public ArrayList<Object> objectsInContact = new ArrayList<Object>();
        
        public Organelle(double x, double y, int activationImpulses) {
                super(x, y);
                this.activationImpulses = activationImpulses;
                Rectangle2D.Double shape = new Rectangle2D.Double(x, y, WIDTH, HEIGHT);
                setShape(shape);
        }
        
        public abstract Organelle clone();
        
        public int getActivationImpulses() { return activationImpulses; }
        
        public void setDefaultChangeInEnergy(double change) {
                defaultChangeInEnergy = change;
        }
        
        public void setChangeInEnergyOnActivation(double change) {
                changeInEnergyOnActivation = change;
        }
        
        public double getChangeInEnergyOnActivation() {
                return changeInEnergyOnActivation;
        }
        
        public void setChangeInEnergy(double change) { changeInEnergy = change; }
        
        public double getChangeInEnergy() {
                double change = changeInEnergy;
                changeInEnergy = defaultChangeInEnergy;
                return change;
        }
        
        public void setDefaultChangeInPosition(Vector2 change) {
                defaultChangeInPosition = change;
        }
        
        public Vector2 getDefaultChangeInPosition() {
                return defaultChangeInPosition;
        }
        
        public void setChangeInPositionOnActivation(Vector2 change) {
                changeInPositionOnActivation = change;
        }
        
        public Vector2 getChangeInPositionOnActivation() {
                return changeInPositionOnActivation;
        }
        
        public void setChangeInPosition(Vector2 change) {
                changeInPosition = change;
        }
        
        public Vector2 getChangeInPosition() {
                Vector2 change = changeInPosition;
                changeInPosition = defaultChangeInPosition;
                return change;
        }
        
        public void setIsTouching(Object object) {
                objectsInContact.clear();
                if (object != null)
                        objectsInContact.add(object);
        }
        
        public boolean touchingSomething() {
                return objectsInContact.size() > 0;
        }
        
        public boolean canActivate() {
                return impulses >= activationImpulses;
        }
        
        public void receiveImpulse() {
                impulses++;
        }
        
        public void receiveImpulse(Organelle sender) {
                receiveImpulse();
        }
        
        public void activate() {
                changeInEnergy = changeInEnergyOnActivation;
                changeInPosition = changeInPositionOnActivation;
                impulses = 0;
        }
        
        @Override
        public boolean canBeDeleted() {
                return false;
        }
        
        @Override
        public void update() {
                if (canActivate()) {
                        activate();
                }
        }
}