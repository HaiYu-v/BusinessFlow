package org.example.designs.state_machine.core;

/**
 * 事件或状态接口
 *
 * <p>
 *     1.【状态类】实现此接口，返回值是此状态的字符串
 *     2.【事件类】实现此接口，返回值是此状态机的描述信息（简述状态机是做什么的）
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @create 2024-12-01
 */
public interface IEventOrState {
    String getDesc();
}
