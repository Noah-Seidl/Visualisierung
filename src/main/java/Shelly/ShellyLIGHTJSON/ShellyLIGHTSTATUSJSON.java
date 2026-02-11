
package Shelly.ShellyLIGHTJSON;

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
    "lights",
    "meters",
    "inputs",
    "tmp",
    "calibrated",
    "calib_progress",
    "calib_status",
    "calib_running",
    "wire_mode",
    "forced_neutral",
    "overtemperature",
    "loaderror",
    "overpower",
    "debug",
    "update",
    "ram_total",
    "ram_free",
    "fs_size",
    "fs_free",
    "uptime"
})

public class ShellyLIGHTSTATUSJSON {

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
    @JsonProperty("lights")
    public List<Light> lights;
    @JsonProperty("meters")
    public List<Meter> meters;
    @JsonProperty("inputs")
    public List<Input> inputs;
    @JsonProperty("tmp")
    public Tmp tmp;
    @JsonProperty("calibrated")
    public Boolean calibrated;
    @JsonProperty("calib_progress")
    public Integer calibProgress;
    @JsonProperty("calib_status")
    public Integer calibStatus;
    @JsonProperty("calib_running")
    public Integer calibRunning;
    @JsonProperty("wire_mode")
    public Integer wireMode;
    @JsonProperty("forced_neutral")
    public Boolean forcedNeutral;
    @JsonProperty("overtemperature")
    public Boolean overtemperature;
    @JsonProperty("loaderror")
    public Integer loaderror;
    @JsonProperty("overpower")
    public Boolean overpower;
    @JsonProperty("debug")
    public Integer debug;
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
