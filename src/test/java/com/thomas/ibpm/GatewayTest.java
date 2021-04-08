package com.thomas.ibpm;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: yantx
 * @version: 1.0
 * @date: 2021-04-07 14:48
 * @description:
 */
@Slf4j
@SpringBootTest
public class GatewayTest {
    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;

    @Test
    public void initProcessInstance(){
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("MyProcessParallel");
        log.info("流程实例ID："+ processInstance.getProcessDefinitionId());
    }

    @Test
    public void completeTask(){
        taskService.complete("dbed6485-975e-11eb-9fc7-90324b059e89");
        log.info("完成任务--------");
    }
}
