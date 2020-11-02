package dsaa3_list_stack_queue.src.math;


import java.util.*;

/**
 * 可以计算的后缀表达式
 */
public class MathUnitSequence implements Iterable<MathUnitSequence.Unit> {

    public static final int OPERAND = 0;
    public static final int OPERATOR = 1;

    private List<Unit> expression = new ArrayList<>();


    public MathUnitSequence(Unit...units) {
        if (units.length > 0) {
            Collections.addAll(expression, units);
        }
    }

    public MathUnitSequence(Collection<Unit> unitCollection) {
        expression.addAll(unitCollection);
    }


    public int size() {
        return expression.size();
    }

    public boolean isEmpty() {
        return expression.isEmpty();
    }

    public void clear() {
        expression.clear();
    }

    public void addUnit(int index, Unit unit) {
        expression.add(index, unit);
    }

    public void addUnit(Unit unit) {
        expression.add(unit);
    }

    public Unit getUnit(int index) {
        return expression.get(index);
    }

    public Unit setUnit(int index, Unit unit) {
        return expression.set(index, unit);
    }

    public Unit removeUnit(int index) {
        return expression.remove(index);
    }

    public Unit removeFirstUnit() {
        if (size() > 0) {
            return expression.remove(0);
        }

        return null;
    }

    public Unit removeLastUnit() {
        if (size() > 0) {
            return expression.remove(size() - 1);
        }

        return null;
    }

    @Override
    public Iterator<Unit> iterator() {
        return expression.iterator();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Unit unit : expression) {
            if (unit.getContentType() == OPERAND) {
                builder.append(unit.getOperand()).append(" ");
            } else if (unit.getContentType() == OPERATOR) {
                builder.append(unit.getOperator().getSymbol()).append(" ");
            }
        }

        return builder.toString();
    }

    public static class Unit {
        private int contentType;
        private Double operand;
        private MathOperator operator;


        public Unit(int contentType, Double operand, MathOperator operator) {
            this.contentType = contentType;
            this.operand = operand;
            this.operator = operator;
        }


        public int getContentType() {
            return contentType;
        }

        public Double getOperand() {
            return operand;
        }

        public MathOperator getOperator() {
            return operator;
        }

        public void setContentType(int contentType) {
            this.contentType = contentType;
        }

        public void setOperand(Double operand) {
            this.operand = operand;
        }

        public void setOperator(MathOperator operator) {
            this.operator = operator;
        }
    }
}
