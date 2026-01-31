package shelly;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public abstract class ShellyBase implements ShellyDevice {
    protected double x,y;
    protected Boolean status = false;
    protected String ip;
    protected int channel;
    protected HttpRequest requestStatus;
    protected HttpRequest requestToggle;
    protected HttpClient client = HttpClient.newHttpClient();

    protected ShellyBase(String ip, int channel) throws ShellyException{
        this.ip = ip;
        this.channel = channel;
        boolean test = this.tryShelly();
        if(!test)
            throw new WrongShellyType("");
    }

        private boolean tryShelly() {
        try{
            requestStatus = HttpRequest.newBuilder(new URI(getStatusUrl()))
                    .timeout(Duration.ofSeconds(1))
                    .build();

            HttpResponse<String> response = this.client.send(requestStatus, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200)
                return false;

            requestToggle = HttpRequest.newBuilder(new URI(getToggleUrl()))
                    .timeout(Duration.ofSeconds(2))
                    .build();

        }catch (Exception e){
            System.out.println("Fehler bei Shelly init falsche url");
            return false;
        }

        return true;
    }

    protected abstract String getStatusUrl();

    protected String getToggleUrl() {
        return "http://" + ip + "/relay/" + channel + "?turn=toggle";
    }

    @Override
    public Boolean toggle() {
        try {
            HttpResponse<String> response = client.send(requestToggle, HttpResponse.BodyHandlers.ofString());
            System.out.println("Toggle Response: " + response.body());
        } catch (Exception e) {
            System.out.println("Fehler in toggle Shelly: " + e.getMessage());
        }
        return false;
    }

    public boolean getStatus(){return status;}

    public ShellyDevice addCoords(double x, double y)
    {
        this.x = x;
        this.y = y;
        return this;
    }

    public double getX(){return x;}
    public double getY(){return y;}


}
