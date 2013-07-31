package com.example.messaging;

import java.io.File;

import org.apache.activemq.apollo.broker.Broker;
import org.apache.activemq.apollo.broker.jmx.dto.JmxDTO;
import org.apache.activemq.apollo.dto.AcceptingConnectorDTO;
import org.apache.activemq.apollo.dto.BrokerDTO;
import org.apache.activemq.apollo.dto.TopicDTO;
import org.apache.activemq.apollo.dto.VirtualHostDTO;
import org.apache.activemq.apollo.dto.WebAdminDTO;
import org.apache.activemq.apollo.stomp.dto.StompDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	@Bean
	public Broker broker() throws Exception {
		final Broker broker = new Broker();
				
		// Configure STOMP over WebSockects connector
		final AcceptingConnectorDTO ws = new AcceptingConnectorDTO();
		ws.id = "ws";
		ws.bind = "ws://localhost:61614";		
		ws.protocols.add( new StompDTO() );

		// Create a topic with name 'test'
		final TopicDTO topic = new TopicDTO();
		topic.id = "test";
		
		// Create virtual host (based on localhost)
		final VirtualHostDTO host = new VirtualHostDTO();
		host.id = "localhost";		
		host.topics.add( topic );
		host.host_names.add( "localhost" );
		host.host_names.add( "127.0.0.1" );
		host.auto_create_destinations = false;
		
		// Create a web admin UI (REST) accessible at: http://localhost:61680/api/index.html#!/ 
        final WebAdminDTO webadmin = new WebAdminDTO();
        webadmin.bind = "http://localhost:61680";

        // Create JMX instrumentation 
        final JmxDTO jmxService = new JmxDTO();
		jmxService.enabled = true;
		
		// Finally, glue all together inside broker configuration
		final BrokerDTO config = new BrokerDTO();
		config.connectors.add( ws );
		config.virtual_hosts.add( host );
		config.web_admins.add( webadmin );
		config.services.add( jmxService );
		
		broker.setConfig( config );
		broker.setTmp( new File( System.getProperty( "java.io.tmpdir" ) ) );
		
		broker.start( new Runnable() {			
			@Override
			public void run() {		
				System.out.println("The broker has been started.");
			}
		} );
		
		return broker;
	}
}
