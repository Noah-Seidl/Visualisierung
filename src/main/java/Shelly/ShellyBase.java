package Shelly;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class ShellyBase{
    //instance variables to get Status and toggling of shellys
    protected Boolean status = null;
    protected Double power = null;
    protected Double[] temp = null;
    protected String ip;
    protected String channel;
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
    //for autodetect
    private static List<Class<? extends ShellyBase>> shellyTypes = Arrays.asList(ShellyTemp.class, Shelly2PM.class, ShellyRelay.class, ShellyLight.class, ShellyEM3.class);

    protected ShellyBase(String ip, String channel) {
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
    public Double[] getTemp(){return temp;}

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

    public static ShellyBase autodetectShelly(String ip, String channel)
    {
        ShellyBase shelly = null;

        for (Class<? extends ShellyBase> shellyclass : shellyTypes)
        {
            try {
                shelly = shellyclass.getDeclaredConstructor(String.class, String.class)
                        .newInstance(ip,channel);

                if(shelly.tryShelly())
                    return shelly;

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }

        return null;
    }


    public List<Class<? extends ShellyBase>> getShellyTypes() {
        return shellyTypes;
    }

    public void setShellyTypes(List<Class<? extends ShellyBase>> shellyTypes) {
        this.shellyTypes = shellyTypes;
    }
}
