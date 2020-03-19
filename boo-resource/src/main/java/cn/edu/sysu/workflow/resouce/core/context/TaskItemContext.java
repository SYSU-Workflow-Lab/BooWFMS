package cn.edu.sysu.workflow.resouce.core.context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Author: Rinkako
 * Date  : 2018/2/4
 * Usage : Task context is an encapsulation of RenRSTaskEntity in a
 * convenient way for resourcing service.
 */
public class TaskItemContext implements Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Task global id.
     */
    private String taskGlobalId;

    /**
     * Task id, unique in a process.
     */
    private String taskId;

    /**
     * Task name, unique in a process except base BO, for polymorphism.
     */
    private String taskName;

    /**
     * Task resourcing principle.
     */
    private String principle;

    /**
     * Global id of Belong to which BO.
     */
    private String boid;

    /**
     * Global id of Belong to which Process.
     */
    private String pid;

    /**
     * Brole of this task.
     */
    private String brole;

    /**
     * Task documentation.
     */
    private String documentation;

    /**
     * Notification hook dictionary. (Change, NotifyURL)
     */
    private HashMap<String, ArrayList<String>> hooks = new HashMap<>();

    /**
     * Callback event dictionary. (Status, EventName)
     */
    private HashMap<String, ArrayList<String>> callbacks = new HashMap<>();

    /**
     * Parameters vector.
     */
    private ArrayList<String> parameters = new ArrayList<>();

    /**
     * Create a new context.
     */
    public TaskItemContext() {
    }

    /**
     * Create a new context.
     *
     * @param id            task unique id
     * @param name          task name
     * @param brole         business role name
     * @param pid           belong to Process global id
     * @param boid          belong to BO global id
     * @param principle     resourcing principle
     * @param documentation task documentation text
     */
    public TaskItemContext(String id, String name, String brole, String pid, String boid, String principle, String documentation) {
        this.taskId = id;
        this.taskName = name;
        this.brole = brole;
        this.pid = pid;
        this.boid = boid;
        this.principle = principle;
        this.documentation = documentation;
    }

    /**
     * Get task global unique id.
     *
     * @return global id, this is NOT defined in BOXML but generated at runtime.
     */
    public String getTaskGlobalId() {
        return this.taskGlobalId;
    }

    public void setTaskGlobalId(String taskGlobalId) {
        this.taskGlobalId = taskGlobalId;
    }

    /**
     * Get documentation text.
     *
     * @return documentation string
     */
    public String getDocumentation() {
        return this.documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    /**
     * Get the unique id.
     *
     * @return id string
     */
    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * Get task polymorphism name.
     *
     * @return name string
     */
    public String getTaskName() {
        return this.taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * Get the global id of BO which this task belong to.
     *
     * @return BO global id string
     */
    public String getBoid() {
        return this.boid;
    }

    public void setBoid(String boid) {
        this.boid = boid;
    }

    /**
     * Get the global id of Process which this task belong to.
     *
     * @return Process global id string
     */
    public String getPid() {
        return this.pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    /**
     * Get the resourcing principle.
     *
     * @return principle string
     */
    public String getPrinciple() {
        return this.principle;
    }

    public void setPrinciple(String principle) {
        this.principle = principle;
    }

    /**
     * Get the business role name.
     *
     * @return business role name string
     */
    public String getBrole() {
        return this.brole;
    }

    public void setBrole(String brole) {
        this.brole = brole;
    }

    /**
     * Get the notification hooks dictionary.
     *
     * @return HashMap of (ChangedName-NotifyURL)
     */
    public HashMap<String, ArrayList<String>> getNotifyHooks() {
        return this.hooks;
    }

    public void setHooks(HashMap<String, ArrayList<String>> hooks) {
        this.hooks = hooks;
    }

    /**
     * Get the callback events dictionary.
     *
     * @return HashMap of (Status-EventName)
     */
    public HashMap<String, ArrayList<String>> getCallbackEvents() {
        return this.callbacks;
    }

    public void setCallbacks(HashMap<String, ArrayList<String>> callbacks) {
        this.callbacks = callbacks;
    }

    /**
     * Get the parameter vector.
     *
     * @return ArrayList of parameter name
     */
    public ArrayList<String> getParameters() {
        return this.parameters;
    }

    public void setParameters(ArrayList<String> parameters) {
        this.parameters = parameters;
    }
}
