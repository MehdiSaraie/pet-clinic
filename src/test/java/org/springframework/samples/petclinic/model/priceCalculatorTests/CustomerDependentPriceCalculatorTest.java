package org.springframework.samples.petclinic.model.priceCalculatorTests;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.UserType;
import org.springframework.samples.petclinic.model.priceCalculators.CustomerDependentPriceCalculator;

import java.util.ArrayList;
import java.util.List;


public class CustomerDependentPriceCalculatorTest {

	private static int INFANT_YEARS = 2;
	private static double RARE_INFANCY_COEF = 1.4;
	private static double COMMON_INFANCY_COEF = 1.2;
	private static double BASE_RARE_COEF = 1.2;
	private static int DISCOUNT_MIN_SCORE = 10;

	CustomerDependentPriceCalculator customerDependentPriceCalculator;
	List<Pet> petList;
	static double baseCharge = 10;
	static double basePricePerPet = 20;


	@BeforeEach
	public void setUp(){
		customerDependentPriceCalculator = new CustomerDependentPriceCalculator();
		petList =  new ArrayList<>();
	}

	@Test
	void testCalcPriceIsRareInfantNew() {
		Pet pet = new Pet();
		pet.setType(new PetType());
		pet.setBirthDate(new DateTime().toDate());

		petList.add(pet);

		double totalPrice = customerDependentPriceCalculator.calcPrice(petList, baseCharge, basePricePerPet, UserType.NEW);

		assertEquals(basePricePerPet*BASE_RARE_COEF*RARE_INFANCY_COEF , totalPrice);

	}

	@Test
	void testCalcPriceIsRareNew() {
		Pet pet = new Pet();
		pet.setType(new PetType());
		pet.setBirthDate(LocalDate.now().minusYears(INFANT_YEARS+1).toDate());

		petList.add(pet);

		double totalPrice = customerDependentPriceCalculator.calcPrice(petList, baseCharge, basePricePerPet, UserType.NEW);

		assertEquals(basePricePerPet*BASE_RARE_COEF , totalPrice);

	}

	@Test
	void testCalcPriceInfantNew() {
		Pet pet = new Pet();

		PetType mockPetType = Mockito.mock(PetType.class);
		when(mockPetType.getRare()).thenReturn(false);

		pet.setType(mockPetType);
		pet.setBirthDate(LocalDate.now().toDate());

		petList.add(pet);

		double totalPrice = customerDependentPriceCalculator.calcPrice(petList, baseCharge, basePricePerPet, UserType.NEW);

		assertEquals(basePricePerPet*COMMON_INFANCY_COEF , totalPrice);

	}

	@Test
	void testCalcPriceNew() {
		Pet pet = new Pet();

		PetType mockPetType = Mockito.mock(PetType.class);
		when(mockPetType.getRare()).thenReturn(false);

		pet.setType(mockPetType);
		pet.setBirthDate(LocalDate.now().minusYears(INFANT_YEARS+1).toDate());

		petList.add(pet);

		double totalPrice = customerDependentPriceCalculator.calcPrice(petList, baseCharge, basePricePerPet, UserType.NEW);

		assertEquals(basePricePerPet , totalPrice);

	}

	@Test
	void testCalcPriceGold() {
		Pet pet = new Pet();

		PetType mockPetType = Mockito.mock(PetType.class);
		when(mockPetType.getRare()).thenReturn(false);

		pet.setType(mockPetType);
		pet.setBirthDate(LocalDate.now().minusYears(INFANT_YEARS+1).toDate());

		petList.add(pet);

		double totalPrice = customerDependentPriceCalculator.calcPrice(petList, baseCharge, basePricePerPet, UserType.GOLD);

		assertEquals(basePricePerPet*UserType.GOLD.discountRate + baseCharge , totalPrice);

	}

	@Test
	void testCalcPriceIsRareInfantNewDiscount() {
		Pet pet = new Pet();
		pet.setType(new PetType());
		pet.setBirthDate(new DateTime().toDate());

		Pet pet1 = new Pet();
		pet1.setType(new PetType());
		pet1.setBirthDate(new DateTime().toDate());

		Pet pet2 = new Pet();
		pet2.setType(new PetType());
		pet2.setBirthDate(new DateTime().toDate());

		Pet pet3 = new Pet();
		pet3.setType(new PetType());
		pet3.setBirthDate(new DateTime().toDate());

		Pet pet4 = new Pet();
		pet4.setType(new PetType());
		pet4.setBirthDate(new DateTime().toDate());

		petList.add(pet);
		petList.add(pet1);
		petList.add(pet2);
		petList.add(pet3);
		petList.add(pet4);

		double totalPrice = customerDependentPriceCalculator.calcPrice(petList, baseCharge, basePricePerPet, UserType.NEW);

		assertEquals(basePricePerPet*BASE_RARE_COEF*RARE_INFANCY_COEF*5*UserType.NEW.discountRate + baseCharge , totalPrice);

	}

	@Test
	void testCalcPriceIsRareInfantNotNewDiscount() {
		Pet pet = new Pet();
		pet.setType(new PetType());
		pet.setBirthDate(new DateTime().toDate());

		Pet pet1 = new Pet();
		pet1.setType(new PetType());
		pet1.setBirthDate(new DateTime().toDate());

		Pet pet2 = new Pet();
		pet2.setType(new PetType());
		pet2.setBirthDate(new DateTime().toDate());

		Pet pet3 = new Pet();
		pet3.setType(new PetType());
		pet3.setBirthDate(new DateTime().toDate());

		Pet pet4 = new Pet();
		pet4.setType(new PetType());
		pet4.setBirthDate(new DateTime().toDate());

		petList.add(pet);
		petList.add(pet1);
		petList.add(pet2);
		petList.add(pet3);
		petList.add(pet4);

		double totalPrice = customerDependentPriceCalculator.calcPrice(petList, baseCharge, basePricePerPet, UserType.SILVER);

		assertEquals((basePricePerPet*BASE_RARE_COEF*RARE_INFANCY_COEF*5 + baseCharge)*UserType.SILVER.discountRate , totalPrice);

	}



}
