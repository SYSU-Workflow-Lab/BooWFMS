package cn.edu.sysu.workflow.constant;

/**
 * Created by Skye on 2019/9/19.
 */
public class LocationContext {

    /**
     * Service URL for BO Engine Serialization BO.
     */
    public static final String URL_ENGINE_SERIALIZEBO = "/gateway/serializeBO";

    /**
     * Service URL for BO Engine Serialization BO.
     */
    public static final String URL_ENGINE_START = "/gateway/launchProcess";

    /**
     * Service URL for BO Engine event callback.
     */
    public static final String URL_ENGINE_SPANTREE = "/gateway/getSpanTree";

    /**
     * Service URL for BO Engine event callback.
     */
    public static final String URL_ENGINE_CALLBACK = "/gateway/callback";

    /**
     * Service URL for RS submit task.
     */
    public static final String URL_RS_SUBMITTASK = "/internal/submitTask";

    /**
     * Service URL for RS finish life cycle of BO.
     */
    public static final String URL_RS_FINISH = "/internal/finRtid";

    /**
     * Service URL gateway for RS workitem actions.
     */
    public static final String GATEWAY_RS_WORKITEM = "/workitem/";

    /**
     * Service URL gateway for RS workqueue actions.
     */
    public static final String GATEWAY_RS_QUEUE = "/queue/";
}
