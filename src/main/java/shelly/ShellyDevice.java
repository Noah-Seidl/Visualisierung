package shelly;

public interface ShellyDevice {
    Boolean status();
    Boolean toggle();
    boolean getStatus();
    double getX();
    double getY();
    void addCoords(double x, double y);
    int getId();
}
