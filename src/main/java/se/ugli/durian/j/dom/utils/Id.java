package se.ugli.durian.j.dom.utils;

import java.util.UUID;

public class Id {

    private Id() {

    }

    public static String create() {
        return UUID.randomUUID().toString();
    }

}
