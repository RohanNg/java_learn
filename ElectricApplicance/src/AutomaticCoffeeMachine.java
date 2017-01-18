/**
 * Created by rohan on 1/18/17.
 */
public class AutomaticCoffeeMachine extends ElectricAppliance {
    /**
     * Type of bean and recipe for it
     */
    public enum BEAN {
        AMERICANBEAN(10, 100),
        VIETNAMESEBEAN(10, 100),
        THAILANDBEAN(10, 100),
        CHINABEAN(10, 100),
        SHITBEAN(10, 100);

        private final int beanAmount;
        private final int waterAmount;

        BEAN(int beanAmount, int waterAmount) {
            this.beanAmount = beanAmount;
            this.waterAmount = waterAmount;
        }
    }

    public enum LIQUID {
        WATER
    }

    private final Container<BEAN> beanContainer;
    private final Container<LIQUID> liquidContainer;
    private BEAN bean;
    private LIQUID liquid;

    // creators
        // constructor
    private AutomaticCoffeeMachine(double beanContainerCapacity, BEAN bean, double beanAmount,
                                   double liquidContainterCapacity, LIQUID liquid, double liquidAmount) {
        beanContainer = new Container<BEAN>(beanContainerCapacity, bean, beanAmount);
        liquidContainer = new Container<LIQUID>(liquidContainterCapacity, liquid, beanAmount);
        this.bean = bean;
        this.liquid = liquid;
    }
        // factory method: SINGLETON PART

    private static final AutomaticCoffeeMachine singleInstance =
            new AutomaticCoffeeMachine(100, BEAN.VIETNAMESEBEAN, 100, 3000, LIQUID.WATER, 3000);

    public static AutomaticCoffeeMachine getSingleInstance() {
        return singleInstance;
    }

    // mutators

    /**
     * Make the coffee with current been in the beanContainer and liquid in the liquidContainer
     *
     * @return true if the coffee is ready
     */
    public boolean brew() {
        try {
            beanContainer.take(bean.beanAmount);
            liquidContainer.take(bean.waterAmount);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public void changeBean(BEAN bean) {
        beanContainer.changeContent(bean);
        this.bean = bean;
    }

    public void fillBeanContainer(double amount) {
        beanContainer.fill(amount);
    }

    public void fillBeanContainerFull() {
        beanContainer.fillFull();
    }

    public void changeLiquid(LIQUID liquid) {
        liquidContainer.changeContent(liquid);
        this.liquid = liquid;
    }

    public void fillWaterContainer(double amount) {
        liquidContainer.fill(amount);
    }

    public void fillWaterContainerFull() {
        liquidContainer.fillFull();
    }



    // observers
    public String getCoffeeName() {
        return "coffee: " + this.bean + ", in " + liquid;
    }

    public BEAN getBeanSample() {
        return bean;
    }

    public boolean isBeanContainerEmpty() {
        return beanContainer.isEmpty();
    }

    public boolean isBeanContainerFull() {
        return beanContainer.isFull();
    }


    public double getAvailableBeanAmount() {
        return beanContainer.getAvailableAmount();
    }

    public LIQUID getLiquidSample() {
        return liquid;
    }

    public boolean isLiquidContainerEmpty() {
        return liquidContainer.isEmpty();
    }

    public boolean isLiquidContainerFull() {
        return liquidContainer.isFull();
    }

    public double getAvailableLiquidAmount() {
        return liquidContainer.getAvailableAmount();
    }
}
