//package club.gach_dong.config;
//
//import org.springframework.amqp.rabbit.connection.ConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//
//@Configuration
//public class RabbitMQConfig {
//    @Bean
//    public Queue notificationQueue() {
//        return new Queue("notifications", true);
//    }
//
//    @Bean
//    public DirectExchange notificationExchange() {
//        return new DirectExchange("notifications-exchange");
//    }
//
//    @Bean
//    public Binding notificationBinding(Queue notificationQueue, DirectExchange notificationExchange) {
//        return BindingBuilder.bind(notificationQueue).to(notificationExchange).with("notifications");
//    }
//
//    @Bean
//    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
//        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
//        rabbitTemplate.setMessageConverter(jsonMessageConverter());
//        rabbitTemplate.setMandatory(true);
//        return rabbitTemplate;
//    }
//
//    @Bean
//    public Jackson2JsonMessageConverter jsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//}
