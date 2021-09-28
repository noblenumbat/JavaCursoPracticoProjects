package org.jomaveger.bookexamples.chapter2;

import java.io.IOException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

import org.jomaveger.lang.DeepCloneable;

public class EmployeeTryCatch implements DeepCloneable, Comparable<EmployeeTryCatch> {

    private final String nombre;
    private Double sueldo;
    private final Date fechaContrato;

    public EmployeeTryCatch(final String nombre, final Double salario, final Integer anno, final Integer mes, final Integer dia) {
        this.nombre = nombre;
        this.sueldo = salario;
        GregorianCalendar calendario = new GregorianCalendar(anno, mes - 1, dia);
        this.fechaContrato = calendario.getTime();
    }

    public EmployeeTryCatch(EmployeeTryCatch employee) {
        this.nombre = employee.nombre;
        this.sueldo = employee.sueldo;
        this.fechaContrato = new Date(employee.fechaContrato.getTime());
    }

    public static EmployeeTryCatch newInstance(EmployeeTryCatch employee) {
        return new EmployeeTryCatch(employee);
    }

    public String getNombre() {
        return this.nombre;
    }

    public Double getSueldo() {
        return this.sueldo;
    }

    public Date getFechaContrato() {
        return new Date(this.fechaContrato.getTime());
    }

    public void subirSueldo(Double porcentaje) {
        Double aumento = this.sueldo * porcentaje / 100;
        this.sueldo += aumento;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;

        if (otherObject == null) return false;

        if (this.getClass() != otherObject.getClass()) return false;

        EmployeeTryCatch other = (EmployeeTryCatch) otherObject;

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
        return getClass().getName() + "[nombre=" + this.nombre + ", sueldo=" + this.sueldo + ", fechaContrato=" + this.fechaContrato + "]";
    }

    public EmployeeTryCatch deepCopy() {
        try {
			return DeepCloneable.deepCopy(this);
		} catch (ClassNotFoundException | IOException ex) {
			return null;
		} 
    }

    @Override
    public final int compareTo(EmployeeTryCatch o) {
        return sueldo.compareTo(o.sueldo);
    }
}
