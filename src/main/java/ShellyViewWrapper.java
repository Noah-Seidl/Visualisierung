import shelly.ShellyDevice;
import javafx.scene.image.ImageView;


public class ShellyViewWrapper {
    private final ShellyDevice shelly;
    private final ImageView view;

    public ShellyViewWrapper(ShellyDevice shelly, ImageView view)
    {
        this.shelly = shelly;
        this.view = view;
    }

    public ImageView getView() {
        return view;
    }

    public ShellyDevice getShelly() {
        return shelly;
    }
}
