package com.jddev.velemenyezz;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jddev.velemenyezz.business.impl.dto.CreateBusinessRequest;
import com.jddev.velemenyezz.business.impl.model.Business;
import com.jddev.velemenyezz.business.impl.repository.BusinessRepository;
import com.jddev.velemenyezz.business.impl.service.BusinessService;
import com.jddev.velemenyezz.shared.SharedMethods;
import com.jddev.velemenyezz.subscription.api.SubscriptionModuleApi;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
class VelemenyezzApplicationTests {
	@Mock
	private SubscriptionModuleApi subscriptionModuleApi;
	@InjectMocks
	private BusinessService businessService;

	@Mock
	private SharedMethods sharedMethods;

	@Mock
	private BusinessRepository businessRepository;

	void setUp(){
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void contextLoads() {
	}
	@Test
	void writeDocumentationSnippets() {
		var modules = ApplicationModules.of(VelemenyezzApplication.class).verify();

		new Documenter(modules)
				.writeModulesAsPlantUml()
				.writeIndividualModulesAsPlantUml();

	}

	@Test
	void testBusinessCreation(){
		CreateBusinessRequest request = new CreateBusinessRequest("nametest", "locationtest", "urltest", "2");
		Mockito.when(sharedMethods.getAuthenticatedUserEmail()).thenReturn("dev@revdev.com");

		Business expectedBusiness = new Business.Builder()
				.withName("nametest")
				.withLocation("locationtest")
				.withOwner("dev@revdev.com")
				.withWebsiteURL("urltest")
				.withGoogleReview("2")
				.build();

		Mockito.when(businessRepository.save(Mockito.any(Business.class))).thenReturn(expectedBusiness);

		// Act
	 	Business result = businessService.createBusiness(request);

	 	// Assert
		assertNotNull(result, "Returned object should not be null!");
		assertEquals(expectedBusiness.getName(), result.getName(), "business names do not match");
		assertEquals(expectedBusiness.getLocation(), result.getLocation(), "business locations do not match");
		assertEquals(expectedBusiness.getOwnerEmail(), result.getOwnerEmail(), "business owner's do not match");
		assertEquals(expectedBusiness.getWebsiteUrl(), result.getWebsiteUrl(), "website urls do not match");
		assertEquals(expectedBusiness.getGoogleReview(), result.getGoogleReview(), "google reviews do not match");
	}

	@Test
	void testActiveSubscription()
	{
		String test1 = "dev@revdev.com";
		assertFalse(subscriptionModuleApi.hasActiveSubscription(test1));
	}

	@Test
	void testInactiveSubscription()
	{
		String test1 = "dev2awfefg@revde124v.co2m";

		assertFalse(subscriptionModuleApi.hasActiveSubscription(test1));
	}

	@Test
	void stripePayloadTest() throws JsonProcessingException {
		String payload = """
		{
		  "id": "evt_1Q1sUKIxg8gc9yntikiqiAHr",
		  "object": "event",
		  "api_version": "2024-06-20",
		  "created": 1727021900,
		  "data": {
			"object": {
			  "id": "cs_test_a1QQf0DXfewnnEKXa2cVJ2UQu2ehzy6GZ6CDuwzGpFIOt9cJjBp8vn4kcR",
			  "object": "checkout.session",
			  "after_expiration": null,
			  "allow_promotion_codes": null,
			  "amount_subtotal": 99000,
			  "amount_total": 99000,
			  "automatic_tax": {
				"enabled": false,
				"liability": null,
				"status": null
			  },
			  "billing_address_collection": null,
			  "cancel_url": null,
			  "client_reference_id": null,
			  "client_secret": null,
			  "consent": null,
			  "consent_collection": null,
			  "created": 1727021881,
			  "currency": "huf",
			  "currency_conversion": null,
			  "custom_fields": [
		
			  ],
			  "custom_text": {
				"after_submit": null,
				"shipping_address": null,
				"submit": null,
				"terms_of_service_acceptance": null
			  },
			  "customer": "cus_QtfpCok4AAw4YF",
			  "customer_creation": "always",
			  "customer_details": {
				"address": {
				  "city": null,
				  "country": "HU",
				  "line1": null,
				  "line2": null,
				  "postal_code": null,
				  "state": null
				},
				"email": "dev@revdev.com",
				"name": "rgrg",
				"phone": null,
				"tax_exempt": "none",
				"tax_ids": [
		
				]
			  },
			  "customer_email": "dev@revdev.com",
			  "expires_at": 1727108281,
			  "invoice": "in_1Q1sUHIxg8gc9yntcW8vbd9F",
			  "invoice_creation": null,
			  "livemode": false,
			  "locale": null,
			  "metadata": {
				"subscription_type": "STANDARD"
			  },
			  "mode": "subscription",
			  "payment_intent": null,
			  "payment_link": null,
			  "payment_method_collection": "always",
			  "payment_method_configuration_details": {
				"id": "pmc_1Q0NK6Ixg8gc9yntjvR9wAYv",
				"parent": null
			  },
			  "payment_method_options": {
				"card": {
				  "request_three_d_secure": "automatic"
				}
			  },
			  "payment_method_types": [
				"card",
				"link"
			  ],
			  "payment_status": "paid",
			  "phone_number_collection": {
				"enabled": false
			  },
			  "recovered_from": null,
			  "saved_payment_method_options": {
				"allow_redisplay_filters": [
				  "always"
				],
				"payment_method_remove": null,
				"payment_method_save": null
			  },
			  "setup_intent": null,
			  "shipping_address_collection": null,
			  "shipping_cost": null,
			  "shipping_details": null,
			  "shipping_options": [
		
			  ],
			  "status": "complete",
			  "submit_type": null,
			  "subscription": "sub_1Q1sUHIxg8gc9ynt4jKPsInS",
			  "success_url": "http://localhost:8080/api/home",
			  "total_details": {
				"amount_discount": 0,
				"amount_shipping": 0,
				"amount_tax": 0
			  },
			  "ui_mode": "hosted",
			  "url": null
			}
		  },
		  "livemode": false,
		  "pending_webhooks": 2,
		  "request": {
			"id": null,
			"idempotency_key": null
		  },
		  "type": "checkout.session.completed"
		}""";

		ObjectMapper objectMapper = new ObjectMapper();

		JsonNode node = objectMapper.readTree(payload);

		System.out.println(node.get("id").asText());
		assertNotNull(node.get("id").asText());


	}

}
