package main; /**
 * Created by rohan on 1/18/17.
 * <p>
 * Null value is implicitly not permitted.
 */

/**
 * Represent a container of T with definite capacity.
 */
public class Container<T> {
    // (AF) Abstraction function:
    //      represent a container of a content with definite capacity


    private final double capacity;  // capacity of this container
    private double available;       // available amount of content in this container
    private T content;   // content, or substance contained in this container

    // (RI) Representation Invariant:
    //      content is non empty string
    //      capacity > 0
    //      0 <= available <= capacity

    // Safety from rep exposure
    //      capacity: private final
    //      all fields are immutable data type

    private void checkRep() {
        assert available >= 0 && available <= capacity;
        //     assert capacity > 0;
    }

    // Creators

    // Constructors

    /**
     * Create a new container of the content.
     * If the initial amount of this container is greater than its capacity
     * the container have capacity amount of the available content.
     *
     * @param capacity      capacity of this container
     * @param content       the content of this container
     * @param initialAmount initial amount of this container
     * @throws IllegalArgumentException if the capacity is not positive,
     *                                  if the amount is negative
     */
    public Container(final double capacity, T content, final double initialAmount) {
        if (capacity <= 0 || initialAmount < 0) {
            throw new IllegalArgumentException("Invalid constructor argument");
        }
        this.content = content;
        this.capacity = capacity;
        available = (initialAmount > capacity) ? capacity : initialAmount;
    }

    /**
     * Create a new container of the content.
     * If the initial amount of this empty container is 0
     *
     * @param capacity capacity of this container
     * @param content  the content of this container
     * @throws IllegalArgumentException if the capacity is not positive,
     *                                  if the content is empty string
     */
    public Container(final double capacity, T content) {
        this(capacity, content, 0);
    }


    // mutators

    /**
     * Fill this container with new content.
     * The old content of this container is dumped.
     *
     * @param content new content for this container
     */
    public void changeContent(T content) {
        this.content = content;
        this.available = 0.0;
        checkRep();

    }

    /**
     * Fill this container, increasing available amount of this container by amount.
     * If filled amount is a surplus, this container's available amount is its capacity
     *
     * @param amount of content to fill this container
     * @throws IllegalArgumentException if this amount to be filled is negative
     */
    public void fill(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("cannot fill this container with negative amount");
        } else {
            available = (capacity <= available + amount) ? capacity : available + amount;
        }
        checkRep();
    }

    /**
     * Make this container full.
     */
    public void fillFull() {
        available = capacity;
    }

    /**
     * Take from available content of this container, decreasing available amount by amount
     * Return true if the action is successful : the amount of content to be taken is in range [0, available content]
     * Return false if the amount of content to be taken is larger than available content amount
     *
     * @param amount amount of content to be taken
     * @return true if the amount specified have been taken successfully
     * @throws IllegalArgumentException if amount is negative
     */
    public boolean take(double amount) {
        if (amount < 0)
            throw new IllegalArgumentException("take negative amount");
        if (amount == available) {
            available = 0; // case amount == available == + INFINITY
            return true;
        }
        else if (available > amount){
            available = available - amount;
            checkRep();
            return true;
        }
        checkRep();
        return false;
    }

    /**
     * Take all available amount, making this container empty
     */
    public void takeAll() {
        available = 0;
    }


    // observers
    public boolean isEmpty() {
        return available == 0.0;
    }

    public boolean isFull() {
        return available == capacity;
    }

    /**
     * Get available amount of content in this container
     *
     * @return amount of content available in this container
     */
    public double getAvailableAmount() {
        return available;
    }

    /**
     * Get capacity of this container
     *
     * @return capacity of this container
     */
    public double getCapacity() {
        return capacity;
    }

    /**
     * Get content of this container
     *
     * @return content of this container
     */
    public T getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "content: " + content + ", capacity: " + capacity + ", available amount: " + available;
    }
}
