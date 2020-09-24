package com.antplatform.admin.common.tracker;

/**
 * Tracker Context's Holder
 *
 * @author: maoyan
 * @date: 2020/9/1 16:16:21
 * @description:
 */
public class ExecutionContextHolder {

    private static final ThreadLocal<ExecutionContext> CONTEXT = new InitialInstanceThreadLocal<ExecutionContext>(
            ExecutionContext.class);

    /**
     * retrieve current app's tracker context
     *
     * @return
     */
    public static ExecutionContext getContext() {
        return CONTEXT.get();
    }

    public static void clearContext() {
        ExecutionContext executionContext = getContext();
        executionContext.setTrackerContext(null);
        executionContext.clear();
    }

    /**
     * retrieve current app's tracker context
     * @return
     */
    public static TrackerContext getTrackerContext() {
        return getContext().getTrackerContext();
    }

    /**
     * whether track function is required
     * @return
     */
    public static boolean isTrackRequired() {
        TrackerContext trackerContext = getTrackerContext();
        return trackerContext != null && trackerContext.isTrackRequired();
    }

    /**
     * set current app's tracker context
     * @param trackerContext
     */
    public static void setTrackerContext(TrackerContext trackerContext) {
        trackerContext.setArriveTime(System.nanoTime());
        getContext().setTrackerContext(trackerContext);
    }

    /**
     * create remote tracker context based on current app's tracker context
     * @param location
     * @return
     */
    public static TrackerContext createRemoteTrackerContext(String location) {
        TrackerContext trackerContext = getTrackerContext();
        if (trackerContext == null) {
            return null;
        }
        return trackerContext.createRemoteContext(location);
    }

    public static void addSucceedRemoteTrackerContext(TrackerContext context) {
        context.setSucceed(true);
        addRemoteTrackerContext(context);
    }

    public static void addFailedRemoteTrackerContext(TrackerContext context) {
        context.setSucceed(false);
        addRemoteTrackerContext(context);
    }

    /**
     * add remote service's tracker context to current app's tracker context
     * @param context
     */
    private static void addRemoteTrackerContext(TrackerContext context) {
        TrackerContext trackerContext = getContext().getTrackerContext();
        if (trackerContext == null) {
            throw new IllegalStateException("Current Tracker Context is not set yet.");
        }
        context.setReturnTime(System.nanoTime());
        trackerContext.addRemoteContext(context);
    }

}
