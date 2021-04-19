package com.example.servingwebcontent.concurrent;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * Inspired by:
 * - https://docs.oracle.com/middleware/1213/jdev/api-reference-esdk/oracle/ide/task/TaskMonitor.html
 * - http://bits.netbeans.org/dev/javadoc/org-netbeans-api-progress/overview-summary.html
 * - https://docs.oracle.com/javaee/7/api/javax/enterprise/concurrent/ManagedTaskListener.html
 */
@Service
public class TaskRegistry {

    private Map<String, ManagedTask> tasks = new HashMap<>();

    public ManagedTask getTask(String id) {
        return tasks.get(id);
    }

    public void submit(ManagedTask task) {
        Assert.notNull(task.getId(), "Parameter task.id cannot be null");
        (new Thread(task)).start();
        tasks.put(task.getId(), task);
    }
}
