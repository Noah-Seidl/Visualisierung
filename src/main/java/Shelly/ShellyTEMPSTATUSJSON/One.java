
package Shelly.ShellyTEMPSTATUSJSON;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "hwID",
    "tC",
    "tF"
})
public class One {

    @JsonProperty("hwID")
    public String hwID;
    @JsonProperty("tC")
    public Double tC;
    @JsonProperty("tF")
    public Double tF;

}
