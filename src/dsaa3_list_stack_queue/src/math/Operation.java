package dsaa3_list_stack_queue.src.math;


/**
 * 用来描述操作符的操作
 */
public interface Operation<RETURN> {

    /**
     * 操作符具体的操作
     *
     * @param params 操作符需要的参数
     * @return 操作的结果
     */
    RETURN execute(Object...params);

    /**
     * 对所做操作的描述
     *
     * @return 描述字符串
     */
    String description();
}
