package com;


import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.EntryPoint;
import org.kie.api.runtime.rule.FactHandle;
import org.kie.api.time.SessionClock;
import org.kie.api.time.SessionPseudoClock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Main class of the demo project wich creates a new {@link CreditCardTransaction}, loads the previous transactions from a CSV file and uses
 * the Drools CEP engine to determine whether there was a potential fraud with the transactions.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class Main {

	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyyMMdd:HHmmssSSS");

	private static final KieServices KIE_SERVICES = KieServices.Factory.get();

	private static KieContainer kieContainer;

	private static Event event = new Event();

	public static void main(String[] args) {
		// Load the Drools KIE-Container.
		kieContainer = KIE_SERVICES.newKieClasspathContainer();

		long transactionTime = 0L;
		try {
			transactionTime = DATE_FORMAT.parse("20180629:094000000").getTime();
		} catch (ParseException pe) {
			throw new RuntimeException(pe);
		}

		// Define the new incoming credit-card transaction. In an actual system, this event would come a Kafka stream or a Vert.x EventBus
		// event.

		List<Event> eventList = new ArrayList<Event>();
		Event event = new Event("CC_BALANCE_PAYMENT","ONLINE",new Date(),"GFFHJU-7657","LATE_PAYMENT");
		eventList.add(event);



		TrainingModel trainingModel = new TrainingModel("CUSTOMER_GOOD_STANDING",95);

		// Process the incoming transaction.
		processTransaction(eventList,trainingModel);

	}

	private static void processTransaction(List<Event> events, TrainingModel trainingModel) {
		// Retrieve all transactions for this account


		KieSession kieSession = kieContainer.newKieSession();
		// Insert transaction history/context.
		LOGGER.debug("Inserting credit-card transaction context into session.");
		for (Event nextTransaction : events) {
			kieSession.insert(nextTransaction);
		}
		kieSession.insert(trainingModel);
		eventAnalysis eventAnalysis = new eventAnalysis();
		kieSession.insert(eventAnalysis);

		// And fire the rules.
		kieSession.fireAllRules();



		// Dispose the session to free up the resources.
		kieSession.dispose();

	}


}
