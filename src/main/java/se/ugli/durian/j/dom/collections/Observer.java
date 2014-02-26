package se.ugli.durian.j.dom.collections;

class Observer {

    private final ObservableList<?> list1;
    private final ObservableList<?> list2;

    public Observer(final ObservableList<?> list1, final ObservableList<?> list2) {
        this.list1 = list1;
        this.list2 = list2;
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
        return getOtherList(list).getBackendList().add(obj);
    }

    boolean remove(final ObservableList<?> list, final Object obj) {
        return getOtherList(list).getBackendList().remove(obj);
    }

}
