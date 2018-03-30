package com.hzj.hpai.bean;

/**
 * Created by zx on 2016/12/28.
 */

public class NodeParent extends BaseBean {
    private Node node;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return "NodeParent{" +
                "node=" + node +
                '}';
    }
}
