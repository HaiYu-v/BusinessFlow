package org.example.designs.state_machine.core;

import java.util.Map;

/**
 * 状态处理者
 *
 * <p>
 *     实现此接口来对状态进行处理
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @create 2024-12-01
 */
public interface IStateHandle<R> {
    R process(Map<String, Object> params);
}
