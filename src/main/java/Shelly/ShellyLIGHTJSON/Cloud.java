
package Shelly.ShellyLIGHTJSON;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "enabled",
    "connected"
})
public class Cloud {

    @JsonProperty("enabled")
    public Boolean enabled;
    @JsonProperty("connected")
    public Boolean connected;

}
