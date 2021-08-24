package com.seckill.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    /**
     * 用简单发布订阅模式
     */
    //交换机名称
    public static final String SECKILL_ROUTER_EXCHANGE = "seckill_router_exchange";
    //队列名称
    public static final String SECKILL_QUEUE = "seckill_queue";

    @Bean("seckillRouterExchange")
    public FanoutExchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange(SECKILL_ROUTER_EXCHANGE).durable(true).build();
    }

    @Bean("seckillQueue")
    public Queue fanoutQueue() {
        return QueueBuilder.durable(SECKILL_QUEUE).build();
    }

    /**
     * 绑定交换机队列
     * @param exchange
     * @param queue
     * @return
     */
    @Bean
    public Binding itemQueueExchange(@Qualifier("seckillRouterExchange") FanoutExchange exchange,
                                     @Qualifier("seckillQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange);
    }
}
