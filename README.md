📦 ms-donaciones — Microservicio de Donaciones
Microservicio encargado de la gestión de donaciones y centros de acopio para la plataforma Donaton, desarrollado con arquitectura de microservicios.

🚀 Tecnologías utilizadas
TecnologíaVersiónUsoJava17Lenguaje principalSpring Boot3.5.xFramework baseSpring Data JPA3.xPersistencia de datosFactory Method—Patrón de diseño para creación de donacionesSwagger/OpenAPI2.xDocumentación de APIJUnit 5 + Mockito5.xPruebas unitariasMySQL8.xBase de datos

📁 Estructura del proyecto
ms-donaciones/
├── src/
│   ├── main/
│   │   ├── java/com/donaton/demo/
│   │   │   ├── Controller/        # Endpoints REST
│   │   │   ├── DTO/               # Objetos de transferencia de datos
│   │   │   ├── Exception/         # Manejo de excepciones
│   │   │   ├── Factory/           # Patrón Factory Method
│   │   │   │   ├── DonacionFactory.java
│   │   │   │   ├── DonacionFactoryProvider.java
│   │   │   │   ├── DonacionAlimento.java
│   │   │   │   ├── DonacionRopa.java
│   │   │   │   └── DonacionInsumoMedico.java
│   │   │   ├── Model/             # Entidades JPA y enums
│   │   │   ├── Repository/        # Repositorios Spring Data
│   │   │   └── Service/           # Lógica de negocio
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/donaton/demo/
│           ├── ControllerTest/    # Pruebas de controllers
│           ├── FactoryTest/       # Pruebas de factories
│           └── ServiceTest/       # Pruebas de servicios
└── pom.xml

⚙️ Configuración
Variables en application.properties
propertiesserver.port=8082
spring.datasource.url=jdbc:mysql://localhost:3307/db_donaciones
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true

📖 Endpoints disponibles
Donaciones
MétodoEndpointDescripciónAuthPOST/api/donacionesRegistrar nueva donación❌GET/api/donaciones/{id}Obtener donación por ID❌GET/api/donaciones/categoria/{cat}Listar donaciones por categoría❌GET/api/donaciones/estado/{estado}Listar donaciones por estado❌GET/api/donaciones/usuario/{donadorId}Listar donaciones por donador❌
Centros de Acopio
MétodoEndpointDescripciónAuthPOST/api/centrosCrear centro de acopio❌GET/api/centrosListar todos los centros❌GET/api/centros/{id}Obtener centro por ID❌GET/api/centros/region/{region}Listar centros por región❌GET/api/centros/activosListar centros activos❌

🏭 Patrón Factory Method
El microservicio implementa el patrón Factory Method para la creación de donaciones según su categoría:
DonacionFactory (interfaz)
├── DonacionAlimento       → valida unidad: kg o cajas
├── DonacionRopa           → valida unidad: unidades
└── DonacionInsumoMedico   → valida unidad: cajas o unidades

DonacionFactoryProvider
└── getFactory(CategoriaDonacion) → selecciona la fábrica correcta

🧪 Pruebas unitarias
Ejecutar las pruebas con Maven:
bashmvn test
ServiceTest/ — Pruebas de servicios
DonacionServiceImplTest — 10 pruebas

crearDonacion_exitoso ✅
crearDonacion_centroNoExiste_lanzaExcepcion ✅
listarTodas_retornaLista ✅
listarTodas_listaVacia ✅
obtenerPorId_existe ✅
obtenerPorId_noExiste_lanzaExcepcion ✅
listarPorCategoria_retornaFiltrado ✅
listarPorCategoria_sinResultados ✅
listarPorEstado_retornaFiltrado ✅
listarPorDonador_retornaFiltrado ✅

CentroAcopioServiceImplTest — 8 pruebas

crear_exitoso ✅
listar_retornaLista ✅
listar_listaVacia ✅
obtenerPorId_existe ✅
obtenerPorId_noExiste_lanzaExcepcion ✅
listarPorRegion_conResultados ✅
listarPorRegion_sinResultados ✅
listarActivo_retornaSoloActivos ✅

FactoryTest/ — Pruebas de factories
Pendiente de implementación 🔧

📄 Documentación Swagger
http://localhost:8082/swagger-ui/index.html

▶️ Cómo ejecutar el proyecto
bash# Clonar el repositorio
git clone https://github.com/tu-usuario/ms-donaciones.git

# Entrar al directorio
cd ms-donaciones

# Ejecutar con Maven
mvn spring-boot:run

👩‍💻 Autora
Proyecto académico — Arquitectura de Microservicios
