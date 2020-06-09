package cn.edu.sysu.workflow.common.constant;

/**
 * @author Rinkako, Skye
 * Created on 2019/12/18
 */
public class LocationContext {

    public static final String ACCESS = "http://ACESS";
    public static final String ENGINE = "http://ENGINE";
    public static final String RESOURCE = "http://RESOURCE";
    public static final String ENGINE_FEIGN = "http://ENGINE-LB";
    public static final String BUSINESS_PROCESS_DATA = "http://BUSINESS-PROCESS-DATA";

    /**
     * Service URL for starting BO Engine.
     */
    public static final String URL_ENGINE_START = "/engine/launchProcess";

    /**
     * Service URL for BO Engine upload BO.
     */
    public static final String URL_ENGINE_UPLOAD = "/engine/uploadBO";

    /**
     * Service URL for span tree.
     */
    public static final String URL_ENGINE_SPANTREE = "/engine/getSpanTree";

    /**
     * Service URL for resuming process instance.
     */
    public static final String URL_ENGINE_RESUME = "/engine/resume";

    /**
     * Service URL for resuming some process instances.
     */
    public static final String URL_ENGINE_RESUMEMANY = "/engine/resumeMany";

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
}
