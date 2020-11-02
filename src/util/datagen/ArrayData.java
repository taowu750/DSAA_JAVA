package util.datagen;


public class ArrayData {
	/**
	 * 填充一个已经存在的数组
	 * @param array
	 * @param gen
	 * @return
	 */
	public static <T> T[] array(T[] array, IGenerator<T> gen) {
		return new CollectionData<T>(gen, array.length).toArray(array);
	}
	
	/**
	 * 创建一个新数组
	 * @param type
	 * @param gen
	 * @param size
	 * @return
	 */
	public static <T> T[] array(Class<T> type, IGenerator<T> gen, int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[])java.lang.reflect.Array.newInstance(type, size);
		
		return new CollectionData<T>(gen, size).toArray(array);
	}
}
