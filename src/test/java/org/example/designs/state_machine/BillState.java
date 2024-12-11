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
 * @create 2024-12-11
 */
public enum BillState implements IEventOrState {
    UN_OPEN       (1,"未开具"),
    HAS_OPEN      (2,"已开具")
    ;

    private int code;
    private String desc;

    BillState( int code, String desc) {
        this.desc = desc;
        this.code = code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
