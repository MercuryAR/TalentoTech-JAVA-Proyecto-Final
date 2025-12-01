# Proyecto Final - API REST de Productos Deportivos (Herencia y Polimorfismo)

**Autor:** Paulo Orsini  
**Curso:** BACK END JAVA  
**Comisión:** C25254

## Descripción del Proyecto

Este proyecto es una aplicación full-stack que implementa un sistema CRUD (Crear, Leer, Actualizar, Eliminar) para la gestión de **productos deportivos** usando conceptos de **herencia** y **polimorfismo** en Java. Incluye:
- Backend con Spring Boot y MySQL
- Frontend con HTML, CSS y JavaScript vanilla
- Arquitectura orientada a objetos con jerarquía de productos

## Estructura del Proyecto

### 📁 Backend: `articulo-api-mysql-funcional/`

API REST desarrollada con **Spring Boot 3.2.5** y **Java 17/21** que gestiona productos deportivos polimórficos.

#### Tecnologías Utilizadas:
- **Spring Boot** - Framework principal
- **Spring Data JPA** - Persistencia de datos
- **MySQL** - Base de datos relacional
- **Maven** - Gestor de dependencias

#### Arquitectura y Polimorfismo:
El backend implementa una jerarquía de productos usando herencia:

- **Producto** (`Producto.java`): Clase abstracta base
- **Remera** (`Remera.java`): Subclase (10% descuento)
- **Zapatilla** (`Zapatilla.java`): Subclase (15% descuento)
- **Pelota** (`Pelota.java`): Subclase (sin descuento)

Cada tipo tiene atributos y lógica de precio final propios. El controlador (`ProductoController.java`) expone los endpoints REST en `/api/productos`:
- `GET /api/productos` - Listar todos los productos
- `GET /api/productos/{id}` - Obtener un producto por ID
- `POST /api/productos` - Crear un nuevo producto (enviar campo `tipo`)
- `PUT /api/productos/{id}` - Actualizar un producto
- `DELETE /api/productos/{id}` - Eliminar un producto

**Ejemplo de JSON para crear una Remera:**
```json
{
  "tipo": "REMERA",
  "nombre": "Remera Nike Dri-Fit",
  "precio": 15000,
  "marca": "Nike",
  "talle": 42,
  "material": "Poliéster"
}
```

#### Configuración:
El archivo `application.properties` contiene la configuración de conexión a MySQL:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/shop?allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=root
spring.datasource.password=Dantesol.0407
```

### 📁 Frontend: `crud_articulos_frontend/`

Interfaz de usuario web que consume la API REST y permite gestionar productos deportivos polimórficos.

#### Tecnologías:
- **HTML5** (`index.html`) - Estructura de la página
- **CSS3** (`css/styles.css`) - Estilos y diseño responsivo
- **JavaScript** (`js/app.js`) - Lógica del cliente y comunicación con la API

#### Funcionalidades:
- Visualización de productos en una tabla dinámica
- Formulario polimórfico: los campos cambian según el tipo de producto
- Edición y eliminación de productos
- Cálculo automático de precio final según el tipo
- Comunicación asíncrona con la API mediante Fetch API

### 📁 Base de Datos

- **`articulos_db.sql`**: Script SQL original (ver `database_herencia.sql` para la versión polimórfica)
- **`database_herencia.sql`**: Script SQL para la estructura polimórfica y migración de datos
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
mysql -u root -p < database_herencia.sql
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

# Iniciar un servidor HTTP local
python3 -m http.server 5500
```

Luego abre `http://localhost:5500` en tu navegador.

## Características Principales

✅ API RESTful polimórfica (herencia y discriminador de tipo)  
✅ Operaciones CRUD completas  
✅ Validación de datos y manejo de errores  
✅ Soporte CORS para comunicación frontend-backend  
✅ Interfaz de usuario intuitiva y responsiva  
✅ Formulario dinámico según el tipo de producto  
✅ Actualización dinámica sin recargar la página

## Endpoints de la API

| Método | Endpoint | Descripción |
|--------|---------------------|-------------------------------|
| GET    | `/api/productos`    | Obtener todos los productos    |
| GET    | `/api/productos/{id}` | Obtener un producto específico |
| POST   | `/api/productos`    | Crear un nuevo producto        |
| PUT    | `/api/productos/{id}` | Actualizar un producto         |
| DELETE | `/api/productos/{id}` | Eliminar un producto           |

## Pruebas

Se incluye una colección de Postman (`API_Articulos_MySQL_Coleccion.postman_collection.json`) para realizar pruebas de los endpoints de la API.

---

*Proyecto desarrollado como trabajo final del curso de Back End Java - TalentoTech*
