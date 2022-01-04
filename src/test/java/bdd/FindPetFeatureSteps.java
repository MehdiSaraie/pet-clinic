package bdd;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;
import static org.junit.jupiter.api.Assertions.*;

public class FindPetFeatureSteps {

	private int petId;
	private Pet actualPet;
	@Autowired
	private PetService petService;

	@Before("@find_pet")
	public void setup() {

	}

	@Given("petId is {int}")
	public void petId_is(int petId) {
		this.petId = petId;
	}

	@When("I ask for the pet")
	public void i_ask_for_the_pet() {
		actualPet = petService.findPet(petId);
	}

	@Then("I should be given a pet with petId {int}")
	public void i_should_be_given_the_pet(int petId) {
		assertEquals(petId,(long) actualPet.getId());
	}
}
