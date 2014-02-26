package se.ugli.durian.j.dom.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ObservableList<E> implements List<E> {

    private List<E> backendList;
    private Observer observer;

    public ObservableList() {
        this(new ArrayList<E>());
    }

    public ObservableList(final List<E> backendList) {
        this.backendList = backendList;
    }

    void setObserver(final Observer observer) {
        this.observer = observer;
    }

    List<E> getBackendList() {
        return backendList;
    }

    @Override
    public int size() {
        return backendList.size();
    }

    @Override
    public boolean isEmpty() {
        return backendList.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return backendList.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return backendList.iterator();
    }

    @Override
    public Object[] toArray() {
        return backendList.toArray();
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        return backendList.toArray(a);
    }

    @Override
    public boolean add(final E e) {
        observer.add(this, e);
        return backendList.add(e);
    }

    @Override
    public boolean remove(final Object o) {
        observer.remove(this, o);
        return backendList.remove(o);
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        return backendList.containsAll(c);
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(final int index, final Collection<? extends E> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    @Override
    public E get(final int index) {
        return backendList.get(index);
    }

    @Override
    public E set(final int index, final E element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(final int index, final E element) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(final int index) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException();
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

    @Override
    public String toString() {
        return backendList.toString();
    }

}
