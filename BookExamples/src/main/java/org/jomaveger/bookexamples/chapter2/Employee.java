package org.jomaveger.bookexamples.chapter2;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import org.jomaveger.lang.DeepCloneable;


public class Employee implements DeepCloneable {
	private final String nombre;
	private Double sueldo;
	private final Date fechaContrato;
	public Employee(final  String nombre, Double sueldo, final Integer anno, final Integer mes, final Integer dia)
	{
		this.nombre = nombre;
		this.sueldo = sueldo;
		GregorianCalendar calendario = new GregorianCalendar (anno, mes -1, dia);
		this.fechaContrato = calendario.getTime();
	}
	// Constructor para el método factoria de copia
//	public Employee(Employee employee) {
//		this.nombre = employee.nombre;
//		this.sueldo = employee.sueldo;
//		this.fechaContrato = new Date(employee.fechaContrato.getTime());
//	}
	// Metodo de factoria de copia
//	public static Employee newInstance(Employee employee) {
//		return new Employee(employee);
//	}
	public String getNombre() {
		return this.nombre;
	}
	public Double getSueldo() {
		return this.sueldo;
	}
	public Date getFechaContrato() {
		return new Date(this.fechaContrato.getTime());
	}
	// Este método es nuevo
	public void subirSueldo(Double porcentaje) {
		Double aumento = this.sueldo * porcentaje / 100;
		this.sueldo += aumento;
	}
	@Override
	public boolean equals(Object otherObject) {
		if (this == otherObject) return true;
		if (otherObject == null) return false;
		if (this.getClass() != otherObject.getClass()) return false;
		Employee other = (Employee) otherObject;
		return Objects.equals(this.nombre, other.nombre) &&
				Objects.equals(this.sueldo, other.sueldo) &&
				Objects.equals(this.fechaContrato, other.fechaContrato);
	}
	@Override
	public int hashCode() {
		return Objects.hash(this.nombre, this.sueldo, this.fechaContrato);
	}
	@Override
	public String toString() {
		return getClass().getName() + "[nombre=" + this.nombre
				+ ", sueldo=" + this.sueldo
				+ ", fechaContrato=" + this.fechaContrato + "]";
	}
	// DeepCopy
	
	public Employee deepCopy() throws Exception {
		return DeepCloneable.deepCopy(this);
	}
}