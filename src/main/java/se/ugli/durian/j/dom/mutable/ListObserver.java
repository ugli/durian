package se.ugli.durian.j.dom.mutable;

public interface ListObserver {

    void add(ObservableList2<?> list, Object obj);

    void remove(ObservableList2<?> list, Object obj);

}
