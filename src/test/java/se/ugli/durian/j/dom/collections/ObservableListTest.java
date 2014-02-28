package se.ugli.durian.j.dom.collections;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import se.ugli.durian.j.dom.mutable.ListSynchronizer;
import se.ugli.durian.j.dom.mutable.ObservableList;

public class ObservableListTest {

    @Test
    public void test() {
        final List<Integer> intList = new ObservableList<Integer>();
        final List<Number> numberList = new ObservableList<Number>();

        ListSynchronizer.applyLiveUpdates(intList, numberList, null);

        intList.add(3);
        intList.add(4);

        numberList.add(5l);

        assertEquals(3, intList.size());
        assertEquals(3, numberList.size());
        assertEquals("[3, 4, 5]", intList.toString());
        assertEquals("[3, 4, 5]", numberList.toString());

    }

}
