import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Organism {
    
    private int age = 0;
    private boolean canReproduce = true;
    
    public ArrayList<Organelle> organelles = new ArrayList<Organelle>();
    
    private Vector2 position = new Vector2();
    
    private final int NUM_ORGAN_TYPES = 4;
    private int maxOrganellesPerOrganism = 150;
    
    public double energy = 10;
    
    public int lifeSpan = 0;
    
    public Organism parent;
    
    public boolean adjacent(Organelle a, Organelle b) {
        if (b.getPosition().x == a.getPosition().x + Organelle.WIDTH && b.getPosition().y == a.getPosition().y)
            return true;
        if (b.getPosition().x == a.getPosition().x - Organelle.WIDTH && b.getPosition().y == a.getPosition().y)
            return true;
        if (b.getPosition().y == a.getPosition().y - Organelle.HEIGHT && b.getPosition().x == a.getPosition().x)
            return true;
        if (b.getPosition().y == a.getPosition().y + Organelle.HEIGHT && b.getPosition().x == a.getPosition().x)
            return true;
        return false;
    }
    
    private Vector2 randomlySelectAvailableSpotAdjacentTo(Organelle organelle) {
        if (organelle.contacts.size() >= 4)
            // No adjacent spots available
            return null;
        
        Vector2[] positions = new Vector2[] {
            new Vector2(organelle.getPosition().x + Organelle.WIDTH, organelle.getPosition().y),  // RIGHT  (i=0)
            new Vector2(organelle.getPosition().x - Organelle.WIDTH, organelle.getPosition().y),  // LEFT   (i=1)
            new Vector2(organelle.getPosition().x, organelle.getPosition().y - Organelle.HEIGHT), // TOP    (i=2)
            new Vector2(organelle.getPosition().x, organelle.getPosition().y + Organelle.HEIGHT)  // BOTTOM (i=3)
        };
        
        for (Organelle neighbor : organelle.contacts) {
            if (positions[0] != null && neighbor.getPosition().x == positions[0].x && neighbor.getPosition().y == positions[0].y)
                positions[0] = null;
            if (positions[1] != null && neighbor.getPosition().x == positions[1].x && neighbor.getPosition().y == positions[1].y)
                positions[1] = null;
            if (positions[2] != null && neighbor.getPosition().y == positions[2].y && neighbor.getPosition().x == positions[2].x)
                positions[2] = null;
            if (positions[3] != null && neighbor.getPosition().y == positions[3].y && neighbor.getPosition().x == positions[3].x)
                positions[3] = null;
        }
        
        Vector2 selected = null;
        do { selected = positions[(int) Util.rin(4)]; }
        while(selected == null);
        
        return selected;
    }
    
    public Organelle createRandomOrganelle(Vector2 pos) {
        double p = 1.0 / NUM_ORGAN_TYPES;
        
        double r = Util.rin(1);
        
        if (r > 1 - p) {
            return new Neuron(pos.x, pos.y, (int) Util.rin(5) + 1);
        } else if (r > 1 - p * 2) {
            return new Fin(
                pos.x, pos.y, (int) Util.rin(5),
                Util.rin(1) > 0.5 ? new Vector2(Util.rsign(Util.rin(3)), Util.rsign(Util.rin(3))) : new Vector2(),
                new Vector2(Util.rsign(Util.rin(5)), Util.rsign(Util.rin(5)))
            );
        } else if (r > 1 - p * 3) {
            return new Chloroplast(pos.x, pos.y, (int) Util.rin(5));
        } else {
            return new Mouth(pos.x, pos.y, 0);
        }
    }
    
    public Organelle getRandomOrganelle() {
        return organelles.get((int) Util.rin(organelles.size() - 1));
    }
    
    public Organism(Vector2 position, int maxOrganelles) {
        this.position = position;
        
        Organelle organelle;
        for (int i = 0; i < maxOrganelles; i++) {
            if (i == 0 || Util.rin(1) > 0.5) {
                organelles.add(organelle = createRandomOrganelle(position));
                
                if (organelles.size() > 1) {
                    Organelle randomOrganelle;
                    while (true) {
                        randomOrganelle = getRandomOrganelle();
                        if (randomOrganelle.contacts.size() == 4)
                            continue;
                        
                        Vector2 pos = randomlySelectAvailableSpotAdjacentTo(randomOrganelle);
                        organelle.setPosition(pos.x, pos.y);
                        
                        organelle.contacts.add(randomOrganelle);
                        randomOrganelle.contacts.add(organelle);
                        
                        break;
                    }
                }
                
            }
        }
    }
    
    public Organism() {}
    
    public Organism(Organism parent, Vector2 position) {
        for (Organelle po : parent.organelles) {
            if (Util.rin(1) > 0.97 && po.contacts.size() > 2)
                continue;
            organelles.add(po.clone());
        }
        
        for (int i = 0; i < organelles.size(); i++) {
            if (Util.rin(1) > 0.97) {
                organelles.set(i, createRandomOrganelle(organelles.get(i).getPosition()));
            }
        }
        
        for (Organelle a : organelles) {
            for (Organelle b : organelles) {
                if (b == a) continue;
                if (adjacent(a, b)) {
                    a.contacts.add(b);
                }
            }
        }
        
        setPosition(position);
        
        if (organelles.size() < 1) return;
        
        if (parent.parent != null && Util.rin(1) > 0.8 && organelles.size() < maxOrganellesPerOrganism) {
            
            Organelle mutation = createRandomOrganelle(position);
            Vector2 availablePosition = null;
            while (availablePosition == null) {
                availablePosition = randomlySelectAvailableSpotAdjacentTo(getRandomOrganelle());
            }
            mutation.setPosition(availablePosition.x, availablePosition.y);
            organelles.add(mutation);
        }
        this.parent = parent;
        parent.setEnergy(10);
    }
    
    public void move(double x, double y) {
        for (Organelle o : organelles) {
            o.move(x, y);
        }
    }
    
    public void move(Vector2 offset) {
        move(offset.x, offset.y);
    }
    
    public void setPosition(double x, double y) {
        if (organelles.size() > 0) {
            Vector2 stempos = organelles.get(0).getPosition();
            move(new Vector2(x - stempos.x, y - stempos.y));
        }
    }
    
    public void setPosition(Vector2 pos) {
        setPosition(pos.x, pos.y);
    }
    
    public Vector2 getPosition() {
        if (organelles.size() > 0)
            return organelles.get(0).getPosition();
        return new Vector2();
    }
    
    public void setEnergy(double e) {
        energy = e;
    }
    
    public void update() {
        for (Organelle o : organelles) {
            energy += o.getChangeInEnergy();
        }
        
        if (!isAlive()) return;
        
        for (Organelle org : organelles) {
            org.update();
        }
        
        Vector2 offset = new Vector2();
        
        for (Organelle org : organelles) {
            Vector2 delta = org.getChangeInPosition();
            offset.x += delta.x;
            offset.y += delta.y;
        }
        
        for (Organelle org : organelles) {
            org.move(offset.x, offset.y);
        }
        
        age++;
    }
    
    public boolean checkForContactWith(Organism other) {
        boolean touching = false;
        for (Organelle o : organelles) {
            for (Organelle oo : other.organelles) {
                if (o.getShape().intersects((Rectangle2D) oo.getShape())) {
                    o.setIsTouching(oo);
                    touching = true;
                }
            }
        }
        return touching;
    }
    
    public void sterilize() { canReproduce = false; }
    
    public boolean canReproduce() { return canReproduce && energy > 250; }
    
    public void kill() {
        age = 100000000;
    }
    
    public boolean isAlive() {
        return energy > 0 && age < 15 * organelles.size();
    }
}