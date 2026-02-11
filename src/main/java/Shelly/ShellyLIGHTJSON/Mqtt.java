
package Shelly.ShellyLIGHTJSON;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "connected"
})
public class Mqtt {

    @JsonProperty("connected")
    public Boolean connected;

}
