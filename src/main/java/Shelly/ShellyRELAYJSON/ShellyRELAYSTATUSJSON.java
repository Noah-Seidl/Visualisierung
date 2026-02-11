
package Shelly.ShellyRELAYJSON;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ison",
    "has_timer",
    "timer_started",
    "timer_duration",
    "timer_remaining",
    "source"
})
public class ShellyRELAYSTATUSJSON {

    @JsonProperty("ison")
    public Boolean ison;
    @JsonProperty("has_timer")
    public Boolean hasTimer;
    @JsonProperty("timer_started")
    public Integer timerStarted;
    @JsonProperty("timer_duration")
    public Integer timerDuration;
    @JsonProperty("timer_remaining")
    public Integer timerRemaining;
    @JsonProperty("source")
    public String source;

}
