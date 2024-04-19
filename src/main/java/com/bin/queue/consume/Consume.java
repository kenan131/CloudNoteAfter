package com.bin.queue.consume;

import com.bin.queue.dto.MessageDto;

/**
 * @author: bin.jiang
 * @date: 2024/4/9 11:18
 **/

public interface Consume {
    void process(MessageDto messageDto);
}
