package com.antplatform.admin.common.log;

import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.filter.AbstractFilter;

/**
 * @author: maoyan
 * @date: 2020/1/22 15:42:34
 * @description:
 */
/**
 * This filter returns the onMatch result if the level in the LogEvent is the same or more specific
 * than the configured level and the onMismatch value otherwise. For example, if the ThresholdFilter
 * is configured with Level ERROR and the LogEvent contains Level DEBUG then the onMismatch value will
 * be returned since ERROR events are more specific than DEBUG.
 * <p>
 * The default Level is ERROR.
 */
@Plugin(name = "SuppressLogStackTraceFilter", category = Node.CATEGORY, elementType = Filter.ELEMENT_TYPE, printObject = true)
public final class SuppressLogStackTraceFilter extends AbstractFilter {

    private static final long serialVersionUID = 1L;

    private int depth = -1;

    private SuppressLogStackTraceFilter(final int depth, final Result onMatch, final Result onMismatch) {
        super(onMatch, onMismatch);
        this.depth = depth;
    }

    @Override
    public Result filter(final LogEvent event) {
        assignDepthLogTrace(event.getThrown(), this.depth);
        return onMatch;
    }

    public final static Throwable assignDepthLogTrace(Throwable throwable, int depth) {
        while (throwable != null) {
            // 获取堆栈信息
            StackTraceElement[] stackTraceElements = throwable.getStackTrace();
            // 截短堆栈
            if (depth > -1 && stackTraceElements != null && stackTraceElements.length > depth) {
                StackTraceElement[] truncateStackTraceElement = new StackTraceElement[depth];
                // 只需要最上层的堆栈信息就好
                for (int currLevel = 0; currLevel < depth; currLevel++) {
                    truncateStackTraceElement[currLevel] = stackTraceElements[currLevel];
                }
                throwable.setStackTrace(truncateStackTraceElement);
            }
            throwable = throwable.getCause() != throwable ? throwable.getCause() : null;
        }
        return throwable;
    }

    /**
     * Create a ThresholdFilter.
     *
     * @param depth    The log max depth to output.
     * @param match    The action to take on a match.
     * @param mismatch The action to take on a mismatch.
     * @return The created ThresholdFilter.
     */
    @PluginFactory
    public static SuppressLogStackTraceFilter createFilter(
            @PluginAttribute("depth") final int depth,
            @PluginAttribute("onMatch") final Result match,
            @PluginAttribute("onMismatch") final Result mismatch) {
        final int actualdepth = depth;
        final Result onMatch = match == null ? Result.NEUTRAL : match;
        final Result onMismatch = mismatch == null ? Result.DENY : mismatch;
        return new SuppressLogStackTraceFilter(actualdepth, onMatch, onMismatch);
    }

}