package org.springframework.samples.petclinic.owner;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;


@RunWith(Theories.class)
public class OwnerGetPetTheories {

	Owner owner;


	@DataPoints
	public static Pet[] pets(){
		Pet pet1, pet2, pet3;

		pet1 = new Pet();
		pet2 = new Pet();
		pet3 = new Pet();
		pet1.setName("pet1");
		pet2.setName("pet2");
		pet3.setName("pet3");


		return new Pet[]{pet1, pet2, pet3};
	}

	@DataPoints
	public static boolean[] booleans = {false, true};

	@Theory
	public void getPetTest(boolean bool, Pet pet) {
		Owner owner = new Owner();
		System.out.println(bool + pet.getName());
		Assume.assumeFalse(bool);

		owner.addPet(pet);
		Assert.assertSame(pet, owner.getPet(pet.getName(), bool));
	}
}
