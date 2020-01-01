package cn.edu.sysu.workflow.engine.core.model.extend;

import cn.edu.sysu.workflow.engine.BooEngineApplication;
import cn.edu.sysu.workflow.engine.core.*;
import cn.edu.sysu.workflow.engine.core.env.MultiStateMachineDispatcher;
import cn.edu.sysu.workflow.engine.core.env.SimpleErrorReporter;
import cn.edu.sysu.workflow.engine.core.instanceTree.InstanceManager;
import cn.edu.sysu.workflow.engine.core.instanceTree.RInstanceTree;
import cn.edu.sysu.workflow.engine.core.instanceTree.RTreeNode;
import cn.edu.sysu.workflow.engine.core.io.BOXMLReader;
import cn.edu.sysu.workflow.engine.core.model.*;
import cn.edu.sysu.workflow.engine.dao.BusinessObjectDAO;
import cn.edu.sysu.workflow.common.entity.BusinessObject;
import cn.edu.sysu.workflow.engine.util.SerializationUtil;
import cn.edu.sysu.workflow.engine.util.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Rinkako
 * Date  : 2017/3/7
 * Usage : Label context of NewBO.
 */
public class NewBO extends NamelistHolder implements PathResolverHolder {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(NamelistHolder.class);

    /**
     * The file source of this state machine
     */
    private String src;

    /**
     * How many sub state machine instance ought to be create in expression.
     */
    private String instancesExpr = "1";

    /**
     * Notifiable id.
     */
    private String idExpr;

    /**
     * Path Resolver for the file src
     * {@link PathResolver} for resolving the "src" or "srcexpr" result.
     */
    private PathResolver pathResolver;

    /**
     * Get the value of src
     *
     * @return value of src property
     */
    public String getSrc() {
        return src;
    }

    /**
     * Set the value of src
     *
     * @param src the src value to set
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     * Get the value of instance
     *
     * @return value of instance property
     */
    public String getInstancesExpr() {
        return instancesExpr;
    }

    /**
     * Set the value of instance
     *
     * @param instances the instance value to set, represent how many sub instance ought to be created
     */
    public void setInstancesExpr(String instances) {
        this.instancesExpr = instances;
    }

    /**
     * Get the expr of notifiable id.
     *
     * @return id string
     */
    public String getIdExpr() {
        return idExpr;
    }

    /**
     * Set the expr of notifiable id.
     *
     * @param idExpr id string
     */
    public void setIdExpr(String idExpr) {
        this.idExpr = idExpr;
    }

    /**
     * Get the value of pathResolver
     *
     * @return value of pathResolver property
     */
    @Override
    public PathResolver getPathResolver() {
        return pathResolver;
    }

    /**
     * Set the value of pathResolver
     *
     * @param pathResolver The path resolver to use.
     */
    @Override
    public void setPathResolver(PathResolver pathResolver) {
        this.pathResolver = pathResolver;
    }

    /**
     * Execution of encountering this label
     *
     * @param exctx The ActionExecutionContext for this execution instance
     */
    @Override
    public void execute(ActionExecutionContext exctx) {
        try {
            EnterableState parentState = getParentEnterableState();
            Context ctx = exctx.getContext(parentState);
            ctx.setLocal(getNamespacesKey(), getNamespaces());
            Map<String, Object> payloadDataMap = new LinkedHashMap<String, Object>();
            addParamsToPayload(exctx, payloadDataMap);

            SCXML scxml = null;

            BOXMLExecutionContext currentExecutionContext = (BOXMLExecutionContext) exctx.getInternalIOProcessor();
            String boName = getSrc().split("\\.")[0];


            //read BO from database
            if (!BooEngineApplication.IS_LOCAL_DEBUG) {
                //Ariana:get the serialized BO from the database and deserialize it into SCXML object
                try {
                    byte[] serializedBO = null;
                    BusinessObjectDAO businessObjectDAO = (BusinessObjectDAO) SpringContextUtil.getBean("businessObjectDAOImpl");
                    List<BusinessObject> boList = businessObjectDAO.findBusinessObjectsByProcessId(currentExecutionContext.processId);
                    for (BusinessObject bo : boList) {
                        if (bo.getBusinessObjectName().equalsIgnoreCase(boName)) {
                            serializedBO = bo.getSerialization();
                            break;
                        }
                    }
                    scxml = SerializationUtil.DeserializationSCXMLByByteArray(serializedBO);
                } catch (Exception e) {
                    log.error("[" + currentExecutionContext.processInstanceId + "]When read bo by processInstanceId, exception occurred, " + e.toString() + ", service rollback");
                }
            } else {
                // read local BO
                // get resource file url
                // final URL url = this.getClass().getClassLoader().getResource(getSrc());

                // RINKAKO: get file by passing URL
                URL url = new URL("file", "", getSrc());
                try {
                    InputStream in = url.openStream();
                } catch (Exception e1) {
                    System.out.println("load file directly failed, try get resource");
                    url = this.getClass().getClassLoader().getResource(getSrc());
                }
                try {
                    scxml = BOXMLReader.read(url);
                } catch (Exception e) {
                    System.out.println("couldn't find :" + getSrc());
                    e.printStackTrace();
                }
            }

            // launch sub state machine of the number of instances
            RInstanceTree iTree = InstanceManager.getInstanceTree(currentExecutionContext.processInstanceId);
            RTreeNode curNode = iTree.GetNodeById(currentExecutionContext.NodeId);
            Evaluator evaluator = EvaluatorFactory.getEvaluator(scxml);
            Context tmpCtx = evaluator.newContext(ctx);
            int instanceNum = (int) evaluator.eval(ctx, this.instancesExpr);
            for (int i = 0; i < instanceNum; i++) {
                BOXMLExecutor executor = new BOXMLExecutor(evaluator, new MultiStateMachineDispatcher(), new SimpleErrorReporter(), null, currentExecutionContext.RootNodeId);
                executor.setProcessInstanceId(currentExecutionContext.processInstanceId);
                executor.setProcessId(currentExecutionContext.processId);
                executor.setStateMachine(scxml);
                Context rootContext = evaluator.newContext(null);
                for (Map.Entry<String, Object> entry : payloadDataMap.entrySet()) {
                    rootContext.set(entry.getKey(), entry.getValue());
                }
                // handle initialization parameter
                Datamodel dm = scxml.getDatamodel();
                ArrayList<Data> dataToAdd = new ArrayList<>();
                for (Param argument : this.getParams()) {
                    boolean existFlag = false;
                    for (Data parameter : dm.getData()) {
                        if (parameter.getId().equals(argument.getName())) {
                            parameter.setExpr(argument.getExpr());
                            existFlag = true;
                            break;
                        }
                    }
                    if (!existFlag) {
                        Data d = new Data();
                        d.setId(argument.getName());
                        d.setExpr(argument.getExpr());
                        dataToAdd.add(d);
                    }
                }
                for (Data d : dataToAdd) {
                    dm.addData(d);
                }
                // handle notifiable id of new state machine
                if (this.idExpr != null) {
                    tmpCtx.setLocal("_instanceIndex", i);
                    executor.setNotifiableId(evaluator.eval(tmpCtx, this.idExpr).toString());
                }
                // maintain the relation of this sub state machine on the instance tree
                RTreeNode subNode = new RTreeNode(boName, executor.NodeId, executor.getExctx(), curNode);
                curNode.AddChild(subNode);
                // start sub state machine
                executor.setRootContext(rootContext);
                executor.setExecutorIndex(iTree.Root.getExect().getSCXMLExecutor().getExecutorIndex());
                executor.go();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
