package se.ugli.durian.j.dom.collections;

import java.util.List;

public class ListSynchronizer {

    private ListSynchronizer() {
    }

    @SuppressWarnings("rawtypes")
    public static void applyLiveUpdates(final List<?> list1, final List<?> list2) {
        final ObservableList<?> obsList1 = (ObservableList<?>) list1;
        final ObservableList<?> obsList2 = (ObservableList<?>) list2;
        final Observer observer = new Observer(obsList1, obsList2);
        ((ObservableList) list1).setObserver(observer);
        ((ObservableList) list2).setObserver(observer);
    }

}
