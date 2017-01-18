package test;

import main.AutomaticCoffeeMachine;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by rohan on 1/18/17.
 */
class AutomaticCoffeeMachineTest {

    private final AutomaticCoffeeMachine acm = AutomaticCoffeeMachine.getSingleInstance();
    @Test
    void getSingleInstance() {
        for(int i = 0; i < 100; i ++) {
            assertTrue(acm == AutomaticCoffeeMachine.getSingleInstance()) ;
        }
    }

    @Test
    void brew() {
        // black box testing:
        boolean isOn = acm.isOn();
        boolean isPowerPluged = acm.isPowerReady();
        acm.unplugPower();
        acm.turnOff();
        assertFalse(acm.brew());
        acm.turnOn();
        assertFalse(acm.brew());
        acm.turnOff();
        acm.plugPowerIn();
        assertFalse(acm.brew());

        acm.turnOn();
        acm.plugPowerIn();
        acm.fillBeanContainerFull();
        acm.fillWaterContainerFull();
        assertEquals(acm.getAvailableBeanAmount(), AutomaticCoffeeMachine.BEAN_CONTAINER_CAPACITY);
        assertEquals(acm.getAvailableLiquidAmount(), AutomaticCoffeeMachine.LIQUID_CONTAINER_CAPACITY);

        // white box testing
        final int brewAtMaxCapacity = AutomaticCoffeeMachine.LIQUID_CONTAINER_CAPACITY/100;
        for(int i = 0; i < brewAtMaxCapacity; i++) {
            assertTrue(acm.brew());
        }

        assertFalse(acm.brew());
        assertFalse(acm.brew());
        // restore initial value
        if(isOn) acm.turnOn(); else acm.turnOff();
        if(isPowerPluged) acm.plugPowerIn(); else acm.unplugPower();
    }

//    @Test
//    void changeBean() {
//
//    }
//
//    @Test
//    void fillBeanContainer() {
//
//    }
//
//    @Test
//    void fillBeanContainerFull() {
//
//    }
//
//    @Test
//    void changeLiquid() {
//
//    }
//
//    @Test
//    void fillWaterContainer() {
//
//    }
//
//    @Test
//    void fillWaterContainerFull() {
//
//    }
//
//    @Test
//    void getCoffeeName() {
//
//    }
//
//    @Test
//    void getBeanSample() {
//
//    }

}