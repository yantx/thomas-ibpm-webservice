package com.thomas.ibpm;

import com.thomas.ibpm.config.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author: yantx
 * @version: 1.0
 * @date: 2021-04-08 09:10
 * @description:  activiti7 新特性API TaskRuntime
 */
@Slf4j
@SpringBootTest
public class TaskRuntimeTest {

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private TaskRuntime taskRuntime;


    /**
     * 查询当前登录用户的任务
     */
    @Test
    public void getTasks(){
        securityUtil.logInAs("bajie");
        Page<Task> tasks= taskRuntime.tasks(Pageable.of(0,10));
        List<Task> taskList = tasks.getContent();
        for (Task task : taskList){
            log.info("----------------------------------");
            log.info("Id："+ task.getId());
            log.info("Name："+ task.getName());
            log.info("Status："+ task.getStatus());
            log.info("CreatedDate："+ task.getCreatedDate());
            if(StringUtils.isEmpty(task.getAssignee())){
                // 候选人为当前登录用户 处理人为空时 需要执行拾取操作
                log.info("执行拾取操作");
            }else {
                log.info("Assignee："+ task.getAssignee());
            }
        }
    }

    /**
     * 执行任务
     */
    @Test
    public void completeTask(){
        securityUtil.logInAs("bajie");
        String taskId = "111";
        Task task= taskRuntime.task(taskId);
        if(StringUtils.isEmpty(task.getAssignee())){
            taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
        }
        taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
        log.info("执行完成");
    }
}
