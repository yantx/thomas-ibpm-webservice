package com.thomas.ibpm;

import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @author: yantx
 * @version: 1.0
 * @date: 2021-03-31 16:48
 * @description:
 */
@Slf4j
@SpringBootTest
public class DeploymentTest {
    @Autowired
    private RepositoryService repositoryService;

    /**
     * 流程部署测试 bpmn文件
     */
    @Test
    public void initDeploymentBpmn(){
//        String filePath = "processes/PartDeployment.bpmn";
        String filePath = "processes/UelTestProcessV2.bpmn";
        Deployment deployment = repositoryService.createDeployment().name("UEL测试报销流程V2").addClasspathResource(filePath).deploy();
        log.info(deployment.getName());
    }

    /**
     * 流程部署测试  zip文件
     */
    @Test
    public void initDeploymentZip(){
        String filePath = "processes/PartDeploymentV2.zip";
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(filePath);
        ZipInputStream zip = new ZipInputStream(inputStream);
        Deployment deployment = repositoryService.createDeployment().addZipInputStream(zip).name("流程部署测试ZIP").deploy();
        log.info("流程部署测试ZIP：{}", deployment.getName());
    }

    /**
     * 查询流程部署信息
     * Deployment（流程部署表） 添加资源文件、获取部署信息、部署时间
     */
    @Test
    public void getDeployments(){
       List<Deployment> deployments = repositoryService.createDeploymentQuery().list();
       for (Deployment d: deployments){
           log.info("ID："+d.getId());
           log.info("名称："+d.getName());
           log.info("版本号："+ d.getVersion());
           log.info("部署时间："+d.getDeploymentTime());
       }
    }
}
