package laser.juliette.trace.test;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;

import org.junit.Test;
import org.junit.Assert;

import laser.juliette.trace.Event;
import laser.juliette.trace.Event.Annotation;
import laser.juliette.trace.StateChangeEvent;
import laser.juliette.trace.StateChangeEvent.State;
import laser.juliette.trace.Trace;
import laser.juliette.trace.Traces;

public class TraceAPITestCases 
{
	//Method was Written to autogenerate A timestamp upon calling.  I was lazy
	private long getTimeStamp(){
		Date currentDate = Calendar.getInstance().getTime();
		long currentTimestamp = currentDate.getTime();
		return currentTimestamp;
	}
	
    @Test
    public void testNewTracesNonNullAndEmpty() {
    	Traces newTraces = new Traces();
    	Assert.assertNotNull("The newTraces should be non-null.", newTraces);
    	Assert.assertTrue("The new Traces should be empty.", newTraces.isEmpty());
    }
    
    @Test
    public void testNewTraceNonNullAndEmpty() {
    	Trace newTrace = new Trace();
    	Assert.assertNotNull("The new Trace should be non-null", newTrace); 	
    	Assert.assertFalse("The new Trace should be empty.", newTrace.iterator().hasNext());
    }
    
    @Test 
    public void testNewStateChangeEventUnknownAgent() {
    	Event parentEvent = null;
    	String childEventName = "perform blood transfusion";
    	State childEventState = State.posted;
    	Date childEventDateTime = Calendar.getInstance().getTime();
    	long childEventTimestamp = childEventDateTime.getTime();
    	StateChangeEvent childEvent = new StateChangeEvent(childEventName, childEventState, childEventTimestamp, parentEvent);
    	Assert.assertNotNull(childEvent);
    	Assert.assertEquals(parentEvent, childEvent.getAncestor());
    	Assert.assertEquals(StateChangeEvent.UNKNOWN_AGENT, childEvent.getAgentName());
    	Assert.assertEquals(childEventName, childEvent.getEventName());
    	Assert.assertEquals(childEventState, childEvent.getState());
    	Assert.assertEquals(childEventTimestamp, childEvent.getTimestamp());
    }
    
    @Test
    public void testNewStateChangeEventFullConstructor(){
    	long childEventTimestamp = getTimeStamp();
    	Event parentEvent = null;
    	String childEventName = "perform blood transfusion";
    	HashMap<String,String> childParameters = new HashMap<String,String>();
    	Assert.assertNotNull(childParameters);
    	Assert.assertTrue(childParameters.isEmpty());
    	childParameters.put("Patient","Name");
    	
    	Assert.assertTrue(childParameters.get("Patient").equals("Name"));
    	
    	StateChangeEvent childEvent = new StateChangeEvent(StateChangeEvent.UNKNOWN_AGENT,childEventName,State.posted,childEventTimestamp,parentEvent,childParameters,null,null,null);
    	Assert.assertNotNull(childEvent);
    	Assert.assertEquals(childEvent.getParameters().get("Patient"),childParameters.get("Patient"));
    }
    
    //To Do: testNewStateChangeEvent using the other constructor with input parameters
    
    //To Do: testNewStateChangeEvent using the other constructor with completed and output parameters (using results)
    
    //To Do: testNewStateChangeEvent using the other constructor with terminated and exceptions thrown (using results)
    
    @Test
    public void testTracesAdd(){
    	Traces newTraces = new Traces();
    	Trace newTrace = new Trace();
    	newTraces.add(newTrace);
    	Assert.assertEquals(newTraces.size(), 1);
    	Assert.assertTrue(newTraces.get(0).equals(newTrace));
    	Assert.assertTrue(newTraces.contains(newTrace));
    	Assert.assertNotNull(newTraces.get(0));
    	Trace secondTrace = new Trace();
    	newTraces.add(secondTrace);
    	Assert.assertTrue(newTraces.contains(newTrace));
    	Assert.assertTrue(newTraces.contains(secondTrace));
    	Assert.assertEquals(newTraces.size(), 2);
    	Assert.assertTrue(newTraces.get(1).equals(secondTrace));
    	Assert.assertFalse(newTraces.get(1).equals(newTrace));
    }
    
    @Test
    public void testTracesRemove(){
    	Traces newTraces = new Traces();
    	Trace newTrace = new Trace();
    	newTraces.add(newTrace);
    	newTraces.remove(0);
    	Assert.assertEquals(newTraces.size(), 0);
    	Assert.assertFalse(newTraces.contains(newTrace));
    }
    
    @Test
    public void testTraceGetEventDoesNotExist() {
    	Trace newTrace = new Trace();
    	int ID = 0;
    	Assert.assertEquals(0, newTrace.length());
    	Event foundEvent = newTrace.get(ID);
    	Assert.assertNull(foundEvent);
    }
    
    @Test
    public void testTraceGetEventDoesExist() {
    	Trace newTrace = new Trace();
    	Event testEvent;
    	Event testParentEvent = null;
    	String testEventName = "perform mock blood transfusion";
    	State testEventState = State.posted;
    	Date testEventDateTime = Calendar.getInstance().getTime();
    	long testEventTimestamp = testEventDateTime.getTime();
    	testEvent = new StateChangeEvent(testEventName, testEventState, testEventTimestamp, testParentEvent);
    	newTrace.add(testEvent);
    	Assert.assertEquals(testEvent, newTrace.get(testEvent.getID()));
    }
    
    @Test
    public void testTraceAddOneEvent(){
    	Trace newTrace = new Trace();
    	Event testEvent;
    	Event testParentEvent = null;
    	String testEventName = "perform mock blood transfusion";
    	State testEventState = State.posted;
    	Date testEventDateTime = Calendar.getInstance().getTime();
    	long testEventTimestamp = testEventDateTime.getTime();
    	testEvent = new StateChangeEvent(testEventName, testEventState, testEventTimestamp, testParentEvent);
    	newTrace.add(testEvent);
    	Assert.assertEquals(1, newTrace.length());
    	Event testEventPrime = newTrace.iterator().next();
    	Assert.assertEquals(testEvent, testEventPrime);
    }
    
    @Test
    public void testTraceAddTwoEvents(){
    	Trace newTrace = new Trace();
    	Event testEvent;
    	Event testParentEvent = null;
    	String testEventName = "perform mock blood transfusion";
    	State testEventState = State.posted;
    	Date testEventDateTime = Calendar.getInstance().getTime();
    	long testEventTimestamp = testEventDateTime.getTime();
    	testEvent = new StateChangeEvent(testEventName, testEventState, testEventTimestamp, testParentEvent);
    	newTrace.add(testEvent);
    	Assert.assertEquals(1, newTrace.length());
    	Iterator<Event> newEventsItr = newTrace.iterator();
    	Assert.assertEquals(testEvent, newEventsItr.next());
    	
    	Event testEvent2;
    	Date testEvent2DateTime = Calendar.getInstance().getTime();
    	long testEvent2Timestamp = testEvent2DateTime.getTime();
    	testEvent2 = new StateChangeEvent("perform actual blood transfusion", State.posted, testEvent2Timestamp, testEvent);
    	newTrace.add(testEvent2);
    	Assert.assertEquals(2, newTrace.length());
    	Iterator<Event> newEventsItr2 = newTrace.iterator();
    	Assert.assertEquals(testEvent, newEventsItr2.next());
    	Assert.assertEquals(testEvent2, newEventsItr2.next());
    }
    
    public void testTraceGetPropertyDoesNotExist() {
    	// Attempt to get a trace property that doesn't exist
    	Trace newTrace = new Trace();
    	Assert.assertEquals(null, newTrace.getProperty(Trace.PROCESS_NAME_PROPERTY_NAME));    
    }
    
    @Test
    public void testTraceGetPropertyDoesExist(){
    	// Attempt to get a trace property that does exist
    	Trace newTrace = new Trace();
    	String newProcessName = "perform mock blood transfusion";
    	Assert.assertEquals(newTrace.getProperty(Trace.PROCESS_NAME_PROPERTY_NAME), null);
    	newTrace.setProperty(Trace.PROCESS_NAME_PROPERTY_NAME, newProcessName);
    	Assert.assertEquals(newProcessName, newTrace.getProperty(Trace.PROCESS_NAME_PROPERTY_NAME));
    }
    
    @Test
    public void testEventGetAnnotationDoesNotExist(){
    	Event plainEvent = new StateChangeEvent(null,State.posted,getTimeStamp(),null); //This event has no Annotations
    	List<Annotation> emptyAnnotations = plainEvent.getAnnotations();
    	Assert.assertNotNull(Event.ISLEAF_ANNOTATION_NAME, emptyAnnotations);
    	
    	//This list should be completely empty
    	Assert.assertEquals(emptyAnnotations.size(), 0);
    }
    
    @Test
    public void TestEventGetAnnotationDoesExist(){
    	//As this event is for testing, few of the requirements must be filled
    	Event annotatedEvent = new StateChangeEvent(null,null,getTimeStamp(),null);
    	String isLeafAnnotationValue = "true";
    	
    	annotatedEvent.addAnnotation(Event.ISLEAF_ANNOTATION_NAME, isLeafAnnotationValue);
    	List<Annotation> annotationList = annotatedEvent.getAnnotations();
    	Assert.assertNotNull(annotationList);
    	Assert.assertEquals(annotationList.size(), 1);
    	Annotation annotation = annotationList.get(0);
    	Assert.assertEquals(Event.ISLEAF_ANNOTATION_NAME, annotation.getKind());
    	Assert.assertEquals(isLeafAnnotationValue, annotation.getValue());
    }
    
} // end for TraceAPITestCases
