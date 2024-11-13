package org.example.designs.chain1;


/**
 *  处理者的抽象类，用于在流水线上处理产品
 */

public abstract class ChainHandler {
    //产品的处理方法
    // 可以输入一个产品，修改其内容后再传至下一节点；
    // 也可以输入一个产品后，再生成一个新产品用于后续加工
    public abstract ChainData process(ChainData chainData);
}
