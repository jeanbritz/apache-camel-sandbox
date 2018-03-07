package com.britz.apachecamelsandbox;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.remote.ConsulConfigurationDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CamelRoute extends RouteBuilder {

	@Autowired
	CamelContext context;

	@Override
	public void configure() throws Exception {

		ConsulConfigurationDefinition config = new ConsulConfigurationDefinition();
		config.setComponent("netty4-http");
		config.setUrl("http://192.168.99.100:8500");
		context.setServiceCallConfiguration(config);

		// restConfiguration().component("netty4-http").bindingMode(RestBindingMode.json).port(8000)
		// .apiContextPath("/api-doc").apiProperty("api.title", "Example
		// API").apiProperty("api.version", "1.0")
		// .apiProperty("cors", "true");

		from("jetty:http://localhost:8080").to("http://www.google.com?bridgeEndpoint=true").end();

		// JacksonDataFormat formatAcc = new JacksonDataFormat(Account.class);
		// JacksonDataFormat formatCus = new JacksonDataFormat(Customer.class);
		// JacksonDataFormat formatAccList = new JacksonDataFormat(Account.class);
		// formatAccList.useList();
		// JacksonDataFormat formatCusList = new JacksonDataFormat(Customer.class);
		// formatAccList.useList();
		//
		// rest("/account").get("/{id}").description("Find account by
		// id").outType(Account.class).param().name("id")
		// .type(RestParamType.path).description("Account
		// identificator").dataType("int").endParam().route()
		// .serviceCall("account").unmarshal(formatAcc).endRest().get("/customer/{customerId}")
		// .description("Find account by customer
		// id").outType(Account.class).param().name("customerId")
		// .type(RestParamType.path).description("Customer
		// identificator").dataType("int").endParam().route()
		// .serviceCall("account").unmarshal(formatAcc).endRest().get("/").description("Find
		// all accounts")
		// .outTypeList(Account.class).route().serviceCall("account").unmarshal(formatAccList).endRest().post("/")
		// .description("Add new
		// account").outTypeList(Account.class).param().name("account")
		// .type(RestParamType.body).description("Account JSON
		// object").dataType("Account").endParam().route()
		// .serviceCall("account").unmarshal(formatAcc).endRest();
		//
		// rest("/customer").get("/{id}").description("Find customer by
		// id").outType(Customer.class).param().name("id")
		// .type(RestParamType.path).description("Customer
		// identificator").dataType("int").endParam().route()
		// .serviceCall("customer").unmarshal(formatCus).endRest().get("/").description("Find
		// all customers")
		// .outTypeList(Customer.class).route().serviceCall("customer").unmarshal(formatCusList).endRest()
		// .post("/").description("Add new
		// customer").outTypeList(Customer.class).param().name("customer")
		// .type(RestParamType.body).description("Customer JSON
		// object").dataType("Account").endParam().route()
		// .serviceCall("customer").unmarshal(formatCus).endRest();

		// from("rest:get:account:/{id}").serviceCall("account");
		// from("rest:get:account:/customer/{customerId}").serviceCall("account");
		// from("rest:get:account:/").serviceCall("account");
		// from("rest:post:account:/").serviceCall("account");
		// from("rest:get:customer:/{id}").serviceCall("customer");
		// from("rest:get:customer:/").serviceCall("customer");
		// from("rest:post:customer:/").serviceCall("customer");
	}

}
