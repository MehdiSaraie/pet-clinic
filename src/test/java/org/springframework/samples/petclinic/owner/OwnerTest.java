package org.springframework.samples.petclinic.owner;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OwnerTest {

	Owner owner;
	Pet pet1, pet2, pet3;

	@BeforeEach
	public void setUp() {
		owner  = new Owner();
		pet1 = new Pet();
		pet2 = new Pet();
		pet3 = new Pet();
		pet1.setName("pet1");
		pet2.setName("pet2");
		pet3.setName("pet3");
		owner.addPet(pet2);
		owner.addPet(pet1);
		owner.addPet(pet3);
	}


	//getOwner
	@Test
	void testForOwnerOfAddedPet() {
		assertEquals(pet1.getOwner() , owner);
	}

	//getPet with 1 parameter
	@Test
	void testForGetPetNameOnly() {
		assertNotNull(owner.getPet("pet2"));
	}

	//getPet with 2 parameters
	@Test
	void testForGetPet() {
		assertNull(owner.getPet("pet1",true));
	}

	//removePet
	@Test
	void testForRemovePet(){
		owner.removePet(pet2);
		assertFalse(owner.getPetsInternal().contains(pet2));
	}

	//getPets
	@Test
	void testForGetPetsIsSorted() {
		List<Pet> list = owner.getPets();
		assertEquals(list.get(0), pet1);
	}

	//getAddress
	@Test
	void testForAddressSetter() {
		String address = "azadi";
		owner.setAddress(address);
		assertEquals(address, owner.getAddress());
	}

	//getCity
	@Test
	void testForCitySetter() {
		String city = "tehran";
		owner.setCity(city);
		assertEquals(city, owner.getCity());
	}

	//getTelephone
	@Test
	void testForGetTelephone() {
		String tel = "09123456789";
		owner.setTelephone(tel);
		assertEquals(tel, owner.getTelephone());
	}
}
