package com.thomas.ibpm;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author: yantx
 * @version: 1.0
 * @date: 2021-04-01 16:21
 * @description:
 */
@Slf4j
@SpringBootTest
public class TaskTest {

    @Autowired
    private TaskService taskService;

    // 查询所有任务
    @Test
    public void getTasks(){
       List<Task> tasks = taskService.createTaskQuery().list();
       for (Task task : tasks){
           log.info("id："+ task.getId());
           log.info("name："+ task.getName());
           log.info("Assignee："+ task.getAssignee());
       }
    }
    // 根据处理人查询
    @Test
    public void getTaskByAssignee(){
        List<Task> tasks = taskService.createTaskQuery().taskAssignee("tangseng").list();
        for (Task task : tasks){
            log.info("id："+ task.getId());
            log.info("name："+ task.getName());
            log.info("Assignee："+ task.getAssignee());
        }
    }

    /**
     * 执行任务
     */
    @Test
    public void completeTask(){

        taskService.complete("2772e8f4-9354-11eb-8ed1-90324b059e89");
        log.info("完成任务--------");
    }
    /**
     * 拾取任务
     */
    @Test
    public void claimTask(){
        Task task = taskService.createTaskQuery().taskId("2772e8f4-9354-11eb-8ed1-90324b059e89").singleResult();
        taskService.claim(task.getId(), "shaseng");
    }
    /**
     * 交办和归还任务
     */
    @Test
    public void setTaskAssignee(){
        Task task = taskService.createTaskQuery().taskId("2772e8f4-9354-11eb-8ed1-90324b059e89").singleResult();
        taskService.setAssignee(task.getId(), "null"); // 归还任务
        taskService.setAssignee(task.getId(), "bajie"); // 交办
    }

}
