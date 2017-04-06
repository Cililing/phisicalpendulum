package PhysicalPendulum;

/**
 * A simple representation of double point (x, y)
 */
public class Point {

    // <editor-fold defaultstate="collapsed" desc="Private Variables">
    private double x;
    private double y;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Public Interface">
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this.x = 0;
        this.y = 0;
    }

    public void setNew(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("[%1$.1f, %1$.1f]", x, y);

    }
    //</editor-fold>
}
