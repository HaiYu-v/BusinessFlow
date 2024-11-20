package org.example.designs.chain1;

/**
 * 流水线上待加工的产品，把数据和状态信息包装为产品
 */
public class ChainData {

    //数据
    private Object data;
    //状态信息
    private DataCode dataCode;

    private ChainData(Object data, DataCode dataCode) {
        this.data = data;
        this.dataCode = dataCode;
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 获得一个正常产品
     * @Describe: TODO
     **/
    public static ChainData getNormal(Object data){
        return new ChainData(data, DataCode.NORMAL);
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 获得一个异常产品
     * @Describe: TODO
     **/
     public static ChainData getAbnormal(String message){
        return new ChainData(message, DataCode.ABNORMAL);
    }

    /** ---------------------------------------------------------------------------------------------------------------------
     * @Method  : 获取指定类型的产品数据
     * @Describe: TODO
     **/
     public <T> T getData(Class<T> retType) {
         //捕获类型转换的异常，并只返回一个null
         try {
             return (T)data;
         } catch (ClassCastException e) {
             e.printStackTrace();
             return null;
         }
     }

    public void setData(Object data) {
        this.data = data;
    }

    public DataCode getProductCode() {
        return dataCode;
    }

    public void setProductCode(DataCode dataCode) {
        this.dataCode = dataCode;
    }
}
