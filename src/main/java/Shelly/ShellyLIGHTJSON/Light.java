
package Shelly.ShellyLIGHTJSON;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "ison",
    "source",
    "has_timer",
    "timer_started",
    "timer_duration",
    "timer_remaining",
    "mode",
    "brightness",
    "transition"
})
public class Light {

    @JsonProperty("ison")
    public Boolean ison;
    @JsonProperty("source")
    public String source;
    @JsonProperty("has_timer")
    public Boolean hasTimer;
    @JsonProperty("timer_started")
    public Integer timerStarted;
    @JsonProperty("timer_duration")
    public Integer timerDuration;
    @JsonProperty("timer_remaining")
    public Integer timerRemaining;
    @JsonProperty("mode")
    public String mode;
    @JsonProperty("brightness")
    public Integer brightness;
    @JsonProperty("transition")
    public Integer transition;

}
