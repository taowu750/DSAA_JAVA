package util.datagen;

import java.util.ArrayList;


/**
 * 使用对象生成器填充一个CollectionData对象,该对象可以用作容器类对象的测试数据
 * @author wutao
 *
 * @param <T>
 */
@SuppressWarnings("serial")
public class CollectionData<T> extends ArrayList<T>{
	
	public CollectionData(IGenerator<T> gen, int size) {
		for(int i = 0; i < size; i++) {
			add(gen.next());
		}
	}
	
	public static <T> CollectionData<T> list(IGenerator<T> gen, int size) {
		return new CollectionData<>(gen, size);
	}
}
