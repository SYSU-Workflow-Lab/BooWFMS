package cn.edu.sysu.workflow.engine.core.model.extend;

import cn.edu.sysu.workflow.common.util.JsonUtil;
import cn.edu.sysu.workflow.engine.core.ActionExecutionContext;
import cn.edu.sysu.workflow.engine.core.model.Param;
import cn.edu.sysu.workflow.engine.core.model.ParamsContainer;
import cn.edu.sysu.workflow.engine.util.SerializationUtil;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Rinkako
 * Date  : 2018/3/1
 * Usage : Label context of TaskItem.
 */
public class Task extends ParamsContainer implements Serializable {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * TaskItem polymorphism id, unique in a process.
     */
    private String id;

    /**
     * TaskItem polymorphism name.
     */
    private String name;

    /**
     * Business role of this task handler.
     */
    private String brole;

    /**
     * Documentation.
     */
    private String documentation = "";

    /**
     * Call back event name.
     */
    private String event = "";

    /**
     * TaskItem resourcing principle.
     */
    private Principle principle;

    /**
     * TaskItem callbacks.
     */
    private ArrayList<Callback> callbacks = new ArrayList<>();

    /**
     * Generate descriptor for this constraint.
     *
     * @return string descriptor
     */
    public String GenerateParamDescriptor() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for (Param p : this.getParams()) {
            sb.append(p.GenerateDescriptor(null, null)).append(",");
        }
        String jsonifyParam = sb.toString();
        if (jsonifyParam.length() > 1) {
            jsonifyParam = jsonifyParam.substring(0, jsonifyParam.length() - 1);
        }
        jsonifyParam += "}";
        return jsonifyParam;
    }

    /**
     * Generate callback descriptors.
     *
     * @return a entry of (HookDescriptor, EventDescriptor)
     */
    public AbstractMap.SimpleEntry<String, String> GenerateCallbackDescriptor() {
        HashMap<String, ArrayList<Callback>> callbackMap = new HashMap<>();
        for (Callback callback : this.callbacks) {
            callbackMap.computeIfAbsent(callback.getOn(), k -> new ArrayList<>()).add(callback);
        }
        StringBuilder sbHook = new StringBuilder("{");
        StringBuilder sbEvent = new StringBuilder("{");
        for (Map.Entry<String, ArrayList<Callback>> kvp : callbackMap.entrySet()) {
            ArrayList<Callback> callbackForOn = kvp.getValue();
            ArrayList<String> hooks = new ArrayList<>();
            ArrayList<String> events = new ArrayList<>();
            for (Callback cb : callbackForOn) {
                String hook = cb.getHook();
                if (!StringUtils.isEmpty(hook)) {
                    hooks.add(hook);
                }
                String event = cb.getEvent();
                if (!StringUtils.isEmpty(event)) {
                    events.add(event);
                }
            }
            String onOneHooks = String.format("\"%s\":%s", kvp.getKey(), JsonUtil.jsonSerialization(hooks, ""));
            String onOneEvents = String.format("\"%s\":%s", kvp.getKey(), JsonUtil.jsonSerialization(events, ""));
            sbHook.append(onOneHooks).append(",");
            sbEvent.append(onOneEvents).append(",");
        }
        String retHook = sbHook.toString();
        String retEvent = sbEvent.toString();
        if (retHook.length() > 1) {
            retHook = retHook.substring(0, retHook.length() - 1);
        }
        if (retEvent.length() > 1) {
            retEvent = retEvent.substring(0, retEvent.length() - 1);
        }
        retHook += "}";
        retEvent += "}";
        return new AbstractMap.SimpleEntry<>(retHook, retEvent);
    }

    /**
     * Add a callback to this task.
     *
     * @param callback callback package
     */
    public void AddCallback(Callback callback) {
        this.callbacks.add(callback);
    }

    /**
     * Get id.
     */
    public String getId() {
        return id;
    }

    /**
     * Set id.
     *
     * @param id TaskItem id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set name.
     *
     * @param name TaskItem name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get agent.
     */
    public String getBrole() {
        return this.brole;
    }

    /**
     * Set agent.
     *
     * @param brole TaskItem Business Role name
     */
    public void setBrole(String brole) {
        this.brole = brole;
    }

    /**
     * Get event.
     */
    public String getEvent() {
        return event;
    }

    /**
     * Set event.
     *
     * @param event callback event name
     */
    public void setEvent(String event) {
        this.event = event;
    }

    /**
     * Get principle.
     */
    public Principle getPrinciple() {
        return principle;
    }

    /**
     * Set principle.
     *
     * @param principle resourcing principle
     */
    public void setPrinciple(Principle principle) {
        this.principle = principle;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public ArrayList<Callback> getCallbacks() {
        return callbacks;
    }

    /**
     * Execute when encounter.
     *
     * @param exctx execution context
     */
    @Override
    public void execute(ActionExecutionContext exctx) {
        // nothing
    }
}
