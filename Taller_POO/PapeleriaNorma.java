import java.util.ArrayList;
import java.util.Date;
import java.util.List;
//List<T>: Estructura de datos de Java (interfaz) que representa una lista de elementos.
//ArrayList<T>: Implementación de List que guarda datos de forma dinámica
// Clase base Persona para herencia
class Persona {
    private  String nombre;    //public: Modificador de acceso → significa que algo es visible desde cualquier parte del programa.
    private String apellido;
    
    public Persona(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }
    
    public String mostrarDatos() {
        return "Nombre: " + nombre + " Apellido: " + apellido;
    }
}
//extends: Se usa para heredar de otra clase
// Cliente hereda de Persona
class Cliente extends Persona {
    private int idCliente;       //private:solo accesible dentro de la misma clase.
    private String correoElectronico;
    private List<Venta> ventas;
    
    public Cliente(int idCliente, String nombre, String apellido, String correoElectronico) {
        super(nombre, apellido);
        this.idCliente = idCliente; //this: referencia al objeto actual. Sirve para diferenciar entre variables locales y atributos de la clase.
        this.correoElectronico = correoElectronico;
        this.ventas = new ArrayList<>();
    }
    
    public void agregarVenta(Venta venta) {
        ventas.add(venta);
    }
    
    public List<Venta> consultarVenta() {
        return ventas; //return: Devuelve un valor desde un método.
    }
    
    // Polimorfismo: sobreescribir mostrarDatos
    @Override  // Anotación que indica que estás sobrescribiendo (reescribiendo) un método heredado de una superclase.
    public String mostrarDatos() {
        return super.mostrarDatos() + " (Cliente)";
    }
}

// Vendedor hereda de Persona
class Vendedor extends Persona {
    private int idVendedor;
    private String telefono;
    
    public Vendedor(int idVendedor, String nombre, String apellido, String telefono) {
        super(nombre, apellido);
        this.idVendedor = idVendedor;
        this.telefono = telefono;
    }
    
    public String registrarVenta(Venta venta) {
        return "Venta registrada con ID " + venta.getIdVenta();
    }
    
    @Override
    public String mostrarDatos() {
        return super.mostrarDatos() + " (Vendedor)";
    }
}

// Producto
class Producto {
    private int idProducto;
    private String descripcion;
    private double precio;
    private String marca;
    
    public Producto(int idProducto, String descripcion, double precio, String marca) {
        this.idProducto = idProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.marca = marca;
    }
    
    public String mostrarInformacion() {
        return marca + ": " + descripcion + " ($" + precio + ")";
    }
    
    public double getPrecio() {
        return precio;
    }
}

// DetalleVenta
class DetalleVenta {
    private int idDetalleVenta;
    private int cantidad;
    private Producto producto;
    
    public DetalleVenta(int idDetalleVenta, int cantidad, Producto producto) {
        this.idDetalleVenta = idDetalleVenta;
        this.cantidad = cantidad;
        this.producto = producto;
    }
    
    public double calcularSubtotal() {
        return cantidad * producto.getPrecio();
    }
    
    public String mostrarDetalle() {
        return producto.mostrarInformacion() + " x" + cantidad;
    }
}

// Venta
class Venta {
    private int idVenta;
    private double costoVenta;
    private Date fechaVenta;
    private Vendedor vendedor;
    private Cliente cliente;
    private List<DetalleVenta> detalles;
    
    public Venta(int idVenta, Vendedor vendedor, Cliente cliente) {
        this.idVenta = idVenta;
        this.costoVenta = 0.0;
        this.fechaVenta = new Date();//new: Crea un nuevo objeto en memoria.
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.detalles = new ArrayList<>();
    }
    //void: Tipo de retorno que significa "no devuelve nada".
    public void agregarProducto(DetalleVenta detalle) {
        detalles.add(detalle);
    }
    
    public double calcularTotal() {
        costoVenta = 0.0;
        for (DetalleVenta d : detalles) {
            costoVenta += d.calcularSubtotal();
        }
        return costoVenta;
    }
    //Recorre cada detalle de la venta y acumula el total.
    public List<String> mostrarDetalle() {
        List<String> detallesStr = new ArrayList<>();
        for (DetalleVenta d : detalles) {
            detallesStr.add(d.mostrarDetalle());
        }
        return detallesStr;
    }
    
    public int getIdVenta() {
        return idVenta;
    }
}

public class PapeleriaNorma {
    public static void main(String[] args) {
        // aquí se usa polimorfismo: un Cliente y un Vendedor son tratados como Persona
        Persona cliente = new Cliente(1, "Juan", "Perez", "juan@mail.com");
        Persona vendedor = new Vendedor(2, "Ana", "Lopez", "555-1234");
        System.out.println(cliente.mostrarDatos());
        System.out.println(vendedor.mostrarDatos());
        
        // Creación de productos
        Producto taza = new Producto(1, "Taza Cerámica", 50.0, "Norma");
        Producto cuaderno = new Producto(2, "Cuaderno A4", 30.0, "Norma");
        
        // Detalles de venta
        DetalleVenta detalle1 = new DetalleVenta(1, 2, taza);
        DetalleVenta detalle2 = new DetalleVenta(2, 3, cuaderno);
        
        // Creador de venta
        Vendedor vendedorReal = new Vendedor(3, "Carlos", "Gutierrez", "123456789");
        Cliente clienteReal = new Cliente(4, "Maria", "Rodriguez", "maria@mail.com");
        Venta venta = new Venta(1, vendedorReal, clienteReal);
        
        venta.agregarProducto(detalle1);
        venta.agregarProducto(detalle2);
        
        double total = venta.calcularTotal();
        List<String> detallesVenta = venta.mostrarDetalle();
        
        System.out.println("Detalle de la venta:");
        for (String detalle : detallesVenta) {
            System.out.println(detalle);
        }
        
        System.out.println("Total venta: $" + total);
        System.out.println(vendedorReal.registrarVenta(venta));
        
        List<Venta> ventasCliente = clienteReal.consultarVenta();
        System.out.println("Ventas del cliente: " + ventasCliente.size()); // Sin registrar ventas aún
        System.out.println("Información producto: " + taza.mostrarInformacion());
    }
}