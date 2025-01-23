#   **Koombea Tests**

Automated tests for the Koombea technical assessment, focusing on mobile and REST API testing using Appium and   
Rest-Assured.

## **Project Overview**

This project encompasses automated tests for:

1.  **Mobile Automation**: Testing the functionality of a mobile application, specifically focusing on unit conversions (e.g., length, area, volume) and speed conversions.
    
2.  **REST API Automation**: Validating the endpoints of the [Rick and Morty API](https://rickandmortyapi.com/), including status codes, response payloads, and headers.

## **Prerequisites**

Ensure the following are installed on your system:

-   **Java Development Kit (JDK)**: Version 17 or later.
-   **Apache Maven**: Version 3.8.0 or later.
-   **Appium Server**: For mobile test automation.
-   **Android SDK**: For running Android emulators or connecting physical devices.

## **Running the tests**

1.  **Clone the Repository**: 
`git clone https://github.com/alexislopezg/KoombeaTest.git`
2. **Install Dependencies**: 
`mvn clean install`
3. **Start Appium Server (for mobile tests)**
`appium` 
4. **Start the mobile tests**
`cd KoombeaTest
mvn -Dtest=UnitConversionTest test`
5. **Start the API tests**
`cd KoombeaTest/KoombeaRestTest/
mvn -Dtest=characterTests.CharacterTest test`  

