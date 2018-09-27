package se.ugli.durian.j.json.jackson;

import se.ugli.durian.j.dom.node.Attribute;
import se.ugli.durian.j.dom.node.Text;

public interface FieldValueFactory {

	Object create(Attribute attribute);

	Object create(Text text);
}
