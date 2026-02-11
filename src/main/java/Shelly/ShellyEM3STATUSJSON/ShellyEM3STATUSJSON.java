
package Shelly.ShellyEM3STATUSJSON;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "a_current",
    "a_voltage",
    "a_act_power",
    "a_aprt_power",
    "a_pf",
    "a_freq",
    "b_current",
    "b_voltage",
    "b_act_power",
    "b_aprt_power",
    "b_pf",
    "b_freq",
    "c_current",
    "c_voltage",
    "c_act_power",
    "c_aprt_power",
    "c_pf",
    "c_freq",
    "n_current",
    "total_current",
    "total_act_power",
    "total_aprt_power",
    "user_calibrated_phase"
})
public class ShellyEM3STATUSJSON {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("a_current")
    public Double aCurrent;
    @JsonProperty("a_voltage")
    public Double aVoltage;
    @JsonProperty("a_act_power")
    public Double aActPower;
    @JsonProperty("a_aprt_power")
    public Double aAprtPower;
    @JsonProperty("a_pf")
    public Double aPf;
    @JsonProperty("a_freq")
    public Double aFreq;
    @JsonProperty("b_current")
    public Double bCurrent;
    @JsonProperty("b_voltage")
    public Double bVoltage;
    @JsonProperty("b_act_power")
    public Double bActPower;
    @JsonProperty("b_aprt_power")
    public Double bAprtPower;
    @JsonProperty("b_pf")
    public Double bPf;
    @JsonProperty("b_freq")
    public Double bFreq;
    @JsonProperty("c_current")
    public Double cCurrent;
    @JsonProperty("c_voltage")
    public Double cVoltage;
    @JsonProperty("c_act_power")
    public Double cActPower;
    @JsonProperty("c_aprt_power")
    public Double cAprtPower;
    @JsonProperty("c_pf")
    public Double cPf;
    @JsonProperty("c_freq")
    public Double cFreq;
    @JsonProperty("n_current")
    public Object nCurrent;
    @JsonProperty("total_current")
    public Double totalCurrent;
    @JsonProperty("total_act_power")
    public Double totalActPower;
    @JsonProperty("total_aprt_power")
    public Double totalAprtPower;
    @JsonProperty("user_calibrated_phase")
    public List<Object> userCalibratedPhase;

}
