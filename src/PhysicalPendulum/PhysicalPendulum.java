package PhysicalPendulum;

/**
 * Represenation of PhysicalPendulum
 */
public class PhysicalPendulum {

    // <editor-fold defaultstate="collapsed" desc="Introduction">

    // s - displacement, so acceleration = s''
    // -g sin(alpha) = s'' (from second law of motion) [1]

    // When the rod makes an angle alpha with the vertical s of the bob is:
    // s = l * alpha
    // Differentiating this twice with time gives us:
    // s'' = l * alpha''

    // Substituting with [1] gives us:
    // l * alpha'' = -g * sin(alpha)
    // alpha'' = -(g/l) * sin(alpha)

    // Now - it is theoretically possible to calculate the position of bob using only alpha and time.
    // But it would be difficult to program that solution, so:
    // Step 1: calculate the angle alpha at time t
    // Step 2: we have to find method to integrate equation numerically to find alpha from alpha''

    // Java uses (x, y) coorditantes to plot shapes.
    // Therefore we are using alpha to calculate (x,y) coordinates of the centre of the bob at time t.
    // We will be using simple trigonometry.

    // Reference point: (refx, refy) (its sth like (0, 0))
    // (x, y) = (refx + x', refy + y')
    // where x' = l sin(alpha)
    //       y' = l cos(alpha)
    // and l is length of the rod.

    // Final stage is draw the pendulum and show the motion.
    // The pendulum can be drawn by plotting a line from (refx, refy) to the (x, y)
    // and drow a small circle in (x, y).

    // We will be using a recursive loop to:
    // 1) Increase time by deltaTime
    // 2) calculate alpha and (x, y)
    // 3) drow new the pendulum in its new position (clean the screen and drow it again)

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private Variables">
    private double acceleration;
    private double angle; //in radians
    private double angleAcceleration;
    private double angleVelocity;
    private double length; //in pixels
    private Point position;
    private double startAngle;
    private double period;
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Public Interface">
    /**
     * Create a new instance of Pendulum
     * @param acceleration acceleration
     * @param angle angle in radians
     * @param length reducedLenght
     */
    public PhysicalPendulum(double acceleration, double angle, double length) {
        initialize(acceleration, angle, length);
    }

    /**
     * Create a new instance of Pendulum
     * @param acceleration acceleration
     * @param angle angle in radiands
     * @param interia moment of interia
     * @param mass mass
     * @param distance distance from the centre of gravity
     */
    public PhysicalPendulum(double acceleration, double angle, double interia, double mass, double distance) {
        initialize(acceleration, angle, interia, mass, distance);
    }

    /**
     * Create a new instance of Pendulum
     * @param data in order: acceleration, angle, interia, mass, distance
     */
    public PhysicalPendulum(double[] data) {
        assert data.length == 5;
        initialize(data[0], data[1], data[2], data[3], data[4]);
    }

    public Point getPosition() { return position; }

    public double getAngle() { return angle; }

    public double getStartAngle() { return startAngle; }

    public double getLength() { return length; }

    public double getPeriod() { return period; }

    @Override
    public String toString() {

        String stringInfo = "";

        String angleString = String.format("Angle: %1$.1f", angle);
        String positionString = String.format("Position: " + position.toString());
        String angleAccString = String.format("Angle acc: %1$.5f", angleAcceleration);
        String angleVelocityString = String.format("Angle velocity: %1$.1f", angleVelocity);
        String periodInfo = String.format("Period: %1$.2f", period);
        String lengthInfo = String.format("Reduced length: " + length);

        stringInfo += angleString + "\n" +
                positionString + "\n" +
                angleAccString + "\n" +
                angleVelocityString + "\n" +
                lengthInfo + "\n" +
                periodInfo
        ;

        return stringInfo;

        //return String.format("Angle: %1$.1f, position: ", angle) + position.toString();
    }

    /**
     * Update speeds, acceleration from now to now+deltaTime
     * @param deltaTime deltaTime
     */
    public void update(double deltaTime) {
        angleAcceleration = -acceleration / length * Math.sin(angle);
        angleVelocity += angleAcceleration * deltaTime; //because acceleration = d^2 x / dt^2
        angle += angleVelocity * deltaTime;

        //Debugging - log
//        if ((angle > startAngle && angle > 0) || (angle < -startAngle && angle < 0))
//            System.out.println("angle adjusted");

        //adjust aprox
        if (angle > startAngle && angle > 0) angle = startAngle;
        if (angle < -startAngle && angle < 0) angle = -startAngle;

        updatePosition();
    }

    /**
     * Reset fields of objects and set new
     * @param acceleration acc
     * @param angle angle in radiands
     * @param length reduced length
     */
    public void reset(double acceleration, double angle, int length) {
        initialize(acceleration, angle, length);
    }


    /**
     * reset fields
     * @param data in order: acceleration, angle, interia, mass, distance
     */
    public void reset(double[] data) {
        assert data.length == 5;
        initialize(data[0], data[1], data[2], data[3], data[4]);
    }
    //</editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Private Interface">

    /**
     * Initialize object
     * @param acceleration acc
     * @param angle angle in rad
     * @param interia interia
     * @param mass mass
     * @param distance distance
     */
    private void initialize(double acceleration, double angle, double interia, double mass, double distance) {
        this.acceleration  = acceleration;
        this.angle = angle;
        this.startAngle = angle;
        this.length = (int) (interia / (mass * distance));

        angleAcceleration = - acceleration / length * Math.sin(angle);
        angleVelocity = 0;
        period = 2 * Math.PI * Math.sqrt(length / acceleration);

        position = new Point();
        updatePosition();
    }

    private void initialize(double acceleration, double angle, double length) {
        this.acceleration  = acceleration;
        this.angle = angle;
        this.startAngle = angle;
        this.length = length;

        angleAcceleration = - acceleration / length * Math.sin(angle);
        angleVelocity = 0;
        period = 2 * Math.PI * Math.sqrt(length / acceleration);

        position = new Point();
        updatePosition();
    }

    /**
     * Update field position into XY coordinates
     */
    private void updatePosition() {
        double newX = Math.sin(angle) * length;
        double newY = Math.cos(angle) * length;

        position.setX(newX);
        position.setY(newY);
    }

    //</editor-fold>

    public static void main(String... args) {

    }
}

