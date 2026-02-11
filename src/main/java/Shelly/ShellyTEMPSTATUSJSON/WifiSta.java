
package Shelly.ShellyTEMPSTATUSJSON;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "connected",
    "ssid",
    "ip",
    "rssi"
})
public class WifiSta {

    @JsonProperty("connected")
    public Boolean connected;
    @JsonProperty("ssid")
    public String ssid;
    @JsonProperty("ip")
    public String ip;
    @JsonProperty("rssi")
    public Integer rssi;

}
