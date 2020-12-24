package algs6_background.sec1_event_driven_simulation.src;

import util.algs.StdDraw;
import util.algs.StdRandom;

import static java.lang.Double.POSITIVE_INFINITY;
import static java.lang.Math.*;

/**
 * 粒子类，封装了和粒子有关的计算。
 * <p>
 * 在 x 坐标 [0, 1]，y 坐标 [0, 1] 的空间内模拟粒子的运动和碰撞。
 */
public class Particle {

    // 粒子中点坐标
    private double rx, ry;
    // 横轴和纵轴的速度（时间单位毫秒）
    private double vx, vy;
    // 半径
    private double s;
    // 质量
    private double mass;
    // 此粒子参与的碰撞次数
    private int count;

    // 在单位空间内随机创建一个粒子
    public Particle() {
        s = StdRandom.uniform(0.01, 0.04);

        rx = StdRandom.uniform(s, 1 - s);
        ry = StdRandom.uniform(s, 1 - s);
        mass = PI * pow(s, 2);

        // 速度大于 0 向坐标轴增长的方向运动；小于 0 向坐标轴减少的方向运动
        vx = StdRandom.uniform(0.005, 0.025);
        if (StdRandom.bernoulli())
            vx = StdRandom.uniform(-0.025, -0.005);
        vy = StdRandom.uniform(0.005, 0.025);
        if (StdRandom.bernoulli())
            vy = StdRandom.uniform(-0.025, -0.005);
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
        StdDraw.filledCircle(rx, ry, s);
    }

    /**
     * 根据事件的流逝 dt 改变粒子位置
     *
     * @param dt
     */
    public void move(double dt) {
        rx += vx * dt;
        ry += vy * dt;
    }

    public int count() {
        return count;
    }

    /**
     * 该粒子和粒子 b 碰撞所需时间。如果不会碰撞返回{@link Double#POSITIVE_INFINITY}。
     * 此方法的实现所需数学公式来自：https://algs4.cs.princeton.edu/61event/
     *
     * @param b
     * @return
     */
    public double timeToHit(Particle b) {
        double deltaRx = b.rx - rx, deltaRy = b.ry - ry;
        double deltaVx = b.vx - vx, deltaVy = b.vy - vy;
        double deltaRV = deltaRx * deltaVx + deltaRy * deltaVy;

        // deltaRV >= 0
        if (deltaRV > -1e-15)
            return POSITIVE_INFINITY;

        double deltaR2 = pow(deltaRx, 2) + pow(deltaRy, 2);
        double deltaV2 = pow(deltaVx, 2) + pow(deltaVy, 2);
        double sigma = s + b.s;
        double d = pow(deltaRV, 2) - deltaV2 * (deltaR2 - pow(sigma, 2));

        // d < 0
        if (d < -1e-15)
            return POSITIVE_INFINITY;

        return -(deltaRV + sqrt(d)) / deltaV2;
    }

    /**
     * 该粒子与水平墙体碰撞所需时间。如果不会碰撞返回{@link Double#POSITIVE_INFINITY}。
     *
     * @return
     */
    public double timeToHitHorizontalWall() {
        double time = timeToHitHorizontalOnlyX();
        if (time == POSITIVE_INFINITY || time > timeToHitVerticalOnlyY())
            // 如果水平速度为 0 或者会先撞上垂直墙壁，返回 POSITIVE_INFINITY
            return POSITIVE_INFINITY;
        else
            return time;
    }

    /**
     * 该粒子与垂直墙体碰撞所需时间。如果不会碰撞返回{@link Double#POSITIVE_INFINITY}。
     *
     * @return
     */
    public double timeToHitVerticalWall() {
        double time = timeToHitVerticalOnlyY();
        if (time == POSITIVE_INFINITY || time > timeToHitHorizontalOnlyX())
            // 如果垂直速度为 0 或者会先撞上水平墙壁，返回 POSITIVE_INFINITY
            return POSITIVE_INFINITY;
        else
            return time;
    }

    /**
     * 只考虑 x 轴时水平墙体碰撞所需时间
     *
     * @return
     */
    private double timeToHitHorizontalOnlyX() {
        if (abs(vy) < 1e-15)
            // vx 等于 0，则不会和水平墙体碰撞
            return POSITIVE_INFINITY;
        else if (vy < 0)
            return (ry - s) / -vy;
        else
            return (1 - s - ry) / vy;
    }

    /**
     * 只考虑 y 轴时水平墙体碰撞所需时间
     *
     * @return
     */
    private double timeToHitVerticalOnlyY() {
        if (abs(vx) < 1e-15)
            // vy 等于 0，则不会和垂直墙体碰撞
            return POSITIVE_INFINITY;
        else if (vx < 0)
            return (rx - s) / -vx;
        else
            return (1 - s - rx) / vx;
    }

    /**
     * 该粒子和粒子 b 碰撞后的速度。
     * 此方法的实现所需数学公式来自：https://algs4.cs.princeton.edu/61event/
     *
     * @param b
     * @return
     */
    public double bounceOff(Particle b) {
        double deltaRx = b.rx - rx, deltaRy = b.ry - ry;
        double deltaVx = b.vx - vx, deltaVy = b.vy - vy;
        double deltaRV = deltaRx * deltaVx + deltaRy * deltaVy;

        double sigma = s + b.s;
        double J = (2 * mass * b.mass * deltaRV) / (sigma * (mass + b.mass));
        double Jx = J * deltaRx / sigma, Jy = J * deltaRy / sigma;

        vx += Jx / mass;
        b.vx -= Jx / b.mass;

        vy += Jy / mass;
        b.vy -= Jy / b.mass;

        count++;
        b.count++;

        return 0;
    }

    /**
     * 该粒子和水平墙体碰撞后的速度。
     *
     * @return
     */
    public double bounceOffHorizontalWall() {
        vy = -vy;
        count++;
        return vy;
    }

    /**
     * 该粒子和垂直墙体碰撞后的速度。
     *
     * @return
     */
    public double bounceOffVerticalWall() {
        vx = -vx;
        count++;
        return vx;
    }

    @Override
    public String toString() {
        return "Particle{" +
                "rx=" + rx +
                ", ry=" + ry +
                ", vx=" + vx +
                ", vy=" + vy +
                ", s=" + s +
                ", count=" + count +
                '}';
    }

    public static void main(String[] args) {
        double sa = 0.038604982989289154, sb = 0.03760966207849281;
        Particle a = new Particle(0.7475462204954608, 0.4251769629960736,
                6.833647258368218E11, -6.736203154688473E11,
                sa, PI * pow(sa, 2));
        Particle b = new Particle(0.7388944464865955, 0.3975619582828199,
                -1.29822208268956E11, -4.188490541962184E11,
                sb, PI * pow(sb, 2));

        System.out.println(a);
        System.out.println(b);
        System.out.println();
        a.bounceOff(b);
        System.out.println(a);
        System.out.println(b);
    }
}
