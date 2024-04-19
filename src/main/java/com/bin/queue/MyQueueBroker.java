package com.bin.queue;

import com.bin.queue.consume.Consume;
import com.bin.queue.dto.MessageDto;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @author: bin.jiang
 * @date: 2024/4/9 10:50
 **/
@Component
public class MyQueueBroker implements Produce,ApplicationContextAware, InitializingBean {
    private static LinkedBlockingQueue<MessageDto> queue = new LinkedBlockingQueue<>();

    private static LinkedBlockingQueue<MessageDto> failQueue = new LinkedBlockingQueue<>();

    private Map<String, Consume> processMap = new HashMap<>();
    private ThreadPoolExecutor threadPoolExecutor;
    private Thread processThread;

    @Override
    public void afterPropertiesSet() throws Exception {
        threadPoolExecutor = new ThreadPoolExecutor(3, 6, 60L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                // 使用当前线程进行执行。阻塞后续任务提交。防止消息丢失。
                r.run();
            }
        });
        processThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try {
                        MessageDto messageDto = queue.take();
                        String topic = messageDto.getTopic();
                        Consume consume = processMap.get(topic);
                        if(consume != null){
                            threadPoolExecutor.execute(new Runnable() {
                                @Override
                                public void run() {
                                    consume.process(messageDto);
                                }
                            });
                        }else{
                            failQueue.add(messageDto);
                            System.out.println("该 topic:[ " + topic + " ]未定义消费逻辑。");
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        processThread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Consume> beansOfType = applicationContext.getBeansOfType(Consume.class);
        for(Map.Entry<String,Consume> consume : beansOfType.entrySet()){
            String key = consume.getKey();
            Consume value = consume.getValue();
            processMap.put(key,value);
        }
    }

    public static boolean send(MessageDto messageDto){
        return queue.add(messageDto);
    }

    public static boolean addFailMessage(MessageDto messageDto){
        return failQueue.add(messageDto);
    }
}
