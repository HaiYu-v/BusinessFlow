package org.example.designs.chain.core;

/**
 * Bean异常
 *
 * <p>
 *      TODO
 * </p>
 *
 * @author  HaiYu
 * @version 1.0.0
 * @create  2024-11-19
 */
public class ChainException extends Exception{
    public ChainException(String message){
        super(message);
    }
    public ChainException(Exception e){
        super(e);
    }
}
