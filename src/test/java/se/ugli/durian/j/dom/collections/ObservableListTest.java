package se.ugli.durian.j.dom.collections;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import se.ugli.durian.j.dom.collections.ListSynchronizer;
import se.ugli.durian.j.dom.collections.ObservableList;

public class ObservableListTest {

    @Test
    public void test() {
        final ObservableList<Integer> intList = new ObservableList<Integer>();
        final ObservableList<Number> numberList = new ObservableList<Number>();

        ListSynchronizer.applyLiveUpdates(intList, numberList);

        intList.add(3);
        intList.add(4);

        numberList.add(5l);

        assertEquals(3, intList.size());
        assertEquals(3, numberList.size());
        assertEquals("[3, 4, 5]", intList.toString());
        assertEquals("[3, 4, 5]", numberList.toString());

    }

}
