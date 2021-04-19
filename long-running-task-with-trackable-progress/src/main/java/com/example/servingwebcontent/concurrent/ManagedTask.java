package com.example.servingwebcontent.concurrent;

/**
 * Inspired by:
 * - https://stackoverflow.com/questions/33009721/long-running-rest-api-with-queues
 * - https://docs.oracle.com/middleware/1213/jdev/api-reference-esdk/oracle/ide/task/Task.html
 * - http://bits.netbeans.org/dev/javadoc/org-netbeans-api-progress/overview-summary.html
 * - https://docs.oracle.com/javaee/7/api/javax/enterprise/concurrent/ManagedTaskListener.html
 * - https://docs.oracle.com/javaee/7/api/javax/enterprise/concurrent/ManagedTask.html
 * - https://developer.android.com/topic/libraries/architecture/workmanager/how-to/intermediate-progress
 * - https://developer.android.com/topic/libraries/architecture/workmanager/advanced/long-running
 * - https://docs.oracle.com/javase/9/docs/api/javafx/concurrent/Task.html
 */
public abstract class ManagedTask implements Runnable {

    private final String id;
    protected String progress;

    public ManagedTask(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getProgress() {
        return progress;
    }
}
