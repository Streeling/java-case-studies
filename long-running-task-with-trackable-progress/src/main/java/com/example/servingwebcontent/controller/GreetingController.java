package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.service.GreetingTask;
import com.example.servingwebcontent.concurrent.ManagedTask;
import com.example.servingwebcontent.concurrent.TaskRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;

@Controller
public class GreetingController {

	private static final String UNIQUE_GENERATED_TASK_ID = "1";

    @Resource
    private TaskRegistry taskRegistry;

    @GetMapping("/greeting")
    public String greeting(Model model) {
		ManagedTask task = taskRegistry.getTask(UNIQUE_GENERATED_TASK_ID);
		if (task == null) {
			task = new GreetingTask(UNIQUE_GENERATED_TASK_ID);
			taskRegistry.submit(task);
		}
        model.addAttribute("task", task);
        return "greeting";
    }

    @GetMapping("/status")
    public ResponseEntity<String> status(@RequestParam String taskId) {
        ManagedTask task = taskRegistry.getTask(taskId);
        if (task == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(task.getProgress());
        }
    }
}