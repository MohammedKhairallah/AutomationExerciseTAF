# AutomationExerciseTAF

Test automation framework (Java, Selenium WebDriver, TestNG, Maven, Allure) for [automationexercise.com](https://automationexercise.com), built with the Page Object Model.

## Tech Stack

- Java
- Selenium WebDriver
- TestNG
- Maven
- Allure Report
- Log4j2
- <<API library, e.g. Rest Assured>>

## Features

- Page Object Model with reusable components (navigation bar, side bar, footer)
- Driver factories for Chrome, Edge, Firefox and Safari
- Data-driven tests using JSON test data (Excel and properties readers also available)
- API layer for user management, used for test preconditions
- Custom TestNG listeners
- Screenshots and screen recording
- Allure reporting with attachments and environment info
- Logging with Log4j2
- Selenium Grid support via properties file

## Project Structure

```
src
├── main
│   ├── java/com/automationexercise
│   │   ├── apis          # API calls for test preconditions
│   │   ├── drivers       # Browser factories
│   │   ├── listeners     # TestNG listeners
│   │   ├── media         # Screenshots and screen recording
│   │   ├── pages         # Page objects and components
│   │   ├── utils         # Actions, waits, data readers, logs, Allure
│   │   └── validations   # Assertions and verifications
│   └── resources         # Configuration properties files
└── test
    ├── java/com/automationexercise/tests
    │   ├── api
    │   ├── ui            # Cart, Checkout, Contact Us, Home, Login,
    │   │                 # Payment, Product Details, Products, Signup
    │   └── BaseTest.java
    └── resources
        ├── downloads     # Files downloaded by tests
        ├── test-data     # JSON test data
        └── uploads       # Files used by upload tests
```

## Test Coverage

UI tests for: Signup, Login, Home, Products, Product Details, Cart, Checkout, Payment and Contact Us.

## Author
**Mohammed Khairallah**
- GitHub: [MohammedKhairallah](https://github.com/MohammedKhairallah)
- LinkedIn: https://www.linkedin.com/in/mohammed-khairallah/
**Mohammed Ali**
- GitHub: [MohammedKhairallah](https://github.com/MohammedKhairallah)
- LinkedIn: <<your LinkedIn URL>>
