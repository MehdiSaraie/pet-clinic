package org.springframework.samples.petclinic.utility;

import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.samples.petclinic.owner.Pet;
import org.springframework.samples.petclinic.visit.Visit;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.samples.petclinic.utility.PriceCalculator.calcPrice;

public class PriceCalculatorTest {

	Pet pet1, pet2, pet3, pet4, pet5;
	final double baseCharge = 1000;
	final double basePricePerPet = 500;

	private static int INFANT_YEARS = 2;
	private static double RARE_INFANCY_COEF = 1.4;
	private static double BASE_RARE_COEF = 1.2;
	private static int DISCOUNT_MIN_SCORE = 10;
	private static int DISCOUNT_PRE_VISIT = 2;

	@BeforeEach
	void setup(){
		pet1 = new Pet();
		pet2 = new Pet();
		pet3 = new Pet();
		pet4 = new Pet();
		pet5 = new Pet();

	}

	@Test
	void test1() {
		pet1.setBirthDate(LocalDate.now().minusYears(2));
		pet2.setBirthDate(LocalDate.now().minusYears(3));
		double actualPrice = calcPrice(new ArrayList<Pet>(Arrays.asList(pet1, pet2)),baseCharge ,basePricePerPet );
		double expectedPrice = basePricePerPet*BASE_RARE_COEF*RARE_INFANCY_COEF + basePricePerPet*BASE_RARE_COEF;
		assertEquals(expectedPrice, actualPrice);
	}

	@Test
	void test2(){
		pet1.setBirthDate(LocalDate.now().minusYears(1));
		pet2.setBirthDate(LocalDate.now().minusYears(1));
		pet3.setBirthDate(LocalDate.now().minusYears(1));
		pet4.setBirthDate(LocalDate.now().minusYears(1));
		pet5.setBirthDate(LocalDate.now().minusYears(1));
		pet5.addVisit(new Visit());

		double actualPrice = calcPrice(new ArrayList<Pet>(Arrays.asList(pet1, pet2, pet3, pet4, pet5)),baseCharge ,basePricePerPet );
		double expectedPrice = (basePricePerPet*BASE_RARE_COEF*RARE_INFANCY_COEF*4)*DISCOUNT_PRE_VISIT + baseCharge + basePricePerPet*BASE_RARE_COEF*RARE_INFANCY_COEF;
		assertEquals(expectedPrice, actualPrice);
	}

	@Test
	void test3() {
		pet1.setBirthDate(LocalDate.now().minusYears(1));
		pet2.setBirthDate(LocalDate.now().minusYears(1));
		pet3.setBirthDate(LocalDate.now().minusYears(1));
		pet4.setBirthDate(LocalDate.now().minusYears(1));
		pet5.setBirthDate(LocalDate.now().minusYears(1));

		Visit visit = new Visit();
		visit.setDate(LocalDate.now().minusDays(100));

		pet5.addVisit(visit);

		double actualPrice = calcPrice(new ArrayList<Pet>(Arrays.asList(pet1, pet2, pet3, pet4, pet5)),baseCharge ,basePricePerPet );
		double expectedPrice = (basePricePerPet*BASE_RARE_COEF*RARE_INFANCY_COEF*4 + baseCharge) * (2) + basePricePerPet*BASE_RARE_COEF*RARE_INFANCY_COEF;
		assertEquals(expectedPrice, actualPrice);
	}

//	@Test
//	void test() {
//		pet1.setBirthDate(LocalDate.now().minusYears(1));
//		pet2.setBirthDate(LocalDate.now().minusYears(1));
//		pet3.setBirthDate(LocalDate.now().minusYears(1));
//		pet4.setBirthDate(LocalDate.now().minusYears(1));
//		pet5.setBirthDate(LocalDate.now().minusYears(1));
//
//		Visit visit = new Visit();
//		visit.setDate(LocalDate.now().plusDays(100));
//
//		pet5.addVisit(visit);
//
//		assertThrows()
//		double actualPrice = calcPrice(new ArrayList<Pet>(Arrays.asList(pet1, pet2, pet3, pet4, pet5)),baseCharge ,basePricePerPet );
//		double expectedPrice = (basePricePerPet*BASE_RARE_COEF*RARE_INFANCY_COEF*4 + baseCharge) * (2) + basePricePerPet*BASE_RARE_COEF*RARE_INFANCY_COEF;
//		assertEquals(expectedPrice, actualPrice);
//	}

	@Test
	void test4(){
		pet1.setBirthDate(LocalDate.now().minusYears(1));
		pet2.setBirthDate(LocalDate.now().minusYears(1));
		pet3.setBirthDate(LocalDate.now().minusYears(1));
		pet4.setBirthDate(LocalDate.now().minusYears(3));
		pet5.setBirthDate(LocalDate.now().minusYears(3));
		Pet pet6 = new Pet();
		pet6.setBirthDate(LocalDate.now().minusYears(3));
		Pet pet7 = new Pet();
		pet7.setBirthDate(LocalDate.now().minusYears(3));

		double actualPrice = calcPrice(new ArrayList<Pet>(Arrays.asList(pet1, pet2, pet3, pet4, pet5)),baseCharge ,basePricePerPet );
		assertEquals(3720.0, actualPrice);
	}
}
