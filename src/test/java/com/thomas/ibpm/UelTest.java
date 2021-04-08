package com.thomas.ibpm;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: yantx
 * @version: 1.0
 * @date: 2021-04-06 16:11
 * @description: uel表达式
 */
@Slf4j
@SpringBootTest
public class UelTest {

    @Autowired
    private TaskService taskService;
    @Autowired
    private RuntimeService runtimeService;

    /**
     * 启动流程实例带参数 指定执行人 ${taskAssignee}
     */
    @Test
    public void initProcessInstanceWithArgs(){
        // 流程变量
        Map<String,Object> variables = new HashMap<>();
        variables.put("taskAssignee", "bajie");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("UelTestV2","bizuel002",variables);
        log.info("流程实例ID："+ processInstance.getProcessDefinitionId());
    }
    /**
     * 执行任务实例带参数
     */
    @Test
    public void completeTaskWithArgs(){
        Map<String,Object> variables = new HashMap<>();
        variables.put("money", "101");

        taskService.complete("dbed6485-975e-11eb-9fc7-90324b059e89",variables);
        log.info("完成任务--------");
    }
    /**
     * 启动流程实例带参数  参数为实体类
     */
    @Test
    public void initProcessInstanceWithClassArgs(){
        UelPojo pojo = new UelPojo();
        pojo.setAssignee("bajie");
        Map<String,Object> variables = new HashMap<>();
        variables.put("uelpojo", pojo);

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("UelTestV3","bizuel002",variables);
        log.info("流程实例ID："+ processInstance.getProcessDefinitionId());
    }
    /**
     * 执行任务  指定多个候选人
     */
    @Test
    public void initProcessInstanceWithCandiDateArgs(){
        Map<String,Object> variables = new HashMap<>();
        variables.put("candidates", "wukong,tangseng");

        taskService.complete("dbed6485-975e-11eb-9fc7-90324b059e89",variables);
        log.info("完成任务--------");
    }
    /**
     * 直接指定流程变量
     */
    @Test
    public void otherArgs(){
        // 参数1：任务ID； 参数2：流程参数的key； 参数3：流程参数的value
        taskService.setVariable("","","");

        // 一次指定多个参数通过map
        Map<String,Object> variables = new HashMap<>();
        taskService.setVariables("",variables);
    }


    /**
     * 直接指定流程变量  (局部变量当前环节中可用)
     */
    @Test
    public void otherLocalArgs(){
        // 参数1：任务ID； 参数2：流程参数的key； 参数3：流程参数的value
        taskService.setVariableLocal("","","");

        // 一次指定多个参数通过map
        Map<String,Object> variables = new HashMap<>();
        taskService.setVariablesLocal("",variables);
    }
}
