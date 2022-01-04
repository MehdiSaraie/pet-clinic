@find_pet
Feature: Find Pet
  Finds the pet given it's id

  Scenario:
    Given petId is 1
    When I ask for the pet
    Then I should be given a pet with petId 1

