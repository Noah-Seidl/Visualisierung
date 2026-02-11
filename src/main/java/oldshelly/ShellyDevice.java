package oldshelly;

public interface ShellyDevice {
    Boolean fetchStatus();
    Boolean fetchToggle();
    boolean getStatus();
    double getX();
    double getY();
    void addCords(double x, double y);
    int getId();
}
