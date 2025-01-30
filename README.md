
## Übersicht

Für das Modul 223 mussten wir eine **multiuserfähige** Applikation entwickeln.

Wir entschieden uns für eine Anwendung, die den **Wissensaustausch** erleichtert. Sie könnte beispielsweise **intern in einer Firma** genutzt werden. Die App soll ähnlich wie Wikipedia funktionieren, jedoch interaktiver sein und selbstverständlich den **gleichzeitigen Zugriff mehrerer Nutzer** ermöglichen.

Dieses Projekt **baute auf dem vorherigen Modul 183 – Applikationssicherheit auf** und erweiterte es um einige neue Themen. Dazu gehörten die **Implementierung des Frontends** sowie die **Absicherung und der Zugriff auf bestimmte Ressourcen** anhand von **User-Rollen und Berechtigungen**. 

Ergo: Wiederholtes aber dazu noch die Implementation eines Frontends dazu. (Nicht nur das austesten der Endpoints mit POSTMAN).

In dieser Dokumentation erläutern wir unser Projekt genauer und geben euch einen **kleinen Einblick** in die umgesetzten Funktionen. Bitte beachtet, dass sich diese Dokumentation **ausschließlich auf das Backend** bezieht. Für weitere Informationen, insbesondere zum Frontend, siehe bitte hier: (_LINK ZUM README.MD vom Frontend falls nicht möglich, dann sein lassen!).

## Benutzte Technologien: 
- Java 21 
- Spring Boot
- Spring Security mit JWT Authentifikation
- Spring DATA JPA 
- MYSQL
- etc.

## Architektur unserer Anwendung

Hier zur Darstellung der Architektur:
https://github.com/HollowWeb/Modul223-Backend/blob/Documentation/Modul-223-Backend/docs/images/Mdl233-Architecture.png

Für neuankömmlige die sich noch nicht so vertraut sind mit der Springboot Architektur haben wir uns die Zeit genommen noch eine detailliertere Anatomie mit simplen Code-Beispielen zu erstellen.

![[Springboot_architecture_explanation.png]]
Das ganze findet ihr auch im Projekt unter 
https://github.com/HollowWeb/Modul223-Backend/blob/Documentation/Modul-223-Backend/docs/images/Springboot_architecture_explanation.png

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

Hier eine detailliertere Modellierung unserer Datenbank Schematas : Modul-223-Backend/Project Specification/ERD_V1.png

## API Endpunkte: 
Hier werden folgende API- Endpunkte aufgelistet die auch aktiv zur Verwendung gekommen sind, bitte entschuldigt das wir nicht alle Endpoints aufgezählt haben, dies würde sonst den Rahmen dieser Dokumentation sprengen.

#### Rollen spezifische Endpoints:
- **URL:** `POST /api/users/register`:  Beschreibung: Registriert einen neuen Benutzer mit der standard Rolle "User"
- **URL:** `POST /api/users/login`: Authentifiziert den Benutzer. 

(Uns ist bewusst das dieses Vorgehen nicht gerade best practice ist. Unser Hauptgedanke war, das registrieren zu optimieren/ schneller zu machen und gleich den JWT Token mit den dazugehörigen claims auszustellen.)

#### Abgesicherte Endpoints:
-  **URL:**  `GET /api/roles`: Hollt alle Rollen. Ist nur für Benutzer mit ADMIN Rolle zugänglich.
-  **URL:**  `POST /api/roles`: Erstellt neue Rollen, was nur ein Admin machen kann.
- **URL:**  `POST /api/users/{id}`: Kann User ihre "Credentials", infos ändern/hinzufügen. Nur für Admins zugänglich. 

## Spring Security Crash Kurs:

Bevor wir folgende Sicherheitsthemen ansprechen, haben wir uns auch hier die Zeit genommen gewisse Sicherheitsthemen wie `Securityfilterchain` usw. anzusprechen und auch bildlich darzustellen, damit jeder sich ein besseres Bild unserer Springboot security machen kann.

Folgendes Bild erklärt einen typischen Ablauf wie z.B das registrieren eines neuen Users und wie alle Layers und gewisse Komponente miteinander agieren, was zu was geschickt wird etc:

Hier noch zur Aufklärung:
https://github.com/HollowWeb/Modul223-Backend/blob/Documentation/Modul-223-Backend/docs/images/SecurityFilters.png

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


**TODO**
- Bilder in projekt einfügen (docs/images)
- Bilder richtig einsetzen
- weitere erwähnenswerte Themen dokumentieren
- Tagesjournal erstellen und einfügen.
