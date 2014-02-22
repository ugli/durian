package se.ugli.durian.j.core;

import java.util.Collection;

public interface Content extends Node {

    Collection<? extends Content> getContent();

}
