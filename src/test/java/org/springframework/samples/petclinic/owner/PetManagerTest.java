package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.utility.SimpleDI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetManagerTest {

	PetManager petManager;
	PetRepository petRepository;

	PetTimedCache petTimedCache;
	PetTimedCache spyPetTimedCache;

	OwnerRepository spyOwnerRepository;
	OwnerRepository ownerRepository;
	Owner owner;

	LoggerConfig loggerConfig;

	@BeforeEach
	public void setUp() {
		petRepository = new PetRepository() {
			@Override
			public List<PetType> findPetTypes() {
				return null;
			}

			@Override
			public Pet findById(Integer id) {
				return null;
			}

			@Override
			public void save(Pet pet) {

			}
		};

		loggerConfig = new LoggerConfig();
		ownerRepository = new OwnerRepository() {
			@Override
			public Collection<Owner> findByLastName(String lastName) {
				return null;
			}

			@Override
			public Owner findById(Integer id) {
				return null;
			}

			@Override
			public void save(Owner owner) {

			}
		};
		spyOwnerRepository = spy(ownerRepository);


		petTimedCache = new PetTimedCache(petRepository);
		spyPetTimedCache = spy(petTimedCache);

		// Fake Object
		petManager  = new PetManager(spyPetTimedCache, spyOwnerRepository, loggerConfig.getLogger());
	}


	//spy
	@Test
	void testFindOwner() {
		int id  = 1;
		petManager.findOwner(id);
		Mockito.verify(spyOwnerRepository).findById(id);
	}

	//stub state Classical
	@Test
	void testNewPet() {

		Owner owner = new Owner();
		int initialSize = owner.getPets().size();


		petManager.newPet(owner);

		assertEquals(owner.getPets().size(), initialSize+1);
	}

	//Stub State Classical
	@Test
	void testFindPet() {

		int id = 1;

		Pet pet = new Pet();
		pet.setId(id);

		petManager.savePet(pet, new Owner());
		Pet pet1 = petManager.findPet(id);

		assertEquals(pet.getId(), id);
	}

	//Spy & Mock Mockisty Behavior
	@Test
	void testSavePet() {
		Owner owner = new Owner();
		Owner spyOwner = spy(owner);

		Pet mockPet = Mockito.mock(Pet.class);
		when(mockPet.toString()).thenReturn("pet");


		petManager.savePet(mockPet, spyOwner);

		Mockito.verify(spyOwner).addPet(mockPet);
		Mockito.verify(mockPet).getId();
		Mockito.verify(spyPetTimedCache).save(mockPet);
	}

	@Test
	void testGetOwnerPets() {

		int ownerId = 1;
		Owner owner = new Owner();
		owner.addPet(new Pet());
		owner.setId(ownerId);

		OwnerRepository mockOwnerRepository = mock(OwnerRepository.class);
		when(mockOwnerRepository.findById(ownerId)).thenReturn(owner);


		petManager = new PetManager(petTimedCache, mockOwnerRepository, loggerConfig.getLogger());

		assertEquals(owner.getPets(), petManager.getOwnerPets(ownerId));
	}
}
