# Acceptance Testing Documentation template

Authors:

- Rosetta Pagliuca s262725
- Raniero Pirraglia s263597
- Mohamed Mamdouh Tourab s259371
- Antonino Vitale 262665

Date: 28/05/2019

Version: 2

# Contents

- [Scenarios](#scenarios)
- [Coverage of scenarios](#scenario-coverage)
- [Coverage of non-functional requirements](#nfr-coverage)



# Scenarios

| Scenario ID: SC1 | Corresponds to UC1                             |
| ---------------- | ---------------------------------------------- |
| Description      | Colleague uses one capsule of type 1           |
| Precondition     | account of C has enough money to buy capsule T |
| Postcondition    | account of C updated, count of T updated       |
| Step#            | Step description                               |
| 1                | Administrator selects capsule type T           |
| 2                | Administrator selects colleague C              |
| 3                | Deduce one for quantity of capsule T           |
| 4                | Deduce price of T from account of C            |

| Scenario ID: SC2 | Corresponds to UC1                                     |
| ---------------- | ------------------------------------------------------ |
| Description      | Colleague uses one capsule of type T, account negative |
| Precondition     | account of C has not enough money to buy capsule T     |
| Postcondition    | account of C updated, count of T updated               |
| Step#            | Step description                                       |
| 1                | Administrator selects capsule type T                   |
| 2                | Administrator selects colleague C                      |
| 3                | Deduce one for quantity of capsule T                   |
| 4                | Deduce price of T from account of C                    |
| 5                | Account of C is negative, issue warning                |

| Scenario ID: SC3 | Corresponds to UC2                                           |
| ---------------- | ------------------------------------------------------------ |
| Description      | Visitor uses n capsule of type T,  there are enough capsules |
| Precondition     | capsule T are more than n                                    |
| Postcondition    | count of T updated, LaTazza account updated                  |
| Step#            | Step description                                             |
| 1                | Administrator selects capsule type T                         |
| 2                | Deduce n for quantity of capsule T                           |
| 3                | Add n * price of capsule T to LaTazza account                |

| Scenario ID: SC4 | Corresponds to UC2                                           |
| ---------------- | ------------------------------------------------------------ |
| Description      | Visitor uses n capsule of type T,  there are not enough capsules |
| Precondition     | capsule T are less than n                                    |
| Postcondition    | -                                                            |
| Step#            | Step description                                             |
| 1                | Administrator selects capsule type T                         |
| 2                | issue warning                                                |

| Scenario ID: SC5 | Corresponds to UC3                                           |
| ---------------- | ------------------------------------------------------------ |
| Description      | Colleague recharges his/her Account                          |
| Precondition     | Account "A" exists                                           |
| Postcondition    | account A updated                                            |
| Step#            | Step description                                             |
| 1                | Administrator asks the Colleague if he/she has an account; Colleague replies by giving his/her credentials |
| 2                | Administrator selects the Colleague's Account A from the combo box "Employee" in "Payment" |
| 3                | Administrator asks the Colleague the amount of the recharge; Colleague replies with the quantity. |
| 4                | Administrator fills the "Balance" text field in "Payment" and asks for confirmation; Colleague confirms |
| 5                | Administrator clicks the "Payment" button                    |
| 6                | Application shows a pop-up confirming the Payment and updates the status of the system |



| Scenario ID: SC6 | Corresponds to UC4                                           |
| ---------------- | ------------------------------------------------------------ |
| Description      | Manager purchase new capsules                                |
| Precondition     | Capsule type T exists                                        |
| Postcondition    | count of T updated, LaTazza account updated                  |
| Step#            | Step description                                             |
| 1                | Administrator select the number of boxes required N          |
| 2                | Administrator select capsules type  T                        |
| 3                | Update the number of capsules for capsules of type T         |
| 4                | Deduce the price of the purchased capsules T from the cash account C |

| Scenario ID: SC7 | Corresponds to UC5                                           |
| ---------------- | ------------------------------------------------------------ |
| Description      | Creates a report about consumptions of a Colleague in a time range. |
| Precondition     | Colleague has an Account A                                   |
| Postcondition    |                                                              |
| Step#            | Step description                                             |
| 1                | Administrator clicks the "Menu"  button                      |
| 2                | Application shows a drop down Menu with                      |
| 3                | Adiministrator click on "Logs"                               |
| 4                | Application shows the "LaTazza Reports " window              |
| 5                | Administrator selects the Colleague's Account from the "Employee" Menu in "Employee Report" |
| 6                | Administrator selects a Start and an End date.               |
| 7                | Administrator clicks the "Generate employee report"          |
| 8                | Application shows the report in a "Shows Logs" windows.      |

| Scenario ID: SC8 | Corresponds to UC6                                           |
| ---------------- | ------------------------------------------------------------ |
| Description      | Creates a report about all consumptions.                     |
| Precondition     |                                                              |
| Postcondition    |                                                              |
| Step#            | Step description                                             |
| 1                | Administrator clicks the "Menu"  button                      |
| 2                | Application shows a drop down Menu with                      |
| 3                | Administrator click on "Logs"                                |
| 4                | Application shows the "LaTazza Reports " window              |
| 5                | Administrator selects a Start and an End date in "Consumption Report" and then clicks the "Generate consumption report" button |
| 6                | Application shows the complete report in the "Shows Logs" window. |

| Scenario ID: SC9 | Corresponds to FR7                                           |
| ---------------- | ------------------------------------------------------------ |
| Description      | Change the price of box price.                               |
| Precondition     | Capsule type c exist                                         |
| Postcondition    | The price of the box is updated to the new price             |
| Step#            | Step description                                             |
| 1                | Administrator click on "Edit"                                |
| 2                | Administrator click on "Beverages"                           |
| 3                | Administrator select the desired beverage and update the price |

| Scenario ID: SC10 | Corresponds to FR8                                           |
| ----------------- | ------------------------------------------------------------ |
| Description       | Change the name of an employee.                              |
| Precondition      | Employee e exist                                             |
| Postcondition     | The employee name get updated                                |
| Step#             | Step description                                             |
| 1                 | Administrator click on "Edit"                                |
| 2                 | Administrator click on "Employees"                           |
| 3                 | Administrator select the desired employee and update the name |

# Coverage of Scenarios

| Scenario ID | Functional Requirements covered | API Test(s)                                                  | GUI Test(s)                                    |
| ----------- | ------------------------------- | ------------------------------------------------------------ | ---------------------------------------------- |
| 1           | FR1                             | latazza/src/test/java/it/polito/latazza/data/TestScenario.java/TestScenario1 | latazza/guitests/Scenario1Test/Scenario1.txt   |
| 2           | FR1                             | latazza/src/test/java/it/polito/latazza/data/TestScenario.java/TestScenario2 | latazza/guitests/Scenario2Test/Scenario2.txt   |
| 3           | FR2                             | latazza/src/test/java/it/polito/latazza/data/TestScenario.java/TestScenario3 | latazza/guitests/Scenario3Test/Scenario3.txt   |
| 4           | FR2                             | latazza/src/test/java/it/polito/latazza/data/TestScenario.java/TestScenario4 | latazza/guitests/ScenarioTest/Scenario4.txt    |
| 5           | FR3 -FR8                        | latazza/src/test/java/it/polito/latazza/data/TestScenario.java/TestScenario5 | latazza/guitests/Scenario5Test/Scenario5.txt   |
| 6           | FR4                             | latazza/src/test/java/it/polito/latazza/data/TestScenario.java/TestScenario6 | latazza/guitests/Scenario6Test/Scenario6.txt   |
| 7           | FR5                             | latazza/src/test/java/it/polito/latazza/data/TestScenario.java/TestScenario7 | latazza/guitests/Scenario7Test/Scenario7.txt   |
| 8           | FR6                             | latazza/src/test/java/it/polito/latazza/data/TestScenario.java/TestScenario8 | latazza/guitests/Scenario8Test/Scenario8.txt   |
| 9           | FR7                             | latazza/src/test/java/it/polito/latazza/data/TestScenario.java/TestScenario9 | latazza/guitests/Scenario9Test/Scenario9.txt   |
| 10          | FR8                             | latazza/src/test/java/it/polito/latazza/data/TestScenario.java/TestScenario10 | latazza/guitests/Scenario10Test/Scenario10.txt |



# Coverage of Non Functional Requirements



| Non Functional Requirement | Test name                                                    |
| -------------------------- | ------------------------------------------------------------ |
| NFR4                       | latazza/src/test/java/it/polito/latazza/data/TestScenario.java/TestPerformance |
| NFR5                       | latazza/src/test/java/it/polito/latazza/data/TestScenario.java/TestScenario7-8 |