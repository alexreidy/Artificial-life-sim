import java.util.ArrayList;

// TODO
// - Prevent organelle overlap in organism
// - ******Allow for mutations to *remove* organelles too. May be tricky. (DONE.. I think)
// - Make highly configurable from a text file
// - Make better use of inheritance... default & active colors, for example.
// - Allow change in organelle parameters during mutation. ******

public class Main {
        
        public static int WIDTH = 900, HEIGHT = 750;

        Canvas canvas = new Canvas("Simulation to Encourage the Evolution of Complex Artificial Life", WIDTH, HEIGHT);
        
        ArrayList<Organism> organisms = new ArrayList<Organism>();
        
        public static void main(String[] args) {
                new Main().begin();
        }
        
        public void addOrganismToCanvas(Organism organism) {
                for (Organelle organelle : organism.organelles) {
                        canvas.addEntity(organelle);
                }
        }
        
        public void removeOrganismFromCanvas(Organism organism) {
                for (Organelle organelle : organism.organelles) {
                        canvas.removeEntity(organelle);
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
                createOrganisms(100, 50);
                
                while (true) {
                        ArrayList<Organism> children = new ArrayList<Organism>(),
                                        deadList = new ArrayList<Organism>();
                        
                        if (Util.rin(1) > 0.95) createOrganisms(3, (int) Util.rin(5));
                        
                        for (Organism o : organisms) {
                                o.update();
                                
                                for (Organelle organelle : o.organelles) {
                                        if (!canvas.isOnScreen(organelle.getPosition())) {
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
                                
                                if (o.organelles.size() > 0 && !canvas.isOnScreen(o.organelles.get(0).getPosition())) {
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
