package algs6_background.sec1_event_driven_simulation.src;

/**
 * 粒子类，封装了和粒子有关的计算。
 */
public class Particle {

    // 中点坐标
    private double rx, ry;
    // 横轴和纵轴的速度
    private double vx, vy;
    // 半径
    private double s;
    // 质量
    private double mass;
    // 此粒子参与的碰撞次数
    private int count;

    // 在单位空间内随机创建一个例子
    public Particle() {

    }

    public Particle(double rx, double ry, double vx, double vy, double s, double mass) {
        this.rx = rx;
        this.ry = ry;
        this.vx = vx;
        this.vy = vy;
        this.s = s;
        this.mass = mass;
    }

    /**
     * 画出粒子
     */
    public void draw() {

    }

    /**
     * 根据事件的流逝 dt 改变粒子位置
     *
     * @param dt
     */
    public void move(double dt) {

    }

    public int count() {
        return count;
    }

    /**
     * 该粒子和粒子 b 碰撞所需时间。如果不会碰撞返回{@link Double#POSITIVE_INFINITY}。
     *
     * @param b
     * @return
     */
    public double timeToHit(Particle b) {
        return 0;
    }

    /**
     * 该粒子与水平墙体碰撞所需时间。如果不会碰撞返回{@link Double#POSITIVE_INFINITY}。
     *
     * @return
     */
    public double timeToHitHorizontalWall() {
        return 0;
    }

    /**
     * 该粒子与垂直墙体碰撞所需时间。如果不会碰撞返回{@link Double#POSITIVE_INFINITY}。
     *
     * @return
     */
    public double timeToHitVerticalWall() {
        return 0;
    }

    /**
     * 该粒子和粒子 b 碰撞后的速度。
     *
     * @param b
     * @return
     */
    public double bounceOff(Particle b) {
        return 0;
    }

    /**
     * 该粒子和水平墙体碰撞后的速度。
     *
     * @return
     */
    public double bounceOffHorizontalWall() {
        return 0;
    }

    /**
     * 该粒子和垂直墙体碰撞后的速度。
     *
     * @return
     */
    public double bounceOffVerticalWall() {
        return 0;
    }
}
