/*
 * Author: Pablo Lalloni <plalloni@gmail.com>
 * Created: 07/12/2010 18:05:10
 */
package afip.dit.common.jboss.logging;

import static org.slf4j.spi.LocationAwareLogger.DEBUG_INT;
import static org.slf4j.spi.LocationAwareLogger.ERROR_INT;
import static org.slf4j.spi.LocationAwareLogger.INFO_INT;
import static org.slf4j.spi.LocationAwareLogger.TRACE_INT;
import static org.slf4j.spi.LocationAwareLogger.WARN_INT;

import java.util.Map;

import org.jboss.logging.LoggerPlugin;
import org.jboss.logging.MDCProvider;
import org.jboss.logging.MDCSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.slf4j.spi.LocationAwareLogger;

/**
 * @author Pablo Lalloni <plalloni@gmail.com>
 * @since 07/12/2010 18:05:10
 */
public class SLF4JLoggerPlugin implements LoggerPlugin, MDCSupport {

    private static final MDCProvider mdc = new MDCProvider() {

        @Override
        public void put(String key, Object value) {
            MDC.put(key, value.toString());
        }

        @Override
        public Object get(String key) {
            return MDC.get(key);
        }

        @Override
        public void remove(String key) {
            MDC.remove(null);
        }

        @Override
        public Map<String, Object> getMap() {
            return MDC.getCopyOfContextMap();
        }

    };

    private Logger log;

    private LocationAwareLogger loc;

    @Override
    public MDCProvider getMDCProvider() {
        return mdc;
    }

    @Override
    public void init(String name) {
        log = LoggerFactory.getLogger(name);
        if (log instanceof LocationAwareLogger) {
            loc = (LocationAwareLogger) log;
        }
    }

    @Override
    public boolean isTraceEnabled() {
        return log.isTraceEnabled();
    }

    @Override
    public void trace(Object message) {
        log.trace(message.toString());
    }

    @Override
    public void trace(Object message, Throwable t) {
        log.trace(message.toString(), t);
    }

    @Override
    public void trace(String loggerFcqn, Object message, Throwable t) {
        if (loc != null) {
            loc.log(null, loggerFcqn, TRACE_INT, message.toString(), null, t);
        } else {
            log.trace(message.toString(), t);
        }
    }

    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public void debug(Object message) {
        log.debug(message.toString());
    }

    @Override
    public void debug(Object message, Throwable t) {
        log.debug(message.toString(), t);
    }

    @Override
    public void debug(String loggerFcqn, Object message, Throwable t) {
        if (loc != null) {
            loc.log(null, loggerFcqn, DEBUG_INT, message.toString(), null, t);
        } else {
            log.debug(message.toString(), t);
        }
    }

    @Override
    public boolean isInfoEnabled() {
        return log.isDebugEnabled();
    }

    @Override
    public void info(Object message) {
        log.info(message.toString());
    }

    @Override
    public void info(Object message, Throwable t) {
        log.info(message.toString(), t);
    }

    @Override
    public void info(String loggerFcqn, Object message, Throwable t) {
        if (loc != null) {
            loc.log(null, loggerFcqn, INFO_INT, message.toString(), null, t);
        } else {
            log.info(message.toString(), t);
        }
    }

    @Override
    public void warn(Object message) {
        log.warn(message.toString());
    }

    @Override
    public void warn(Object message, Throwable t) {
        log.warn(message.toString(), t);
    }

    @Override
    public void warn(String loggerFcqn, Object message, Throwable t) {
        if (loc != null) {
            loc.log(null, loggerFcqn, WARN_INT, message.toString(), null, t);
        } else {
            log.warn(message.toString(), t);
        }
    }

    @Override
    public void error(Object message) {
        log.error(message.toString());
    }

    @Override
    public void error(Object message, Throwable t) {
        log.error(message.toString(), t);
    }

    @Override
    public void error(String loggerFcqn, Object message, Throwable t) {
        if (loc != null) {
            loc.log(null, loggerFcqn, ERROR_INT, message.toString(), null, t);
        } else {
            log.error(message.toString(), t);
        }
    }

    @Override
    public void fatal(Object message) {
        error(message);
    }

    @Override
    public void fatal(Object message, Throwable t) {
        error(message, t);
    }

    @Override
    public void fatal(String loggerFcqn, Object message, Throwable t) {
        error(loggerFcqn, message, t);
    }

}
