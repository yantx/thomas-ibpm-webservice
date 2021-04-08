package com.thomas.ibpm;

import com.thomas.ibpm.config.SecurityUtil;
import lombok.extern.slf4j.Slf4j;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * @author: yantx
 * @version: 1.0
 * @date: 2021-01-27 23:31
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class BootActivitiApplicationTests {
    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private RepositoryService repositoryService;

    /**
     * 流程实例力署
     */
    @Test
    public void deploy() {
        securityUtil.logInAs("salaboy");
        String bpmnName = "MyProcess";
        DeploymentBuilder deploymentBuilder = repositoryService.createDeployment().name("请假流程");
        Deployment deployment = null;
        try {
            deployment = deploymentBuilder.addClasspathResource("processes/" + bpmnName + ".bpmn")
                    .addClasspathResource("processes/" + bpmnName + ".png").deploy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test // 远程外部BPMN
    public void deploy2() {
        securityUtil.logInAs("salaboy");
        try {
            Deployment deployment = null;
            InputStream in = new FileInputStream(new File("C:\\Users\\飞牛\\git\\SpringBoot2_Activiti7\\src\\main\\resources\\processes\\leaveProcess.zip"));
            ZipInputStream zipInputStream = new ZipInputStream(in);
            deployment = repositoryService.createDeployment().name("请假流程2")
                    // 指定zip格式的文件完成部署
                    .addZipInputStream(zipInputStream).deploy();// 完成部署
            zipInputStream.close();
        } catch (Exception e) {
            // TODO 上线时删除
            e.printStackTrace();
        }
    }

    @Test // 查看流程
    public void contextLoads() {
        securityUtil.logInAs("salaboy");
        Page processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));

        log.info("已部署的流程个数：" + processDefinitionPage.getTotalItems());
        for (Object obj : processDefinitionPage.getContent()) {
            log.info("流程定义：" + obj);
        }
    }

    @Test // 启动流程
    public void startInstance() {
        securityUtil.logInAs("salaboy");
        ProcessInstance processInstance = processRuntime
                .start(ProcessPayloadBuilder.start().withProcessDefinitionKey("myProcess_1").build());
        log.info("流程实例ID：" + processInstance.getId());
    }

    @Test // 执行流程
    public void testTask() {
        securityUtil.logInAs("salaboy");
        Page<Task> page = taskRuntime.tasks(Pageable.of(0, 10));
        if (page.getTotalItems() > 0) {
            for (Task task : page.getContent()) {
                log.info("当前任务有1：" + task);
                // 拾取
                taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
                // 执行
                taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
            }
        } else {
            log.info("没的任务1");
        }

        page = taskRuntime.tasks(Pageable.of(0, 10));
        if (page.getTotalItems() > 0) {
            for (Task task : page.getContent()) {
                log.info("当前任务有2：" + task);
            }
        } else {
            log.info("没的任务2");
        }
    }
}
