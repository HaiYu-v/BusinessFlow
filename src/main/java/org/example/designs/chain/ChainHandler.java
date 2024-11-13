package org.example.designs.chain;

import cn.hutool.core.bean.BeanUtil;
import org.example.designs.task.AbstractTask;
import org.example.designs.task.TaskInfo;
import org.example.designs.utils.beanUtils.AppContextUtil;
import org.springframework.context.ApplicationContext;

import javax.swing.text.AsyncBoxView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * @Program: 8.mindmap
 * @Author: 海里的鱼
 * @Create: 2024-09-10 10:30
 * @Name: 链路处理者
 * @Desc: TODO
 */
public class ChainHandler extends AbstractTask {
    //链路列表
    private List<TaskInfo> infoList = new ArrayList<>();
    //全局的Map
    private Map<String,Object> GlobalMap = new HashMap<>();
    //临时的Map，只传递一步
    private Map<String,Object> temporaryMap = new HashMap<>();
    //业务逻辑
    private BiFunction<Map<String,Object>,Map<String,Object>,Object> process;


    public ChainHandler putTemporary(String Key,Object value){
        this.temporaryMap.put(Key,value);
        return this;
    }

    public ChainHandler putGlobalMap(String Key,Object value){
        this.GlobalMap.put(Key,value);
        return this;
    }


    public <T extends IChainNode> ChainHandler add(Class<T> nodeType, String describe) throws Exception {
        IChainNode node = AppContextUtil.getBean(nodeType);
        return add(node::process,nodeType.getName(),describe);
    }

    public ChainHandler add(BiFunction<Map<String,Object>,Map<String,Object>,Object> process, String describe) throws Exception {
        return add(process,process.toString(),describe);
    }

    public ChainHandler add(BiFunction<Map<String,Object>,Map<String,Object>,Object> process,String name ,String describe) throws Exception {
        this.name = name;
        this.describe = describe;
        this.process = process ;
        execute();
        infoList.add(getInfo());
        return this;
    }

    @Override
    public boolean executeFunction() throws Exception {
        Object response = process.apply(temporaryMap, GlobalMap);
        temporaryMap = BeanUtil.beanToMap(response,false,false);
        return true;
    }

    public List<TaskInfo> getInfoList() {
        return infoList;
    }

    public void setInfoList(List<TaskInfo> infoList) {
        this.infoList = infoList;
    }

    public Map<String, Object> getGlobalMap() {
        return GlobalMap;
    }

    public void setGlobalMap(Map<String, Object> globalMap) {
        GlobalMap = globalMap;
    }

    public Map<String, Object> getTemporaryMap() {
        return temporaryMap;
    }

    public void setTemporaryMap(Map<String, Object> temporaryMap) {
        this.temporaryMap = temporaryMap;
    }

    public BiFunction<Map<String, Object>, Map<String, Object>, Object> getProcess() {
        return process;
    }

    public void setProcess(BiFunction<Map<String, Object>, Map<String, Object>, Object> process) {
        this.process = process;
    }
}
