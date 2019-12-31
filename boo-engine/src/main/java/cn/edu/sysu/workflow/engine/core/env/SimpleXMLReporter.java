package cn.edu.sysu.workflow.engine.core.env;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.stream.Location;
import javax.xml.stream.XMLReporter;
import java.io.Serializable;

/**
 * Custom {@link XMLReporter} that logs the StAX parsing warnings in the
 * SCXML document.
 *
 * @since 1.0
 */
public class SimpleXMLReporter implements XMLReporter, Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Log.
     */
    private Log log = LogFactory.getLog(getClass());

    /**
     * Constructor.
     */
    public SimpleXMLReporter() {
        super();
    }

    /**
     * @see XMLReporter#report(String, String, Object, Location)
     */
    public void report(final String message, final String errorType, final Object relatedInformation,
                       final Location location) {
        if (log.isWarnEnabled()) {
            log.warn("[" + errorType + "] " + message + " (" + relatedInformation + ") at " + location);
        }

    }

}
