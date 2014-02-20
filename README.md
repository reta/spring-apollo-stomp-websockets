#### ActiveMQ Apollo with STOMP over Websockets

- http://localhost:61680/api/index.html#!/
- http://localhost:61680/api/json/broker/virtual-hosts/localhost/topics/test?producers=true&consumers=true

#### To build and run
- mvn clean package
- target/appassembler/bin/runner (target/appassembler/bin/runner.bat on Windows)
or  
- java -cp target/appassembler/lib/* com.example.messaging.Starter