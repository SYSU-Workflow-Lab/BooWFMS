package cn.edu.sysu.workflow.enginefeign.dag;

import java.util.Iterator;

/**
 * @author Skye
 * Created on 2020/4/3
 */
public class ProcessInstanceDag implements Iterable<ProcessInstanceDagItem> {

    private class ProcessInstanceDagNode {
        ProcessInstanceDagItem processInstanceDagItem;
        ProcessInstanceDagNode next;
    }

    private ProcessInstanceDagNode head;
    private int size;

    public ProcessInstanceDag() {
        this.head = new ProcessInstanceDagNode();
        this.size = 0;
    }

    public void add(ProcessInstanceDagItem processInstanceDagItem) {
        ProcessInstanceDagNode temp = this.head;
        while (temp != null && temp.next != null) {
            temp = temp.next;
        }
        ProcessInstanceDagNode newNode = new ProcessInstanceDagNode();
        temp.next = newNode;
        newNode.processInstanceDagItem = processInstanceDagItem;
        size++;

    }

    public void remove(ProcessInstanceDagItem processInstanceDagItem) {
        ProcessInstanceDagNode temp = this.head;
        while (temp != null && temp.next != null) {
            if (temp.next.processInstanceDagItem.getTaskItemId().equals(processInstanceDagItem.getTaskItemId())) {
                break;
            }
            temp = temp.next;
        }
        ProcessInstanceDagNode toBeRemoved = temp.next;
        temp.next = toBeRemoved.next;
        this.size--;
    }

    public ProcessInstanceDagItem get(int i) {
        ProcessInstanceDagNode temp = this.head;
        for (int cnt = 0; cnt <= i; cnt++) {
            temp = temp.next;
        }
        return temp.processInstanceDagItem;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    @Override
    public Iterator<ProcessInstanceDagItem> iterator() {
        return new Iterator<ProcessInstanceDagItem>() {
            ProcessInstanceDagNode node = head;

            @Override
            public boolean hasNext() {
                return node.next != null;
            }

            @Override
            public ProcessInstanceDagItem next() {
                ProcessInstanceDagItem processInstanceDagItem = node.processInstanceDagItem;
                node = node.next;
                return processInstanceDagItem;
            }
        };
    }
}
