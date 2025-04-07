# Website Generator API
memory site generator for couples

## Project architecture
```
com.webgenerator.app
├── config                 <- Configurações gerais (ModelMapper, Swagger, Security etc)
│   └── ModelMapperConfig.java
│
├── api                   <- Tudo que é exposto pro mundo externo
│   ├── controller        <- Controllers REST
│   ├── dto               <- DTOs de entrada/saída
│   └── mapper            <- Conversores entre entidade e DTO (ex: com MapStruct ou manual)
│
├── domain                <- Regras e modelos do negócio
│   ├── model             <- Entidades JPA (User, Website, etc.)
│   └── service           <- Interfaces de serviços (ex: UserService)
│
├── infrastructure        <- Implementações técnicas
│   ├── repository        <- Repositórios JPA (UserRepository etc.)
│   └── service           <- Implementações de serviços (ex: UserServiceImpl)
│
└── AppApplication.java   <- Classe principal
```
[Link to api documentation](http://youtube.com.br)