Feature: Adding Product Functinality

  Scenario: Product Creation and listing
    Given I have the following products
      | name    | price | priceCurrency |
      | Aspirin | 2.2   | GBP           |
      | Lempsip | 1.2   | CHF           |
      | Newdrug | 3.1   | CHF           |
    When I add the products to the system
    Then I will see those products in the system
      | productId | name    | price | priceCurrency |
      | 1         | Aspirin | 2.2   | GBP           |
      | 2         | Lempsip | 1.2   | CHF           |
      | 3         | Newdrug | 3.1   | CHF           |

#  Scenario: Product update
#    Given I have the following products
#      | Milk         | 2.2 GBP |
#      | Sugar        | 1.2 CHF |
#    When I update the products as follows
#      | Milk         | 1.3 GBP |
#      | Sugar        | 1.7 GBP |
#    Then I will see those products in the system
#      | Milk         | 1.3 GBP |
#      | Sugar        | 1.7 GBP |
#
#
#  Scenario: Product delete
#    Given I have the following products
#      | Coffee         | 2.2 GBP |
#      | Tea         | 1.2 CHF |
#    When I delete the following products
#      | 1 |
#    Then I will see those products in the system
#      | Tea         | 1.2 CHF |
