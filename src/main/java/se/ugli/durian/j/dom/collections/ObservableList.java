package se.ugli.durian.j.dom.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class ObservableList<E> extends ObservableCollection<E> implements List<E> {

    private final List<E> backendList;

    public ObservableList() {
        this(null);
    }

    public ObservableList(final CollectionObserver<?> observer) {
        this(new ArrayList<E>(), observer);
    }

    public ObservableList(final List<E> backendList, final CollectionObserver<?> observer) {
        super(backendList, observer);
        this.backendList = backendList;
    }

    public List<E> getBackendList() {
        return backendList;
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        final List<E> copy = new ArrayList<E>(backendList);
        copy.removeAll(c);
        for (final Object e : copy) {
            notifyRemove(e);
        }
        return backendList.retainAll(c);
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends E> c) {
        for (final E e : c) {
            notifyAdd(e);
        }
        return backendList.addAll(index, c);
    }

    @Override
    public E get(final int index) {
        return backendList.get(index);
    }

    @Override
    public E set(final int index, final E element) {
        notifyRemove(backendList.get(index));
        // TODO not correct index
        notifyAdd(element);
        return backendList.set(index, element);
    }

    @Override
    public void add(final int index, final E element) {
        notifyAdd(element);
        backendList.add(index, element);
    }

    @Override
    public E remove(final int index) {
        notifyRemove(backendList.get(index));
        return backendList.remove(index);
    }

    @Override
    public int indexOf(final Object o) {
        return backendList.indexOf(o);
    }

    @Override
    public int lastIndexOf(final Object o) {
        return backendList.lastIndexOf(o);
    }

    @Override
    public ListIterator<E> listIterator() {
        return backendList.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(final int index) {
        return backendList.listIterator(index);
    }

    @Override
    public List<E> subList(final int fromIndex, final int toIndex) {
        return backendList.subList(fromIndex, toIndex);
    }

}
