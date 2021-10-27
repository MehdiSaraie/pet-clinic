package org.springframework.samples.petclinic.owner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.utility.SimpleDI;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.*;

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


	//mock & behavior & classical
	@Test
	void testFindOwner() {
		int id = 1;
		Owner owner = new Owner();
		owner.setId(id);
		ownerRepository.save(owner);
		OwnerRepository mockOwnerRepository = mock(OwnerRepository.class);
		when(mockOwnerRepository.findById(id)).thenReturn(owner);
		petManager = new PetManager(petTimedCache, mockOwnerRepository, loggerConfig.getLogger());
		assertEquals(petManager.findOwner(id), owner);
		Mockito.verify(mockOwnerRepository).findById(id);
	}

	//mock & state & classical (Question 3)
	@Test
	void testFindOwnerState(){
		int id = 1;
		Owner owner = new Owner();
		owner.setId(id);
		ownerRepository.save(owner);
		OwnerRepository mockOwnerRepository = mock(OwnerRepository.class);
		when(mockOwnerRepository.findById(id)).thenReturn(owner);
		petManager = new PetManager(petTimedCache, mockOwnerRepository, loggerConfig.getLogger());
		assertEquals(petManager.findOwner(id), owner);
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

	//Spy & Mock Mockisty & Behavior
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

	//mock & state & mockisty
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

	//mock & state & mockisty
	@Test
	void testGetOwnerPetTypes() {
		int ownerId = 1;
		Owner owner = new Owner();
		owner.setId(ownerId);
		Pet pet = new Pet();
		PetType petType = new PetType();
		petType.setName("dog");
		pet.setType(petType);
		owner.addPet(pet);
		OwnerRepository mockOwnerRepository = mock(OwnerRepository.class);
		when(mockOwnerRepository.findById(ownerId)).thenReturn(owner);
		petManager = new PetManager(petTimedCache, mockOwnerRepository, loggerConfig.getLogger());
		Set <PetType> petSet = new HashSet<PetType>();
		petSet.add(petType);
		assertEquals(petManager.getOwnerPetTypes(ownerId), petSet);
	}

	//mock & state & mockisty
	@Test
	void testGetVisitsBetween() {
		int petId = 1;
		Pet pet = new Pet();
		pet.setId(1);
		PetTimedCache mockPetTimedCache = mock(PetTimedCache.class);
		when(mockPetTimedCache.get(petId)).thenReturn(pet);
		petManager = new PetManager(mockPetTimedCache, ownerRepository, loggerConfig.getLogger());
		LocalDate end = LocalDate.now();
		Visit visit = new Visit();
		visit.setDate(end.minusDays(3));
		pet.addVisit(visit);
		LocalDate start = end.minusDays(6);
		List<Visit> visits = new ArrayList<Visit>();
		visits.add(visit);
		assertEquals(petManager.getVisitsBetween(petId, start, end), visits);
	}
}
