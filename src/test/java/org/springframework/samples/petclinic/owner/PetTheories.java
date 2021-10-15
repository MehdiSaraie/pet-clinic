package org.springframework.samples.petclinic.owner;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.experimental.theories.DataPoint;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.springframework.samples.petclinic.visit.Visit;


import javax.validation.constraints.AssertTrue;
import java.time.LocalDate;
import java.util.*;

@RunWith(Theories.class)
public class PetTheories {

	@DataPoints
	public static List[] descendingVisits(){
		ArrayList<Visit> visitList1 = new ArrayList<>();
		ArrayList<Visit> visitList2 = new ArrayList<>();


		for (int i = 0; i < 9; i++) {
			if (i%2 == 1) {
				visitList1.add(new Visit());
				visitList1.get(visitList1.size()-1).setDate(LocalDate.now().minusDays(i));
			}else{
				visitList2.add(new Visit());
				visitList2.get(visitList2.size()-1).setDate(LocalDate.now().minusDays(i));
			}
		}

//		System.out.println("descending");
//		for (int i = 0; i < visitList2.size(); i++) {
//			System.out.println(visitList2.get(i).getDate());
//		}

		return new List[] {visitList1, visitList2};
	}

	@DataPoints
	public static List[] ascendingVisits(){
		ArrayList<Visit> visitList1 = new ArrayList<>();
		ArrayList<Visit> visitList2 = new ArrayList<>();

		for (int i = 8; i >= 0; i--) {
			if (i%2 == 1) {
				visitList1.add(new Visit());
				visitList1.get(visitList1.size()-1).setDate(LocalDate.now().minusDays(i));
			}else{
				visitList2.add(new Visit());
				visitList2.get(visitList2.size()-1).setDate(LocalDate.now().minusDays(i));
			}
		}

//		System.out.println("ascending");
//		for (int i = 0; i < visitList2.size(); i++) {
//			System.out.println(visitList2.get(i).getDate());
//		}

		return new List[] {visitList1, visitList2};
	}

	@Theory
	public void getVisitsTest(List<Visit> ascending, List<Visit> descending){

		Pet pet = new Pet();
		pet.setVisitsInternal(ascending);
		Assume.assumeTrue(descending.size() == ascending.size());
		Assume.assumeTrue(ascending.get(0).getDate().isBefore(ascending.get(1).getDate()));
		Assume.assumeTrue(descending.get(0).getDate().isAfter(descending.get(1).getDate()));

//		for (int i = 0; i < descending.size(); i++) {
//			System.out.println(descending.get(i).getDate());
//		}
//		System.out.println("-------");
//		for (int i = 0; i < ascending.size(); i++) {
//			System.out.println(ascending.get(i).getDate());
//		}
//		System.out.println("******");

		List<Visit> getVisit = pet.getVisits();

		for (int i = 0; i < ascending.size(); i++) {
//			System.out.println(descending.get(i).getDate());
//			System.out.println(getVisit.get(i).getDate());
			Assert.assertTrue(descending.get(i).getDate().isEqual(getVisit.get(i).getDate()));
		}
//		System.out.println("kkkkk");


	}

}
