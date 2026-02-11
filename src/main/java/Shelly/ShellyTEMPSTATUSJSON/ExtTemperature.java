
package Shelly.ShellyTEMPSTATUSJSON;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "0",
    "1",
    "2"
})
public class ExtTemperature {

    @JsonProperty("0")
    public One zero;

    @JsonProperty("1")
    public One one;

    @JsonProperty("2")
    public One two;

}
