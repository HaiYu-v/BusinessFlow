package org.example.designs.chain.core;

import org.example.designs.task.TaskInfo;


/**
 * TODO
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-19
 */
public  class ChainNode<T extends IChainNode> {
    //处理者类型，用于从Bean容器里获取处理者对象
    private Class<T> nodeClass;
    //处理者对象，直接new对象也是可以的
    private T node;
    //处理者名称，介绍一下处理的用途什么什么的，作为一个标识
    private String handlerName;

    private TaskInfo info;


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * TODO
     *
     * @param nodeClass
     * @param node
     * @param nodeName
     * @throws Exception
     */
    public ChainNode(Class<T> nodeClass, T node,String nodeName) throws Exception{
        this.nodeClass = nodeClass;
        this.node = node;
        this.handlerName = nodeName;
    }

    public Class<T> getNodeClass() {
        return nodeClass;
    }

    public void setNodeClass(Class<T> nodeClass) {
        this.nodeClass = nodeClass;
    }

    public T getNode() {
        return node;
    }

    public void setNode(T node) {
        this.node = node;
    }

}
