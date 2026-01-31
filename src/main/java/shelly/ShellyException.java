package shelly;

public class ShellyException extends Exception{

    public ShellyException(String message){
        super(message);
    }

    public ShellyException(String message, Throwable cause) {
        super(message, cause);
    }
}
