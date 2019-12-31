package cn.edu.sysu.workflow.engine.service.impl;

import cn.edu.sysu.workflow.common.entity.ArchivedTree;
import cn.edu.sysu.workflow.common.util.JsonUtil;
import cn.edu.sysu.workflow.engine.core.instanceTree.InstanceManager;
import cn.edu.sysu.workflow.engine.core.instanceTree.RInstanceTree;
import cn.edu.sysu.workflow.engine.core.instanceTree.RTreeNode;
import cn.edu.sysu.workflow.engine.core.model.EnterableState;
import cn.edu.sysu.workflow.engine.dao.ArchivedTreeDAO;
import cn.edu.sysu.workflow.engine.service.ProcessInstanceManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @see cn.edu.sysu.workflow.engine.service.ProcessInstanceManagementService
 * @author Skye
 * Created on 2019/12/31
 */
@Service
public class ProcessInstanceManagementServiceImpl implements ProcessInstanceManagementService {

    @Autowired
    private ArchivedTreeDAO archivedTreeDAO;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getSpanTreeDescriptor(String processInstanceId) {
        RInstanceTree tree = InstanceManager.GetInstanceTree(processInstanceId, false);
        if (tree == null || tree.Root == null) {
            ArchivedTree archivedTree = null;
            try {
                archivedTree = archivedTreeDAO.findOne(processInstanceId);
            } catch (Exception ex) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            if (archivedTree == null) {
                return null;
            }
            return archivedTree.getTree();
        }
        FriendlyTreeNode rootFNode = new FriendlyTreeNode();
        this.Nephren(tree.Root, rootFNode);
        return JsonUtil.jsonSerialization(rootFNode, processInstanceId);
    }

    /**
     * Recursively handle span the user-friendly package tree of a specific instance tree.
     * This method is to commemorate a girl devoted her love to guard the happiness of who she loved and his lover. -RK
     *
     * @param node  current span root node
     * @param fNode user-friendly package node of current span node
     */
    private void Nephren(@NotNull RTreeNode node, @NotNull FriendlyTreeNode fNode) {
        fNode.BOName = node.getExect().getScInstance().getStateMachine().getName();
        fNode.GlobalId = node.getGlobalId();
        fNode.NotifiableId = node.getExect().NotifiableId;
        Set<EnterableState> status = node.getExect().getScInstance().getCurrentStatus().getActiveStates();
        HashSet<String> stringSet = new HashSet<>();
        for (EnterableState st : status) {
            stringSet.add(st.getId());
        }
        fNode.StatusDescriptor = JsonUtil.jsonSerialization(stringSet, node.getExect().processInstanceId);
        for (RTreeNode sub : node.Children) {
            FriendlyTreeNode subFn = new FriendlyTreeNode();
            this.Nephren(sub, subFn);
            fNode.Children.add(subFn);
        }
    }

    /**
     * A class for user-friendly tree node data package.
     */
    private class FriendlyTreeNode {

        public String NotifiableId;

        public String GlobalId;

        public String BOName;

        public String StatusDescriptor;

        public ArrayList<FriendlyTreeNode> Children = new ArrayList<>();
    }

}
