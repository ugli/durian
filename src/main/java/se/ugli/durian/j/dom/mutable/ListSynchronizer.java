package se.ugli.durian.j.dom.mutable;

import java.util.List;

public final class ListSynchronizer {

    private ListSynchronizer() {
    }

    public static void applyLiveUpdates(final List<?> list1, final List<?> list2, MutableElement parent) {
        final ObservableList<?> obsList1 = (ObservableList<?>) list1;
        final ObservableList<?> obsList2 = (ObservableList<?>) list2;
        final Observer observer = new Observer(obsList1, obsList2, parent);
        obsList1.setObserver(observer);
        obsList2.setObserver(observer);
    }

}
