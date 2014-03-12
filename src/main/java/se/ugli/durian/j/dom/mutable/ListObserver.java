package se.ugli.durian.j.dom.mutable;

public interface ListObserver<E> {

    void add(ObservableList2<E> list, E e);

    void remove(ObservableList2<E> list, E e);

}
