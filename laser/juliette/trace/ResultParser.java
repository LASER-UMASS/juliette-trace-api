package laser.juliette.trace;

import java.util.List;
import java.util.Stack;

import org.xml.sax.Attributes;

class ResultParser extends EmptyTagParser {

	ResultParser(Stack<Parser> stack, List<String> results,
			Attributes attrs) {
		super(stack);
		results.add(attrs.getValue(XMLSupport.VALUE));
	}

}
