package com.vti.kafka.recevicer;

import com.vti.kafka.conn.KafkaReceiver;

import kafka.message.MessageAndMetadata;

public class RegKafkaReceiver extends KafkaReceiver {

	@Override
	public void onRevice(MessageAndMetadata<byte[], byte[]> messageAndMetadata) {
		System.out.println("Receive:" + messageAndMetadata.topic() + new String(messageAndMetadata.message()));
	}
}
