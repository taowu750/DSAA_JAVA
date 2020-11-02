package algs2_sort.sec5_application;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 公正的选举。为了避免对名字排在字母表靠后的候选人的偏见，加州在 2003 年
 * 的州长选举中将所有候选人按照以下字母顺序排列：
 * R W Q O J M V A H B S G Z X N T C I E K U P D Y F L
 * 创建一个遵循这种顺序的数据类型并编写一个用例 California，在它的静态方法
 * main() 中将字符串按照这种方式排序。假设所有的字符串都是大写的。
 * </p>
 */
public class E16_FairElection {
}

class California implements Comparable<California> {

    private static final Map<Character, Integer> CHAR_ORDER = new HashMap<>();
    static {
        char[] orders = "RWQOJMVAHBSGZXNTCIEKUPDYFL".toCharArray();
        for (int i = 0; i < orders.length; i++) {
            CHAR_ORDER.put(orders[i], orders.length - i);
        }
    }


    private String name;


    public California(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int compareTo(California o) {
        int cmp = name.length() - o.name.length();
        int len = cmp > 1 ? o.name.length() : name.length();

        for (int i = 0; i < len; i++) {
            int diff = CHAR_ORDER.get(name.charAt(i)) - CHAR_ORDER.get(o.name.charAt(i));
            if (diff > 0)
                return 1;
            else if (diff < 0)
                return -1;
        }

        return cmp > 0 ? 1 : cmp == 0 ? 0 : -1;
    }
}
