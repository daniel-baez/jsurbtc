package cl.daplay.jsurbtc.fun;

@FunctionalInterface
public interface ThrowingSupplier<T> {

    T get() throws Exception;

}