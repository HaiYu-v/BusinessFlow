package org.example.designs.chain1.Node;

import org.example.designs.chain1.ChainData;
import org.example.designs.chain1.ChainHandler;

/**
 * 流水线上的空的节点
 * 这个节点出现后，产品将一直走到流水线的末尾，不再做任何处理
 */
public class NullNode extends ChainNode {

    private NullNode(ChainData chainData){
        this.chainData = chainData;
    }

    public static NullNode start(ChainData chainData){
        return new NullNode(chainData);
    }

    @Override
    public <T extends ChainHandler> ChainNode addHandler(Class<T> bean) {
        // 坏产品不处理
        return this;
    }

    @Override
    public ChainNode addHandler(ChainHandler handler) {
        // 坏产品不处理
        return null;
    }
}
