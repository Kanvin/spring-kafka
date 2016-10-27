package com.vti.kafka.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.cache.guava.GuavaCacheManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vti.kafka.recevicer.ViewKafkaReceiver;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:beans.xml" })
public class AppTest {
	
	@Resource
	private ViewKafkaReceiver viewKafkaReceiver;
	
	@Resource
	private GuavaCacheManager guavaCacheManager;
	
	public void test(){
		
		ExecutorService executorService = Executors.newCachedThreadPool();

		executorService.submit(new Runnable() {
			@Override
			public void run() {
				viewKafkaReceiver.start();
			}
		});
	}
	
	@Test
	public void testCache(){
		guavaCacheManager.getCache("default").put("name","abc");
		System.out.println(guavaCacheManager.getCache("default").get("name").get());
	}
}
