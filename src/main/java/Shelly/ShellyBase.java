package Shelly;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public abstract class ShellyBase{
    //instance variables to get Status and toggling of shellys
    protected Boolean status = null;
    protected Double power = null;
    protected String ip;
    protected int channel;
    //Requests
    protected ObjectMapper mapper;
    protected HttpRequest requestStatus;
    protected HttpRequest requestToggle;
    protected HttpClient client = HttpClient.newHttpClient();
    //index to identify shelly
    private static int index = 0;
    private final int id;
    //for shelly buttons on vis
    protected double x,y;

    protected ShellyBase(String ip, int channel) {
        this.ip = ip;
        this.channel = channel;
        createRequests();
        id = index++;
        mapper = new ObjectMapper();
    }


    //creates and tries shelly response;
    public boolean tryShelly() {
        try{
            HttpResponse<Void> response = this.client.send(requestStatus, HttpResponse.BodyHandlers.discarding());

            if(response.statusCode() != 200)
                return false;

        }catch (Exception e){
            System.out.println("Error in Shelly init: " + e.getMessage());
            return false;
        }

        return true;
    }


    private void createRequests() {
        try {
            requestStatus = HttpRequest.newBuilder(new URI(getStatusUrl()))
                    .timeout(Duration.ofSeconds(1))
                    .build();

            requestToggle = HttpRequest.newBuilder(new URI(getToggleUrl()))
                    .timeout(Duration.ofSeconds(1))
                    .build();
        } catch (Exception e) {
            System.out.println("Error URLS in createRequests");
        }
    }

    //Status
    protected abstract String getStatusUrl();

    protected Boolean fetchStatus(){
        try {
            String response = client.send(requestStatus, HttpResponse.BodyHandlers.ofString()).body();
            status = queryStatus(response);
            return status;
        } catch (Exception e) {
            System.out.println("Error in Shelly toggle: " + e.getMessage());
        }
        return null;
    }

    protected abstract Boolean queryStatus(String response);

    public Boolean getStatus(){return status;}

    //Toggle
    protected String getToggleUrl() {return "http://" + ip + "/relay/" + channel + "?turn=toggle";}

    protected Boolean fetchToggle() {
        try {
            String response = client.send(requestToggle, HttpResponse.BodyHandlers.ofString()).body();
            status = queryToggle(response);
            return status;
        } catch (Exception e) {
            System.out.println("Error in Shelly toggle: " + e.getMessage());
        }

        return null;
    }

    public abstract Boolean queryToggle(String response);

    //extra shelly methods:

    public Double getPower(){return power;}


    //Buttons JavaFX
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
