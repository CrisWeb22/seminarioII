
package seminariofinal.logica;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Docente implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int legajoDocente;
    private int dniDocente;
    private String nombreDocente;
    private String apellidoDocente;
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    private String correoDocente;
    @OneToOne
    private CargoDocente cargo;

    
    public Docente() {
    }
    public Docente(int legajoDocente, int dniDocente, String nombreDocente, String apellidoDocente, Date fechaNacimiento, String correoDocente, CargoDocente cargo) {
        this.legajoDocente = legajoDocente;
        this.dniDocente = dniDocente;
        this.nombreDocente = nombreDocente;
        this.apellidoDocente = apellidoDocente;
        this.fechaNacimiento = fechaNacimiento;
        this.correoDocente = correoDocente;
        this.cargo = cargo;
    }

    public CargoDocente getCargo() {
        return cargo;
    }

    public void setCargo(CargoDocente cargo) {
        this.cargo = cargo;
    }
    

    public int getLegajoDocente() {
        return legajoDocente;
    }

    public void setLegajoDocente(int legajoDocente) {
        this.legajoDocente = legajoDocente;
    }

    public int getDniDocente() {
        return dniDocente;
    }

    public void setDniDocente(int dniDocente) {
        this.dniDocente = dniDocente;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getApellidoDocente() {
        return apellidoDocente;
    }

    public void setApellidoDocente(String apellidoDocente) {
        this.apellidoDocente = apellidoDocente;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCorreoDocente() {
        return correoDocente;
    }

    public void setCorreoDocente(String correoDocente) {
        this.correoDocente = correoDocente;
    }

    @Override
    public String toString() {
        return "Docente{" + "legajoDocente=" + legajoDocente + ", dniDocente=" + dniDocente + ", nombreDocente=" + nombreDocente + ", apellidoDocente=" + apellidoDocente + ", fechaNacimiento=" + fechaNacimiento + ", correoDocente=" + correoDocente + '}';
    }
    
    
}
