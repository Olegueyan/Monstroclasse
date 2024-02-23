package net.olegueyan.monstroclasse.function;

/**
 * Operation on two values that return a new value
 * @param <T> type one of value to use
 * @param <U> type two of value to use
 * @param <V> type of value to return
 */
public interface BiFunction<T, U, V>
{
    V apply(T t, U u);
}