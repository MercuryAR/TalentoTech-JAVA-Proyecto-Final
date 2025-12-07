# Proyecto Final - API REST de Productos Deportivos (Herencia y Polimorfismo)

**Autor:** Paulo Orsini  
**Curso:** BACK END JAVA  
**Comisión:** C25254

## 🎯 Descripción del Proyecto

Este proyecto es una aplicación **full-stack** que implementa un sistema CRUD completo para la gestión de **productos deportivos** usando:

- ✅ **Herencia y Polimorfismo** en Java
- ✅ **Manejo robusto de excepciones** con `@RestControllerAdvice`
- ✅ **Validaciones** en backend (Bean Validation) y frontend (JavaScript)
- ✅ **Documentación automática** con Swagger/OpenAPI
- ✅ **Tests unitarios** con JUnit 5 y Mockito
- ✅ **Logging** estructurado con SLF4J
- ✅ **Arquitectura RESTful** profesional en capas

## 📁 Estructura del Proyecto

```
proyecto_final_paulo_orsini/
├── articulo-api-mysql-funcional/          # Backend Spring Boot
│   ├── src/main/java/com/ejemplo/articulos/
│   │   ├── ArticuloApiApplication.java
│   │   ├── controller/
│   │   │   └── ProductoController.java    # REST endpoints
│   │   ├── model/
│   │   │   ├── Producto.java              # Clase abstracta (herencia)
│   │   │   ├── Remera.java                # Subclase (10% descuento)
│   │   │   ├── Zapatilla.java             # Subclase (15% descuento)
│   │   │   └── Pelota.java                # Subclase (sin descuento)
│   │   ├── service/
│   │   │   ├── ProductoService.java
│   │   │   └── ProductoServiceImpl.java
│   │   ├── repository/
│   │   │   └── ProductoRepository.java
│   │   ├── exception/                     # Manejo de excepciones
│   │   │   ├── ProductoNotFoundException.java
│   │   │   ├── ValidationException.java
│   │   │   ├── BadRequestException.java
│   │   │   ├── ConflictException.java
│   │   │   ├── InternalServerErrorException.java
│   │   │   └── GlobalExceptionHandler.java
│   │   └── config/
│   │       └── OpenApiConfig.java         # Swagger/OpenAPI
│   ├── src/test/java/                     # Tests unitarios
│   │   └── com/ejemplo/articulos/
│   │       ├── ProductoControllerTest.java
│   │       └── GlobalExceptionHandlerTest.java
│   ├── pom.xml                            # Dependencias Maven
│   └── src/main/resources/
│       └── application.properties
├── crud_articulos_frontend/               # Frontend Web
│   ├── index.html                         # Interfaz de usuario
│   ├── js/app.js                          # Lógica del cliente
│   └── css/styles.css                     # Estilos Bootstrap 5
├── database_herencia.sql                  # Script SQL completo
└── README.md                              # Este archivo
```

## 🏗️ Arquitectura del Backend

### Herencia y Polimorfismo

```
Producto (abstract)
├── Remera (10% descuento)
├── Zapatilla (15% descuento)
└── Pelota (sin descuento)
```

Cada subclase implementa métodos polimórficos:
- `getTipo()`: Devuelve el nombre del tipo
- `calcularPrecioFinal()`: Aplica descuento específico
- `getDetalleEspecifico()`: Formatea información única

**Estrategia de Herencia:** Single Table Inheritance (STI) con discriminator `type_producto`

### Manejo Centralizado de Excepciones

| Excepción | Código HTTP | Descripción |
|-----------|-------------|-------------|
| `ProductoNotFoundException` | 404 | Producto no encontrado |
| `ValidationException` | 400 | Errores de validación con detalle |
| `BadRequestException` | 400 | Petición mal formada |
| `ConflictException` | 409 | Conflictos (duplicados, cambio de tipo) |
| `InternalServerErrorException` | 500 | Errores del servidor |

**Ejemplo de respuesta de error (404):**
```json
{
  "timestamp": "2025-12-05T22:45:30",
  "status": 404,
  "error": "Not Found",
  "message": "Producto con ID 999 no encontrado",
  "path": "/api/productos/999"
}
```

**Ejemplo de respuesta de validación (400):**
```json
{
  "timestamp": "2025-12-05T22:45:30",
  "status": 400,
  "error": "Bad Request",
  "message": "Error de validación",
  "errors": {
    "nombre": "El nombre es obligatorio",
    "precio": "El precio debe ser mayor a 0",
    "talle": "El talle debe estar entre 1 y 60"
  },
  "path": "/api/productos"
}
```

### Validaciones Implementadas

#### Backend (Bean Validation - Jakarta Validation)
```java
// Campos comunes a todos
@NotBlank(message = "El nombre es obligatorio")
@Size(min = 3, max = 100)
private String nombre;

@DecimalMin(value = "0.01", message = "Debe ser mayor a 0")
@DecimalMax(value = "999999.99")
private Double precio;

// Campos específicos
@Remera:
  - marca: @NotBlank, @Size(min = 2, max = 50)
  - talle: @Min(1), @Max(60)
  - material: @Size(max = 100)

@Zapatilla:
  - marca: @NotBlank, @Size(min = 2, max = 50)
  - numeroCalzado: @Min(20), @Max(50)
  - tipoDeporte: @Size(max = 100)

@Pelota:
  - deporte: @NotBlank, @Size(min = 3, max = 50)
  - tamanio: @NotBlank, @Size(min = 1, max = 20)
```

#### Frontend (JavaScript)
- Validación de campos obligatorios
- Validación de rangos numéricos
- Validación de longitud de strings
- Validación de email (si aplica)

### Logging Estructurado

Configuración en `application.properties`:
```properties
# Logging
logging.level.root=INFO
logging.level.com.ejemplo.articulos=DEBUG
```

Ejemplo de logs en consola:
```
2025-12-05 22:45:30.123  INFO --- [ProductoController] : Listando todos los productos
2025-12-05 22:45:31.456  INFO --- [ProductoController] : Producto creado: Remera Nike (ID: 10)
2025-12-05 22:45:32.789  WARN --- [GlobalExceptionHandler] : ProductoNotFoundException: ID 999
2025-12-05 22:45:33.012 ERROR --- [GlobalExceptionHandler] : Error interno del servidor
```

## 🚀 Tecnologías Utilizadas

### Backend
- **Java 17/21** - Lenguaje de programación
- **Spring Boot 3.2.5** - Framework web
  - spring-boot-starter-web (REST API)
  - spring-boot-starter-data-jpa (ORM)
  - spring-boot-starter-validation (Bean Validation)
- **MySQL 8.0** - Base de datos relacional
- **Maven** - Gestor de dependencias
- **SpringDoc OpenAPI 2.3.0** - Swagger UI
- **SLF4J + Logback** - Logging
- **JUnit 5 + Mockito** - Testing

### Frontend
- **HTML5** - Estructura semántica
- **CSS3** - Bootstrap 5 + estilos personalizados
- **JavaScript ES6+** - Fetch API, async/await

## 📋 Requisitos Previos

- ✅ Java 17 o superior
- ✅ MySQL 8.0 o superior
- ✅ Maven 3.6 o superior
- ✅ Navegador web moderno (Chrome, Firefox, Edge)

## ⚙️ Instalación y Ejecución

### 1️⃣ Base de Datos

```bash
# Conectar a MySQL
mysql -u root -p

# Ejecutar el script SQL
mysql> source database_herencia.sql;
```

**Credenciales de conexión:**
```properties
url=jdbc:mysql://localhost:3306/shop
user=root
password=Dantesol.0407
```

### 2️⃣ Backend

```bash
cd articulo-api-mysql-funcional

# Compilar el proyecto
mvn clean compile

# Ejecutar tests
mvn test

# Iniciar la aplicación
mvn spring-boot:run
```

Backend disponible en: `http://localhost:8080`

### 3️⃣ Frontend

```bash
cd crud_articulos_frontend

# Opción 1: Python 3
python3 -m http.server 5500

# Opción 2: Node.js (si tienes installed)
npx http-server -p 5500
```

Abre en el navegador: `http://localhost:5500`

## 📖 Documentación de la API

### Swagger UI (Interfaz Interactiva)

Una vez iniciado el backend, accede a:

```
http://localhost:8080/swagger-ui.html
```

**Funcionalidades:**
- ✅ Ver todos los endpoints disponibles
- ✅ Probar las peticiones directamente en el navegador
- ✅ Ver esquemas de datos (request/response)
- ✅ Descargar especificación OpenAPI (JSON)

### OpenAPI JSON

```
http://localhost:8080/api-docs
```

## 🔌 Endpoints de la API

| Método | Endpoint | Descripción | Código |
|--------|----------|-------------|--------|
| `GET` | `/api/productos` | Listar todos | 200 OK |
| `GET` | `/api/productos/{id}` | Obtener por ID | 200 OK / 404 |
| `POST` | `/api/productos` | Crear producto | 200 OK / 400 / 409 |
| `PUT` | `/api/productos/{id}` | Actualizar | 200 OK / 400 / 404 / 409 |
| `DELETE` | `/api/productos/{id}` | Eliminar | 204 No Content / 404 |

### Ejemplos de Peticiones

#### Crear Remera
```bash
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{
    "tipo": "REMERA",
    "nombre": "Remera Nike Dri-Fit",
    "precio": 15000,
    "marca": "Nike",
    "talle": 42,
    "material": "Poliéster"
  }'
```

#### Crear Zapatilla
```bash
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{
    "tipo": "ZAPATILLA",
    "nombre": "Adidas Ultraboost",
    "precio": 85000,
    "marca": "Adidas",
    "numeroCalzado": 42,
    "tipoDeporte": "Running"
  }'
```

#### Crear Pelota
```bash
curl -X POST http://localhost:8080/api/productos \
  -H "Content-Type: application/json" \
  -d '{
    "tipo": "PELOTA",
    "nombre": "Pelota Wilson NBA",
    "precio": 12000,
    "deporte": "Básquet",
    "tamanio": "7"
  }'
```

## 🧪 Testing

### Ejecutar todos los tests

```bash
cd articulo-api-mysql-funcional
mvn test
```

### Cobertura de Tests

- ✅ **ProductoControllerTest.java** (5 tests)
  - `testListar()` - Verificar listado de productos
  - `testObtenerExistente()` - Obtener producto existente
  - `testObtenerNoExistente()` - Verificar 404
  - `testCalculoPrecioFinal()` - Validar descuentos polimórficos
  - `testGetTipo()` - Verificar discriminador

- ✅ **GlobalExceptionHandlerTest.java** (5 tests)
  - `testHandleProductoNotFound()` - Manejo de 404
  - `testHandleBadRequest()` - Manejo de 400
  - `testHandleConflict()` - Manejo de 409
  - `testHandleInternalServerError()` - Manejo de 500
  - `testHandleMethodArgumentNotValid()` - Validación Bean

## 📊 Características Principales

### Backend ✅
- API RESTful con arquitectura en capas (Controller → Service → Repository)
- Herencia polimórfica con Single Table Inheritance
- Manejo centralizado de excepciones con `@RestControllerAdvice`
- Validaciones con Bean Validation (Jakarta Validation)
- Logging estructurado con SLF4J/Logback
- Documentación automática con Swagger/OpenAPI
- Tests unitarios con JUnit 5 y Mockito
- CORS habilitado para frontend
- Respuestas JSON estructuradas y consistentes

### Frontend ✅
- Interfaz responsive con Bootstrap 5
- Formulario dinámico según tipo de producto
- Validaciones en cliente antes de enviar
- Actualización en tiempo real sin recargar
- Manejo de errores con mensajes claros
- Visualización de precios con descuentos aplicados
- Interfaz intuitiva y fácil de usar

## 🎨 Interfaz de Usuario

### Funcionalidades Principales
1. **Tabla de Productos** - Muestra todos los productos con precio final
2. **Formulario de Creación/Edición** - Dinámico según tipo seleccionado
3. **Botones de Acción** - Editar y eliminar por producto
4. **Validaciones en Cliente** - Feedback inmediato al usuario

### Campos Dinámicos
- **Remera:** marca, talle, material
- **Zapatilla:** marca, numeroCalzado, tipoDeporte
- **Pelota:** deporte, tamanio

## 🔐 Seguridad y Validaciones

### Validación en Capas
1. **Frontend** - Validaciones JavaScript antes de enviar
2. **Backend** - Validaciones con Bean Validation en modelos
3. **Controller** - Validación de lógica de negocio
4. **Exception Handler** - Manejo consistente de errores

### Prevención de Errores
- No se permite cambiar el tipo de un producto existente
- Validación de rangos numéricos
- Validación de campos obligatorios
- Manejo de transacciones de base de datos

## 📝 Estructura de Respuestas

### Respuesta Exitosa (200)
```json
{
  "id": 1,
  "tipo": "REMERA",
  "nombre": "Remera Nike",
  "precio": 15000,
  "precioFinal": 13500,
  "marca": "Nike",
  "talle": 42,
  "material": "Poliéster"
}
```

### Respuesta de Error (400/404/500)
```json
{
  "timestamp": "2025-12-05T22:45:30",
  "status": 404,
  "error": "Not Found",
  "message": "Producto con ID 999 no encontrado",
  "path": "/api/productos/999"
}
```

## 📚 Recursos Adicionales

- [Documentación de Spring Boot](https://spring.io/projects/spring-boot)
- [SpringDoc OpenAPI](https://springdoc.org/)
- [Bean Validation](https://beanvalidation.org/)
- [JUnit 5](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org/)
- [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)

## 🤝 Autor

**Paulo Orsini**  
Curso: BACK END JAVA - C25254  
Plataforma: TalentoTech

---

*Proyecto desarrollado con ❤️ como trabajo final del curso de Back End Java - TalentoTech*
