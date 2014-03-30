package se.ugli.durian.j.dom.collections;

public interface CollectionObserver<E> {

    void elementAdded(ObservableCollection<?> list, E e);

    void elementRemoved(ObservableCollection<?> list, E e);

}
