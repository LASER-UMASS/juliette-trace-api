package laser.juliette.trace;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.xml.sax.SAXException;

import laser.juliette.trace.Controller;
import laser.juliette.trace.Event;
import laser.juliette.trace.Event.Annotation;
import laser.juliette.trace.ObservedEvent;
import laser.juliette.trace.PredicateController;
import laser.juliette.trace.ReactiveController;
import laser.juliette.trace.StateChangeEvent;
import laser.juliette.trace.Trace;
import laser.juliette.trace.Traces;
import laser.juliette.trace.XMLIO;


public class TracePrinter 
{
	public String print(Event currentTraceEvent)
	{
		String printString = "";
		
		printString = "On " + new Date(currentTraceEvent.getTimestamp()) + ", " + currentTraceEvent.getAgentName();
		if (currentTraceEvent instanceof ObservedEvent) {
			printString += " \"" + currentTraceEvent.getEventName() + "\"";
		}
		else if (currentTraceEvent instanceof StateChangeEvent) {
			StateChangeEvent currentStateChangeEvent = (StateChangeEvent)currentTraceEvent;
			
			printString += " " + currentStateChangeEvent.getState() + " \"" + currentStateChangeEvent.getEventName() + "\".";
			Map<String,String> currentParams = currentStateChangeEvent.getParameters();
			Controller currentController = currentStateChangeEvent.getController();
			if (currentController instanceof PredicateController) {
				printString += " The predicate is: " + ((PredicateController)currentController).getPredicate() + ".";
			}
			else if (currentController instanceof ReactiveController) {
				printString += " The trigger is: " + ((ReactiveController)currentController).getTrigger() + ".";
			}
			//TODO) What other controllers should be supported?
			if (currentParams != null) {
				printString += " The following are required: " + currentParams + ".";
			}
			List<String> currentResults = currentStateChangeEvent.getResults();
			if (currentResults != null) {
				printString += " The results are: " + currentResults + ".";
			}
			Annotation currentNotes = currentStateChangeEvent.getAnnotation("userComments");
			if (currentNotes != null) {
				printString += " The user comments are: \"" + currentNotes.getValue() + "\".";
			}
			Annotation currentIsLeaf = currentStateChangeEvent.getAnnotation("isLeaf");
			if (currentIsLeaf != null) {
				printString += " The step is a leaf: " + currentIsLeaf.getValue() + ".";
			}
		}
		else {
			throw new UnsupportedOperationException("In the trace, event of class " + currentTraceEvent.getClass().getName() + " is not supported.");
		}
		
		return printString;
	}
	
	public void print(Trace currentTrace, Writer out) throws IOException 
	{
		PrintWriter pw = new PrintWriter(out);
		
		pw.println("Trace from process \"" + currentTrace.getProperty(Trace.PROCESS_NAME_PROPERTY_NAME) + "\":");
		int eventID_ = 1;
		for (Iterator currentTraceEventsItr = currentTrace.iterator(); currentTraceEventsItr.hasNext(); )
		{
			Event currentTraceEvent = (Event)currentTraceEventsItr.next();
			pw.println("\t" + eventID_ + ") " + this.print(currentTraceEvent));
			eventID_++;
		} // end for currentEventsItr
		pw.flush();
	}
	
	public static void main (String[] args) throws IOException, SAXException
	{
		if (args.length != 1) {
			System.err.println("The current trace (in XML) must be specified!");
			System.exit(1);
		}
		
		BufferedReader in = new BufferedReader(new FileReader(args[0]));
		TracePrinter tracePrinter = new TracePrinter();
		try {
			Traces currentTraceList = XMLIO.read(in);
			Trace currentTrace = currentTraceList.get(0);
			tracePrinter.print(currentTrace, new PrintWriter(System.out));
		}
		finally {
			in.close();
		}
	}
}
