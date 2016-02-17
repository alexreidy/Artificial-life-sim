import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

public class Main {
    
    public static int WIDTH = 900, HEIGHT = 750;

    Canvas canvas = new Canvas(WIDTH, HEIGHT);
    
    List<Organism> organisms = new ArrayList<Organism>();
    
    public static void main(String[] args) {
        new Main().begin();
    }
    
    public void addOrganismToCanvas(Organism organism) {
        for (Organelle organelle : organism.organelles) {
            canvas.add(organelle);
        }
    }
    
    public void removeOrganismFromCanvas(Organism organism) {
        for (Organelle organelle : organism.organelles) {
            canvas.remove(organelle);
        }
    }
    
    void createOrganisms(int n, int maxOrganelles) {
        for (int i = 0; i < n; i++) {
            Organism o = new Organism(new Vector2(Util.rin(WIDTH), Util.rin(HEIGHT)), maxOrganelles);
            organisms.add(o);
            addOrganismToCanvas(o);
        }
    }
    
    private void begin() {
        canvas.displayInWindow("Artificial Life Simulation by Alex Reidy");

        createOrganisms(100, 50);
        
        while (true) {
            List<Organism> children = new ArrayList<Organism>(),
                    deadList = new ArrayList<Organism>();
            
            if (Util.rin(1) > 0.95) createOrganisms(3, (int) Util.rin(50));
            
            for (Organism o : organisms) {
                o.update();
                
                for (Organelle organelle : o.organelles) {
                    if (!canvas.contains(organelle.getPosition())) {
                        organelle.setIsTouching(new Object());
                    }
                }
                
                if (!o.isAlive()) {
                    removeOrganismFromCanvas(o);
                    deadList.add(o);
                }
                
                for (Organism oo : organisms) {
                    if (oo == o) continue;
                    o.checkForContactWith(oo);
                }
                
                if (o.organelles.size() > 0 && !canvas.contains(o.organelles.get(0).getPosition())) {
                    o.setPosition(Util.rin(WIDTH), Util.rin(HEIGHT));
                }
                
                if (o.canReproduce()) {
                    children.add(new Organism(o,
                        new Vector2(
                            o.getPosition().x + Util.rsign(Util.rin(100)),
                            o.getPosition().y + Util.rsign(Util.rin(100)))
                        )
                    );
                }
                
            }
            
            for (Organism o : deadList) {
                organisms.remove(o);
            }
            
            for (Organism child : children) {
                organisms.add(child);
                addOrganismToCanvas(child);
            }
            children.clear();
            
            canvas.render();
            
            try { Thread.sleep(0); }
            catch(Exception e) {}
        }
    }

}
