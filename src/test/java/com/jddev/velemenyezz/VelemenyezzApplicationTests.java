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
	void testInactiveSubscription()
	{
		String test1 = "dev2awfefg@revde124v.co2m";

		assertFalse(subscriptionModuleApi.hasActiveSubscription(test1));
	}


}
