/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.owner;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.petclinic.utility.PetTimedCache;
import org.springframework.test.web.servlet.MockMvc;

import org.junit.jupiter.api.Assertions.*;
/**
 * Test class for the {@link PetController}
 *
 * @author Colin But
 */
@WebMvcTest(value = PetController.class,
	includeFilters = {
		@ComponentScan.Filter(value = PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = PetService.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = LoggerConfig.class, type = FilterType.ASSIGNABLE_TYPE),
		@ComponentScan.Filter(value = PetTimedCache.class, type = FilterType.ASSIGNABLE_TYPE)
	})
class PetControllerTests {

	private static final int OWNER_ID = 1;

	private static final int PET_ID = 1;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PetRepository pets;

	@MockBean
	private OwnerRepository owners;

	private Owner owner;
	private Pet pet;

	@BeforeEach
	void setup() {
		owner = new Owner();
		owner.setId(OWNER_ID);

		pet = new Pet();
		pet.setId(PET_ID);
		pet.setName("sag");

		given(this.owners.findById(OWNER_ID)).willReturn(owner);
		given(this.pets.findById(PET_ID)).willReturn(pet);
	}

	@Test
	void testInitCreationForm() throws Exception {
		int initial_pets = owner.getPets().size();
		mockMvc.perform(get("/owners/{ownerId}/pets/new", OWNER_ID)).andExpect(status().isOk());
		Assertions.assertEquals(1,owner.getPets().size() - initial_pets);
	}

	@Test
	void testProcessCreationFormSuccess() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/pets/new", OWNER_ID).param("name", "dog")
				.param("type", "hamster").param("birthDate", "2015-02-12"));
		Assertions.assertNotNull(owner.getPet("dog"));
	}

//	@Test
//	void testProcessCreationFormDuplicate() throws Exception {
//		Pet pet = new Pet();
//		pet.setName("doge");
//		owner.addPet(pet);
//		int initial_count = owner.getPets().size();
//		mockMvc.perform(post("/owners/{ownerId}/pets/new", OWNER_ID).param("name", "doge").param("birthDate",
//				"2015-02-12"));
//		Assertions.assertEquals(initial_count, owner.getPets().size());
//	}

	@Test
	void testProcessUpdateFormResultHasError() throws Exception {
		Owner newOwner = new Owner();
		mockMvc.perform(post("/owners/{ownerId}/pets/{petId}/edit", OWNER_ID, PET_ID).param("name", "sag")).andExpect(view().name("pets/createOrUpdatePetForm"));
	}


}
