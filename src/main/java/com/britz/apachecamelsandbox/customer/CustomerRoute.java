package com.britz.apachecamelsandbox.customer;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ShutdownRunningTask;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.apache.camel.model.remote.ConsulConfigurationDefinition;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.britz.apachecamelsandbox.customer.service.AggregationStrategyImpl;
import com.britz.apachecamelsandbox.model.Account;
import com.britz.apachecamelsandbox.model.Customer;

public class CustomerRoute extends RouteBuilder {

	@Autowired
	CamelContext context;

	@Value("${port}")
	private int port;

	@Override
	public void configure() throws Exception {

		JacksonDataFormat format = new JacksonDataFormat();
		format.useList();
		format.setUnmarshalType(Account.class);

		ConsulConfigurationDefinition config = new ConsulConfigurationDefinition();
		config.setComponent("netty4-http");
		config.setUrl("http://192.168.99.100:8500");
		context.setServiceCallConfiguration(config);

		restConfiguration().component("netty4-http").bindingMode(RestBindingMode.json).port(port);

		from("direct:start").routeId("account-consul").marshal().json(JsonLibrary.Jackson)
				.setHeader(Exchange.HTTP_METHOD, constant("PUT"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.to("http://192.168.99.100:8500/v1/agent/service/register");
		from("direct:stop").shutdownRunningTask(ShutdownRunningTask.CompleteAllTasks)
				.toD("http://192.168.99.100:8500/v1/agent/service/deregister/${header.id}");

		rest("/customer").get("/").to("bean:customerService?method=findAll").post("/").consumes("application/json")
				.type(Customer.class).to("bean:customerService?method=add(${body})").get("/{id}").to("direct:account");

		from("direct:account").to("bean:customerService?method=findById(${header.id})").log("Msg: ${body}")
				.enrich("direct:acc", new AggregationStrategyImpl());

		from("direct:acc").setBody().constant(null).serviceCall("account//account").unmarshal(format);

	}

}
