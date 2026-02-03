package shelly;

import java.util.LinkedList;
import java.util.List;

public abstract class Subject {
    private final List<Observer> observerList = new LinkedList<>();

    public void registerObserver(Observer o)
    {
        observerList.add(o);
    }

    public void notifyO(List<Integer> index)
    {
        for (Observer observer : observerList) {
            observer.update(index);
        }
    }

}

