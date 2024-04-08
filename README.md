# Diagram
```mermaid
flowchart LR
    Req[Request]
    SFC[Security Filter Chain]
    ORS[OAuth Resource Server]
    UC[UserController]
    AC[AdminController]
    AuthC[Authentication Controller]
    AuthS[Authentication Service]
    TS[Token Service]
    US[User Service]
    AM[Authentication Manger]
    U[(User)]
    R[(Role)]
    
    Req --> SFC
    
    SFC --> ORS
    SFC --> AuthC
    AuthC --> SFC
    SFC --> AuthC
    AuthC --> SFC
    
    AuthC --> AuthS
    AuthS --> AuthC

    AuthS --> TS 
    TS --> AuthS
    
    AuthS --> US
    US --> AuthS
    
    AuthS --> AM
    AM --> AuthS
    
    US --> AM
    AM --> US
    
    US --> U
    U --> US
    
    AuthS --> R
    R --> AuthS
    
    ORS --> UC
    UC --> ORS
    ORS --> AC
    AC --> ORS
```

# Ref

- [Spring Security 6 - Login System with Spring Data JPA and JWTs](https://youtu.be/TeBt0Ike_Tk?si=GDhH_V0KQxvkEBMV)