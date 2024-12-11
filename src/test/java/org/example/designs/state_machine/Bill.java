package org.example.designs.state_machine;

import org.example.designs.state_machine.core.IEventOrState;

/**
 * TODO
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @date 2024-12-11
 */
public class Bill  {
    private BillState state = BillState.UN_OPEN;



    public BillState getState() {
        return state;
    }

    public void setState(BillState state) {
        this.state = state;
    }
}
