package bdd;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;
import static org.junit.jupiter.api.Assertions.*;


public class SavePetFeatureSteps {

	private Pet pet;
	private String petName;
	private Owner owner;

	@Before("@save_pet")
	public void setup() {
		// sample setup code
	}

	@Given("pet with name {string} and owner with id {int}")
	public void thereIsAPetOwnerCalled(String petName, int ownerId) {
		pet = new Pet();
		this.petName = petName;
		pet.setName(petName);
		owner = new Owner();
		owner.setId(ownerId);
	}

	@When("He performs save pet service")
	public void hePerformsNewPetService() {
		owner.addPet(pet);
	}

	@Then("The pet should be saved to his pets")
	public void heHasANewPet() {
		Assert.assertTrue(owner.getPets().contains(pet));
	}

	@Then("The pet should exist in the all pets list")
	public void heIsOwnerOfNewPet() {
		Assert.assertNotNull(owner.getPet(petName));
	}


}
