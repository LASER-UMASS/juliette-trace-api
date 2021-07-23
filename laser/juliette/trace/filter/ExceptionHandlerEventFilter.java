package laser.juliette.trace.filter;

import laser.juliette.trace.Controller;
import laser.juliette.trace.Event;
import laser.juliette.trace.Event.Annotation;
import laser.juliette.trace.EventFilter;
import laser.juliette.trace.ReactiveController;
import laser.juliette.trace.StateChangeEvent;


/**
 * The ExceptionHandlerEventFilter class accepts StateChangeEvents corresponding
 * to exception handlers.
 */
public class ExceptionHandlerEventFilter implements EventFilter 
{
	/**
	 * Automatically generated serial version UID
	 */
	private static final long serialVersionUID = 5943849783891888468L;

	
	/**
	 * Creates a new ExceptionHandlerEventFilter.
	 */
	public ExceptionHandlerEventFilter() {
		super();
	}
	
	@Override
	public boolean accept(Event event) {
		if (event instanceof StateChangeEvent) {
			StateChangeEvent stateChangeEvent = (StateChangeEvent)event;
			Annotation handlerContinuationActionAnnotation = stateChangeEvent.getAnnotation(StateChangeEvent.HANDLER_CONTINUATION_ACTION_ANNOTATION_NAME);
			Controller controller = stateChangeEvent.getController();			
			if ((handlerContinuationActionAnnotation != null) &&
				(controller instanceof ReactiveController)) 
			{
				return true;
			}
		}
		
		return false;
	}
}
