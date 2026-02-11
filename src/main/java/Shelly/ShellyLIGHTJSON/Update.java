
package Shelly.ShellyLIGHTJSON;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "status",
    "has_update",
    "new_version",
    "old_version",
    "beta_version"
})
public class Update {

    @JsonProperty("status")
    public String status;
    @JsonProperty("has_update")
    public Boolean hasUpdate;
    @JsonProperty("new_version")
    public String newVersion;
    @JsonProperty("old_version")
    public String oldVersion;
    @JsonProperty("beta_version")
    public String betaVersion;

}
