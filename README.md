# Petroleum Management System

## Project Overview

The **Petroleum Management System** is a Java-based console application designed to automate and simplify the management of petroleum products (petrol and diesel) at fuel stations. The system provides a robust solution for tracking sales, managing inventory, and generating reports.

---

## Features

1. **User Authentication & Management**

   - Secure login system with two user roles:
     - **Administrator**: System administration and complete access
     - **Manager**: Daily operations and record management
   - Administrator can create up to 5 manager accounts
   - View all users (Admin only)

2. **Inventory Management**

   - Track real-time stock levels for both petrol and diesel
   - Add new stock
   - Automatic stock updates after each sale
   - Low stock warnings

3. **Sales & Record Management**

   - Record daily readings for 8 nozzles (4 petrol + 4 diesel)
   - Automatic calculation of:
     - Total liters sold
     - Total sales amount
     - Remaining stock
   - Input validation for all readings

4. **Reporting**
   - View today's sales records
   - Detailed transaction summaries
   - Current inventory status
   - Formatted tabular output

---

## Technical Implementation

### Programming Language & Design

- **Java** (JDK 8+)
- Object-Oriented Design
- Singleton Pattern for core managers
- Exception handling for robust error management

### Key Classes

- `PetroleumManagementSystem`: Main system controller
- `UserManager`: Handles user authentication and management
- `RecordManager`: Manages daily sales records
- `Inventory`: Controls stock management
- `MenuManager`: Handles UI/menu display
- `DailyRecord`: Data model for daily transactions
- `NozzleReading`: Data model for individual nozzle readings

### Data Management

- In-memory data structures
- ArrayList for dynamic record storage
- Real-time calculations and updates

---

## Class Structure

```java
// Core system classes
PetroleumManagementSystem
├── UserManager (Singleton)
├── RecordManager (Singleton)
├── Inventory (Singleton)
└── MenuManager (Singleton)

// Data models
DailyRecord
└── NozzleReading

// Support classes
InputValidator
PetroleumSystemException
```

---

## How to Run

1. Ensure Java JDK 8 or higher is installed
2. Clone the repository:
   ```bash
   git clone <repository-url>
   ```
3. Compile the project:
   ```bash
   javac src/petroleum_managemnt_system/*.java
   ```
4. Run the application:
   ```bash
   java -cp src petroleum_managemnt_system.Main
   ```

---

## First Time Setup

1. On first run, you'll be prompted to:

   - Create an administrator account
   - Set initial inventory levels for petrol and diesel

2. After setup, you can:
   - Login as administrator to create manager accounts
   - Start recording daily sales
   - Monitor inventory levels

---

## Features in Detail

### Administrator Functions

- Create manager accounts (up to 5)
- View all user accounts
- View daily records
- Check inventory
- Add stock
- System management

### Manager Functions

- Enter new readings
- View today's records
- Check inventory
- Basic operations
