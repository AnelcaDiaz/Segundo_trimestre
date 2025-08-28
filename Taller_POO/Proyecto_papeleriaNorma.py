#
from datetime import date

# Clase base Persona para herencia
class Persona:
    #-self:Se usa dentro de clases para acceder a atributos y métodos del objeto que se está creando o manipulando.
#- Es un método especial que se ejecuta automáticamente al crear una instancia de una clase.
#Se usa para inicializar atributos.

    def __init__(self, nombre, apellido):
        self.nombre = nombre
        self.apellido = apellido
    
    def mostrar_datos(self):
        return f"Nombre: {self.nombre} Apellido: {self.apellido}" #Es la forma en que una función devuelve un resultado.


# Clase Cliente hereda de Persona
class Cliente(Persona):
    def __init__(self, id_cliente, nombre, apellido, correo_electronico):
        super().__init__(nombre, apellido)#Llama métodos de la clase padre sin tener que nombrarla directamente.
        self.id_cliente = id_cliente
        self.correo_electronico = correo_electronico
        self.ventas = []
    
    def consultarVenta(self):
        return [venta.idVenta for venta in self.ventas]

# Clase Vendedor hereda de Persona
class Vendedor(Persona):
    def __init__(self, idVendedor, nombre, apellido, telefono):
        super().__init__(nombre, apellido)
        self.idVendedor = idVendedor
        self.telefono = telefono
    
    def registrar_venta(self, venta):
        return f"Venta registrada con ID {venta.idVenta}"

# Clase Producto
class Producto:
    def __init__(self, idProducto, descripcion, precio, marca):
        self.idProducto = idProducto
        self.descripcion = descripcion
        self.precio = precio
        self.marca = marca
    
    def mostrar_informacion(self):
        return f"{self.marca}: {self.descripcion} (${self.precio})"

# Clase DetalleVenta
class DetalleVenta:
    def __init__(self, iddetalleVenta, cantidad, producto):
        self.iddetalleVenta = iddetalleVenta
        self.cantidad = cantidad
        self.producto = producto
    
    def calcular_subtotal(self):
        return self.cantidad * self.producto.precio

# Clase Venta
class Venta:
    def __init__(self, idVenta, vendedor, cliente):
        self.idVenta = idVenta
        self.costo_venta = 0.0    #ESTE OBJETO GUARDA UN COSTO DE VENTA QUE INICIA EN 0.0
        self.fecha_venta = date.today()
        self.vendedor = vendedor
        self.cliente = cliente
        self.detalles = []
    
    def agregar_producto(self, detalle):
        self.detalles.append(detalle)#agrega un objeto DetalleVenta a la lista detalles
    
    def calcular_total(self):
        self.costo_venta = sum(detalle.calcular_subtotal() for detalle in self.detalles)
        return self.costo_venta
    
    def mostrar_detalle(self):
        return [detalle.producto.mostrar_informacion() + f" x{detalle.cantidad}" for detalle in self.detalles]

# Polimorfismo: método mostrar_datos en Persona/Cliente/Vendedor
#Es la capacidad de diferentes clases de responder al mismo método de forma distinta

personas = [Cliente(1, "Juan", "Perez", "juan@mail.com"), Vendedor(2, "Ana", "Lopez", "555-1234")]
outputs = [p.mostrar_datos() for p in personas]

# Ejemplo de uso
taza = Producto(1, "Taza Cerámica", 50.0, "Norma")
cuaderno = Producto(2, "Cuaderno A4", 30.0, "Norma")
detalle1 = DetalleVenta(1, 2, taza)
detalle2 = DetalleVenta(2, 3, cuaderno)
# Crear vendedor y cliente
vendedor = Vendedor(1, "Carlos", "Gutierrez", "123456789")
cliente = Cliente(2, "Maria", "Rodriguez", "maria@mail.com")
# Crear y configurar venta
venta = Venta(1, vendedor, cliente)
venta.agregar_producto(detalle1)
venta.agregar_producto(detalle2)
total = venta.calcular_total()
detalles_venta = venta.mostrar_detalle()
salida = {
    "Polimorfismo Persona": outputs,
    "Detalle Venta": detalles_venta,
    "Total Venta": total,
    "Venta registrada": vendedor.registrar_venta(venta)
}
salida['Consultar venta cliente'] = cliente.consultarVenta()
salida['Informacion producto'] = taza.mostrar_informacion()
print(salida)