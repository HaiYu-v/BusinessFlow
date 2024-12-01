package org.example.designs.state_machine.core;

import java.util.EnumMap;
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
public class StateMachine<S extends StateMachineEnum,E extends StateMachineEnum> {
    //状态=(事件=处理者)
    private Map<S,Map<E,IStateHandle>> stateEventMap;

    public StateMachine() {
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
        if(null == state) throw new StateMachineException("状态不能为空");
        if(null == event) throw new StateMachineException("事件不能为空");
        if(null == handle) throw new StateMachineException("处理者不能为空");

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
        if(null == eventHandler) throw new StateMachineException(String.format("状态[%s]不存在或没有事件",state.getDesc()));

        IStateHandle handle = eventHandler.get(event);
        if(null == handle) throw new StateMachineException(String.format("状态[%s]的事件[%s]没有处理",state.getDesc(),event.getDesc()));

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
            throw new StateMachineException(String.format("状态[%s]的事件[%s]处理失败",state.getDesc(),event.getDesc()),e);
        }
        return ret;
    }


}
