package com.kontomatik.util;

public class Exceptions {

  public static <R> R uncheck(ThrowingSupplier<R> supplier) {
    try {
      return supplier.get();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @FunctionalInterface
  public interface ThrowingSupplier<T> {

    T get() throws Exception;

  }

}
