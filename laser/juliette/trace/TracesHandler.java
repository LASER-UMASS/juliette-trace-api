package laser.juliette.trace;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

class TracesHandler implements ContentHandler {
	Traces getTraces() {
		return traces;
	}

	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		if (parsestack.isEmpty()) {
			if (localName.equals(XMLSupport.TRACES)) {
				parsestack.push(new TracesParser(parsestack, traces));
			} else {
				throw new SAXParseException("Unexpected tag " + localName, null);
			}
		} else {
			parsestack.peek().startElement(localName, atts);
		}
	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		parsestack.peek().endElement();
	}

	public void characters(char[] ch, int start, int length)
	throws SAXException {
	}

	public void endDocument() throws SAXException {
	}


	public void endPrefixMapping(String prefix) throws SAXException {
	}

	public void ignorableWhitespace(char[] ch, int start, int length)
			throws SAXException {
	}

	public void processingInstruction(String target, String data)
			throws SAXException {
	}

	public void setDocumentLocator(Locator locator) {
	}

	public void skippedEntity(String name) throws SAXException {
	}

	public void startDocument() throws SAXException {
	}

	public void startPrefixMapping(String prefix, String uri)
			throws SAXException {
	}
	
	private Traces traces = new Traces();
	private Stack<Parser> parsestack = new Stack<Parser>();

}
