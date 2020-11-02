package dsaa3_list_stack_queue.src.math;


/**
 * 数学操作符
 */
public class MathOperator {

    private String symbol;
    private Priority priority;
    private Orientation orientation;
    private BindingQuantity bindingQuantity;
    private Operation<Double> operation;


    public MathOperator(String symbol, Priority priority, Orientation orientation, BindingQuantity bindingQuantity,
                        Operation<Double> operation) {
        this.symbol = symbol;
        this.priority = priority;
        this.orientation = orientation;
        this.bindingQuantity = bindingQuantity;
        this.operation = operation;
    }


    public String getSymbol() {
        return symbol;
    }

    public Priority getPriority() {
        return priority;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public BindingQuantity getBindingQuantity() {
        return bindingQuantity;
    }

    public Operation<Double> getOperation() {
        return operation;
    }

    @Override
    public String toString() {
        return "[symbol='" + symbol + "', priority=" + priority + ", orientation=" + orientation + ", bindingQuantity="
                + bindingQuantity + "]";
    }

    public static enum Priority {

        LEVEL1("level1"), LEVEL2("level2"), LEVEL3("level3"), LEVEL4("level4"), LEVEL5("level5");


        private String description;


        private Priority(String description) {
            this.description = description;
        }


        public String toString() {
            return description;
        }
    }

    public static enum Orientation {
        LEFT_TO_RIGHT("left to right"), RIGHT_TO_LEFT("right to left");


        private String description;


        private Orientation(String description) {
            this.description = description;
        }


        public String toString() {
            return description;
        }
    }

    public static enum BindingQuantity {
        NONE("none"), ONE("monocular"), TWO("binocular");


        private String description;


        private BindingQuantity(String description) {
            this.description = description;
        }


        public String toString() {
            return description;
        }
    }
}
