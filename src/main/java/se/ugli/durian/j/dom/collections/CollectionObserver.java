package se.ugli.durian.j.dom.collections;

public interface CollectionObserver<E> {

    void elementAdded(ObservableCollection<E> list, E e);

    void elementRemoved(ObservableCollection<E> list, Object e);

}
