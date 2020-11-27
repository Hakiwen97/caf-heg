Feature: Who get the family allowance

  As a parent i want to know if a can receive the family allowance

Background:
  Given two parents



  Scenario: A parent has a lucrative activity and the other has not
    When a parent has a lucrative activity
    And the other has not
    Then the parent with a lucrative activity should get the allowance





