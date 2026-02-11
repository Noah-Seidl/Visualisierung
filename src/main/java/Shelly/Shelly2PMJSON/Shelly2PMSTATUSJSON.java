
package Shelly.Shelly2PMJSON;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "source",
    "output",
    "apower",
    "voltage",
    "freq",
    "current",
    "aenergy",
    "ret_aenergy",
    "temperature"
})
public class Shelly2PMSTATUSJSON {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("source")
    public String source;
    @JsonProperty("output")
    public Boolean output;
    @JsonProperty("apower")
    public Double apower;
    @JsonProperty("voltage")
    public Double voltage;
    @JsonProperty("freq")
    public Double freq;
    @JsonProperty("current")
    public Double current;
    @JsonProperty("aenergy")
    public Aenergy aenergy;
    @JsonProperty("ret_aenergy")
    public RetAenergy retAenergy;
    @JsonProperty("temperature")
    public Temperature temperature;

}
