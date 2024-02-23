package net.olegueyan.monstroclasse.function;

/**
 * Same comportment of Consumer but allows to not write try/catch inside
 * @param <T> type of value to operate
 */
public interface ConsumerNoError<T>
{
    void accept(T t) throws Exception;
}