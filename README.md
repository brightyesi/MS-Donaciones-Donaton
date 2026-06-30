# 📦 ms-donaciones — Microservicio de Donaciones

Microservicio encargado de la gestión de donaciones y centros de acopio para la plataforma **Donaton**, desarrollado con arquitectura de microservicios.

---

## 🚀 Tecnologías utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 17 | Lenguaje principal |
| Spring Boot | 3.5.x | Framework base |
| Spring Data JPA | 3.x | Persistencia de datos |
| Factory Method | — | Patrón de diseño para creación de donaciones |
| Swagger/OpenAPI | 2.x | Documentación de API |
| JUnit 5 + Mockito | 5.x | Pruebas unitarias |
| MySQL | 8.x | Base de datos |

---

## 📁 Estructura del proyecto

```
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
```

---

## ⚙️ Configuración

**Variables en `application.properties`**

```properties
server.port=8082
spring.datasource.url=jdbc:mysql://localhost:3307/db_donaciones
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.properties.hibernate.format_sql=true
```

---

## 📖 Endpoints disponibles

### Donaciones

| Método | Endpoint | Descripción | Auth |
|---|---|---|---|
| `POST` | `/api/donaciones` | Registrar nueva donación | ❌ |
| `GET` | `/api/donaciones/{id}` | Obtener donación por ID | ❌ |
| `GET` | `/api/donaciones/categoria/{cat}` | Listar donaciones por categoría | ❌ |
| `GET` | `/api/donaciones/estado/{estado}` | Listar donaciones por estado | ❌ |
| `GET` | `/api/donaciones/usuario/{donadorId}` | Listar donaciones por donador | ❌ |

### Centros de Acopio

| Método | Endpoint | Descripción | Auth |
|---|---|---|---|
| `POST` | `/api/centros` | Crear centro de acopio | ❌ |
| `GET` | `/api/centros` | Listar todos los centros | ❌ |
| `GET` | `/api/centros/{id}` | Obtener centro por ID | ❌ |
| `GET` | `/api/centros/region/{region}` | Listar centros por región | ❌ |
| `GET` | `/api/centros/activos` | Listar centros activos | ❌ |

---

## 🏭 Patrón Factory Method

El microservicio implementa el patrón **Factory Method** para la creación de donaciones según su categoría:

```
DonacionFactory (interfaz)
├── DonacionAlimento       → valida unidad: kg o cajas
├── DonacionRopa           → valida unidad: unidades
└── DonacionInsumoMedico   → valida unidad: cajas o unidades

DonacionFactoryProvider
└── getFactory(CategoriaDonacion) → selecciona la fábrica correcta
```

---

## 🧪 Pruebas unitarias

Ejecutar las pruebas con Maven:

```bash
mvn test
```

### `ServiceTest/` — Pruebas de servicios

**`DonacionServiceImplTest`** — 10 pruebas

| Test | Descripción |
|---|---|
| `crearDonacion_exitoso` | Verifica que se guarda correctamente y el DTO retornado contiene los campos esperados ✅ |
| `crearDonacion_centroNoExiste_lanzaExcepcion` | Verifica que lanza `DonacionNotFoundException` cuando el centro no existe ✅ |
| `listarTodas_retornaLista` | Verifica que retorna todos los elementos mapeados a DTO ✅ |
| `listarTodas_listaVacia` | Verifica que retorna lista vacía sin errores ✅ |
| `obtenerPorId_existe` | Verifica que retorna el DTO correcto cuando el ID existe ✅ |
| `obtenerPorId_noExiste_lanzaExcepcion` | Verifica que lanza `DonacionNotFoundException` con el ID en el mensaje ✅ |
| `listarPorCategoria_retornaFiltrado` | Verifica el filtro por categoría ✅ |
| `listarPorCategoria_sinResultados` | Verifica retorno vacío si no hay coincidencias ✅ |
| `listarPorEstado_retornaFiltrado` | Verifica el filtro por estado ✅ |
| `listarPorDonador_retornaFiltrado` | Verifica el filtro por donador ✅ |

**`CentroAcopioServiceImplTest`** — 8 pruebas

| Test | Descripción |
|---|---|
| `crear_exitoso` | Verifica que se guarda correctamente y el DTO retornado contiene los campos esperados ✅ |
| `listar_retornaLista` | Verifica que retorna todos los centros mapeados a DTO ✅ |
| `listar_listaVacia` | Verifica que retorna lista vacía sin errores ✅ |
| `obtenerPorId_existe` | Verifica que retorna el DTO correcto cuando el ID existe ✅ |
| `obtenerPorId_noExiste_lanzaExcepcion` | Verifica que lanza `DonacionNotFoundException` con el ID en el mensaje ✅ |
| `listarPorRegion_conResultados` | Verifica el filtro por región ✅ |
| `listarPorRegion_sinResultados` | Verifica retorno vacío si no hay centros en esa región ✅ |
| `listarActivo_retornaSoloActivos` | Verifica que solo retorna centros con `activo = true` ✅ |

### `FactoryTest/` — Pruebas de factories

> Pendiente de implementación 🔧

---

## 📄 Documentación Swagger

Una vez levantado el servicio, accede a la documentación interactiva en:

```
http://localhost:8082/swagger-ui/index.html
```

---

## ▶️ Cómo ejecutar el proyecto

```bash
# Clonar el repositorio
git clone https://github.com/tu-usuario/ms-donaciones.git

# Entrar al directorio
cd ms-donaciones

# Ejecutar con Maven
mvn spring-boot:run
```

---

## 👩‍💻 Autora

Proyecto académico — Arquitectura de Microservicios
