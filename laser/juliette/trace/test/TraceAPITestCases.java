package laser.juliette.trace.test;

import laser.juliette.trace.Trace;
import laser.juliette.trace.Traces;
import laser.juliette.trace.StateChangeEvent;
import laser.juliette.trace.StateChangeEvent.State;
import laser.juliette.trace.Event;

import org.junit.Assert;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

public class TraceAPITestCases 
{
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
    
    //To Do: testNewStateChangeEvent using the other constructor with input parameters
    
    //To Do: testNewStateChangeEvent using the other constructor with completed and output parameters (using results)
    
    //To Do: testNewStateChangeEvent using the other constructor with terminated and exceptions thrown (using results)
    
    @Test
    public void testTracesAddition(){
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
    public void testTracesRemoval(){
    	Traces newTraces = new Traces();
    	Trace newTrace = new Trace();
    	newTraces.add(newTrace);
    	newTraces.remove(0);
    	Assert.assertEquals(newTraces.size(), 0);
    	Assert.assertFalse(newTraces.contains(newTrace));
    }
    
    @Test
    public void testTraceSingleAddition(){
    	Trace newTrace = new Trace();
    	Event testEvent;
    	Event testParentEvent = null;
    	String testEventName = "perform mock blood transfusion";
    	State testEventState = State.posted;
    	Date testEventDateTime = Calendar.getInstance().getTime();
    	long testEventTimestamp = testEventDateTime.getTime();
    	testEvent = new StateChangeEvent(testEventName, testEventState, testEventTimestamp, testParentEvent);
    	newTrace.add(testEvent);
    	Iterator<Event> newTraceIterator = newTrace.iterator();
    	Assert.assertNotNull(newTraceIterator);
    	Assert.assertTrue(newTraceIterator.hasNext());
    	Assert.assertTrue(newTraceIterator.next().equals(testEvent));
    	Assert.assertFalse(newTraceIterator.hasNext());
    }
    
    @Test
    public void testTraceMultipleAddition(){
    	Trace newTrace = new Trace();
    	Event testEvent;
    	Event testParentEvent = null;
    	String testEventName = "perform mock blood transfusion";
    	State testEventState = State.posted;
    	Date testEventDateTime = Calendar.getInstance().getTime();
    	long testEventTimestamp = testEventDateTime.getTime();
    	testEvent = new StateChangeEvent(testEventName, testEventState, testEventTimestamp, testParentEvent);
    	Assert.assertFalse(newTrace.iterator().hasNext());
    	newTrace.add(testEvent);
    	Assert.assertTrue(newTrace.iterator().hasNext());
    	Assert.assertTrue(newTrace.iterator().next().equals(testEvent));
    	Event testEvent2;
    	Date testEvent2DateTime = Calendar.getInstance().getTime();
    	long testEvent2Timestamp = testEvent2DateTime.getTime();
    	testEvent2 = new StateChangeEvent("perform actual blood transfusion", State.posted, testEvent2Timestamp, testEvent);
    	newTrace.add(testEvent2);
    	Iterator<Event> newTraceIterator = newTrace.iterator();
    	Assert.assertTrue(newTraceIterator.hasNext());
    	Event iterator1 = newTraceIterator.next();
    	Assert.assertTrue(iterator1.equals(testEvent));
    	Assert.assertFalse(iterator1.equals(testEvent2));
    	Assert.assertTrue(newTraceIterator.hasNext());
    	iterator1 = null;
    	Event iterator2 = newTraceIterator.next();
    	Assert.assertTrue(iterator2.equals(testEvent2));
    	Assert.assertFalse(iterator2.equals(testEvent));
    	iterator2 = null;
    	Assert.assertFalse(newTraceIterator.hasNext());
    	newTraceIterator = null;
    }
    
    @Test
    public void testSetAndGetTraceProperty(){
    	Trace newTrace = new Trace();
    	newTrace.setProperty("Blood Type", "AB");
    	Assert.assertEquals(newTrace.getProperty("Blood Type"), "AB");
    }
    
    //To Do: testGetTraceProperty when that property does not exist
    
    //To Do: testEventGetAnnotation(s) when no annotations exist
    
    //To Do: testEventAddAnnotation then testEventGetAnnotation(s) when that annotation exists
    
} // end for TraceAPITestCases
