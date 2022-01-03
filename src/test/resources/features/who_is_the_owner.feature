@sample
Feature: Who is the owner?
  Finds the owner given the id

  Scenario:
    Given ownerId is 1
    When I ask for the owner
    Then I should be given an instance with ownerId 1

