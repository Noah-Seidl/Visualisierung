
package Shelly.ShellyTEMPSTATUSJSON;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "power",
    "is_valid"
})
public class Meter {

    @JsonProperty("power")
    public Double power;
    @JsonProperty("is_valid")
    public Boolean isValid;

}
