
package Shelly.ShellyJSONs;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "tC",
    "tF"
})
public class Temperature {

    @JsonProperty("tC")
    public Double tC;
    @JsonProperty("tF")
    public Double tF;

}
