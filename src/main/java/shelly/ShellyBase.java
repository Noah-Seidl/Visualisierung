package shelly;

import java.net.URI;
import java.net.URISyntaxException;
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
    private static int index = 0;
    private final int id;


    protected ShellyBase(String ip, int channel) throws ShellyException{
        this.ip = ip;
        this.channel = channel;
        if(!this.tryShelly())
            throw new WrongShellyType("Wrong Shelly type in ShellyBase Class");
        id = index++;
    }


    //creates and tries shelly response;
    private boolean tryShelly() {
        try{
            this.createRequests();

            HttpResponse<String> response = this.client.send(requestStatus, HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() != 200)
                return false;

        }catch (Exception e){
            System.out.println("Error in Shelly init: " + e.getMessage());
            return false;
        }

        return true;
    }


    private void createRequests() throws URISyntaxException {
        requestStatus = HttpRequest.newBuilder(new URI(getStatusUrl()))
                .timeout(Duration.ofSeconds(1))
                .build();

        requestToggle = HttpRequest.newBuilder(new URI(getToggleUrl()))
                .timeout(Duration.ofSeconds(1))
                .build();
    }


    protected abstract String getStatusUrl();


    protected String getToggleUrl() {
        return "http://" + ip + "/relay/" + channel + "?turn=toggle";
    }


    @Override
    public Boolean fetchToggle() {
        try {
            HttpResponse<String> response = client.send(requestToggle, HttpResponse.BodyHandlers.ofString());
            status = response.body().contains("\"ison\": true,") || response.body().contains("\"ison\":true,");
            return status;
        } catch (Exception e) {
            System.out.println("Error in Shelly toggle: " + e.getMessage());
        }
        return false;
    }


    public boolean getStatus(){return status;}


    public void addCords(double x, double y)
    {
        this.x = x;
        this.y = y;
    }


    public double getX(){return x;}
    public double getY(){return y;}


    public int getId() {
        return id;
    }
}
