package org.springframework.samples.petclinic.owner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.samples.petclinic.utility.SimpleDI;

import static org.junit.jupiter.api.Assertions.*;



import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class PetServiceTest {

	PetService petService;

	OwnerRepository ownerRepository;
	PetRepository petRepository;
	PetTimedCache petTimedCache;
	LoggerConfig loggerConfig;

	Pet pet;
	Integer petId;

//

	public PetServiceTest(Pet pet, Integer petId) {
		petRepository = new PetRepository() {

			ArrayList<Pet> petArrayList = new ArrayList();

			@Override
			public List<PetType> findPetTypes() {
				return null;
			}

			@Override
			public Pet findById(Integer id) {
				for (Pet pet: petArrayList){
					if (pet.getId().equals(id)){
						return pet;
					}
				}
				return null;
			}

			@Override
			public void save(Pet pet) {
				petArrayList.add(pet);
			}
		};

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
		petTimedCache = new PetTimedCache(petRepository);
		loggerConfig = new LoggerConfig();

		petService = new PetService(petTimedCache, ownerRepository, loggerConfig.getLogger());

		this.pet = pet;
		this.petId = petId;
	}

	@Parameterized.Parameters
	public static Object[] parameters(){

		Pet pet1, pet2, pet3;
		pet1 = new Pet();
		pet1.setId(1);
		pet2 = new Pet();
		pet2.setId(2);
		pet3 = new Pet();
		pet3.setId(3);

		return new Object[][] {
			{pet1, 1},
			{pet2, 2},
			{pet3, 3}
		};
	}

	@Test
	public void findPetTest(){
		petRepository.save(pet);
		assertEquals(pet, petService.findPet(petId));
	}
}
