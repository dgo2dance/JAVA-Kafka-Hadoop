import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;




/**
 * ½ÓÊÕÊý¾Ý
 * ½ÓÊÕµ½: message: 10
½ÓÊÕµ½: message: 11
½ÓÊÕµ½: message: 12
½ÓÊÕµ½: message: 13
½ÓÊÕµ½: message: 14
 * @author zm
 *
 */
public class kafkaConsumer extends Thread{

	private String topic;
	
	public kafkaConsumer(String topic){
		super();
		this.topic = topic;
	}
	
	
	@Override
	public void run() {
		ConsumerConnector consumer = createConsumer();
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, 1); // Ò»´Î´ÓÖ÷ÌâÖÐ»ñÈ¡Ò»¸öÊý¾Ý
		 Map<String, List<KafkaStream<byte[], byte[]>>>  messageStreams = consumer.createMessageStreams(topicCountMap);
		 KafkaStream<byte[], byte[]> stream = messageStreams.get(topic).get(0);// »ñÈ¡Ã¿´Î½ÓÊÕµ½µÄÕâ¸öÊý¾Ý
		 ConsumerIterator<byte[], byte[]> iterator =  stream.iterator();
		 while(iterator.hasNext()){
			 String message = new String(iterator.next().message());
			 System.out.println("½ÓÊÕµ½: " + message);
		 }
	}

	private ConsumerConnector createConsumer() {
		Properties properties = new Properties();
		properties.put("zookeeper.connect", "192.168.1.110:2181,192.168.1.111:2181,192.168.1.112:2181");//ÉùÃ÷zk
		properties.put("group.id", "group1");
		return Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));
	 }
	
	
	public static void main(String[] args) {
		new kafkaConsumer("test").start();// Ê¹ÓÃkafka¼¯ÈºÖÐ´´½¨ºÃµÄÖ÷Ìâ test 
		
	}
	 
}
