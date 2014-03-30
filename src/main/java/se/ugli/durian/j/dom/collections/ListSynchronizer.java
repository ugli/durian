package se.ugli.durian.j.dom.collections;

public final class ListSynchronizer<E> implements CollectionObserver<E> {

    private final ObservableList<E> list1;
    private final ObservableList<E> list2;

    private ListSynchronizer(final ObservableList<E> list1, final ObservableList<E> list2) {
        this.list1 = list1;
        this.list2 = list2;
        list1.getObservers().add(this);
        list2.getObservers().add(this);
    }

    @SuppressWarnings({ "unused", "rawtypes", "unchecked" })
    public static void applyLiveUpdates(final ObservableList<?> list1, final ObservableList<?> list2) {
        new ListSynchronizer(list1, list2);
    }

    private ObservableList<E> getOtherList(final ObservableCollection<E> list) {
        if (list == list1) {
            return list2;
        }
        else if (list == list2) {
            return list1;
        }
        else {
            throw new IllegalStateException();
        }
    }

    @Override
    public void elementAdded(final ObservableCollection<E> list, final E obj) {
        final ObservableList<E> otherList = getOtherList(list);
        otherList.getBackendList().add(obj);
    }

    @Override
    public void elementRemoved(final ObservableCollection<E> list, final Object obj) {
        final ObservableList<?> otherList = getOtherList(list);
        otherList.getBackendList().remove(obj);
    }

}
