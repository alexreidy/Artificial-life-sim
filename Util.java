
public class Util {
    
    public static double rin(double x) {
        return x * Math.random();
    }
    
    public static double rsign(double x) {
        return rin(1) > 0.5 ? x : -x;
    }
    
}
