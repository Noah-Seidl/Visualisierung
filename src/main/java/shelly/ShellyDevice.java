package shelly;

public interface ShellyDevice {
    public Boolean status();
    public Boolean toggle();
    public boolean getStatus();
    public double getX();
    public double getY();
    public ShellyDevice addCoords(double x, double y);
}
