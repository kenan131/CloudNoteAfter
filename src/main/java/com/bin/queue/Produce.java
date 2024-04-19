package com.bin.queue;

import com.bin.queue.dto.MessageDto;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: bin.jiang
 * @date: 2024/4/18 10:33
 **/

public interface Produce {
    static boolean send(MessageDto messageDto){
        throw new UnsupportedOperationException();
    }
    static boolean addFailMessage(MessageDto messageDto){
        return false;
    }
}
