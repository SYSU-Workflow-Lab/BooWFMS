package cn.edu.sysu.workflow.common.constant;

/**
 * @author Rinkako, Skye
 * Created on 2019/12/18
 */
public class LocationContext {

    public static final String ACCESS = "http://ACESS";
    public static final String ENGINE = "http://ENGINE";
    public static final String RESOURCE = "http://RESOURCE";
    public static final String ENGINE_FEIGN = "http://ENGINE-FEIGN";
    public static final String BUSINESS_PROCESS_DATA = "http://BUSINESS-PROCESS-DATA";

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
    public static final String URL_RS_SUBMITTASK = "/internal/submitTask";

    /**
     * Service URL for RS finish life cycle of BO.
     */
    public static final String URL_RS_FINISH = "/internal/finProcessInstance";

    /**
     * Service URL gateway for RS work item actions.
     */
    public static final String GATEWAY_RS_WORKITEM = "/resource/workitem/";

    /**
     * Service URL gateway for RS work item list actions.
     */
    public static final String GATEWAY_RS_QUEUE = "/resource/list/";
}
