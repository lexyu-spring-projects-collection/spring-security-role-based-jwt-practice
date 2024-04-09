# Diagram
## Register Flow
```mermaid
flowchart TB
    Req[Request]
    SFC[Security Filter Chain]
    AuthC[Authentication Controller]
    AuthS[Authentication Service]
    UR[User Repository]
    U[(User)]
    R[(Role)]
    
    Req -->|1. Coming| SFC
    SFC -->|2. Go through filters| AuthC
    AuthC -->|3. register: Req Body| AuthS
    AuthS -->|4. Check user isPresent| UR
    UR -->|5. | U
    U -->|6. Result |UR
    UR -->|7. | AuthS
    AuthS -->|8. Save new User | UR
    UR -->|9. | U
    U -->|10. Result | UR
    UR -->|11. | AuthS
    AuthS -->|12. | AuthC
    
    AuthS --> R
    R --> AuthS
    
    AuthC --> SFC
```

## Login Flow
```mermaid
flowchart TB
    C[Client]
    SFC[Security Filter Chain]
    AuthC[Authentication Controller]
    AuthS[Authentication Service]
    TS[Token Service]
    UR[User Repository]
    AM[Authentication Manger]
    U[(User)]
    R[(Role)]
    
    C -->|1. Req Coming| SFC
    SFC -->|2-x. Go through filters| AuthC
    AuthC -->|3. login: Req Body| AuthS
    AuthS -->|4. pass username and password| AM
    AM -->|5. create and return auth object| AuthS
    AuthS -->|6. pass auth object| TS
    TS -->|7. create jwt token| AuthS
    AuthS-->|8. find User| UR
    UR-->|9. Query| U
    UR-->|10. Return result| AuthS
    AuthS-->|11. Result|AuthC
    AuthC-->|12. Token,User Info|C
```

## After Login, Use Token Access Resource Flow
```mermaid
flowchart TB
    C[Client]
    SFC[Security Filter Chain]
    ORS[OAuth Resource Server]
    UC[UserController]
    AC[AdminController]
    AM[Authentication Manger]

  
    C -->|1. Req Coming With Bearer Token| AuthC
    AuthC -->|2. Go through filters| SFC
    SFC -->|2.x | AM
    AM -->|Auth| SFC
    SFC -->|3. | ORS
    ORS -->|4. | UC
    ORS -->|4. | AC
    UC -->|5. Response| ORS
    AC -->|5. Response| ORS
    ORS -->|6. Response| AuthC
    AuthC -->|7. Response|C
```

# Ref
- [Spring Security 6 - Login System with Spring Data JPA and JWTs](https://youtu.be/TeBt0Ike_Tk?si=GDhH_V0KQxvkEBMV)