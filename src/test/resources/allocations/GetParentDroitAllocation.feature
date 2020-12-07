Feature: Who get the family allowance

  As a parent i want to know if a can receive the family allowance


#A
  Scenario: A parent has a lucrative activity and the other has not
    Given two parents
    And a parent has a lucrative activity
    When i ask who has the right
    Then the parent with a lucrative activity gets the right

#B
  Scenario: Two parents have a lucrative activity but one has the parental authority
    Given two parents
    And two parents have a lucrative activity
    And  one has the parental authority
    When i ask who has the right
    Then the parent with the parental authority gets the right
#C
  Scenario: two parents with a lucrative activity,they are separated and one live with the child
    Given two parents
    And two parents have a lucrative activity
    And  they both have the parental authority
    And  they are separated
    And one live with the child
    When i ask who has the right
    Then the parent who lives with the child get the right
#D
  Scenario: two parents with a lucrative activity,they both have the parental authority,they are together and one works in the same canton than the child's residence
    Given two parents
    And two parents have a lucrative activity
    And  they both have the parental authority
    And  they are together
    And one parent work in the same canton than the child's residence
    When i ask who has the right
    Then the parent who works in the same canton than the child's residence get the right

#E
  Scenario: two parents with a lucrative activity,they both have the parental authority, they are together
    Given two parents
    And two parents have a lucrative activity
    And  they both have the parental authority
    And  they are together
    And  none parents work in the child's residence
    When i ask who has the right
    Then the parent with the highest salary get the right
    #par sur
#F
  Scenario: two parents with a lucrative activity,they both have the parental authority, they are together and they are both freelance
    Given two parents
    And two parents have a lucrative activity
    And  they both have the parental authority
    And  they are together
    And they are freelance
    And none parents work in the child's residence
    When i ask who has the right
    Then the parent with the highest salary get the right








