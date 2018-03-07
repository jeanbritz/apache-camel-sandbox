package com.britz.apachecamelsandbox.customer.service;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import com.britz.apachecamelsandbox.model.Account;
import com.britz.apachecamelsandbox.model.Customer;

public class AggregationStrategyImpl implements AggregationStrategy {

	@Override
	public Exchange aggregate(Exchange original, Exchange resource) {
		Object originalBody = original.getIn().getBody();
		Object resourceResponse = resource.getIn().getBody();
		Customer customer = (Customer) originalBody;
		customer.setAccounts((List<Account>) resourceResponse);
		return original;
	}

}
