package Shelly;


import java.util.LinkedList;
import java.util.List;

public abstract class SubjectShelly {
    private final List<ObserverShelly> observerList = new LinkedList<>();

    public void registerObserver(ObserverShelly o)
    {
        observerList.add(o);
    }

    public void notifyO(int index)
    {
        for (ObserverShelly observer : observerList) {
            observer.update(index);
        }
    }

}

