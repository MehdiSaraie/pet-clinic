package org.springframework.samples.petclinic.model.priceCalculatorTests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;
import org.springframework.samples.petclinic.model.priceCalculators.SimplePriceCalculator;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class SimplePriceCalculatorTest {

	SimplePriceCalculator simplePriceCalculator;
	List<Pet> petList;
	static double baseCharge = 10;
	static double basePricePerPet = 20;


	@BeforeEach
	public void setUp(){
		simplePriceCalculator = new SimplePriceCalculator();
		petList =  new ArrayList<>();
	}

	@Test
	public void testCalcPriceRareNew() {
		Pet rarePet1 = new Pet();
		Pet rarePet2 = new Pet();
		rarePet1.setType(new PetType());
		rarePet2.setType(new PetType());
		petList.add(rarePet1);
		petList.add(rarePet2);

		double totalPrice = simplePriceCalculator.calcPrice(petList, baseCharge, basePricePerPet, UserType.NEW);
		assertEquals(UserType.NEW.discountRate*(baseCharge + basePricePerPet*1.2*2), totalPrice);
	}

	@Test
	void testCalcPriceRareNotNew() {
		Pet rarePet1 = new Pet();
		Pet rarePet2 = new Pet();
		rarePet1.setType(new PetType());
		rarePet2.setType(new PetType());
		petList.add(rarePet1);
		petList.add(rarePet2);

		double totalPrice = simplePriceCalculator.calcPrice(petList, baseCharge, basePricePerPet, UserType.GOLD);
		assertEquals(baseCharge + basePricePerPet*1.2*2, totalPrice);
	}

	@Test
	void testClacPriceNotRareNew() {
		Pet pet1 = new Pet();
		Pet pet2 = new Pet();
		petList.add(pet1);
		petList.add(pet2);
		PetType mockPetType = Mockito.mock(PetType.class);
		when(mockPetType.getRare()).thenReturn(false);
		pet1.setType(mockPetType);
		pet2.setType(mockPetType);

		double totalPrice = simplePriceCalculator.calcPrice(petList, baseCharge, basePricePerPet, UserType.NEW);
		assertEquals(UserType.NEW.discountRate*(baseCharge + basePricePerPet*2), totalPrice);

	}

	@Test
	void testClacPriceNotRareNotNew() {
		Pet pet1 = new Pet();
		Pet pet2 = new Pet();
		petList.add(pet1);
		petList.add(pet2);
		PetType mockPetType = Mockito.mock(PetType.class);
		when(mockPetType.getRare()).thenReturn(false);
		pet1.setType(mockPetType);
		pet2.setType(mockPetType);

		double totalPrice = simplePriceCalculator.calcPrice(petList, baseCharge, basePricePerPet, UserType.GOLD);
		assertEquals((baseCharge + basePricePerPet*2), totalPrice);
	}
}
