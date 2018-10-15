package cardiacsurgery.trace;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import laser.juliette.trace.Context;
import laser.juliette.trace.Controller;
import laser.juliette.trace.Event;
import laser.juliette.trace.StateChangeEvent;
import laser.juliette.trace.StateChangeEvent.State;
import laser.juliette.trace.Trace;
import laser.juliette.trace.Traces;
import laser.juliette.trace.XMLIO;


public class CardiacSurgeryTrace 
{
	public static final String AUTO_AGENT_NAME = "Auto";
	
	private String traceName_;
	private Traces traces_;
	private Trace currentTrace_;
	
	
	public CardiacSurgeryTrace(String traceName) 
	{
		super();
		
		this.traceName_ = traceName;
		this.traces_ = new Traces();
		this.currentTrace_ = this.createTrace();
	}
	
	public Trace createTrace() 
	{
		Trace newTrace = new Trace();
		
		this.traces_.add(newTrace);
		
		return newTrace;
	}
	
	public StateChangeEvent createStateChangeEvent(String agent, String step, State state, long timestamp, Event ancestor) 
	{
		return this.createStateChangeEvent(agent, step, state, timestamp, ancestor, null, null, null, null);
	}
	
	public StateChangeEvent createStateChangeEvent(String agent, String step, State state, long timestamp, Event ancestor, Map<String, String> parameters, List<String> results, Context context, Controller controller) 
	{
		StateChangeEvent newEvent = new StateChangeEvent(agent, step, state, timestamp, ancestor, parameters, results, context, controller);
		
		this.currentTrace_.add(newEvent);
		
		return newEvent;
	}
	
	public void save() throws IOException 
	{
		BufferedWriter out = new BufferedWriter(new FileWriter(this.traceName_ + ".trace"));
		
		try {
			XMLIO.write(this.traces_, out);
		}
		finally {
			out.close();
		}
	}
}
