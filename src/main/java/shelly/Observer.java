package shelly;

import java.util.List;

public interface Observer {
    public void update(List<Integer> index);
    public void updateSingle(int index, boolean status);
}
