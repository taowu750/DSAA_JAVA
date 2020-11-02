package util.datagen;

import util.base.ExRandom;

import java.util.Random;


public class RandomGenerator {

    public static class Boolean implements IGenerator<java.lang.Boolean> {
        private Random rand;

        public Boolean() {
            rand = new Random();
        }

        public Boolean(int seed) {
            rand = new Random(seed);
        }

        public java.lang.Boolean next() {
            return rand.nextBoolean();
        }
    }

    public static class Byte implements IGenerator<java.lang.Byte> {
        private ExRandom rand = new ExRandom();
		private byte lower = java.lang.Byte.MIN_VALUE, upper = java.lang.Byte.MAX_VALUE;

        public Byte() {
        }

        public Byte(long seed) {
            rand = new ExRandom(seed);
        }

        public Byte(byte lower) {
            this.lower = lower;
        }

        public Byte(byte lower, byte upper) {
            this.lower = lower;
            this.upper = upper;
        }

        public Byte(long seed, byte lower) {
            this(lower);
            rand = new ExRandom(seed);
        }

        public Byte(long seed, byte lower, byte upper) {
            this(lower, upper);
            rand = new ExRandom(seed);
        }

        public java.lang.Byte next() {
            return (byte) rand.rand(lower, upper);
        }
    }

    public static class Short implements IGenerator<java.lang.Short> {
        private ExRandom rand = new ExRandom();
        private short lower = java.lang.Short.MIN_VALUE, upper = java.lang.Short.MAX_VALUE;

        public Short() {
        }

        public Short(long seed) {
            rand = new ExRandom(seed);
        }

        public Short(short lower) {
            this.lower = lower;
        }

        public Short(short lower, short upper) {
            this.lower = lower;
            this.upper = upper;
        }

        public Short(long seed, short lower) {
            this(lower);
            rand = new ExRandom(seed);
        }

        public Short(long seed, short lower, short upper) {
            this(lower, upper);
            rand = new ExRandom(seed);
        }

        public java.lang.Short next() {
            return (short) rand.rand(lower, upper);
        }
    }

    public static class Integer implements IGenerator<java.lang.Integer> {
        private ExRandom rand = new ExRandom();
        private int lower = java.lang.Integer.MIN_VALUE, upper = java.lang.Integer.MAX_VALUE;


        public Integer() {
        }

        public Integer(long seed) {
            rand = new ExRandom(seed);
        }

        public Integer(int lower) {
            this.lower = lower;
        }

        public Integer(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }

        public Integer(long seed, int lower) {
            this(lower);
            rand = new ExRandom(seed);
        }

        public Integer(long seed, int lower, int upper) {
            this(lower, upper);
            rand = new ExRandom(seed);
        }

        public java.lang.Integer next() {
            return rand.rand(lower, upper);
        }
    }

    public static class Long implements IGenerator<java.lang.Long> {
        private ExRandom rand = new ExRandom();
        private long lower = java.lang.Long.MIN_VALUE, upper = java.lang.Long.MAX_VALUE;


        public Long() {
        }

        public Long(long seed) {
            rand = new ExRandom(seed);
        }

        public Long(long lower, long upper) {
            this.lower = lower;
            this.upper = upper;
        }

        public Long(long seed, long lower, long upper) {
            this(lower, upper);
            rand = new ExRandom(seed);
        }

        public java.lang.Long next() {
            return rand.rand(lower, upper);
        }
    }

    public static class Float implements IGenerator<java.lang.Float> {
        private ExRandom rand = new ExRandom();
        private float lower = java.lang.Float.MIN_VALUE, upper = java.lang.Float.MAX_VALUE;


        public Float() {
        }

        public Float(long seed) {
            rand = new ExRandom(seed);
        }

        public Float(float lower) {
            this.lower = lower;
        }

        public Float(float lower, float upper) {
            this.lower = lower;
            this.upper = upper;
        }

        public Float(long seed, float lower) {
            this(lower);
            rand = new ExRandom(seed);
        }

        public Float(long seed, float lower, float upper) {
            this(lower, upper);
            rand = new ExRandom(seed);
        }

        public java.lang.Float next() {
            return rand.rand(lower, upper);
        }
    }

    public static class Double implements IGenerator<java.lang.Double> {
        private ExRandom rand = new ExRandom();
        private double lower = java.lang.Double.MIN_VALUE, upper = java.lang.Double.MAX_VALUE;

        public Double() {
        }

        public Double(long seed) {
            rand = new ExRandom(seed);
        }

        public Double(double lower) {
            this.lower = lower;
        }

        public Double(double lower, double upper) {
            this.lower = lower;
            this.upper = upper;
        }

        public Double(long seed, double lower) {
            this(lower);
            rand = new ExRandom(seed);
        }

        public Double(long seed, double lower, double upper) {
            this(lower, upper);
            rand = new ExRandom(seed);
        }

        public java.lang.Double next() {
            return rand.rand(lower, upper);
        }
    }
}
