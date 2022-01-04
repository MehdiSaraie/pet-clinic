@new_pet
Feature: Create New Pet For Owner
  Create a new pet and add it to the given owner

  Scenario:
    Given owner with id 1, name "Mehdi" "Saraie" exists
    When he performs new pet service to add a new pet to his pets
    Then he has a new pet
    And he is owner of the new pet
