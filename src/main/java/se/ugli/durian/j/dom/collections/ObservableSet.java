package se.ugli.durian.j.dom.collections;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class ObservableSet<E> extends ObservableCollection<E> implements Set<E> {

    private final Set<E> backendSet;

    public ObservableSet(final Set<E> backendSet, final CollectionObserver<?> observer) {
        super(backendSet, observer);
        this.backendSet = backendSet;
    }

    public ObservableSet(final CollectionObserver<?> observer) {
        this(new LinkedHashSet<E>(), observer);
    }

    public ObservableSet() {
        this(null);
    }

    @Override
    public boolean retainAll(final Collection<?> c) {
        final Set<E> copy = new HashSet<E>(backendSet);
        copy.removeAll(c);
        for (final Object e : copy) {
            notifyRemove(e);
        }
        return backendSet.retainAll(c);
    }

}
