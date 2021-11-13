class Pair<T1,T2> {
    private T1 first;
    private T2 second;

    /**
     * Constructor
     * @param first First Element
     * @param second Second Element
     */
    public Pair(T1 first, T2 second) {
        this.first=first;
        this.second=second;
    }

    /**
     * Set new value to the pair
     * @param a First Element
     * @param b Second Element
     */
    public void setValue(T1 a, T2 b)
    {
        this.first = a;
        this.second = b;
    }

    /**
     * Get Pair
     * @return this
     */
    public Pair getValue()
    {
        return this;
    }

    /**
     * Get the first element of the pair
     * @return first
     */
    public T1 getFirst() {
        return first;
    }

    /**
     * Get the second element of the pair
     * @return second
     */
    public T2 getSecond() {
        return second;
    }
}
