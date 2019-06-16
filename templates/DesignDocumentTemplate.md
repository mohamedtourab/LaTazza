# Design Document Template

Authors:
- Rosetta Pagliuca s262725
- Raniero Pirraglia s263597
- Mohamed Mamdouh Tourab s259371
- Antonino Vitale 262665

Date: 07/06/2019

Version: 3

# Contents

- [Package diagram](#package-diagram)
- [Class diagram](#class-diagram)
- [Verification traceability matrix](#verification-traceability-matrix)
- [Verification sequence diagrams](#verification-sequence-diagrams)

# Instructions

The design document has to comply with:
1. [Official Requirement Document](../Official\ Requirements\ Document.md)
2. [DataInterface.java](../src/main/java/it/polito/latazza/data/DataInterface.java)

UML diagrams **MUST** be written using plantuml notation.

# Package diagram

```plantuml
package it.polito.latazza{
}
package it.polito.latazza.data{
}
package it.polito.latazza.exception{
}

package it.polito.latazza.gui{
}

it.polito.latazza --> it.polito.latazza.data
it.polito.latazza --> it.polito.latazza.gui
it.polito.latazza.data <-- it.polito.latazza.exception
it.polito.latazza.gui --> it.polito.latazza.data
```
The architectural pattern used is a Model View one:
* **it.polito.latazza** contains the **Main class**, whose job is to properly initialize the data related to the Model and show the GUI.
* **it.polito.latazza.data** is the **Model**, which contains the classes that compose the status of the system. These classes are mostly taken from the Glossary of the Requirement Document.
* **it.polito.latazza.gui** is the **View**, that handles the interaction between the user and the system.
*  **it.polito.latazza.exception** implements all the constraints' checks that allow the application to be usable, robust and consistent. From a MVC point of view, it is related to the Model, hence the connection to the .data package.


# Class diagram

```plantuml
package it.polito.latazza.exceptions{
class Exception{
}

class BeverageException extends Exception{
serialVersionUID: private static final long
}

class DateException extends Exception{
serialVersionUID: private static final long
}

class EmployeeException extends Exception{
serialVersionUID: private static final long
}

class NotEnoughBalance extends Exception{
serialVersionUID: private static final long
}

class NotEnoughCapsules extends Exception {
serialVersionUID: private static final long
}
}

package it.polito.latazza{
class LaTazza{
}
}


package it.polito.latazza.data{

class DataImpl{
account: LaTazzaAccount
capsules: Map<Integer, CapsuleType>
colleagues: Map<Integer, Colleague>
sellCapsules()
sellCapsulesToVisitor()
rechargeAccount()
buyBoxes()
getEmployeeReport()
getReport()
createBeverage()
updateBeverage()
getBeverageName()
getBeverageBoxPrice()
getBeveragesId()
getBeverages()
getBeverageCapsules()
createEmployee()
updateEmplotee()
getLaTazzaAccount()
getEmployeeName()
getEmployeeSurname()
getEmployeeBalance()
getEmployeesId()
getEmployees()
getBalance()
reset()
}


class LaTazzaAccount{
amount: Integer
transactions: List<Transaction>
getAmount()
setAmount()
getTransactions()
addTransaction()
}

class CapsuleType{
beverageId: Integer
name: String
priceInCents: Integer
quantity: Integer
capsulesPerBox: Integer
boxPrice: Integer
boxPurchases: List<BoxPurchase>
num_beverages: Integer
newBoxPrice: Integer
newCapsulesPerBox: Integer
newQuantity: Integer
getBeverageId()
addBoxPurchase()
getBoxPurchases()
getCapsulesPerBox()
getNewBoxPrice()
getNewCapsulesPerBox()
getNewQuantity()
setCapsulesPerBox()
getPrice()
setPrice()
getQuantity()
setQuantity()
getBoxPrice()
setBoxPrice()
getName()
setName()
setNumBeverage()
setNewBoxPrice()
setNewCapsule()
setNewCapsulesPerBox()
setNewQuantity()
}

class Colleague{
employeeId: Integer
name: String
surname: String
account: Account
num_colleague: Integer
getAccount()
setAccount()
getName()
setName()
getSurname()
setSurname()
getEmployeeId()
buyCapsules()
buyCapsules()
rechargeAccount()
getTransactions()
updateJsonAccount()
}

class Account{
id: Integer
amount: Integer
transactions: List<Transaction>
getEmployeeId()
getAmount()
setAmount()
setNewTransaction()
getTransactions()
rechargeAccount()
setTransaction()
}

class Transaction{
date: Date
amount: Integer
getDate()
getAmount()
}

class Consumption extends Transaction{
 isCash: Boolean
 capsuleId: Integer
 capsulesNumber: Integer
 account: Account
 isVisitor: boolean
 getIsCash()
 getCapsuleId()
 getCapsulesNumber()
 getAccount()
 getIsVisitor()
}

class Recharge extends Transaction{
employeeId: Integer
getEmployeeId()
}

class BoxPurchase extends Transaction{
quantity: Integer
capsuleType: Integer
getQuantity()
getCapsuleType()
}
}

LaTazza --> DataImpl
DataImpl --> LaTazzaAccount
Account --> "1..*" Transaction
DataImpl --> CapsuleType
DataImpl --> "1..*" Colleague
LaTazzaAccount --> "1..*" Transaction
CapsuleType --> "1..*" BoxPurchase
Colleague --> Account


DataImpl --> Exception
```

# Verification traceability matrix


|  | DataImpl | LaTazzaAccount  | Colleague |Account|Transaction|CapsuleType|Consumption|Recharge|BoxPurchase|
| -------------|-------------:| -----:| ---------:|-----:|-----:|-----:|-----:|----:|-----:|
|FR1   |   x|  x  | x             |      x| x |     x|     x|      |         |
|FR2   |   x|  x  |               |       | x |     x|     x|      |         |
|FR3   |   x|  x  |              x|      x| x |      |      |     x|         |
|FR4   |   x|   x|               |       | x |     x|      |      |        x|
|FR5   |   x|    |              x|      x|          x|    x  |    x  |  x    |         |
|FR6   |   x|   x| x | x | x | x | x | x | x |
|FR7   |   x|    |               |      |            |     x|      |      |         |
|FR8   |   x|    |              x|     x|            |      |      |      |         |


# Verification sequence diagrams
## Scenario 1 - SC1
```plantuml
autonumber
actor ": Administrator"

": Administrator" -> ": GUI": selectEmployee()
": Administrator" -> ": GUI": selectBeverage()
": Administrator" -> ": GUI": selectNumberOfCapsules()
": Administrator" -> ": GUI": selectFromAccount()
": GUI" -> ": DataImpl" : sellCapsules(employeeId, beverageId, numberOfCapsules, fromAccount)
": DataImpl" -> ": CapsuleType" : getQuantity()
": DataImpl" -> ": CapsuleType" : setQuantity(quantity)
": DataImpl" -> ": Colleague" : buyCapsules(price, num, capsuleId, isCash)
": Colleague" -> ": Account" : setNewTransaction(amount, date, type, capsuleId, capsulesNumber, isCash)
": Colleague" -> ": Colleague" : updateJsonAccount()
": DataImpl" -> ": LaTazzaAccount" : addTransaction(tr)
```

## Scenario 2 - SC2
```plantuml
autonumber
actor ": Administrator"

": Administrator" -> ": GUI": selectEmployee()
": Administrator" -> ": GUI": selectBeverage()
": Administrator" -> ": GUI": selectNumberOfCapsules()
": Administrator" -> ": GUI": selectFromAccount()
": GUI" -> ": DataImpl" : sellCapsules(employeeId, beverageId, numberOfCapsules, fromAccount)
": DataImpl" -> ": CapsuleType" : getQuantity()
": DataImpl" -> ": CapsuleType" : setQuantity(quantity)
": DataImpl" -> ": Colleague" : buyCapsules(price, num, capsuleId, isCash)
": Colleague" -> ": Account" : setNewTransaction(amount, date, type, capsuleId, capsulesNumber, isCash)
": Colleague" -> ": Colleague" : updateJsonAccount()
": DataImpl" -> ": LaTazzaAccount" : addTransaction(tr)
": GUI" -> ": Administrator" : issueWarning()
@enduml
```
