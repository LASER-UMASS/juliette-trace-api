package laser.juliette.trace;

import java.util.Stack;

import laser.juliette.trace.Context.Connection;
import laser.juliette.trace.StateChangeEventParser.Cell;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

class ContextParser extends Parser {

	ContextParser(Stack<Parser> stack, Attributes attrs,
			Cell<Context> context) {
		super(stack);
		this.attrs = attrs;
		this.cell = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	void startElement(String tag, Attributes attrs) throws SAXException {
		if (tag.equals(XMLSupport.CONTEXT)) {
			stack.push(new ContextParser(stack, attrs, inner));
		} else {
			throw new SAXParseException("Unexpected tag " + tag, null);
		}
	}
	
	@Override
	void endElement() {
		cell.set(new Context(attrs.getValue(XMLSupport.NAME), Connection.valueOf(attrs.getValue(XMLSupport.CONNECTION)), Integer.parseInt(attrs.getValue(XMLSupport.INDEX)), inner.get()));
		super.endElement();
	}
	
	private Attributes attrs;
	private Cell<Context> cell;
	private Cell<Context> inner = new Cell<Context>();

}
