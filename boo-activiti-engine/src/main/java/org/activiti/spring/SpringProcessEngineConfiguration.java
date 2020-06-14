package org.activiti.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.sql.DataSource;

import cn.edu.sysu.workflow.activiti.cache.ProcessDefinitionCache;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.activiti.engine.impl.interceptor.CommandConfig;
import org.activiti.engine.impl.interceptor.CommandInterceptor;
import org.activiti.engine.impl.variable.EntityManagerSession;
import org.activiti.spring.SpringEntityManagerSessionFactory;
import org.activiti.spring.SpringTransactionContextFactory;
import org.activiti.spring.SpringTransactionInterceptor;
import org.activiti.spring.autodeployment.AutoDeploymentStrategy;
import org.activiti.spring.autodeployment.DefaultAutoDeploymentStrategy;
import org.activiti.spring.autodeployment.ResourceParentFolderAutoDeploymentStrategy;
import org.activiti.spring.autodeployment.SingleResourceAutoDeploymentStrategy;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * 引擎配置类
 * 用于设置流程定义缓存
 * @author: Gordan Lin
 * @create: 2019/6/10
 */
public class SpringProcessEngineConfiguration extends ProcessEngineConfigurationImpl implements ApplicationContextAware {
    protected PlatformTransactionManager transactionManager;
    protected String deploymentName = "SpringAutoDeployment";
    protected Resource[] deploymentResources = new Resource[0];
    protected String deploymentMode = "default";
    protected ApplicationContext applicationContext;
    protected Integer transactionSynchronizationAdapterOrder = null;
    private Collection<AutoDeploymentStrategy> deploymentStrategies = new ArrayList();

    public SpringProcessEngineConfiguration() {
        super.setProcessDefinitionCache(new ProcessDefinitionCache());
        this.transactionsExternallyManaged = true;
        this.deploymentStrategies.add(new DefaultAutoDeploymentStrategy());
        this.deploymentStrategies.add(new SingleResourceAutoDeploymentStrategy());
        this.deploymentStrategies.add(new ResourceParentFolderAutoDeploymentStrategy());
    }

    public ProcessEngine buildProcessEngine() {
        ProcessEngine processEngine = super.buildProcessEngine();
        ProcessEngines.setInitialized(true);
        this.autoDeployResources(processEngine);
        return processEngine;
    }

    public void setTransactionSynchronizationAdapterOrder(Integer transactionSynchronizationAdapterOrder) {
        this.transactionSynchronizationAdapterOrder = transactionSynchronizationAdapterOrder;
    }

    protected void initDefaultCommandConfig() {
        if (this.defaultCommandConfig == null) {
            this.defaultCommandConfig = (new CommandConfig()).setContextReusePossible(true);
        }

    }

    protected CommandInterceptor createTransactionInterceptor() {
        if (this.transactionManager == null) {
            throw new ActivitiException("transactionManager is required property for SpringProcessEngineConfiguration, use " + StandaloneProcessEngineConfiguration.class.getName() + " otherwise");
        } else {
            return new SpringTransactionInterceptor(this.transactionManager);
        }
    }

    protected void initTransactionContextFactory() {
        if (this.transactionContextFactory == null && this.transactionManager != null) {
            this.transactionContextFactory = new SpringTransactionContextFactory(this.transactionManager, this.transactionSynchronizationAdapterOrder);
        }

    }

    protected void initJpa() {
        super.initJpa();
        if (this.jpaEntityManagerFactory != null) {
            this.sessionFactories.put(EntityManagerSession.class, new SpringEntityManagerSessionFactory(this.jpaEntityManagerFactory, this.jpaHandleTransaction, this.jpaCloseEntityManager));
        }

    }

    protected void autoDeployResources(ProcessEngine processEngine) {
        if (this.deploymentResources != null && this.deploymentResources.length > 0) {
            AutoDeploymentStrategy strategy = this.getAutoDeploymentStrategy(this.deploymentMode);
            strategy.deployResources(this.deploymentName, this.deploymentResources, processEngine.getRepositoryService());
        }

    }

    public ProcessEngineConfiguration setDataSource(DataSource dataSource) {
        if (dataSource instanceof TransactionAwareDataSourceProxy) {
            return super.setDataSource(dataSource);
        } else {
            DataSource proxiedDataSource = new TransactionAwareDataSourceProxy(dataSource);
            return super.setDataSource(proxiedDataSource);
        }
    }

    public PlatformTransactionManager getTransactionManager() {
        return this.transactionManager;
    }

    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public String getDeploymentName() {
        return this.deploymentName;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public Resource[] getDeploymentResources() {
        return this.deploymentResources;
    }

    public void setDeploymentResources(Resource[] deploymentResources) {
        this.deploymentResources = deploymentResources;
    }

    public ApplicationContext getApplicationContext() {
        return this.applicationContext;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public String getDeploymentMode() {
        return this.deploymentMode;
    }

    public void setDeploymentMode(String deploymentMode) {
        this.deploymentMode = deploymentMode;
    }

    protected AutoDeploymentStrategy getAutoDeploymentStrategy(String mode) {
        AutoDeploymentStrategy result = new DefaultAutoDeploymentStrategy();
        Iterator var3 = this.deploymentStrategies.iterator();

        while(var3.hasNext()) {
            AutoDeploymentStrategy strategy = (AutoDeploymentStrategy)var3.next();
            if (strategy.handlesMode(mode)) {
                result = strategy;
                break;
            }
        }

        return (AutoDeploymentStrategy)result;
    }
}
