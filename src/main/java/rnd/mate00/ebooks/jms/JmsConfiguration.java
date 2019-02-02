package rnd.mate00.ebooks.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class JmsConfiguration {

    private String brokerUrl = "tcp://172.17.0.3:61616";

    @Bean("connectionFactory")
    public ActiveMQConnectionFactory activeMQConnectionFactory() {
        return new ActiveMQConnectionFactory(brokerUrl);
    }

    @Bean("theQueue")
    public ActiveMQQueue theQueue() {
        return new ActiveMQQueue("ebook.queue");
    }

    @Bean("jmsTemplate")
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory connectionFactory,
                                   ActiveMQQueue defaultDestination,
                                   MessageConverter messageConverter) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setDefaultDestination(defaultDestination);
        jmsTemplate.setMessageConverter(messageConverter);
        return jmsTemplate;
    }

    @Bean("messageConverter")
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter= new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

}
