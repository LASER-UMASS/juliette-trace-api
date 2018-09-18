/*
 * Copyright (c) 2009, University of Massachusetts Amherst
 * All Rights Reserved.
 */
package laser.juliette.trace;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import laser.juliette.trace.Event.Annotation;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Support class for the reading and writing of <code>Traces</code> in XML.
 */
public class XMLIO {
	public static void write(Traces traces, Writer stream) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
        
			Element root = doc.createElement (XMLSupport.TRACES);
			doc.appendChild(root);
			
			for (Trace trace: traces) {
				constructTraceNode(doc, root, trace);
			}
			
            Transformer xformer = TransformerFactory.newInstance().newTransformer();
            xformer.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, XMLSupport.SYSTEM_ID);
            xformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, XMLSupport.PUBLIC_ID);
            xformer.transform(new DOMSource(doc), new StreamResult(stream));
		} catch (ParserConfigurationException e) {
            throw new RuntimeException("Internal parser configuration error.", e);
		} catch (TransformerException e) {
            throw new RuntimeException("Internal output error.", e);
		}
	}
	
	public static Traces read(Reader stream) throws SAXException, IOException {
		XMLReader parser = XMLReaderFactory.createXMLReader();
		TracesHandler handler = new TracesHandler();
		parser.setContentHandler(handler);
		parser.parse(new InputSource(stream));
		return handler.getTraces();
	}
	
	private static void constructTraceNode(Document doc, Element root, Trace trace) {
		Element tracenode = doc.createElement(XMLSupport.TRACE);
		root.appendChild(tracenode);
		
		for (String name: trace.getPropertyNames()) {
			Element propnode = doc.createElement(XMLSupport.PROPERTY);
			propnode.setAttribute(XMLSupport.NAME, name);
			propnode.setAttribute(XMLSupport.VALUE, trace.getProperty(name));
			root.appendChild(tracenode);
		}
		
		for (Event event: trace) {
			if (event instanceof StateChangeEvent) {
				constructStateChangeEventNode(doc, tracenode, (StateChangeEvent) event);
			} else if (event instanceof ObservedEvent) {
				constructObservedEventNode(doc, tracenode, (ObservedEvent) event);
			} else {
				throw new RuntimeException("Unexpected event kind: " + event);
			}
		}
	}
	
	private static void constructObservedEventNode(Document doc, Element tracenode, ObservedEvent event) {
		Element eventnode = doc.createElement(XMLSupport.OBSERVED_EVENT);
		tracenode.appendChild(eventnode);
		
		eventnode.setAttribute(XMLSupport.AGENT, event.getAgentName());
		eventnode.setAttribute(XMLSupport.NAME, event.getEventName());
		eventnode.setAttribute(XMLSupport.TIMESTAMP, Long.toString(event.getTimestamp()));
		if (event.getAncestor() != null) {
			eventnode.setAttribute(XMLSupport.ANCESTOR, getID(event.getAncestor()));
		}
		eventnode.setAttribute(XMLSupport.ID, getID(event));
	}

	private static void constructStateChangeEventNode(Document doc, Element tracenode, StateChangeEvent event) {
		Element eventnode = doc.createElement(XMLSupport.STATE_CHANGE_EVENT);
		tracenode.appendChild(eventnode);
		
		eventnode.setAttribute(XMLSupport.AGENT, event.getAgentName());
		eventnode.setAttribute(XMLSupport.NAME, event.getEventName());
		eventnode.setAttribute(XMLSupport.STATE, event.getState().name());
		eventnode.setAttribute(XMLSupport.TIMESTAMP, Long.toString(event.getTimestamp()));
		if (event.getAncestor() != null) {
			eventnode.setAttribute(XMLSupport.ANCESTOR, getID(event.getAncestor()));
		}
		eventnode.setAttribute(XMLSupport.ID, getID(event));
		
		createAnnotationsNode(doc, eventnode, event.getAnnotations());
		
		createParametersNode(doc, eventnode, event.getParameters());
		
		if (event.getResults() != null && !event.getResults().isEmpty()) {
			for (String result: event.getResults()) {
				Element resultnode = doc.createElement(XMLSupport.RESULT);
				eventnode.appendChild(resultnode);
				
				resultnode.setAttribute(XMLSupport.VALUE, result);
			}
		}
		
		createContextNode(doc, eventnode, event.getContext());
		
		createControllerNode(doc, eventnode, event.getController());
	}

	private static void createAnnotationsNode(Document doc, Element eventnode,
			List<Annotation> annotations) {
		if (!annotations.isEmpty()) {
			for (Annotation annotation: annotations) {
				Element annotationnode = doc.createElement(XMLSupport.ANNOTATION);
				eventnode.appendChild(annotationnode);
				
				annotationnode.setAttribute(XMLSupport.KIND, annotation.getKind());
				annotationnode.setAttribute(XMLSupport.VALUE, annotation.getValue());
			}
		}
	}

	private static void createControllerNode(Document doc, Element eventnode,
			Controller controller) {
		if (controller != null) {
			if (controller instanceof ReactiveController) {
				Element controllernode = doc.createElement(XMLSupport.REACTIVE_CONTROLLER);
				eventnode.appendChild(controllernode);
				
				controllernode.setAttribute(XMLSupport.TRIGGER, ((ReactiveController) controller).getTrigger());
				
			} else if (controller instanceof CardinalityController) {
				Element controllernode = doc.createElement(XMLSupport.CARDINALITY_CONTROLLER);
				eventnode.appendChild(controllernode);
				
				controllernode.setAttribute(XMLSupport.INDEX, Integer.toString(((CardinalityController) controller).getIndex()));
			} else if (controller instanceof ParameterController) {
				Element controllernode = doc.createElement(XMLSupport.PARAMETER_CONTROLLER);
				eventnode.appendChild(controllernode);
				
				controllernode.setAttribute(XMLSupport.NAME, ((ParameterController) controller).getParameter());
				controllernode.setAttribute(XMLSupport.INDEX, Integer.toString(((ParameterController) controller).getIndex()));
			} else {
				throw new RuntimeException("Unexpected controller kind " + controller);
			}
		}
	}

	private static void createContextNode(Document doc, Element enclosingnode,
			Context context) {
		if (context != null) {
			Element contextnode = doc.createElement(XMLSupport.CONTEXT);
			enclosingnode.appendChild(contextnode);
			
			contextnode.setAttribute(XMLSupport.NAME, context.getEnclosingStepName());
			contextnode.setAttribute(XMLSupport.CONNECTION, context.getConnection().name());
			contextnode.setAttribute(XMLSupport.INDEX, Integer.toString(context.getIndex()));
			
			createContextNode(doc, contextnode, context.getParentContext());
		}
	}

	private static void createParametersNode(Document doc, Element eventnode,
			Map<String, String> parameters) {
		if (parameters != null && !parameters.isEmpty()) {
			for (String name: parameters.keySet()) {
				Element parameternode = doc.createElement(XMLSupport.PARAMETER);
				eventnode.appendChild(parameternode);
				
				parameternode.setAttribute(XMLSupport.NAME, name);
				parameternode.setAttribute(XMLSupport.VALUE, parameters.get(name));
			}
		}
	}

	private static String getID(Event event) {
		return "_" + Integer.toString(event.getID());
	}
}
