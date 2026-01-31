package shelly;

public class WrongShellyType extends ShellyException {
    public WrongShellyType(String message) {
        super("Wrong Shelly Type" + message);
    }
}
