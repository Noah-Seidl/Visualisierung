
package Shelly.ShellyJSONs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "total",
    "by_minute",
    "minute_ts"
})

public class RetAenergy {

    @JsonProperty("total")
    public Double total;
    @JsonProperty("by_minute")
    public List<Double> byMinute;
    @JsonProperty("minute_ts")
    public Integer minuteTs;

}
