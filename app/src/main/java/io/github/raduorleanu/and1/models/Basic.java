package io.github.raduorleanu.and1.models;

/**
 * To determine the result of the API Query
 * @param <T>
 */
public class Basic<T>{
    public int status;
    public boolean success;
    public T data;
}
