# Fly Around



[![License](https://img.shields.io/:license-apache-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

FHNW - Internet Technology Project 2024  
(Forked from the reference project:  [Pizzeria_Reference_Project](https://github.com/FHNW-INT/Pizzeria_Reference_Project))

🎞️ **Video Presentation:** [Fly Around Presentation](https://fhnw365-my.sharepoint.com/personal/christopher_courtrosa_students_fhnw_ch/_layouts/15/stream.aspx?id=%2Fpersonal%2Fchristopher%5Fcourtrosa%5Fstudents%5Ffhnw%5Fch%2FDocuments%2FFly%20Around%20Presentation%2Emp4&ga=1&referrer=StreamWebApp%2EWeb&referrerScenario=AddressBarCopied%2Eview%2E895dd599%2D3349%2D4ece%2Dac09%2D0eaf2850b014)

Members:

- Franco D'Agostino -  <franco.dagostino@students.fhnw.ch>
- Christopher Court Rosa - <christopher.courtrosa@students.fhnw.ch>
- Fabian Stoll - <fabian.stoll@students.fhnw.ch>



## Contents

- [Fly Around](#fly-around)
  - [Contents](#contents)
  - [Analysis](#analysis)
    - [Scenario](#scenario)
    - [User Stories](#user-stories)
    - [Use Case](#use-case)
  - [Design](#design)
    - [Wireframe](#wireframe)
    - [Prototype](#prototype)
    - [Domain Design](#domain-design)
    - [Backend Architecture](#backend-architecture)
    - [Business Logic](#business-logic)
  - [Implementation](#implementation)
    - [Backend Technology](#backend-technology)
    - [Frontend Technology](#frontend-technology)
  - [Project Management](#project-management)
    - [Roles](#roles)
    - [Milestones](#milestones)
    - [Maintainer](#maintainer)
    - [License](#license)

## Analysis

### Scenario

**Fly Around** is a small PoC project for the Internet Technology course at FHNW. The project is about a simple web app to search for flights to cool destinations and create basic bookings.

The airline industry domain is wide, to keep the project simple we decided to limit the project scope to a minimal set of admin and user features which include for example: managing the flight schedule, searching flight by destination or interests, creating single passenger booking.

### User Stories


1. As an Admin, I want to have a Web app to use on different mobile devices and desktop computers.
2. As an Admin, I want to see a consistent visual appearance to navigate easily, and it should look consistent.
3. As an Admin, I want to use list views to explore and read my flights.
4. As an Admin, I want to use edit and create views to maintain my flights.
5. As an Admin, I want to log-in so that I can authenticate myself.
6. As a User, I want to use list views so that I can access public pages and search flights.
7. As a User, I want to authenticate myself so that I can view my bookings.

### Use Case


![use-case-diagram](https://www.plantuml.com/plantuml/png/TTBDQeD040Vm-pp5aAEGqE-F8fAeUCl5uAdqiEnETSbcDwphePI-UrsBOHlrvCZy79m_isqTiwuxAL3qwT0PjB9k70ffYJjfD33kZCMTECcnhbgo06V6ZwmcBDJNpffE2_m6GEnQugmbN4NHUvNTt7v44MQMcFFIZruXQp4hflJEqvAO264v0-yzhCxYQcOrGnyypKdHVvhFq4TFzqopUgHuu0s9Jj7mpRuu0A1_OBpPz1lW6yQnr5nrWf8aQ9NBhMgvbL-rPi9y2i9CZJbANV-bcKxjyoNDK9OpyYN8ABHv2Z3LhrwF-H2pl1bm7mxYqlxm0-2F1jVhnF_ifIw3EW_gVjfmSCEB3tvBMlH7-Wi0)


- [x] **[UC01] Create Flight** - Admin can create flights.
- [x] **[UC02] Read Flight** - Admin and user can create flights.
- [x] **[UC03] Update Flight** - Admin can update flights.
- [x] **[UC04] Delete Flight** - Admin can delete flights.
- [x] **[UC05] Manage Flight Schedule** - Admin can use a dashboard to manage the flight schedule by creating, re[ ] ading, ~~updating~~, and deleteing flights.
- [x] **[UC06] Create Booking** - User can create bookings.
- [x] **[UC07] Read Booking** - User can read his/her own bookings.
- [x] **[UC08] Delete Booking** - User can delete his/her own bookings.
- [x] **[UC09] Flight Search** - User can search flights by flight details (e.g. flight number and date) or by destination details (e.g. activity, or city). The result is presented with a list view which includes flight.

Remarks and assumptions:

- Once a booking is made, it is not possible to update it anymore, it can only be deleted.
- User doesn't have a dashboard with all his/her bookings. Each booking is accessed individually with the pair lastname and booking ID.
- For simplicity, we assumend that the admin cannot access or manage the user bookings.
  
## Design

For our web app, we will adopt a simple, clean, and modern design. The goal is to create a user-friendly interface that is visually appealing and easy to navigate. We will use a minimalist approach, focusing on clear typography, ample white space, and intuitive user interactions. The color palette will be carefully chosen to create a harmonious and professional look. Our design will prioritize functionality and usability, ensuring that users can easily access the desired features and information. By adopting a modern design, we aim to provide a seamless and enjoyable user experience.

### Wireframe

This wireframe design represents the initial idea for the user interface of our web app. It showcases the layout and structure we envision for the different pages and components.

![design1](/images/design1.png)
![design1](/images/design2.png)

### Prototype

![design1](/images/home-search.png)

### Domain Design

The `ch.fhnw.flyaround.data.domain` package contains the following domain objects / entities including getters and setters:

![class-diagram](https://www.plantuml.com/plantuml/png/XL9DQyCm3BtxLuWS1otRjOpGVQmmA6Em_G4jSL9H9owokylOVv-IsdNcZkmKwRs7_FIKjVF8_jWPLHbq3ZQ6sfs7BmL0DQnWQ_jMwcQ47xMZjaTlMPWtppHojJuC8rZV4HyiU-529IGoqoUQ2ohDHAy57ZxlrEdfl8XjROLceabZvnJ45MCpFXbAGJ-itOjhDsn6pj_UXxvKtoeaS3AOHG3IzDZfwTw47Xzi_Hkhq6POskVywYdaCksH4SbCy3UOeJfUezhfQl_Svyd5vVvWZaxpKuTa9huECb_thMR1NcEjmbK4EtbHoSjP5A99FmUnyozPEWOlCPrPc3z7F4HnKy1Y0SNrSdbLm2RwQzXsKFwdybOqOvTBOhIhqOMkeR4C9QYqvfpUoTYrxkjZPtu0)

Remarks:

- To reduce the complexity we assumend that a booking only contains 1 passenger and 1 flight.

### Backend Architecture

The following digram shows the high level architecture of the backend components.

![architecture diagram](https://www.plantuml.com/plantuml/png/VP5FJmCX4CNlV8f9xurt3wRGIE9jutfJ3qYdMr8M2D36nlZTbPti7pRHutk_n_mPTYchc2-zPUbiN51HzQ1z7xn3bzjyimWHTLQkiyXOKFgiEeJDCmQVJFRnje4l1l0ghUbEURRVGIMGHHVAJGm-hZ4ViV3-jtctmcB4ZKe9NOTn5MY6mFTYh1RZrMYiPchr2GyNYyIG1cklUj5uYKH1ovfxxtBqrcAioiusLTpdQETLrvWJdZgki2WOIblZCiQZqkN6Z-qByEOflRcx3tgMDaEQ1_DNy3WCHpdvJqxHc1Ja9SY6tuBjze7Emsif9YcAbBLiQidAao9fyLdIcSGenI0PhL-CiXhS0tu6dv39oPULpoUVLpvZEtI7Isz_0000)



### Business Logic

Based on the [UC09] Flight Search, the app will offer the option to search flights based on date, departure airport and arrival airport.

**Path**: [`/flights/search?departureAirportIataCode=&arrivalAirportIataCode=&flightDate`] 

**Param**: `departureAirportIataCode` Admitted value: IATA code for the airport stored in the DB.
**Param**: `arrivalAirportIataCode` Admitted value: IATA code for the airport stored in the DB.
**Param**: `flightDate` Admitted value: date.

**Method:** `GET`

## Implementation

### Backend Technology

This Web application is relying on [Spring Boot](https://projects.spring.io/spring-boot) and the following dependencies:

- [Spring Boot](https://projects.spring.io/spring-boot)
- [Spring Data](https://projects.spring.io/spring-data)
- [Java Persistence API (JPA)](http://www.oracle.com/technetwork/java/javaee/tech/persistence-jsp-140049.html)
- [H2 Database Engine](https://www.h2database.com)

To bootstrap the application, the [Spring Initializr](https://start.spring.io/) has been used.

Then, the following further dependencies have been added to the project `pom.xml`:

- DB:

  ```XML
  <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <scope>runtime</scope>
  </dependency>
  ```

- SWAGGER:

  ```XML
   <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>2.3.0</version>
   </dependency>
  ```

- OTHER

  ```XML
  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
  </dependency>
  <dependency>
        <groupId>org.springdoc</groupId>
        <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
        <version>2.5.0</version>
    </dependency>
  <dependency>
    <groupId>org.modelmapper</groupId>
    <artifactId>modelmapper</artifactId>
    <version>2.4.4</version>
  </dependency>
  ```

### Frontend Technology

This Web application was developed using Budibase and it is available for preview at https://flyaround.budibase.app/app/flyaound.

Booking creation page on budibase.

![sample frontent](/images/fronend.png)

## Project Management

### Roles

- Back-end developer: Franco D'Agostino
- Front-end developer: Christopher Court Rosa
- Business Analyst: Fabian Stoll

### Milestones

1. [x] **Analysis**: Scenario ideation, use case analysis and user story writing.
2. [x] **Prototype Design**: Creation of wireframe and prototype.
3. [x] **Domain Design**: Definition of domain model.
4. [x] **Business Logic and API Design**: Definition of business logic and API.
5. [x] **Data and API Implementation**: Implementation of data access and business logic layers, and API.
6. [x] **Security and Frontend Implementation**: Integration of security framework and frontend realisation.
7. [ ] **Deployment**: Deployment of Web application on cloud infrastructure.

### Maintainer

- Franco D'Agostino -  <franco.dagostino@students.fhnw.ch>
- Christopher Court Rosa - <christopher.courtrosa@students.fhnw.ch>
- Fabian Stoll - <fabian.stoll@students.fhnw.ch>

### License

- [Apache License, Version 2.0](blob/master/LICENSE)
