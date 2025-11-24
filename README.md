# Proyecto Final - API REST de Artículos

**Autor:** Paulo Orsini  
**Curso:** BACK END JAVA  
**Comisión:** C25254

## Descripción del Proyecto

Este proyecto es una aplicación full-stack que implementa un sistema CRUD (Crear, Leer, Actualizar, Eliminar) completo para la gestión de artículos. Está compuesto por un backend desarrollado con Spring Boot y un frontend con HTML, CSS y JavaScript vanilla.

## Estructura del Proyecto

### 📁 Backend: `articulo-api-mysql-funcional/`

API REST desarrollada con **Spring Boot 3.2.5** y **Java 17** que proporciona endpoints para la gestión de artículos.

#### Tecnologías Utilizadas:
- **Spring Boot** - Framework principal
- **Spring Data JPA** - Persistencia de datos
- **MySQL** - Base de datos relacional
- **Maven** - Gestor de dependencias

#### Arquitectura:
El backend sigue una arquitectura en capas:

- **Controller** (`ArticuloController.java`): Expone los endpoints REST en `/api/articulos`
  - `GET /api/articulos` - Listar todos los artículos
  - `GET /api/articulos/{id}` - Obtener un artículo por ID
  - `POST /api/articulos` - Crear un nuevo artículo
  - `PUT /api/articulos/{id}` - Actualizar un artículo existente
  - `DELETE /api/articulos/{id}` - Eliminar un artículo

- **Service** (`ArticuloService.java`, `ArticuloServiceImpl.java`): Lógica de negocio

- **Repository** (`ArticuloRepository.java`): Capa de acceso a datos con Spring Data JPA

- **Model** (`Articulo.java`): Entidad JPA que representa un artículo con los siguientes atributos:
  - `id` (Long) - Identificador único
  - `nombre` (String) - Nombre del artículo
  - `marca` (String) - Marca del artículo
  - `talle` (Integer) - Talle del artículo
  - `precio` (Double) - Precio del artículo

#### Configuración:
El archivo `application.properties` contiene la configuración de conexión a MySQL:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/articulos_db
spring.datasource.username=root
spring.datasource.password=
```

### 📁 Frontend: `crud_articulos_frontend/`

Interfaz de usuario desarrollada con tecnologías web estándar que consume la API REST.

#### Tecnologías:
- **HTML5** (`index.html`) - Estructura de la página
- **CSS3** (`css/styles.css`) - Estilos y diseño responsivo
- **JavaScript** (`js/app.js`) - Lógica del cliente y comunicación con la API

#### Funcionalidades:
- Visualización de artículos en una tabla dinámica
- Formulario para crear nuevos artículos
- Edición de artículos existentes
- Eliminación de artículos
- Comunicación asíncrona con la API mediante Fetch API

### 📁 Base de Datos

- **`articulos_db.sql`**: Script SQL para crear la base de datos y la tabla de artículos

- **`API_Articulos_MySQL_Coleccion.postman_collection.json`**: Colección de Postman con ejemplos de peticiones para probar la API

## Requisitos Previos

- Java 17 o superior
- MySQL 8.0 o superior
- Maven 3.6 o superior
- Navegador web moderno

## Instalación y Ejecución

### 1. Base de Datos
```bash
# Importar el script SQL en MySQL
mysql -u root -p < articulos_db.sql
```

### 2. Backend
```bash
# Navegar al directorio del backend
cd articulo-api-mysql-funcional

# Compilar y ejecutar con Maven
mvn spring-boot:run
```

El servidor se iniciará en `http://localhost:8080`

### 3. Frontend
```bash
# Navegar al directorio del frontend
cd crud_articulos_frontend

# Abrir index.html en un navegador
# O usar un servidor HTTP local como Live Server
```

## Características Principales

✅ API RESTful completamente funcional  
✅ Operaciones CRUD completas  
✅ Validación de datos  
✅ Soporte CORS para comunicación frontend-backend  
✅ Interfaz de usuario intuitiva y responsiva  
✅ Manejo de errores en el frontend  
✅ Actualización dinámica sin recargar la página

## Endpoints de la API

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/articulos` | Obtener todos los artículos |
| GET | `/api/articulos/{id}` | Obtener un artículo específico |
| POST | `/api/articulos` | Crear un nuevo artículo |
| PUT | `/api/articulos/{id}` | Actualizar un artículo existente |
| DELETE | `/api/articulos/{id}` | Eliminar un artículo |

## Pruebas

Se incluye una colección de Postman (`API_Articulos_MySQL_Coleccion.postman_collection.json`) para realizar pruebas de los endpoints de la API.

---

*Proyecto desarrollado como trabajo final del curso de Back End Java - TalentoTech*
