package bdd;

import io.cucumber.java.Before;
//import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.Marker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.owner.*;
import org.springframework.samples.petclinic.utility.PetTimedCache;

import static org.junit.jupiter.api.Assertions.*;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.util.Collection;
import java.util.List;

//import static org.junit.Assert.*;

public class PetServiceTest {

	private int ownerId;
	private Owner actualOwner;
	@Autowired
	private PetService petService;

	@Before("@sample")
	public void setup() {
//		petService = new PetService(
//			new PetTimedCache(1000000000, new PetRepository() {
//				@Override
//				public List<PetType> findPetTypes() {
//					return null;
//				}
//
//				@Override
//				public Pet findById(Integer id) {
//					return null;
//				}
//
//				@Override
//				public void save(Pet pet) {
//
//				}
//			}),
//			new OwnerRepository() {
//				@Override
//				public Collection<Owner> findByLastName(String lastName) {
//					return null;
//				}
//
//				@Override
//				public Owner findById(Integer id) {
//					Owner owner = new Owner();
//					owner.setId(1);
//					return owner;
//				}
//
//				@Override
//				public void save(Owner owner) {
//
//				}
//			},
//			new Logger() {
//				@Override
//				public String getName() {
//					return null;
//				}
//
//				@Override
//				public boolean isTraceEnabled() {
//					return false;
//				}
//
//				@Override
//				public void trace(String s) {
//
//				}
//
//				@Override
//				public void trace(String s, Object o) {
//
//				}
//
//				@Override
//				public void trace(String s, Object o, Object o1) {
//
//				}
//
//				@Override
//				public void trace(String s, Object... objects) {
//
//				}
//
//				@Override
//				public void trace(String s, Throwable throwable) {
//
//				}
//
//				@Override
//				public boolean isTraceEnabled(Marker marker) {
//					return false;
//				}
//
//				@Override
//				public void trace(Marker marker, String s) {
//
//				}
//
//				@Override
//				public void trace(Marker marker, String s, Object o) {
//
//				}
//
//				@Override
//				public void trace(Marker marker, String s, Object o, Object o1) {
//
//				}
//
//				@Override
//				public void trace(Marker marker, String s, Object... objects) {
//
//				}
//
//				@Override
//				public void trace(Marker marker, String s, Throwable throwable) {
//
//				}
//
//				@Override
//				public boolean isDebugEnabled() {
//					return false;
//				}
//
//				@Override
//				public void debug(String s) {
//
//				}
//
//				@Override
//				public void debug(String s, Object o) {
//
//				}
//
//				@Override
//				public void debug(String s, Object o, Object o1) {
//
//				}
//
//				@Override
//				public void debug(String s, Object... objects) {
//
//				}
//
//				@Override
//				public void debug(String s, Throwable throwable) {
//
//				}
//
//				@Override
//				public boolean isDebugEnabled(Marker marker) {
//					return false;
//				}
//
//				@Override
//				public void debug(Marker marker, String s) {
//
//				}
//
//				@Override
//				public void debug(Marker marker, String s, Object o) {
//
//				}
//
//				@Override
//				public void debug(Marker marker, String s, Object o, Object o1) {
//
//				}
//
//				@Override
//				public void debug(Marker marker, String s, Object... objects) {
//
//				}
//
//				@Override
//				public void debug(Marker marker, String s, Throwable throwable) {
//
//				}
//
//				@Override
//				public boolean isInfoEnabled() {
//					return false;
//				}
//
//				@Override
//				public void info(String s) {
//
//				}
//
//				@Override
//				public void info(String s, Object o) {
//
//				}
//
//				@Override
//				public void info(String s, Object o, Object o1) {
//
//				}
//
//				@Override
//				public void info(String s, Object... objects) {
//
//				}
//
//				@Override
//				public void info(String s, Throwable throwable) {
//
//				}
//
//				@Override
//				public boolean isInfoEnabled(Marker marker) {
//					return false;
//				}
//
//				@Override
//				public void info(Marker marker, String s) {
//
//				}
//
//				@Override
//				public void info(Marker marker, String s, Object o) {
//
//				}
//
//				@Override
//				public void info(Marker marker, String s, Object o, Object o1) {
//
//				}
//
//				@Override
//				public void info(Marker marker, String s, Object... objects) {
//
//				}
//
//				@Override
//				public void info(Marker marker, String s, Throwable throwable) {
//
//				}
//
//				@Override
//				public boolean isWarnEnabled() {
//					return false;
//				}
//
//				@Override
//				public void warn(String s) {
//
//				}
//
//				@Override
//				public void warn(String s, Object o) {
//
//				}
//
//				@Override
//				public void warn(String s, Object... objects) {
//
//				}
//
//				@Override
//				public void warn(String s, Object o, Object o1) {
//
//				}
//
//				@Override
//				public void warn(String s, Throwable throwable) {
//
//				}
//
//				@Override
//				public boolean isWarnEnabled(Marker marker) {
//					return false;
//				}
//
//				@Override
//				public void warn(Marker marker, String s) {
//
//				}
//
//				@Override
//				public void warn(Marker marker, String s, Object o) {
//
//				}
//
//				@Override
//				public void warn(Marker marker, String s, Object o, Object o1) {
//
//				}
//
//				@Override
//				public void warn(Marker marker, String s, Object... objects) {
//
//				}
//
//				@Override
//				public void warn(Marker marker, String s, Throwable throwable) {
//
//				}
//
//				@Override
//				public boolean isErrorEnabled() {
//					return false;
//				}
//
//				@Override
//				public void error(String s) {
//
//				}
//
//				@Override
//				public void error(String s, Object o) {
//
//				}
//
//				@Override
//				public void error(String s, Object o, Object o1) {
//
//				}
//
//				@Override
//				public void error(String s, Object... objects) {
//
//				}
//
//				@Override
//				public void error(String s, Throwable throwable) {
//
//				}
//
//				@Override
//				public boolean isErrorEnabled(Marker marker) {
//					return false;
//				}
//
//				@Override
//				public void error(Marker marker, String s) {
//
//				}
//
//				@Override
//				public void error(Marker marker, String s, Object o) {
//
//				}
//
//				@Override
//				public void error(Marker marker, String s, Object o, Object o1) {
//
//				}
//
//				@Override
//				public void error(Marker marker, String s, Object... objects) {
//
//				}
//
//				@Override
//				public void error(Marker marker, String s, Throwable throwable) {
//
//				}
//			}
//		);
	}

	@Given("ownerId is {int}")
	public void owner_is(int ownerId) {
		this.ownerId = ownerId;

	}

	@When("I ask for the owner")
	public void i_ask_whether_it_s_Friday_yet() {
		actualOwner = petService.findOwner(ownerId);
	}

	@Then("I should be given an instance with ownerId {int}")
	public void i_should_be_told(int ownerId) {
		assertEquals(ownerId,(long) actualOwner.getId());
	}
}
