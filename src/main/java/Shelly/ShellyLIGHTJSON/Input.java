
package Shelly.ShellyLIGHTJSON;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "input",
    "event",
    "event_cnt"
})
public class Input {

    @JsonProperty("input")
    public Integer input;
    @JsonProperty("event")
    public String event;
    @JsonProperty("event_cnt")
    public Integer eventCnt;

}
