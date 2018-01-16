package cl.daplay.jsurbtc.fun;


@FunctionalInterface
public interface ThrowingFunction<T, K> {

    K apply(T t) throws Exception;

}