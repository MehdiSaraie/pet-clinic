package bdd;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class CreateNewPetFeatureSteps {

	@Autowired
	PetService petService;

	@Autowired
	OwnerRepository ownerRepository;

	@Autowired
	PetRepository petRepository;

	@Autowired
	PetTypeRepository petTypeRepository;

	private Owner owner;
	private PetType petType;
	private int numPets;
	private Pet pet;

	@Before("@new_pet")
	public void setup() {
		// sample setup code
	}

	@Given("owner with id {int}, name {string} {string} exists")
	public void thereIsAPetOwnerCalled(int id, String firstName, String lastName) {
		owner = new Owner();
		owner.setId(id);
		owner.setFirstName(firstName);
		owner.setLastName(lastName);
		owner.setAddress("khiaban Nabovat");
		owner.setCity("Dezful");
		owner.setTelephone("09111111111");
		ownerRepository.save(owner);
		numPets = owner.getPets().size();
	}

	@When("he performs new pet service to add a new pet to his pets")
	public void hePerformsNewPetService() {
		pet = petService.newPet(owner);
	}

	@Then("he has a new pet")
	public void heHasANewPet() {
		assertEquals(numPets + 1, owner.getPets().size());
	}

	@Then("he is owner of the new pet")
	public void heIsOwnerOfNewPet() {
		assertEquals(owner, pet.getOwner());
	}


}
