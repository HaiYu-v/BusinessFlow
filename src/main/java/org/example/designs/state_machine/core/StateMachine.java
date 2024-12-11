package org.example.designs.state_machine.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 状态机
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-11-30
 */
public class StateMachine<S extends IEventOrState,E extends IEventOrState> {
    //状态=(事件=处理者)
    private Map<S,Map<E,IStateHandle>> stateEventMap;
    //状态机描述
    private String desc;

    static public StateMachine build(String desc){
        return new StateMachine(desc);
    }

    private StateMachine(String desc) {
        this.desc = desc;
        this.stateEventMap = new HashMap<>();
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 链式配置状态机
     *
     * @param state 状态
     * @param event 事件
     * @param handle 处理者
     * @return {@link StateMachine }<{@link S } ,{@link E }>
     * @throws StateMachineException 状态机异常
     */
    public StateMachine<S ,E> put(S state, E event, IStateHandle handle) throws StateMachineException {
        if(null == state) throw new StateMachineException(String.format("状态机[%s]中，状态不能为空",this.desc));
        if(null == event) throw new StateMachineException(String.format("状态机[%s]中，事件不能为空",this.desc));
        if(null == handle) throw new StateMachineException(String.format("状态机[%s]中，处理者不能为空",this.desc));
        Map<E, IStateHandle> eventHandler = stateEventMap.getOrDefault(state, new HashMap<E, IStateHandle>());
        eventHandler.put(event, handle);
        stateEventMap.put(state, eventHandler);
        return this;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 获取处理者
     *
     * @param state 状态
     * @param event 事件
     * @return {@link IStateHandle }
     * @throws StateMachineException 状态机异常
     */
    public IStateHandle get(S state, E event) throws StateMachineException {
        Map<E, IStateHandle> eventHandler = stateEventMap.get(state);
        if(null == eventHandler) throw new StateMachineException(String.format("状态机[%s]中,没有状态[%s]或没有对应事件",this.desc,state.getDesc()));

        IStateHandle handle = eventHandler.get(event);
        if(null == handle) throw new StateMachineException(String.format("状态机[%s]中,状态[%s]的事件[%s]没有处理者",this.desc,state.getDesc(),event.getDesc()));

        return handle;
    }

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * 处理此状态下的此事件
     *
     * @param state 状态
     * @param event 事件
     * @param params 参数
     * @return {@link R }
     * @throws StateMachineException 状态机异常
     */
    public <R> R process(S state,E event,Map<String,Object> params) throws StateMachineException {
        IStateHandle<R> handle = get(state, event);
        R ret = null;
        try {
            ret = handle.process(params);
        } catch (Exception e) {
            throw new StateMachineException(String.format("状态机[%s]执行事件[%s]失败, 此对象的状态为[%s]",this.desc,event.getDesc(),state.getDesc()),e);
        }
        return ret;
    }


    public Map<S, Map<E, IStateHandle>> getStateEventMap() {
        return stateEventMap;
    }

    public void setStateEventMap(Map<S, Map<E, IStateHandle>> stateEventMap) {
        this.stateEventMap = stateEventMap;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
