package com.vti.kafka.conn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;

public abstract class KafkaReceiver {

	private String topic;

	private String group;

	private String zookeeper;

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getZookeeper() {
		return zookeeper;
	}

	public void setZookeeper(String zookeeper) {
		this.zookeeper = zookeeper;
	}

	public abstract void onRevice(MessageAndMetadata<byte[], byte[]> messageAndMetadata);

	public void start() {

		Properties props = new Properties();
		props.put("zookeeper.connect", zookeeper);
		props.put("group.id", group);
		props.put("zookeeper.session.timeout.ms", "100000");
		props.put("rebalance.backoff.ms", "20000");
		props.put("rebalance.max.retries", "10");
		props.put("zookeeper.connection.timeout.ms", "100000");

		ConsumerConfig config = new ConsumerConfig(props);
		ConsumerConnector consumerConnector = kafka.consumer.Consumer.createJavaConsumerConnector(config);

		Map<String, Integer> topicMap = new HashMap<String, Integer>();
		topicMap.put(topic, new Integer(1));

		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumerConnector.createMessageStreams(topicMap);
		List<KafkaStream<byte[], byte[]>> kafkaStreams = consumerMap.get(topic);

		for (KafkaStream<byte[], byte[]> kafkaStream : kafkaStreams) {
			ConsumerIterator<byte[], byte[]> iterator = kafkaStream.iterator();
			while (iterator.hasNext()) {
				onRevice(iterator.next());
			}
		}
	}
}
