package com.thomas.ibpm;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author: yantx
 * @version: 1.0
 * @date: 2021-04-01 09:47
 * @description: 流程定义测试类
 */
@Slf4j
@SpringBootTest
public class ProcessDefinitionTest {

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 查询流程定义信息
     * ProcessDefinition（流程定义表） 获取版本号、key、资源名称、部署ID等
     */
    @Test
    public void getDefinitions(){
        List<ProcessDefinition> definitions = repositoryService.createProcessDefinitionQuery().list();
        for (ProcessDefinition d: definitions){
            log.info("名称："+ d.getName());
            log.info("key："+ d.getKey());
            log.info("版本号："+ d.getVersion());
            log.info("资源名称："+ d.getResourceName());
            log.info("部署信息ID："+ d.getDeploymentId());
        }
    }
    /**
     * 删除流程部署信息
     */
    @Test
    public void delProcessDefinition(){
        String deploymentId = "164aab47-9742-11eb-b794-90324b059e89";
        // 非级联删除 只能删除没有启动的流程， 如果流程启动，就会抛出异常
//        repositoryService.deleteDeployment(deploymentId);
        // 参数2： true-级联删除 false-非级联删除   不管流程是否启动，都会删除
        repositoryService.deleteDeployment(deploymentId, true);
    }
}
