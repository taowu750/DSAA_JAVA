package algs6_background.sec1_event_driven_simulation.src;

import util.datastructure.MyPriorityQueue;

/**
 * 碰撞事件模拟系统，用来完成模拟。
 * <p>
 * 我们用一个优先队列记录所有的事件，事件是未来某个时间的一次潜在碰撞，可能发生在
 * 两个粒子之间，也可能发生在粒子和墙之间。事件的优先级就是它发生的时间，因此当从
 * 优先队列中删除优先级最低的事件时，就会得到下一次潜在的碰撞。
 * <p>
 * 当我们知道粒子的位置、大小和速度，以及墙的位置时，我们就可以计算它是否会和墙
 * 发生碰撞。粒子之间的计算类似，只是更加复杂。预测结果通常是不会发生碰撞，此时
 * 不需要向优先队列中插入任何东西。有可能预测的时间点太过遥远，这种事件很可能被
 * 干扰而不会发生，所以我们指定一个 limit 参数作为有效的时间段，这样就能忽略时间
 * 大于 limit 的所有事件。
 * <p>
 * 有可能预测的许多事件都不会发生，因为它们被其它的碰撞打断了。为了处理这种情况，
 * 我们让每个粒子记录它的碰撞次数。当从优先队列中取出一个事件进行处理时，我们会
 * 检查粒子相较于该事件之前保存的碰撞次数是否发生变化。排除无效事件的方式有两种：
 * <ul>
 *     <li>
 *     延时方法：当某个粒子发生一次碰撞之后，我们不会删除优先队列中和该粒子相关
 *     的其他碰撞，而是在遇到它们时直接忽略。
 *     </li>
 *     <li>
 *     即时方法：当某个粒子发生一次碰撞之后，立即从优先队列中删除所有参与当前事件
 *     粒子的相关事件，然后再计算这些粒子的新的潜在碰撞事件。这种方式需要的优先队
 *     列更加复杂，需要实现删除操作。
 *     </li>
 * </ul>
 */
public class CollisionSystem {

    private Particle[] particles;
    private MyPriorityQueue<Event> priorityQueue;
    private double time;

    public CollisionSystem() {
        priorityQueue = new MyPriorityQueue<>(MyPriorityQueue.Order.MIN);
    }

    /**
     * 这里有三种不同的事件：粒子和垂直墙体碰撞、粒子和水平墙体碰撞、粒子和粒子碰撞。
     * 为了平滑动态地显示运动中的粒子，我们添加了第四种事件，即重绘事件。它的作用是
     * 将所有粒子在它们的当前位置画出。
     *
     * 使用 Event 表示这四种事件，因此允许粒子的值为 null：
     *  - a 和 b 均不为空：粒子与粒子碰撞
     *  - a 非空 b 为空：粒子 a 与水平墙体碰撞
     *  - a 为空 b 非空：粒子 b 与垂直墙体碰撞
     *  - a、b 都为空：重绘事件
     */
    private class Event implements Comparable<Event> {
        private final double time;
        private final Particle a, b;
        private final int countA, countB;

        // 创建一个发生在时间 t 且与 a、b 相关的新事件
        public Event(double t, Particle a, Particle b) {
            this.time = t;
            this.a = a;
            this.b = b;
            if (a != null)
                countA = a.count();
            else
                countA = -1;
            if (b != null)
                countB = b.count();
            else
                countB = -1;
        }

        @Override
        public int compareTo(Event that) {
            return Double.compare(time, that.time);
        }

        // 检测事件是否失效
        public boolean isValid() {
            if (a != null && a.count() != countA)
                return false;
            if (b != null && b.count() != countB)
                return false;

            return true;
        }
    }

    private void predictCollisions(Particle a, double limit) {
        if (a == null)
            return;

        // 添加粒子 a 与 其他粒子碰撞的事件
        for (int i = 0; i < particles.length; i++) {
            double dt = a.timeToHit(particles[i]);
            if (time + dt <= limit) {
                priorityQueue.offer(new Event(time + dt, a, particles[i]));
            }
        }
        // 添加粒子 a 与 水平墙壁碰撞的事件
        double dtX = a.timeToHitHorizontalWall();
        if (time + dtX <= limit)
            priorityQueue.offer(new Event(time + dtX, a, null));
        // 添加粒子 a 与 垂直墙壁碰撞的事件
        double dtY = a.timeToHitVerticalWall();
        if (time + dtY <= limit)
            priorityQueue.offer(new Event(time + dtY, null, a));
    }
}
