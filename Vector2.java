public class Vector2 {
        
        public double x, y;
        
        public Vector2(double x, double y) {
                this.x = x;
                this.y = y;
        }
        
        public Vector2() {
                this(0, 0);
        }
        
        @Override
        public boolean equals(Object other) {
                if (!(other instanceof Vector2)) return false;
                if (other == this) return true;
                Vector2 otherVector = (Vector2) other;
                return otherVector.x == x && otherVector.y == y;
        }
        
        public Vector2 copy() {
                return new Vector2(x, y);
        }
}
