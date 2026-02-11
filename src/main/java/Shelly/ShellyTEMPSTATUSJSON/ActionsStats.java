
package Shelly.ShellyTEMPSTATUSJSON;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "skipped"
})
public class ActionsStats {

    @JsonProperty("skipped")
    public Integer skipped;

}
