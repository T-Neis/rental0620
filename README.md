
# Tool Rental Application

This is a simple tool rental application designed as a programming demonstration. The application is implemented in Java and includes a JUnit test suite to validate its functionality.

## Overview

The application is a point-of-sale tool for a store that rents big tools. Customers can rent tools for a specified number of days, and the application generates a Rental Agreement at checkout. The store charges a daily rental fee, which varies by tool type. Some tools are free of charge on weekends or holidays. Clerks can also apply discounts to the total daily charges.

## Specifications

### Tools Available for Rental

| Tool Code | Tool Type  | Brand  | Daily Charge | Weekday Charge | Weekend Charge | Holiday Charge |
|-----------|------------|--------|--------------|----------------|----------------|----------------|
| CHNS      | Chainsaw   | Stihl  | $1.49        | Yes            | No             | Yes            |
| LADW      | Ladder     | Werner | $1.99        | Yes            | Yes            | No             |
| JAKD      | Jackhammer | DeWalt | $2.99        | Yes            | No             | No             |
| JAKR      | Jackhammer | Ridgid | $2.99        | Yes            | No             | No             |

### Holidays

- **Independence Day**: Observed on the closest weekday if it falls on a weekend.
- **Labor Day**: First Monday in September.

### Checkout Process

The following information is required at checkout:

- **Tool code**: The code of the tool to rent.
- **Rental day count**: The number of days for which the customer wants to rent the tool (must be 1 or greater).
- **Discount percent**: A whole number between 0 and 100.
- **Checkout date**: The date the tool is checked out.

The checkout process will generate a Rental Agreement with the following details:

- Tool code, type, brand.
- Rental days, checkout date, due date.
- Daily rental charge, charge days.
- Pre-discount charge, discount percent, discount amount, final charge.

### Rental Agreement Formatting

- Date: `mm/dd/yy`
- Currency: `$9,999.99`
- Percent: `99%`

## Implementation

### Classes

- **Tool**: Represents a tool with attributes like code, type, brand, daily charge, and charge applicability on weekdays, weekends, and holidays.
- **RentalAgreement**: Represents the rental agreement with attributes such as tool details, rental days, charges, and discount information.
- **ToolRentalService**: Manages the rental process, initializes available tools and holidays, and handles checkout operations.

### Running the Application

1. **Clone the Repository**:
   ```sh
   git clone https://github.com/T-Neis/rental0620.git
   ```

2. **Compile the Code**:
   ```sh
   javac -cp .:junit-5.7.0.jar Tool.java RentalAgreement.java ToolRentalService.java
   ```

3. **Run the Main Class**:
   ```sh
   java ToolRentalService
   ```

4. **Run JUnit Tests**:
   ```sh
   java -cp .:junit-5.7.0.jar org.junit.runner.JUnitCore ToolRentalServiceTest
   ```

### Tests

The application includes a JUnit test suite that validates various scenarios, such as:

- Invalid discount percent (greater than 100%).
- Valid checkout with a ladder, chainsaw, and jackhammer.
- Calculations of charges, discounts, and final amounts for different rental periods and tools.
