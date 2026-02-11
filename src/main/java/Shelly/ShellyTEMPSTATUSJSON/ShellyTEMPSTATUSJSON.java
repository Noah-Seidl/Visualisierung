
package Shelly.ShellyTEMPSTATUSJSON;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "wifi_sta",
    "cloud",
    "mqtt",
    "time",
    "unixtime",
    "serial",
    "has_update",
    "mac",
    "cfg_changed_cnt",
    "actions_stats",
    "relays",
    "meters",
    "inputs",
    "ext_sensors",
    "ext_temperature",
    "ext_humidity",
    "update",
    "ram_total",
    "ram_free",
    "fs_size",
    "fs_free",
    "uptime"
})
public class ShellyTEMPSTATUSJSON {

    @JsonProperty("wifi_sta")
    public WifiSta wifiSta;
    @JsonProperty("cloud")
    public Cloud cloud;
    @JsonProperty("mqtt")
    public Mqtt mqtt;
    @JsonProperty("time")
    public String time;
    @JsonProperty("unixtime")
    public Integer unixtime;
    @JsonProperty("serial")
    public Integer serial;
    @JsonProperty("has_update")
    public Boolean hasUpdate;
    @JsonProperty("mac")
    public String mac;
    @JsonProperty("cfg_changed_cnt")
    public Integer cfgChangedCnt;
    @JsonProperty("actions_stats")
    public ActionsStats actionsStats;
    @JsonProperty("relays")
    public List<Relay> relays;
    @JsonProperty("meters")
    public List<Meter> meters;
    @JsonProperty("inputs")
    public List<Input> inputs;
    @JsonProperty("ext_sensors")
    public ExtSensors extSensors;
    @JsonProperty("ext_temperature")
    public ExtTemperature extTemperature;
    @JsonProperty("ext_humidity")
    public ExtHumidity extHumidity;
    @JsonProperty("update")
    public Update update;
    @JsonProperty("ram_total")
    public Integer ramTotal;
    @JsonProperty("ram_free")
    public Integer ramFree;
    @JsonProperty("fs_size")
    public Integer fsSize;
    @JsonProperty("fs_free")
    public Integer fsFree;
    @JsonProperty("uptime")
    public Integer uptime;

}
