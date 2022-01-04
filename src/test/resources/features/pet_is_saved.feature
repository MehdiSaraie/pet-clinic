@save_pet
Feature: Pet is saved

  Scenario: a pet is saved
    Given pet with name "hapu" and owner with id 1
    When He performs save pet service
    Then The pet should be saved to his pets
    And The pet should exist in the all pets list
