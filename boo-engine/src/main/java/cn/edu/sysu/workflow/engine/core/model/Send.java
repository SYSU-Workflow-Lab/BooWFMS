package cn.edu.sysu.workflow.engine.core.model;

import cn.edu.sysu.workflow.engine.core.*;
import cn.edu.sysu.workflow.engine.core.env.MultiStateMachineDispatcher;
import cn.edu.sysu.workflow.engine.core.env.SimpleDispatcher;
import cn.edu.sysu.workflow.engine.core.model.extend.MessageMode;
import org.apache.commons.logging.Log;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: Rinkako
 * Date  : 2017/7/20
 * Usage : Label context of Send.
 */
public class Send extends NamelistHolder implements ContentContainer {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Millisecond unit string.
     */
    private static final String MILLIS = "ms";

    /**
     * Second unit string.
     */
    private static final String SECONDS = "s";

    /**
     * Minute unit string.
     */
    private static final String MINUTES = "m";

    /**
     * The number of milliseconds in a second.
     */
    private static final long MILLIS_IN_A_SECOND = 1000L;

    /**
     * The number of milliseconds in a minute.
     */
    private static final long MILLIS_IN_A_MINUTE = 60000L;

    /**
     * The ID of the send message.
     */
    private String id;

    /**
     * Path expression evaluating to a location within a previously defined XML data tree.
     */
    private String idlocation;

    /**
     * The target location of the event.
     */
    private String target;


    /**
     * An expression specifying the target location of the event.
     */
    private String targetexpr;

    /**
     * The type of the Event I/O Processor that the event should be dispatched to.
     */
    private String type;

    /**
     * An expression defining the type of the Event I/O Processor that the event should be dispatched to.
     */
    private String typeexpr;

    /**
     * The delay the event is dispatched after.
     */
    private String delay;

    /**
     * An expression defining the delay the event is dispatched after.
     */
    private String delayexpr;

    /**
     * The data containing information which may be used by the implementing platform to configure the event processor.
     */
    private String hints;

    /**
     * The type of event being generated.
     */
    private String event;

    /**
     * An expression defining the type of event being generated.
     */
    private String eventexpr;

    /**
     * Message spreading mode on instance tree.
     */
    private String messageMode;

    /**
     * Target BO Name.
     */
    private String targetName;

    /**
     * Target BO State.
     */
    private String targetState;

    /**
     * The &lt;content/&gt; of this send
     */
    private Content content;

    /**
     * Constructor.
     */
    public Send() {
        super();
    }

    /**
     * @return the idlocation
     */
    public String getIdlocation() {
        return idlocation;
    }

    /**
     * Set the idlocation expression
     *
     * @param idlocation The idlocation expression
     */
    public void setIdlocation(final String idlocation) {
        this.idlocation = idlocation;
    }

    /**
     * Get the delay.
     *
     * @return Returns the delay.
     */
    public final String getDelay() {
        return delay;
    }

    /**
     * Set the delay.
     *
     * @param delay The delay to set.
     */
    public final void setDelay(final String delay) {
        this.delay = delay;
    }

    /**
     * @return The delay expression
     */
    public String getDelayexpr() {
        return delayexpr;
    }

    /**
     * Set the delay expression
     *
     * @param delayexpr The delay expression to set
     */
    public void setDelayexpr(final String delayexpr) {
        this.delayexpr = delayexpr;
    }

    /**
     * Get the hints for this &lt;send&gt; element.
     *
     * @return String Returns the hints.
     */
    public final String getHints() {
        return hints;
    }

    /**
     * Set the hints for this &lt;send&gt; element.
     *
     * @param hints The hints to set.
     */
    public final void setHints(final String hints) {
        this.hints = hints;
    }

    /**
     * Get the identifier for this &lt;send&gt; element.
     *
     * @return String Returns the id.
     */
    public final String getId() {
        return id;
    }

    /**
     * Set the identifier for this &lt;send&gt; element.
     *
     * @param id The id to set.
     */
    public final void setId(final String id) {
        this.id = id;
    }

    /**
     * Get the target for this &lt;send&gt; element.
     *
     * @return String Returns the target.
     */
    public final String getTarget() {
        return target;
    }

    /**
     * Set the target for this &lt;send&gt; element.
     *
     * @param target The target to set.
     */
    public final void setTarget(final String target) {
        this.target = target;
    }

    /**
     * @return The target expression
     */
    public String getTargetexpr() {
        return targetexpr;
    }

    /**
     * Set the target expression
     *
     * @param targetexpr The target expression to set
     */
    public void setTargetexpr(final String targetexpr) {
        this.targetexpr = targetexpr;
    }

    /**
     * Get the type for this &lt;send&gt; element.
     *
     * @return String Returns the type.
     */
    public final String getType() {
        return type;
    }

    /**
     * Set the type for this &lt;send&gt; element.
     *
     * @param type The type to set.
     */
    public final void setType(final String type) {
        this.type = type;
    }

    /**
     * @return The type expression
     */
    public String getTypeexpr() {
        return typeexpr;
    }

    /**
     * Sets the type expression
     *
     * @param typeexpr The type expression to set
     */
    public void setTypeexpr(final String typeexpr) {
        this.typeexpr = typeexpr;
    }

    /**
     * Set the event to send.
     *
     * @return String Returns the event.
     */
    public final String getEvent() {
        return event;
    }

    /**
     * Get the event to send.
     *
     * @param event The event to set.
     */
    public final void setEvent(final String event) {
        this.event = event;
    }

    /**
     * @return The event expression
     */
    public String getEventexpr() {
        return eventexpr;
    }

    /**
     * Sets the event expression
     *
     * @param eventexpr The event expression to set
     */
    public void setEventexpr(final String eventexpr) {
        this.eventexpr = eventexpr;
    }


    public String getMessageMode() {
        return messageMode;
    }

    public void setMessageMode(String messageMode) {
        this.messageMode = messageMode;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetState() {
        return targetState;
    }

    public void setTargetState(String targetState) {
        this.targetState = targetState;
    }

    /**
     * Returns the content
     *
     * @return the content
     */
    public Content getContent() {
        return content;
    }

    /**
     * Sets the content
     *
     * @param content the content to set
     */
    public void setContent(final Content content) {
        this.content = content;
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public void execute(ActionExecutionContext exctx) throws ModelException, SCXMLExpressionException {
        // Send attributes evaluation
        //获取send标签所在的父状态
        EnterableState parentState = getParentEnterableState();
        Context ctx = exctx.getContext(parentState);
        ctx.setLocal(getNamespacesKey(), getNamespaces());
        Evaluator eval = exctx.getEvaluator();
        // Most attributes of <send> are expressions so need to be evaluated before the EventDispatcher callback

        //求出hint的值
        Object hintsValue = null;
        if (hints != null) {
            hintsValue = eval.eval(ctx, hints);
        }
        //得到id
        if (id == null) {
            id = ctx.getSystemContext().generateSessionId();
            if (idlocation != null) {
                eval.evalAssign(ctx, idlocation, id, Evaluator.AssignType.REPLACE_CHILDREN, null);
            }
        }
        //得到targetValue和typeValue
        String targetValue = target;
        if (targetValue == null && targetexpr != null) {
            targetValue = (String) getTextContentIfNodeResult(eval.eval(ctx, targetexpr));
            if ((targetValue == null || targetValue.trim().length() == 0)
                    && exctx.getAppLog().isWarnEnabled()) {
                exctx.getAppLog().warn("<send>: target expression \"" + targetexpr
                        + "\" evaluated to null or empty String");
            }
        }
        String typeValue = type;
        if (typeValue == null && typeexpr != null) {
            typeValue = (String) getTextContentIfNodeResult(eval.eval(ctx, typeexpr));
            if ((typeValue == null || typeValue.trim().length() == 0)
                    && exctx.getAppLog().isWarnEnabled()) {
                exctx.getAppLog().warn("<send>: type expression \"" + typeexpr
                        + "\" evaluated to null or empty String");
            }
        }
        if (typeValue == null) {
            // must default to 'scxml' when unspecified
            typeValue = BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR;
        } else if (!BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR.equals(typeValue) && typeValue.trim().equalsIgnoreCase(BOXMLIOProcessor.SCXML_EVENT_PROCESSOR)) {
            typeValue = BOXMLIOProcessor.DEFAULT_EVENT_PROCESSOR;
        }

        //得到消息传播模式
        MessageMode messageModeValue = null;
        if (messageMode != null) {
            messageModeValue = Enum.valueOf(MessageMode.class, messageMode);
        }
        String targetNameValue = targetName;
        String targetStateValue = targetState;

        //将事件要传递的参数放到payload中
        Object payload = null;
        Map<String, Object> payloadDataMap = new LinkedHashMap<String, Object>();
        addNamelistDataToPayload(exctx, payloadDataMap);
        addParamsToPayload(exctx, payloadDataMap);
        if (!payloadDataMap.isEmpty()) {
            payload = makeEventPayload(eval, payloadDataMap);
        } else if (content != null) {
            if (content.getExpr() != null) {
                payload = clonePayloadValue(eval.eval(ctx, content.getExpr()));
            } else {
                payload = clonePayloadValue(content.getBody());
            }
        }
        long wait = 0L;
        String delayString = delay;
        if (delayString == null && delayexpr != null) {
            Object delayValue = getTextContentIfNodeResult(eval.eval(ctx, delayexpr));
            if (delayValue != null) {
                delayString = delayValue.toString();
            }
        }
        if (delayString != null) {
            wait = parseDelay(delayString, exctx.getAppLog());
        }
        //得到事件名
        String eventValue = event;
        if (eventValue == null && eventexpr != null) {
            eventValue = (String) getTextContentIfNodeResult(eval.eval(ctx, eventexpr));
            if ((eventValue == null)) {
                throw new SCXMLExpressionException("<send>: event expression \"" + eventexpr
                        + "\" evaluated to null");
            }
        }
        Map<String, BOXMLIOProcessor> ioProcessors = (Map<String, BOXMLIOProcessor>) ctx.get(BOXMLSystemContext.IOPROCESSORS_KEY);
        ctx.setLocal(getNamespacesKey(), null);
        if (exctx.getAppLog().isDebugEnabled()) {
            exctx.getAppLog().debug("<send>: Dispatching event '" + eventValue
                    + "' to target '" + targetValue + "' of target type '"
                    + typeValue + "' with suggested delay of " + wait
                    + "ms");
        }
        if (exctx.getEventDispatcher() instanceof MultiStateMachineDispatcher) {
            if (messageModeValue != null) {
                BOXMLExecutionContext execctx = (BOXMLExecutionContext) exctx.getInternalIOProcessor();
                exctx.getEventDispatcher().send(execctx.processInstanceId, execctx.NodeId, id, messageModeValue,
                        targetNameValue, targetStateValue, typeValue, eventValue, payloadDataMap, hintsValue, wait);
            } else {
                exctx.getEventDispatcher().send(ioProcessors, id, targetValue, typeValue, eventValue,
                        payload, hintsValue, wait);
            }
        } else if (exctx.getEventDispatcher() instanceof SimpleDispatcher) {

            exctx.getEventDispatcher().send(ioProcessors, id, targetValue, typeValue, eventValue,
                    payload, hintsValue, wait);

        } else {
            System.out.println("the message dispatcher is not support");
        }

    }

    /**
     * Parse delay.
     *
     * @param delayString The String value of the delay, in CSS2 format
     * @param appLog      The application log
     * @return The parsed delay in milliseconds
     * @throws SCXMLExpressionException If the delay cannot be parsed
     */
    private long parseDelay(final String delayString, final Log appLog)
            throws SCXMLExpressionException {
        long wait = 0L;
        long multiplier = 1L;
        if (delayString != null && delayString.trim().length() > 0) {
            String trimDelay = delayString.trim();
            String numericDelay = trimDelay;
            if (trimDelay.endsWith(MILLIS)) {
                numericDelay = trimDelay.substring(0, trimDelay.length() - 2);
            } else if (trimDelay.endsWith(SECONDS)) {
                multiplier = MILLIS_IN_A_SECOND;
                numericDelay = trimDelay.substring(0, trimDelay.length() - 1);
            } else if (trimDelay.endsWith(MINUTES)) {
                multiplier = MILLIS_IN_A_MINUTE;
                numericDelay = trimDelay.substring(0, trimDelay.length() - 1);
            }
            try {
                wait = Long.parseLong(numericDelay);
            } catch (NumberFormatException nfe) {
                appLog.error(nfe.getMessage(), nfe);
                throw new SCXMLExpressionException(nfe.getMessage(), nfe);
            }
            wait *= multiplier;
        }
        return wait;
    }
}