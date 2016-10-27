#Kafka 与 Spring 最佳结合实践

## 项目特点

​		**Kafka High Level API**

​		**观察者设计模式**



## 使用说明

```xml
<bean id="regKafkaRevicer" class="com.vti.kafka.revicer.RegKafkaReceiver">
	<property name="topic" value="${kafka.reg.topic}" />
	<property name="group" value="${kafka.group.id}" />
	<property name="zookeeper" value="${kafka.zookeeper.url}" />
</bean>
```


## 用法

```java
	public void test(){
		
		ExecutorService executorService = Executors.newCachedThreadPool();

		executorService.submit(new Runnable() {
			@Override
			public void run() {
				viewKafkaReceiver.start();
			}
		});
	}
```
