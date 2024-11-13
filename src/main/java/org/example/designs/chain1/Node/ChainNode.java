package org.example.designs.chain1.Node;


import org.example.designs.utils.beanUtils.AppContextUtil;
import org.example.designs.chain1.ChainData;
import org.example.designs.chain1.ChainException;
import org.example.designs.chain1.ChainHandler;
import org.example.designs.chain1.DataCode;
import org.springframework.context.ApplicationContext;

/**
 * 流水线节点抽象类
 */
public abstract class ChainNode {

    //Spring容器，用于从容器中获取处理者的bean
    protected static ApplicationContext applicationContext = AppContextUtil.getApplicationContext();
    //产品
    protected ChainData chainData;
    //异常产品处理者，出现异常产品后的处理方式
    protected ChainHandler exceptionHandler;


    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 设置Bean容器
     * @Describe: TODO
     **/
     public static void setApplicationContext(ApplicationContext applicationContext) {
        ChainNode.applicationContext = applicationContext;
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 添加处理者,spring框架自动注入
     * @Describe: TODO
     **/
    public abstract <T extends ChainHandler> ChainNode addHandler(Class<T> bean);

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 添加处理者,自行创建处理者对象
     * @Describe: TODO
     **/
    // 添加处理者
    public abstract ChainNode addHandler(ChainHandler handler);


    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 添加一个异常产品的处理者
     * @Describe: TODO
     **/
    public ChainNode setExceptionHandler(ChainHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }



    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 结束流水线,返回数据
     * @Describe: TODO
     **/
     public  <T> T finish(Class<T> retType){
         //调用异常产品处理者处理异常产品
         if(chainData.getProductCode() == DataCode.ABNORMAL){
             if(exceptionHandler != null){
                 ChainData chainData1 = exceptionHandler.process(chainData);
                 return chainData1.getData(retType);
             }else {
                 new ChainException("未配置异常产品处理者").printStackTrace();
                 return null;
             }
         }
        return chainData.getData(retType);
    };



}
