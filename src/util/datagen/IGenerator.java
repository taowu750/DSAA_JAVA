package util.datagen;

/**
 * 对象生成器，无需提供参数
 * @author wutao
 *
 * @param <T>
 */
public interface IGenerator<T> {
	T next();
}
