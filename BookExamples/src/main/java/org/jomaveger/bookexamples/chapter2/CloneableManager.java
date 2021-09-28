package org.jomaveger.bookexamples.chapter2;

import java.util.Objects;

public class CloneableManager extends CloneableEmployee implements Cloneable {

    private Double incentivo;

    public CloneableManager(final String nombre, final Double salario, final Integer anno, final Integer mes, final Integer dia) {
        super(nombre, salario, anno, mes, dia);
        this.incentivo = 0.0;
    }

    public Double getIncentivo() {
        return this.incentivo;
    }

    public void setIncentivo(Double incentivo) {
        this.incentivo = incentivo;
    }

    @Override
    public Double getSueldo() {
        Double sueldoBase = super.getSueldo();
        return sueldoBase + this.incentivo;
    }

    @Override
    public boolean equals(Object otherObject) {
        if (!super.equals(otherObject)) return false;

        CloneableManager other = (CloneableManager) otherObject;

        return Objects.equals(this.incentivo, other.incentivo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), this.incentivo);
    }

    @Override
    public String toString() {
        return super.toString() + "[incentivo=" + this.incentivo + ']';
    }

    @Override
    public CloneableManager clone() throws CloneNotSupportedException {
        return (CloneableManager) super.clone();
    }
}
