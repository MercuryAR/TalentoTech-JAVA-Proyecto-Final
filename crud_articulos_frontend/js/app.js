// URL base de la API
const API_URL = "http://localhost:8080/api/productos";

// Cuando se carga la página
document.addEventListener("DOMContentLoaded", () => {
    listarProductos();
    
    // Evento para cambiar campos según tipo
    document.getElementById("tipo").addEventListener("change", mostrarCamposEspecificos);
    
    // Evento del formulario
    document.getElementById("form-producto").addEventListener("submit", guardarProducto);
    
    // Botón cancelar
    document.getElementById("cancelar").addEventListener("click", limpiarFormulario);
});

// POLIMORFISMO: Mostrar campos específicos según el tipo seleccionado
function mostrarCamposEspecificos() {
    const tipo = document.getElementById("tipo").value;
    const container = document.getElementById("campos-especificos");
    container.innerHTML = "";
    
    if (tipo === "REMERA") {
        container.innerHTML = `
            <div class="mb-3">
                <label for="marca" class="form-label">Marca</label>
                <input type="text" class="form-control" id="marca" required>
            </div>
            <div class="mb-3">
                <label for="talle" class="form-label">Talle</label>
                <input type="number" class="form-control" id="talle" required>
            </div>
            <div class="mb-3">
                <label for="material" class="form-label">Material</label>
                <input type="text" class="form-control" id="material" placeholder="Ej: Algodón, Poliéster">
            </div>
        `;
    } else if (tipo === "ZAPATILLA") {
        container.innerHTML = `
            <div class="mb-3">
                <label for="marca" class="form-label">Marca</label>
                <input type="text" class="form-control" id="marca" required>
            </div>
            <div class="mb-3">
                <label for="numeroCalzado" class="form-label">Número de Calzado</label>
                <input type="number" class="form-control" id="numeroCalzado" required>
            </div>
            <div class="mb-3">
                <label for="tipoDeporte" class="form-label">Tipo de Deporte</label>
                <input type="text" class="form-control" id="tipoDeporte" placeholder="Ej: Running, Fútbol, Basket">
            </div>
        `;
    } else if (tipo === "PELOTA") {
        container.innerHTML = `
            <div class="mb-3">
                <label for="deporte" class="form-label">Deporte</label>
                <input type="text" class="form-control" id="deporte" required placeholder="Ej: Fútbol, Básquet, Rugby">
            </div>
            <div class="mb-3">
                <label for="tamanio" class="form-label">Tamaño</label>
                <input type="text" class="form-control" id="tamanio" required placeholder="Ej: 3, 4, 5">
            </div>
        `;
    }
}

// Listar todos los productos (POLIMORFISMO en acción)
function listarProductos() {
    console.log("Cargando productos desde:", API_URL);
    fetch(API_URL)
        .then(response => {
            console.log("Respuesta recibida:", response.status);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log("Datos recibidos:", data);
            const tbody = document.getElementById("tabla-productos");
            tbody.innerHTML = "";
            
            if (!data || data.length === 0) {
                console.warn("No hay productos en la base de datos");
                tbody.innerHTML = '<tr><td colspan="8" class="text-center">No hay productos</td></tr>';
                return;
            }
            
            data.forEach(producto => {
                const descuento = ((producto.precio - producto.precioFinal) / producto.precio * 100).toFixed(0);
                const tipo = producto.tipo || "Desconocido";
                const badgeClass = tipo === "REMERA" ? "bg-primary" : 
                                   tipo === "ZAPATILLA" ? "bg-success" : "bg-warning";
                
                const fila = document.createElement("tr");
                fila.innerHTML = `
                    <td>${producto.id}</td>
                    <td><span class="badge ${badgeClass}">${tipo}</span></td>
                    <td>${producto.nombre}</td>
                    <td><small class="text-muted">${producto.detalle || ''}</small></td>
                    <td>$${producto.precio.toFixed(2)}</td>
                    <td class="fw-bold text-success">$${producto.precioFinal.toFixed(2)}</td>
                    <td><span class="badge bg-danger">${descuento}%</span></td>
                    <td>
                        <button class="btn btn-warning btn-sm" onclick="editarProducto(${producto.id})">
                            Editar
                        </button>
                        <button class="btn btn-danger btn-sm" onclick="eliminarProducto(${producto.id})">
                            Eliminar
                        </button>
                    </td>
                `;
                tbody.appendChild(fila);
            });
            console.log(`${data.length} productos cargados correctamente`);
        })
        .catch(error => {
            console.error("Error al listar productos:", error);
            alert("Error al cargar los productos: " + error.message);
        });
}

// Guardar producto (POLIMORFISMO: construye objeto según tipo)
function guardarProducto(event) {
    event.preventDefault();
    
    const id = document.getElementById("idProducto").value;
    const tipo = document.getElementById("tipo").value;
    const nombre = document.getElementById("nombre").value;
    const precio = parseFloat(document.getElementById("precio").value);
    
    // Construir objeto según el tipo (POLIMORFISMO)
    let producto = {
        tipo: tipo,  // Campo correcto para el backend
        nombre: nombre,
        precio: precio
    };
    
    if (tipo === "REMERA") {
        producto.marca = document.getElementById("marca").value;
        producto.talle = document.getElementById("talle").value;
        producto.material = document.getElementById("material")?.value || null;
    } else if (tipo === "ZAPATILLA") {
        producto.marca = document.getElementById("marca").value;
        producto.numeroCalzado = parseInt(document.getElementById("numeroCalzado").value);
        producto.tipoDeporte = document.getElementById("tipoDeporte")?.value || null;
    } else if (tipo === "PELOTA") {
        producto.deporte = document.getElementById("deporte").value;
        producto.tamanio = document.getElementById("tamanio").value;
    }
    
    const url = id ? `${API_URL}/${id}` : API_URL;
    const metodo = id ? "PUT" : "POST";
    
    fetch(url, {
        method: metodo,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(producto)
    })
    .then(async response => {
        if (!response.ok) {
            const errorData = await response.json().catch(() => ({}));
            const errorMsg = errorData.error || "Error al guardar el producto";
            throw new Error(errorMsg);
        }
        return response.json();
    })
    .then(() => {
        alert(id ? "Producto actualizado" : "Producto creado");
        limpiarFormulario();
    })
    .catch(error => {
        console.error("Error:", error);
        alert("No se pudo guardar el producto: " + error.message);
    })
    .finally(() => {
        listarProductos();
    });
}

// Editar producto
function editarProducto(id) {
    fetch(`${API_URL}/${id}`)
        .then(response => response.json())
        .then(producto => {
            document.getElementById("idProducto").value = producto.id;
            document.getElementById("nombre").value = producto.nombre;
            document.getElementById("precio").value = producto.precio;
            document.getElementById("tipo").value = producto.tipo.toUpperCase();
            
            mostrarCamposEspecificos();
            
            // Cargar campos específicos
            setTimeout(() => {
                if (producto.tipo === "REMERA") {
                    if (document.getElementById("marca")) 
                        document.getElementById("marca").value = producto.marca || "";
                    if (document.getElementById("talle")) 
                        document.getElementById("talle").value = producto.talle || "";
                    if (document.getElementById("material")) 
                        document.getElementById("material").value = producto.material || "";
                } else if (producto.tipo === "ZAPATILLA") {
                    if (document.getElementById("marca")) 
                        document.getElementById("marca").value = producto.marca || "";
                    if (document.getElementById("numeroCalzado")) 
                        document.getElementById("numeroCalzado").value = producto.numeroCalzado || "";
                    if (document.getElementById("tipoDeporte")) 
                        document.getElementById("tipoDeporte").value = producto.tipoDeporte || "";
                } else if (producto.tipo === "PELOTA") {
                    if (document.getElementById("deporte")) 
                        document.getElementById("deporte").value = producto.deporte || "";
                    if (document.getElementById("tamanio")) 
                        document.getElementById("tamanio").value = producto.tamanio || "";
                }
            }, 100);
        })
        .catch(error => {
            console.error("Error:", error);
            alert("Error al cargar el producto");
        });
}

// Eliminar producto
function eliminarProducto(id) {
    if (!confirm("¿Está seguro de eliminar este producto?")) return;
    
    fetch(`${API_URL}/${id}`, { method: "DELETE" })
        .then(response => {
            if (!response.ok) throw new Error("Error al eliminar");
            alert("Producto eliminado");
            listarProductos();
        })
        .catch(error => {
            console.error("Error:", error);
            alert("No se pudo eliminar el producto");
        });
}

// Limpiar formulario
function limpiarFormulario() {
    document.getElementById("form-producto").reset();
    document.getElementById("idProducto").value = "";
    document.getElementById("campos-especificos").innerHTML = "";
}
