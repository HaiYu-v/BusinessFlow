package org.example.designs.state_machine;

import org.example.designs.state_machine.core.StateMachine;
import org.example.designs.state_machine.core.StateMachineException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
@SpringBootTest
public class StateMachineTest {

    static private StateMachine<BillState, BillEvent> stateMachine;
    static {
        try {
            stateMachine = StateMachine.build("发票状态机")
                    .put(BillState.UN_OPEN, BillEvent.OPEN, new OpenHandler());
        } catch (StateMachineException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void test() throws StateMachineException {
        Bill bill = new Bill();
        Map<String, Object> params = Map.of("bill", bill);
        stateMachine.process(bill.getState(), BillEvent.OPEN, params);
        System.out.println(bill.getState());
    }
}
