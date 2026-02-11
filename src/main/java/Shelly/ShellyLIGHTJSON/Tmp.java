
package Shelly.ShellyLIGHTJSON;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "tC",
    "tF",
    "is_valid"
})
public class Tmp {

    @JsonProperty("tC")
    public Double tC;
    @JsonProperty("tF")
    public Double tF;
    @JsonProperty("is_valid")
    public Boolean isValid;

}
