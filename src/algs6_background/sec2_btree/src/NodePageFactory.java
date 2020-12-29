package algs6_background.sec2_btree.src;

import java.util.Comparator;

public class NodePageFactory implements PageFactory {
    @Override
    public <K> Page<K> newPage(int maxNumNodes) {
        return new NodePage<>(maxNumNodes);
    }

    @Override
    public <K> Page<K> newPage(int maxNumNodes, Comparator<K> comparator) {
        return new NodePage<>(maxNumNodes, comparator);
    }

    @Override
    public <K> Page<K> newPage(int maxNumNodes, Comparator<K> comparator, boolean external) {
        return new NodePage<>(maxNumNodes, comparator, external);
    }

    @Override
    public <K> Page<K> newRootPage(int maxNumNodes, Comparator<K> comparator, boolean external) {
        return new NodePage<>(maxNumNodes, comparator, external, true);
    }
}
