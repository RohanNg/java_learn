/**
 * Created by rohan on 1/18/17.
 */
public abstract class ElectricAppliance {
    private boolean isOn;
    private boolean isPowerReady;

    // Mutators

    /**
     * Turn on the electric appliances.
     * Turning on appliance on will make it usable only if the power is connected
     *
     * @return true is the appliance is ready to use
     * The appliance is ready to use if it is turned on and the power is available
     */
    public boolean turnOn() {
        isOn = true;
        return isPowerReady;
    }

    /**
     * Turn off this electric appliance
     */
    public void turnOff() {
        isOn = false;
    }

    // Plug in power is one way to make the power of this appliances available

    /**
     *  Plug, or connect this appliance to power supply.
     *  This make appliances power ready for appliance to be used.
     */
    public void plugPower() {
        isPowerReady = true;
    }

    /**
     *  Unplug this appliance to power supply.
     */
    public void unplugPower() {
        isPowerReady = false;
    }

    // Observers

    /**
     * Check if the appliance is turned on.
     * @return true if the appliance is turned On
     */
    public boolean isOn(){
        return isOn;
    }

    /**
     * Check if power for this appliance is available
     * @return
     */
    public boolean isPowerReady() {
        return isPowerReady;
    }

    /**
     *  Check if this appliance if ready to use
     * @return true if this appliance is ready to use
     */
    public boolean isUsable() {
        return isOn && isPowerReady;
    }
}
