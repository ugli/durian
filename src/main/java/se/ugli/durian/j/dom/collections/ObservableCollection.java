package se.ugli.durian.j.dom.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class ObservableCollection<E> implements Collection<E> {

    private final Collection<E> backendCollecion;
    private final List<CollectionObserver<E>> observers = new ArrayList<CollectionObserver<E>>();

    protected ObservableCollection() {
        this(null);
    }

    protected ObservableCollection(final CollectionObserver<?> observer) {
        this(new ArrayList<E>(), observer);
    }

    @SuppressWarnings("unchecked")
    protected ObservableCollection(final Collection<E> backendCollecion, final CollectionObserver<?> observer) {
        this.backendCollecion = backendCollecion;
        if (observer != null) {
            observers.add((CollectionObserver<E>) observer);
        }
    }

    public List<CollectionObserver<E>> getObservers() {
        return observers;
    }

    protected void notifyAdd(final E e) {
        for (final CollectionObserver<E> observer : observers) {
            observer.elementAdded(this, e);
        }
    }

    protected void notifyRemove(final Object e) {
        for (final CollectionObserver<E> observer : observers) {
            observer.elementRemoved(this, e);
        }
    }

    @Override
    public int size() {
        return backendCollecion.size();
    }

    @Override
    public boolean isEmpty() {
        return backendCollecion.isEmpty();
    }

    @Override
    public boolean contains(final Object o) {
        return backendCollecion.contains(o);
    }

    @Override
    public Iterator<E> iterator() {
        return backendCollecion.iterator();
    }

    @Override
    public Object[] toArray() {
        return backendCollecion.toArray();
    }

    @Override
    public <T> T[] toArray(final T[] a) {
        return backendCollecion.toArray(a);
    }

    @Override
    public boolean add(final E e) {
        notifyAdd(e);
        return backendCollecion.add(e);
    }

    @Override
    public boolean remove(final Object o) {
        return backendCollecion.remove(o);
    }

    @Override
    public boolean containsAll(final Collection<?> c) {
        return backendCollecion.containsAll(c);
    }

    @Override
    public boolean addAll(final Collection<? extends E> c) {
        for (final E e : c) {
            notifyAdd(e);
        }
        return backendCollecion.addAll(c);
    }

    @Override
    public boolean removeAll(final Collection<?> c) {
        for (final Object e : c) {
            notifyRemove(e);
        }
        return backendCollecion.removeAll(c);
    }

    @Override
    public void clear() {
        for (final E e : backendCollecion) {
            notifyRemove(e);
        }
        backendCollecion.clear();
    }

    @Override
    public String toString() {
        return backendCollecion.toString();
    }

    @Override
    public boolean equals(final Object obj) {
        return backendCollecion.equals(obj);
    }

    @Override
    public int hashCode() {
        return backendCollecion.hashCode();
    }

}
