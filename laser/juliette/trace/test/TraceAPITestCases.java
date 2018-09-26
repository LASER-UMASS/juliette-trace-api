package laser.juliette.trace.test;

import laser.juliette.trace.Trace;
import laser.juliette.trace.Traces;

import org.junit.Assert;
import org.junit.Test;


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
    
} // end for TraceAPITestCases
