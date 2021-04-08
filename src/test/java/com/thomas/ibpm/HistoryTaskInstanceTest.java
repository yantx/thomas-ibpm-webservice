package com.thomas.ibpm;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author: yantx
 * @version: 1.0
 * @date: 2021-04-06 14:40
 * @description:
 */
@Slf4j
@SpringBootTest
public class HistoryTaskInstanceTest {

    @Autowired
    private HistoryService historyService;

    /**
     * 根据处理人查询任务历史记录
     */
    @Test
    public void getHistoryTasksByHandler(){
        List<HistoricTaskInstance>  historicTaskInstances= historyService.createHistoricTaskInstanceQuery().orderByHistoricTaskInstanceEndTime().asc().taskAssignee("shaseng").list();
        for (HistoricTaskInstance h : historicTaskInstances){
            log.info("ID："+h.getId());
            log.info("ProcessInstanceId："+h.getProcessInstanceId());
            log.info("Name："+h.getName());
        }
    }

    /**
     * 根据流程实例ID查询任务历史记录
     */
    @Test
    public void getHistoryTaskByProcessInstance(){
        String processInstanceId = "276b20c0-9354-11eb-8ed1-90324b059e89";
        List<HistoricTaskInstance>  historicTaskInstances= historyService.createHistoricTaskInstanceQuery()
                .orderByHistoricTaskInstanceEndTime().asc()
                .processInstanceId(processInstanceId)
                .list();
        for (HistoricTaskInstance h : historicTaskInstances){
            log.info("ID："+h.getId());
            log.info("ProcessInstanceId："+h.getProcessInstanceId());
            log.info("Name："+h.getName());
        }
    }
}
