@who_is_the_owner
Feature: Who is the owner?
  Finds the owner given it's id

  Scenario:
    Given ownerId is 1
    When I ask for the owner
    Then I should be given an owner with ownerId 1

