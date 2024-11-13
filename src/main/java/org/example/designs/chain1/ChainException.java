package org.example.designs.chain1;

/**
 * 流水线异常
 */
public class ChainException extends Exception{
    public ChainException(String message) {
        super(message);
    }
}
