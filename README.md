# Inhaltsverzeichnis:
- [Übersicht](#übersicht)
- [Anforderungsanalyse](#anforderungsanalyse)
- [Benutzte Technologien](#benutzte-technologien)
- [Architektur unserer Anwendung](#architektur-unserer-anwendung)
- [Hands on Installation](#hands-on-installation)
- [Repository klonen](#repository-klonen)
- [Datenbank Konfiguration](#datenbank-konfiguration)
- [Datenbank Schema](#datenbank-schema)
- [API Endpunkte](#api-endpunkte)
- [Rollen spezifische Endpoints](#rollen-spezifische-endpoints)
- [Abgesicherte Endpoints](#abgesicherte-endpoints)
- [Spring Security Crash Kurs](#spring-security-crash-kurs)
- [FilterChain](#filterchain)
- [Authentifizierung](#authentifizierung)
- [Registrieren eines Benutzers](#registrieren-eines-benutzers)
- [Tagesjournale](#tagesjournale)

## Übersicht

Für das Modul 223 mussten wir eine **multiuserfähige** Applikation entwickeln.

Wir entschieden uns für eine Anwendung, die den **Wissensaustausch** erleichtert. Sie könnte beispielsweise **intern in einer Firma** genutzt werden. Die App soll ähnlich wie Wikipedia funktionieren, jedoch interaktiver sein und selbstverständlich den **gleichzeitigen Zugriff mehrerer Nutzer** ermöglichen.

Dieses Projekt **baute auf dem vorherigen Modul 183 – Applikationssicherheit auf** und erweiterte es um einige neue Themen. Dazu gehörten die **Implementierung des Frontends** sowie die **Absicherung und der Zugriff auf bestimmte Ressourcen** anhand von **User-Rollen und Berechtigungen**. 

Ergo: Wiederholtes aber dazu noch die Implementation eines Frontends dazu. (Nicht nur das austesten der Endpoints mit POSTMAN).

In dieser Dokumentation erläutern wir unser Projekt genauer und geben euch einen **kleinen Einblick** in die umgesetzten Funktionen. Bitte beachtet, dass sich diese Dokumentation **ausschließlich auf das Backend** bezieht. Für weitere Informationen, insbesondere zum Frontend, siehe bitte hier: (_LINK ZUM README.MD vom Frontend falls nicht möglich, dann sein lassen!).

## Anforderungsanalyse: 
Da wir für diese Projekt keine echten Stakeholder noch sonstige echte Anforderungen (ausser die für unsere Doku) erhalten haben, waren wir ziemlich flexibel und konnten somit die Anforderungen, die bei `Issues` zu finden sind, selbst entscheiden.

**Hier noch kurz auf einen Blick ein paar Anforderungen:**

**Anforderung:** As a user, I want to create a new wiki page, So, I can document and share my knowledge.
**Akzeptanzkriterie:** Assuming Creating a new wiki page If I close the app. Then The page should still exist.

**Anforderung:** As an **admin**, I want to manage user **roles** and permissions, So, I can ensure appropriate access and maintain security.
**Akzeptanzkriterie:** Assuming Another user has no delete rights. If He would try to delete a page. Then The system should deny such behavior.

**Anforderung:** As an admin, I want to view analytics about article views and edits, so I can monitor engagement and contributions.
**Akzeptanzkriterien:** Assuming I want an overview of my group to know who is working on which note. If I would use the “analytics documentator”. Then I would get a snapshot of the information as a pdf file.

weitere User Stories wie auch ihre Akzeptanzkriterien finden sie hier: 
[Issues · HollowWeb/Modul223-Backend](https://github.com/HollowWeb/Modul223-Backend/issues)
## Benutzte Technologien: 

### Frontend
- Java 21, Unsere Entwicklungsumgebung
- Spring Boot, das zu verwendete Framework 
- Spring Security mit JWT Authentifikation, die zu verwendete Sicherheitsimplementation
- Spring DATA JPA, weiteres datenspezifisches Framework
- MYSQL, Datenbank
- ByCrypt, entschlüsselungs-hash Verfahren

### Backend
- Vite + React: Framework used in frontend.
- react-markdown: Used to render markdown in realtime.
- Vitest: Used for automated UI tests.
- React Router: Handels client-side react routing.

## Architektur unserer Anwendung

![[Mdl233-Architecture.png]]
https://github.com/HollowWeb/Modul223-Backend/blob/Documentation/Modul-223-Backend/docs/images/Mdl233-Architecture.png

Für neuankömmlige die sich noch nicht so vertraut sind mit der Springboot Architektur haben wir uns die Zeit genommen noch eine detailliertere Anatomie mit simplen Code-Beispielen zu erstellen.

Das ganze findet ihr auch im Projekt unter 
https://github.com/HollowWeb/Modul223-Backend/blob/Documentation/Modul-223-Backend/docs/images/Springboot_architecture_explanation.png

![[Springboot_architecture_explanation.png]]
Das ganze findet ihr auch im Projekt unter Docs/images/sb_explanation"

## Hands on installation:

### Repository klonen: 

Zu aller erst müsst ihr dieses Projekt klonen um ihn dann auf eurem lokalen Rechner zu "pullen".
Folgender command hilft euch dabei: 

```git
git clone https://github.com/HollowWeb/Modul223-Backend.git
cd Modul223-Backend
```

### Datenbank Konfiguration:

Die Datenbank befindet sich natürlicherweise auf einem externen Server Provider aber hierzu kann man die Konfigurationen entnehmen: 

```java
spring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.sql.init.mode=always
```

### Datenbank Schema

Hier eine detailliertere Modellierung unserer Datenbank Schematas : [Modul223-Backend/Modul-223-Backend/Project Specification/ERD_V1.png at Documentation · HollowWeb/Modul223-Backend](https://github.com/HollowWeb/Modul223-Backend/blob/Documentation/Modul-223-Backend/Project%20Specification/ERD_V1.png)

## API Endpunkte: 
Hier werden folgende API- Endpunkte aufgelistet die auch aktiv zur Verwendung gekommen sind, bitte entschuldigt das wir nicht alle Endpoints aufgezählt haben, dies würde sonst den Rahmen dieser Dokumentation sprengen.

#### Rollen spezifische Endpoints:
- **URL:** `POST /api/users/register`: Registriert einen neuen Benutzer mit der standard Rolle "User"
- **URL:** `POST /api/users/login`: Authentifiziert den Benutzer. 

(Uns ist bewusst das dieses Vorgehen nicht gerade best practice ist. Unser Hauptgedanke war, das registrieren zu optimieren/ schneller zu machen und gleich den JWT Token mit den dazugehörigen claims auszustellen.)

#### Abgesicherte Endpoints:
-  **URL:**  `GET /api/roles`: Hollt alle Rollen. Ist nur für Benutzer mit ADMIN Rolle zugänglich.
-  **URL:**  `POST /api/roles`: Erstellt neue Rollen, was nur ein Admin machen kann.
- **URL:**  `POST /api/users/{id}`: Kann User ihre "Credentials", infos ändern/hinzufügen. Nur für Admins zugänglich. 

## Spring Security Crash Kurs:

Bevor wir folgende Sicherheitsthemen ansprechen, haben wir uns auch hier die Zeit genommen gewisse Sicherheitsthemen wie `Securityfilterchain` usw. anzusprechen und auch bildlich darzustellen, damit jeder sich ein besseres Bild unserer Springboot security machen kann.

Folgendes Bild erklärt einen typischen Ablauf wie z.B das registrieren eines neuen Users und wie alle Layers und gewisse Komponente miteinander agieren, was zu was geschickt wird etc:

![[SecurityFilters.png]]

## FilterChain:

```java
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {  
    http  
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs  
            .authorizeHttpRequests(auth -> auth  
                    .requestMatchers("/api/users/login", "/api/users/register", "/api/articles/all").permitAll() // Allow unauthenticated access to these endpoints  
                    .anyRequest().authenticated() // Protect all other endpoints  
            )  
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  
  
    return http.build();  
}
```

## Authentifizierung: 
Hier einen Einblick in unsere Klasse die für das herausgeben eines JWT tokens zuständig ist: 

```java
@Component  
public class JwtUtil {  
  
    @Value("${jwt.secret}")  
    private String secret;  
  
    @Value("${jwt.expiration}")  
    private long expiration;  
  
    /**  
     * Generates a JWT token for the given username and roles.     * @param username the username for which the token is generated.  
     * @param roles the roles to be included in the token.  
     * @return the generated JWT token.  
     * @throws IllegalArgumentException if the username or roles contain invalid characters.  
     */    public String generateToken(String username, Set<String> roles) {  
        if (username.contains("_")) {  
            throw new IllegalArgumentException("Username contains invalid characters for JWT.");  
        }  
        // Ensure roles are valid strings  
        roles.forEach(role -> {  
            if (!role.matches("^[a-zA-Z0-9]+$")) {  
                throw new IllegalArgumentException("Invalid role for JWT encoding: " + role);  
            }  
        });  
  
        //only for debugging purposes.  
        System.out.println("Username: " + username);  
        System.out.println("Roles: " + roles);  
  
        return Jwts.builder()  
                .setSubject(username)  
                .claim("roles", roles) // Include roles as a claim  
                .setIssuedAt(new Date())  
                .setExpiration(new Date(System.currentTimeMillis() + expiration))  
                .signWith(SignatureAlgorithm.HS512, secret)  
                .compact();  
    }
```

## Registrieren eines Benutzers: 

```java
@PostMapping("/register")  
public ResponseEntity<Object> registerUser(@RequestBody @Valid UserCreateDTO userCreateDTO) {  
    // Create the user  
    UserDTO registeredUser = userService.createUser(userCreateDTO);  
  
    // Generate JWT token  
    String token = jwtUtil.generateToken(registeredUser.getUsername(), registeredUser.getRoles());  
  
    // Set Authorization header  
    HttpHeaders headers = new HttpHeaders();  
    headers.set("Authorization", "Bearer " + token);  
  
    // Prepare response body  
    Map<String, Object> response = new HashMap<>();  
    response.put("user", registeredUser);  
    response.put("message", "Registration and login successful");  
  
    //Just for debugging purposes nibba.  
    System.out.println("Generated JWT Token: Bearer " + token);  
  
  
    return new ResponseEntity<>(response, headers, HttpStatus.CREATED);  
}
```


# Tagesjournale:

Um es einfacher für die Bewertung zu machen führen wir hier unser persönliches Tagesjournal vom **Backend** und vom **Frontend**.

## Zen Zalapski & Yoel Arcos:
Beispiel: 
Datum: 01.01.2025
Zeit: 14:00-14:30
Personen: **Zen, Yoel**
Tätigkeiten: 
- Erste Kickoff Besprechung
- Taktikbesprechung
- Erste Design Erstellungen und Validierungen.
- Use case erstellung
- Workflow Aufzeichnungen.

Nächste Schritte: 
- Projektstruktur erstellen.


## Yoel Arcos:
Datum: 03.01.2025 
Zeit: 12:00-13:00
Tätigkeiten: 
- Vertical slices code implementieren (Grundstruktur-Version von erster Design-Besprechung)

Datum: 11.01.2025 
Zeit: 14:00-15:00 
Tätigkeiten: 
- Webconfig cors Konfiguration für Request-anfragen von aussen zu erlauben
- Tests geschrieben für den Artikel teil.

Datum: 13.01.2025 
Zeit: 14:00-15:00
Tätigkeiten:
- Arbeiten am Frontend (Article View) erstellt
- Style css (article page)
- Fetch methode eingerichtet
- Kommunikation zum Backend erstellt. (Backend calls)

Datum: 20.01.2025 
Zeit: 19:00-21:00
Tätigkeiten:
- User Registrations Logik implementiert
- User Registrations UI (view) erstellt.
- Debugging Fehlersuche.
- Tokengenerierung implementiert.
- CORS Fehler Behebung.

Datum: 21.01.2025 
Zeit: 20:00-21:00
Tätigkeiten:
- Authentication Filter Behebung
- Erstellung der geschützten Endpoints 
- Speichern des Tokens vom Backend im Frontend 
- Erstellung geschützter Pfade im Frontend
- Redirektion von erfolgreichem Login (frontend)
- Redirektion von unauthoriziertem Zugriff (Frontend)

Datum: 25.01.2025 
Zeit: 20:00-21:00
Tätigkeiten:
- Authentication Filter Behebung
- Erstellung der geschützten Endpoints (Pfade im Frontend)
- Speichern des Tokens im Frontend 
- Weitere routes implementiert
- Klassen dokumentation erstellt.


Datum: 29.01.2025 
Zeit: 16:00-19:00
Tätigkeiten:
- Projekt Doku erstellt.


## Zen Zalapski:

Datum: 11.01.2025 
Zeit: 4h
Tätigkeiten:
- Fixed Application Properties to correctly build connection with DB.
- Added a .env file.
- Added JWT. 

Datum: 12.01.2025 
Zeit: 2h
Tätigkeiten:
-  Fixed JWT handling in backend.

Datum: 14.01.2025 
Zeit: 1h
Tätigkeiten:
- Added new User Endpoints.

Datum: 16.01.2025 
Zeit: 3h
Tätigkeiten:
- Added new User Endpoints.

Datum: 18.01.2025 
Zeit: 7h
Tätigkeiten:
- Added last planned User Endpoints.
- Tests worked successfully.
- Needed to debug some endpoint to get wanted result.

Datum: 21.01.2025 
Zeit: 4h
Tätigkeiten:
- Started adding further endpoint for article, tag. 

Datum: 22.01.2025 
Zeit: 3h
Tätigkeiten:
- Finish adding endpoint for article, tag. 

Datum: 23.01.2025 
Zeit: 5h
Tätigkeiten:
- Tested debugged and fixed the new endpoints. 
- Added Endpoint Documentation for Frontend Development.

Datum: 26.01.2025 
Zeit: 7h
Tätigkeiten:
- Started Implementing Frontend.
- Had to add some more User EndPoints to get full functionality.
- Merged Other Branches into Develop.
- Added Pages CreateArticle, viewArticle, editArticle, userManagement and tag creation.
- Also added styles DARK/LIGHT.


Datum: 28.01.2025 
Zeit: 4h
Tätigkeiten:
- Finished the pages on the frontend.
- Needed to add some more function in the backend.
	- New endpoints.
	- New DTO
	- Changed Mapping

Datum: 29.01.2025 
Zeit:  1h
Tätigkeiten:
- Tried to host DB remote.

Datum: 30.01.2025 
Zeit:  4h
Tätigkeiten:
- Added some simple code doc to frontend.
- Added UI test for Nav, Home, MyArticles, Profile.
- ReadME now has a small list of used technologies in frontend.

Datum: 31.01.2025 
Zeit:  2h
Tätigkeiten:
- Wrote Journal.
- Added used Frontend tech to README

## Hilfestellungen:

Während der Entwicklung des Projekts stiessen wir auf verschiedene noch unbekannte Herausforderungen. Um die Arbeit zu beschleunigen und nicht zu viel Zeit mit Debugging oder allgemeiner Fehlersuche zu verbringen (was leider doch der Fall war), holten wir uns in bestimmten Fällen Unterstützung von Chatgpt.

*Auflistung der Hilfestellungen:*
- *Fehlersuche & Debugging:* Beim *JWT-Token-Handling* und der *Rollenverwaltung* sind wir auf einige Probleme gestoßen, die wir mit zusätzlicher Hilfe lösen konnten.
- *CORS-Konfiguration:* Bei der richtigen *WebConfig-Erstellung* für externe Anfragen haben wir uns Anregungen/Tipps und Verbesserungsmöglichkeiten geholt.
- *Security & Safeguards:* Wir haben uns mit verschiedenen *Sicherheitskonzepten und Schutzmaßnahmen* auseinandergesetzt, um auch neue Arten der Sicherheitsimplementation zu erlernen und beurteilen zu können welche uns am besten passt.
- *Frontend-Design:* Einen Grossteil des *CSS-Stylings* habe wir durch Inspirationen ergänzt und übernommen.
- *Spring Boot Security:* Wir haben uns mit *effektiven Sicherheitskonzepten* und Best Practices auseinandergesetzt, um die Anwendung so (realitätsnahe wie möglich) zu gestalten. (Natürlich nur begrenzt, da uns die Zeit davon flog).






