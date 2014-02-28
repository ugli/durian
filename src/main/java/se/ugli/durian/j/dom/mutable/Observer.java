package se.ugli.durian.j.dom.mutable;

class Observer {

    private final ObservableList<?> list1;
    private final ObservableList<?> list2;
    private final MutableElement parent;

    Observer(final ObservableList<?> list1, final ObservableList<?> list2, MutableElement parent) {
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
            if (obj instanceof MutableElement) {
                MutableElement element = (MutableElement) obj;
                parent.elementAdded(element);
            }
        }
        return getOtherList(list).getBackendList().add(obj);
    }

    boolean remove(final ObservableList<?> list, final Object obj) {
        return getOtherList(list).getBackendList().remove(obj);
    }

}
