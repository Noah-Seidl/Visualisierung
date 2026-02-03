package shelly;

import java.util.List;

public interface Observer {
    void update(List<Integer> index);
    void updateSingle(int index, boolean status);
}
