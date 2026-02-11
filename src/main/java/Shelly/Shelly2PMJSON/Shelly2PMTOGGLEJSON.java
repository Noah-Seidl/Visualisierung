
package Shelly.Shelly2PMJSON;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ison",
    "has_timer",
    "timer_started_at",
    "timer_duration",
    "timer_remaining",
    "overpower",
    "source"
})
public class Shelly2PMTOGGLEJSON {

    @JsonProperty("ison")
    public Boolean ison;
    @JsonProperty("has_timer")
    public Boolean hasTimer;
    @JsonProperty("timer_started_at")
    public Integer timerStartedAt;
    @JsonProperty("timer_duration")
    public Double timerDuration;
    @JsonProperty("timer_remaining")
    public Double timerRemaining;
    @JsonProperty("overpower")
    public Boolean overpower;
    @JsonProperty("source")
    public String source;

}
