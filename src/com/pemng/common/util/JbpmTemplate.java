package com.pemng.common.util;

import java.util.List;
import java.util.Map;

import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.TaskService;
import org.jbpm.api.model.Transition;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.env.EnvironmentImpl;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * JBPM的幫助類
 * 
 * @author Alen
 **/
@Repository
public class JbpmTemplate {

	@Autowired
	private ProcessEngine processEngine;
	/**
	 * 使用其中方法可完成流程模板的发布，删除等操作。
	 */
	@Autowired
	private RepositoryService repositoryService;
	/**
	 * 使用其中方法可以为流程模板生成流程实例，并完成流程实例的管理：定义流程变量、流程运转、流程实例删除等。
	 */
	@Autowired
	private ExecutionService executionService;
	/**
	 * 提供了操作任务列表的接口，定义任务变量、设置任务变量、获取任务列表、完成任务
	 */
	@Autowired
	private TaskService taskService;
	/**
	 * 提供访问流程历史记录访问接口
	 */
	@Autowired
	private HistoryService historyService;
	@Autowired
	private IdentityService identityService;

	private void testJbpm() {
		System.out.println("processEngine:" + this.processEngine);
		System.out.println("repositoryService:" + this.repositoryService);
		System.out.println("executionServcie:" + this.executionService);
		System.out.println("taskService:" + this.taskService);
		System.out.println("historyService:" + this.historyService);
	}

	/**
	 * 部署流程到数据库
	 * 
	 * @param resourceName
	 *            资源文件名字 比如(org/forever/jbpm/jpdl/process.jpdl.xml)
	 * @return 返回流程定义id(格式：key-version)
	 */
	public String Deploy(String resourceName) {
		return repositoryService.createDeployment()
				.addResourceFromClasspath(resourceName).deploy();
	}

	/**
	 * 创建一个新的流程实例
	 * 
	 * @param processDefinitionKey
	 *            (process.jpdl.xml中process标签的key)
	 * @param processInstanceKey
	 *            (用户给的key，比如一个请假单的id)
	 * @return 流程实例
	 */
	public ProcessInstance addProcessInstance(String processDefinitionKey,
			String processInstanceKey) {
		return executionService.startProcessInstanceByKey(processDefinitionKey,
				processInstanceKey);

	}

	/**
	 * 创建一个新的流程实例
	 * 
	 * @param processDefinitionKey
	 *            (process.jpdl.xml中process标签的key)
	 * @param variables
	 *            该流程实例要用到的变量
	 * @param processInstanceKey
	 *            (用户给定的业务key)
	 * @return
	 */
	public ProcessInstance addProcessInstance(String processDefinitionKey,
			Map<String, ?> variables, String processInstanceKey) {
		return executionService.startProcessInstanceByKey(processDefinitionKey,
				variables, processInstanceKey);
	}
	
	public ProcessInstance addProcessInstance(String processDefinitionKey,
			Map<String, ?> variables) {
		return executionService.startProcessInstanceByKey(processDefinitionKey,
				variables);
	}
	public ProcessInstance getProcessInstanceByTask(Task task){
		return executionService.findProcessInstanceById(task.getExecutionId());
	}
	public ProcessDefinition getProcessDefinitionByPdId(String pdId){
		ProcessDefinition pImpl =  processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(pdId).uniqueResult();
		return pImpl;
	}
	/******************
	 * 动态添加Transition
	 * @param pd
	 * @param sourceName
	 * @param destName
	 * @throws Exception
	 */
	public void addOutTransition(ProcessDefinitionImpl pd,String sourceName,String destName) throws Exception{
		EnvironmentFactory environmentFactory = (EnvironmentFactory)processEngine;
		EnvironmentImpl environment = null;
		try {
			environment = environmentFactory.openEnvironment();
			ActivityImpl sourceActivity = pd.findActivity(sourceName);
			ActivityImpl destActivity = pd.findActivity(destName);
			TransitionImpl transition = sourceActivity.createOutgoingTransition();
			transition.setName("to " + destName);
			transition.setDestination(destActivity);
			sourceActivity.addOutgoingTransition(transition);
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (environment != null) {
				environment.close();
			}
		}
	}
	/***************
	 * 动态删除Transition
	 * @param pd
	 * @param sourceName
	 * @param destName
	 * @throws Exception
	 */
	public void removeOutTransition(ProcessDefinitionImpl pd,String sourceName,String destName) throws Exception{
		EnvironmentFactory environmentFactory = (EnvironmentFactory)processEngine;
		EnvironmentImpl environment = null;
		try {
			environment = environmentFactory.openEnvironment();
			ActivityImpl sourceActivity = pd.findActivity(sourceName);
			List<Transition> tranList = (List<Transition>) sourceActivity.getOutgoingTransitions();
			for (Transition transition : tranList) {
				if (destName.equals(transition.getDestination().getName())) {
					tranList.remove(transition);
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		} finally {
			if (environment != null) {
				environment.close();
			}
		}
	}

	/**
	 * 提交任务
	 * 
	 * @param taskId
	 *            任务id
	 */
	public void completeTask(String taskId) {
		taskService.completeTask(taskId);
	}

	/**
	 * 将任务流转到指定名字的流程中去
	 * 
	 * @param taskId
	 * @param outcome
	 */
	public void completeTask(String taskId, String outcome) {
		taskService.completeTask(taskId, outcome);
	}

	/**
	 * 将任务流转到指定名字的流程中去
	 * 
	 * @param taskId
	 * @param outcome
	 */
	public void completeTask(String taskId, String outcome,Map<String, ?> variables) {
		taskService.completeTask(taskId, outcome, variables);
	}
	/**
	 * 根据key获取流程实例(这里我使用的uuid)
	 * 
	 * @param key
	 *            (对应于数据库表jbpm4_execution中的KEY_字段)
	 * @return 返回查找到得流程实例，没有返回null
	 */
	public ProcessInstance getProcessInstance(String key) {
		return executionService.createProcessInstanceQuery()
				.processInstanceKey(key).uniqueResult();
	}

	/**
	 * 根据executionId获取指定的变量值
	 * 
	 * @param executionId
	 * @param variableName
	 * @return
	 */
	public Object getVariableByexecutionId(String executionId,
			String variableName) {
		return executionService.getVariable(executionId, variableName);
	}

	/**
	 * 根据任务id获取指定变量值
	 * 
	 * @param taskId
	 * @param variableName
	 * @return
	 */
	public Object getVariableByTaskId(String taskId, String variableName) {
		return taskService.getVariable(taskId, variableName);
	}

	/**
	 * 获取指定用户名字的任务
	 * 
	 * @param userId
	 * @return
	 */
	public List<Task> findPersonalTasks(String userId) {
		return taskService.findPersonalTasks(userId);
	}

	/**
	 * 根据任务id获取任务
	 * 
	 * @param taskId
	 * @return
	 */
	public Task getTask(String taskId) {
		return taskService.getTask(taskId);

	}
	/**
	 * 通过流程实例获得TASK
	 * @param processId
	 * @return
	 */
	public List<Task> getProcessTask(String processId){
//		Execution execution = executionService.findExecutionById(processId);
		TaskQuery tq = taskService.createTaskQuery();
//		List<Task> taskList = tq.executionId(execution.getId()).list();
		List<Task> taskList = tq.processInstanceId(processId).list();
		return taskList;
	}

	/**
	 * 根据流程实例id获取
	 * 
	 * @param executionId
	 * @return
	 */
	public Execution findExecutionById(String executionId) {
		return executionService.findExecutionById(executionId);
	}

	/**
	 * 彻底删除文件的部署
	 * 
	 * @param deploymentId流程定义id
	 */
	public void deleteDeploymentCascade(String deploymentId) {
		repositoryService.deleteDeploymentCascade(deploymentId);
	}

}
