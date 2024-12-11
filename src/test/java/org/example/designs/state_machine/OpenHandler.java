package org.example.designs.state_machine;

import org.example.designs.state_machine.core.IStateHandle;

import java.util.Map;

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
public class OpenHandler implements IStateHandle<Bill> {
    @Override
    public Bill process(Map<String, Object> params) {
        Object object = params.get("bill");
        Bill bill = (Bill) object;
        int x = 1/0;
        bill.setState(BillState.HAS_OPEN);
        return bill;
    }
}
