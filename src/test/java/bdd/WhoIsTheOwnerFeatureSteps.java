package bdd;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;
import static org.junit.jupiter.api.Assertions.*;

public class WhoIsTheOwnerFeatureSteps {

	private int ownerId;
	private Owner actualOwner;
	@Autowired
	private PetService petService;

	@Before("@who_is_the_owner")
	public void setup() {

	}

	@Given("ownerId is {int}")
	public void ownerId_is(int ownerId) {
		this.ownerId = ownerId;

	}

	@When("I ask for the owner")
	public void i_ask_for_the_owner() {
		actualOwner = petService.findOwner(ownerId);
	}

	@Then("I should be given an owner with ownerId {int}")
	public void i_should_be_given_the_owner(int ownerId) {
		assertEquals(ownerId,(long) actualOwner.getId());
	}
}
