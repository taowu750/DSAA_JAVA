package util.datagen;


/**
 * 顺序值生成器。
 */
public class CountingGenerator {
    private static final char[] ALLCHARS = ("abcdefghijklmnopqrstuvwxyz" +
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

    public static class Boolean implements IGenerator<java.lang.Boolean> {
        private boolean value;

        public Boolean() {
            value = false;
        }

        public Boolean(boolean value) {
            this.value = value;
        }

        public java.lang.Boolean next() {
            value = !value;

            return value;
        }
    }

    public static class Byte implements IGenerator<java.lang.Byte> {
        private byte value = 0, step = 1;
        private boolean isAdd = true;

        public Byte() {
        }

        public Byte(byte value) {
            this.value = value;
        }

        public Byte(boolean isAdd) {
            this.isAdd = isAdd;
        }

        public Byte(byte value, byte step) {
            this.value = value;
            this.step = step;
        }

        public Byte(byte value, boolean isAdd) {
            this.value = value;
            this.isAdd = isAdd;
        }

        public Byte(byte value, byte step, boolean isAdd) {
            this.value = value;
            this.step = step;
            this.isAdd = isAdd;
        }

        public java.lang.Byte next() {
            byte result = value;
            if (isAdd) {
                value += step -= step;
            } else {
                value -= step;
            }

            return result;
        }
    }

    public static class Short implements IGenerator<java.lang.Short> {
        private short value = 0, step = 1;
        private boolean isAdd = true;

        public Short() {
        }

        public Short(short value) {
            this.value = value;
        }

        public Short(boolean isAdd) {
            this.isAdd = isAdd;
        }

        public Short(short value, short step) {
            this.value = value;
            this.step = step;
        }

        public Short(short value, boolean isAdd) {
            this.value = value;
            this.isAdd = isAdd;
        }

        public Short(short value, short step, boolean isAdd) {
            this.value = value;
            this.step = step;
            this.isAdd = isAdd;
        }

        public java.lang.Short next() {
            short result = value;
            if (isAdd) {
                value += step;
            } else {
                value -= step;
            }

            return result;
        }
    }

    public static class Integer implements IGenerator<java.lang.Integer> {
        private int value = 0, step = 1;
        private boolean isAdd = true;

        public Integer() {
        }

        public Integer(int value) {
            this.value = value;
        }

        public Integer(boolean isAdd) {
            this.isAdd = isAdd;
        }

        public Integer(int value, int step) {
            this.value = value;
            this.step = step;
        }

        public Integer(int value, boolean isAdd) {
            this.value = value;
            this.isAdd = isAdd;
        }

        public Integer(int value, int step, boolean isAdd) {
            this.value = value;
            this.step = step;
            this.isAdd = isAdd;
        }

        public java.lang.Integer next() {
            int result = value;
            if (isAdd) {
                value += step;
            } else {
                value -= step;
            }

            return result;
        }
    }

    public static class Long implements IGenerator<java.lang.Long> {
        private long value = 0L, step = 1L;
        private boolean isAdd = true;

        public Long() {
        }

        public Long(long value) {
            this.value = value;
        }

        public Long(boolean isAdd) {
            this.isAdd = isAdd;
        }

        public Long(long value, long step) {
            this.value = value;
            this.step = step;
        }

        public Long(long value, boolean isAdd) {
            this.value = value;
            this.isAdd = isAdd;
        }

        public Long(long value, long step, boolean isAdd) {
            this.value = value;
            this.step = step;
            this.isAdd = isAdd;
        }

        public java.lang.Long next() {
            long result = value;
            if (isAdd) {
                value += step;
            } else {
                value -= step;
            }

            return result;
        }
    }

    public static class Float implements IGenerator<java.lang.Float> {
        private float value = 0.f, step = 1.f;
        private boolean isAdd = true;

        public Float() {
        }

        public Float(float value) {
            this.value = value;
        }

        public Float(boolean isAdd) {
            this.isAdd = isAdd;
        }

        public Float(float value, float step) {
            this.value = value;
            this.step = step;
        }

        public Float(float value, boolean isAdd) {
            this.value = value;
            this.isAdd = isAdd;
        }

        public Float(float value, float step, boolean isAdd) {
            this.value = value;
            this.step = step;
            this.isAdd = isAdd;
        }

        public java.lang.Float next() {
            float result = value;
            if (isAdd) {
                value += step;
            } else {
                value -= step;
            }

            return result;
        }
    }

    public static class Double implements IGenerator<java.lang.Double> {
        private double value = 0., step = 1.;
        private boolean isAdd = true;

        public Double() {
        }

        public Double(double value) {
            this.value = value;
        }

        public Double(boolean isAdd) {
            this.isAdd = isAdd;
        }

        public Double(double value, double step) {
            this.value = value;
            this.step = step;
        }

        public Double(double value, boolean isAdd) {
            this.value = value;
            this.isAdd = isAdd;
        }

        public Double(double value, double step, boolean isAdd) {
            this.value = value;
            this.step = step;
            this.isAdd = isAdd;
        }

        public java.lang.Double next() {
            double result = value;
            if (isAdd) {
                value += step;
            } else {
                value -= step;
            }

            return result;
        }
    }
}






