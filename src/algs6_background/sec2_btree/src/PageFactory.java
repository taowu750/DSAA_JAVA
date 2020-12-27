package algs6_background.sec2_btree.src;

public interface PageFactory {

    <K> Page<K> newPage(int maxNumNodes);
}
