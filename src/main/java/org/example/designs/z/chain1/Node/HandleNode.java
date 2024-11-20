package org.example.designs.chain1.Node;


import org.example.designs.chain1.*;





/**
 * 流水线上的处理节点，调用处理者来处理产品
 */
public class HandleNode extends ChainNode {


    /* ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 私有构造
     * @Describe: TODO
     **/
    private HandleNode(Object data){
        this.chainData = ChainData.getNormal(data);
    }

    /* ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 私有构造
     * @Describe: TODO
     **/
    private HandleNode(){
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 启动流水线，创建一个流水线对象，并且传入产品和异常产品处理者
     * @Describe: TODO
     **/
    public static HandleNode start(Object data, ChainHandler eHandler){
        HandleNode pipeline = new HandleNode(data);
        pipeline.setExceptionHandler(eHandler);
        return pipeline;
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 启动流水线，创建一个流水线对象，并且传入产品
     * @Describe: TODO
     **/
    public static HandleNode start(Object data){
        return new HandleNode(data);
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 启动流水线，创建一个流水线对象，不传入产品，但传入异常产品处理者
     * @Describe: TODO
     **/
    public static HandleNode nullStart(ChainHandler eHandler){
        HandleNode pipeline = new HandleNode();
        pipeline.setExceptionHandler(eHandler);
        return pipeline;
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 启动流水线，创建一个流水线对象，不传入产品
     * @Describe: TODO
     **/
    public static HandleNode nullStart(){
        return new HandleNode();
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 添加流水线处理者
     * @Describe: TODO
     **/
    @Override
    public ChainNode addHandler(ChainHandler handler) {
        //异常产品
        if(chainData.getProductCode() == DataCode.ABNORMAL){
            if(exceptionHandler != null){
                chainData = exceptionHandler.process(chainData);
                return this;
            }else {
                new ChainException("未配置异常产品处理者").printStackTrace();
                return NullNode.start(chainData);
            }
        }

        //不需要再处理的产品
        if(chainData.getProductCode() == DataCode.NO_PROCESSING){
            NullNode errorNode = NullNode.start(chainData);
            errorNode.setExceptionHandler(this.exceptionHandler);
            return errorNode;
        }

        //调用Handler对象的process方法
        chainData = handler.process(chainData);

        return this;
    }


    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 添加流水线处理者
     * @Describe: TODO
     **/
    @Override
    public <T extends ChainHandler> ChainNode addHandler(Class<T> bean) {
        //根据类型获取Handler的Bean对象
        ChainHandler handler = applicationContext.getBean(bean);

        //异常产品
        if(chainData.getProductCode() == DataCode.ABNORMAL){
            if(exceptionHandler != null){
                chainData = exceptionHandler.process(chainData);
                return this;
            }else {
                new ChainException("未配置异常产品处理者").printStackTrace();
                return NullNode.start(chainData);
            }
        }

        //不需要再处理的产品
        if(chainData.getProductCode() == DataCode.NO_PROCESSING){
            NullNode errorNode = NullNode.start(chainData);
            errorNode.setExceptionHandler(this.exceptionHandler);
            return errorNode;
        }

        //调用Handler对象的process方法
        chainData = handler.process(chainData);

        return NullNode.start(chainData);
    }
}
