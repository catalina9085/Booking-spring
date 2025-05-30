# Booking-spring

Proiect Spring Boot modular pentru gestionarea rezervărilor.

## Structura proiectului

- `booking-domain` - conține entitățile și clasele de model.
- `booking-persistence` - gestionează persistenta datelor în baza de date.
- `booking-service` - se ocupa de logica de business
- `booking-web` - expune API-ul REST și interfața web.

## Funcționalități principale

- Autentificare și autorizare cu JWT  
- Roluri: USER și ADMIN, cu acces diferențiat  
- Managementul utilizatorilor și al sălilor de conferință (CRUD)  
- Gestionarea rezervărilor, cu validare suprapuneri și capacitate  
- Export rezervări în format CSV  
- Import grupuri de persoane din fișiere CSV  

## Tehnologii folosite

- Java Spring Boot  
- Hibernate / JPA pentru acces la baza de date  
- JWT pentru securitate și autentificare  
- H2 pentru stocare date  
- API REST pentru comunicarea cu frontend-ul 
