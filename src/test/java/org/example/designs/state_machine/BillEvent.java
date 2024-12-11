package org.example.designs.state_machine;

import org.example.designs.state_machine.core.IEventOrState;

/**
 * 发票事件枚举
 *
 * <p>
 *     TODO
 * </p>
 *
 * @author HaiYu
 * @version 1.0.0
 * @create 2024-12-11
 */
public enum BillEvent implements IEventOrState {
    OPEN       (1,"开单");

    private int code;
    private String desc;

    BillEvent( int code, String desc) {
        this.desc = desc;
        this.code = code;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
