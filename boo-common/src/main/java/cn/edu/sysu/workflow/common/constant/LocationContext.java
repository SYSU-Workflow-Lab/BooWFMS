package cn.edu.sysu.workflow.common.constant;

/**
 * @author Rinkako, Skye
 * Created on 2019/12/18
 */
public class LocationContext {

    /**
     * Service URL for BO Engine Serialization BO.
     */
    public static final String URL_ENGINE_SERIALIZEBO = "/engine/serializeBO";

    /**
     * Service URL for BO Engine Serialization BO.
     */
    public static final String URL_ENGINE_START = "/engine/launchProcess";

    /**
     * Service URL for BO Engine event callback.
     */
    public static final String URL_ENGINE_SPANTREE = "/engine/getSpanTree";

    /**
     * Service URL for BO Engine event callback.
     */
    public static final String URL_ENGINE_CALLBACK = "/engine/callback";

    /**
     * Service URL for RS submit task.
     */
    public static final String URL_RS_SUBMITTASK = "/resource/submitTask";

    /**
     * Service URL for RS finish life cycle of BO.
     */
    public static final String URL_RS_FINISH = "/resource/finish";

    /**
     * Service URL gateway for RS workitem actions.
     */
    public static final String GATEWAY_RS_WORKITEM = "/resource/workitem/";

    /**
     * Service URL gateway for RS workqueue actions.
     */
    public static final String GATEWAY_RS_QUEUE = "/resource/queue/";
}
