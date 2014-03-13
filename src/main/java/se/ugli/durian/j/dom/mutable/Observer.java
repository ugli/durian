package se.ugli.durian.j.dom.mutable;

import java.util.Collection;

class Observer {

    private final ObservableList<?> list1;
    private final ObservableList<?> list2;
    private final ElementImpl parent;

    Observer(final ObservableList<?> list1, final ObservableList<?> list2, ElementImpl parent) {
        this.list1 = list1;
        this.list2 = list2;
        this.parent = parent;
    }

    @SuppressWarnings("rawtypes")
    private ObservableList getOtherList(final ObservableList<?> list) {
        if (list == list1) {
            return list2;
        }
        return list1;
    }

    @SuppressWarnings("unchecked")
    boolean add(final ObservableList<?> list, final Object obj) {
        if (parent != null) {
            if (obj instanceof ElementImpl) {
                ElementImpl element = (ElementImpl) obj;
                parent.elementAdded(element);
            }
        }
        return getOtherList(list).getBackendList().add(obj);
    }

    boolean remove(final ObservableList<?> list, final Object obj) {
        return getOtherList(list).getBackendList().remove(obj);
    }

    void addAll(final ObservableList<?> list, final Collection<?> c) {
        for (Object obj : c) {
            add(list, obj);
        }
    }

    void clear(final ObservableList<?> list) {
        for (Object obj : list) {
            remove(list, obj);
        }
    }
}