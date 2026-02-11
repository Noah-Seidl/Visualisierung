
package Shelly.ShellyLIGHTJSON;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "power",
    "overpower",
    "is_valid",
    "timestamp",
    "counters",
    "total"
})
public class Meter {

    @JsonProperty("power")
    public Double power;
    @JsonProperty("overpower")
    public Double overpower;
    @JsonProperty("is_valid")
    public Boolean isValid;
    @JsonProperty("timestamp")
    public Integer timestamp;
    @JsonProperty("counters")
    public List<Double> counters;
    @JsonProperty("total")
    public Integer total;

}
