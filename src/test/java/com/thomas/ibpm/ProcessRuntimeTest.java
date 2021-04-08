package com.thomas.ibpm;

import com.thomas.ibpm.config.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author: yantx
 * @version: 1.0
 * @date: 2021-04-07 16:18
 * @description: activiti7 新特性API ProcessRuntime
 */
@Slf4j
@SpringBootTest
public class ProcessRuntimeTest {
    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 获取流程实例
     */
    @Test
    public void getProcessInstance(){
        securityUtil.logInAs("bajie");
        Page<ProcessInstance> processInstancePage = processRuntime.processInstances(Pageable.of(0,10));
        log.info("流程实例总数："+processInstancePage.getTotalItems());
        List<ProcessInstance> processInstances = processInstancePage.getContent();
        for(ProcessInstance pi: processInstances){
            log.info("ID："+pi.getId());
            log.info("Name："+pi.getName());
            log.info("StartDate："+pi.getStartDate());
            log.info("Status："+pi.getStatus());
            log.info("ProcessDefinitionId："+pi.getProcessDefinitionId());
            log.info("ProcessDefinitionKey："+pi.getProcessDefinitionKey());
        }
    }

    /**
     * 激活流程实例
     */
    @Test
    public void startProcessInstance(){
        securityUtil.logInAs("bajie");
        // 启动时添加参数使用 .withVariable()
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder.start().withProcessDefinitionKey("defKey").withName("流程实例名称").withBusinessKey("自定义BusinessKey").build());
    }

    /**
     * 激活流程实例
     */
    @Test
    public void delProcessInstance(){
        securityUtil.logInAs("bajie");
        String processDefinitionId = "111";
        ProcessInstance processInstance = processRuntime.delete(ProcessPayloadBuilder.delete().withProcessInstanceId(processDefinitionId).withReason("删除原因").build());
    }

    /**
     * 激活流程实例
     */
    @Test
    public void suspendProcessInstance(){
        securityUtil.logInAs("bajie");
        String processDefinitionId = "111";
        ProcessInstance processInstance = processRuntime.suspend(ProcessPayloadBuilder.suspend().withProcessInstanceId(processDefinitionId).build());
    }

    /**
     * 激活流程实例
     */
    @Test
    public void resumeProcessInstance(){
        securityUtil.logInAs("bajie");
        String processDefinitionId = "111";
        ProcessInstance processInstance = processRuntime.resume(ProcessPayloadBuilder.resume().withProcessInstanceId(processDefinitionId).build());
    }

    /**
     * 获取流程实例的参数
     */
    @Test
    public void getVariable(){
        securityUtil.logInAs("bajie");
        String processDefinitionId = "111";
        List<VariableInstance> variableInstances = processRuntime.variables(ProcessPayloadBuilder.variables().withProcessInstanceId(processDefinitionId).build());
        for (VariableInstance vi: variableInstances){
            log.info("Name："+ vi.getName());
            log.info("Value："+ vi.getValue());
            log.info("TaskId："+ vi.getTaskId());
            log.info("ProcessInstanceId："+ vi.getProcessInstanceId());
        }
    }
}
