package cn.edu.sysu.workflow.engine.core.model.extend;

import cn.edu.sysu.workflow.engine.core.ActionExecutionContext;
import cn.edu.sysu.workflow.engine.core.SCXMLExpressionException;
import cn.edu.sysu.workflow.engine.core.model.ModelException;
import cn.edu.sysu.workflow.engine.core.model.ParamsContainer;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: Rinkako
 * Date  : 2017/6/15
 * Usage : Label context of Resources.
 */
public class Resources extends ParamsContainer implements Serializable {

    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Role vector
     */
    private List<Role> roleVector = new LinkedList<Role>();

    /**
     * Add a resource item to the resources catalogue vector
     *
     * @param r the role pending to add
     */
    public void AddRole(Role r) {
        this.roleVector.add(r);
    }

    /**
     * Resource vector
     */
    private List<Resource> resourcesVector = new LinkedList<Resource>();

    /**
     * Add a resource item to the resources catalogue vector
     *
     * @param r the resource to add
     */
    public void AddResource(Resource r) {
        this.resourcesVector.add(r);
    }

    /**
     * Get the vector of resource
     *
     * @return the reference of resource vector
     */
    public List<Resource> GetResourceList() {
        return this.resourcesVector;
    }

    /**
     * Get the vector of role
     *
     * @return the reference of role vector
     */
    public List<Role> GetRoleList() {
        return this.roleVector;
    }

    /**
     * Register resource to the ResourceService
     *
     * @param exctx The ActionExecutionContext for this execution instance
     * @throws ModelException
     * @throws SCXMLExpressionException
     */
    @Override
    public void execute(ActionExecutionContext exctx) {

    }
}
