package Day25.Day25.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import Day25.Day25.Service.PurchaseOrderEventSubscriber;


// this is a config file for redis pubsub messaging
// this is only needed when you want to implement pubsub
@Configuration
public class MessageConfiguration {

    // you need to include the jedisconnectionfactory
    @Bean
    JedisConnectionFactory jedisConnectionFactory(){
        return new JedisConnectionFactory();
    }

    // annotate this bean according to what you want to call your channel
    @Bean("poPubSub")
    ChannelTopic topic(){
        return new ChannelTopic("po-channel");
    }


    // this is for the subscriber to read messages 
    @Autowired
    PurchaseOrderEventSubscriber poSubscriber;

    // this is for the bean for message listener, takes in the subscriber as an args
    @Bean
    MessageListenerAdapter messageListener(){
        return new MessageListenerAdapter(poSubscriber);
    }

    // the rediscontainer is the one that sends out the messages
    @Bean
    RedisMessageListenerContainer redisContainer(){
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(messageListener(), topic());
        return container;

    }
    
}