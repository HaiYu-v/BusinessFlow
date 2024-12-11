package org.example.designs.state_machine;

import org.example.designs.state_machine.core.IEventOrState;

/**
 * 开票状态机
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-11
 */
public class OpenMachineEventOr implements IEventOrState {

    @Override
    public String getDesc() {
        return "发票开具";
    }
}
