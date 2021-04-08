package com.thomas.ibpm;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author: yantx
 * @version: 1.0
 * @date: 2021-04-01 11:37
 * @description:
 */
@Slf4j
@SpringBootTest
public class ProcessInstanceTest {

    @Autowired
    private RuntimeService runtimeService;

    /**
     * 初始化流程实例
     */
    @Test
    public void initProcessInstance(){
        // 表单填报的内容
        // 表单数据 插入业务表， 返回业务表主键 作为bussinessKey
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("ReimbursementProcess","biz001");
        log.info("流程实例ID："+ processInstance.getProcessDefinitionId());
    }

    /**
     * 查询流程实列列表
     */
    @Test
    public void getProcessInstances(){
        List<ProcessInstance> processInstances = runtimeService.createProcessInstanceQuery().list();
        for (ProcessInstance q :processInstances){
            log.info("BussinessKey："+q.getBusinessKey());
            log.info("ProcessInstanceId："+q.getProcessInstanceId());
            log.info("ProcessDefinitionId："+q.getProcessDefinitionId());
            log.info("isEnded："+q.isEnded());
            log.info("isSuspended："+q.isSuspended());
        }
    }

    /**
     * 流程实例 暂停 激活
     */
    @Test
    public void activeProcessInstance(){
        // 流程实例  暂停
//        runtimeService.suspendProcessInstanceById("aec17bca-92ba-11eb-a447-90324b059e89");
//        log.info("流程实例挂起");

        // 激活
        runtimeService.activateProcessInstanceById("aec17bca-92ba-11eb-a447-90324b059e89");
        log.info("流程实例激活");
    }
    /**
     * 流程实例删除 删除
     */
    @Test
    public void delProcessInstance(){
        runtimeService.deleteProcessInstance("276b20c0-9354-11eb-8ed1-90324b059e89","删除原因废弃");
        log.info("流程实例删除");
    }
}
