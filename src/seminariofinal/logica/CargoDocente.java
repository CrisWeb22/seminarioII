
package seminariofinal.logica;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class CargoDocente implements Serializable {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int idCargoDocente;
    private int CargaHoraria;
    @ManyToOne
    private Instituto insti;
    
    public CargoDocente() {
    }

    public CargoDocente(int idCargoDocente, int CargaHoraria, Instituto insti) {
        this.idCargoDocente = idCargoDocente;
        this.CargaHoraria = CargaHoraria;
        this.insti = insti;
    }

    public Instituto getInsti() {
        return insti;
    }

    public void setInsti(Instituto insti) {
        this.insti = insti;
    }

    public int getIdCargoDocente() {
        return idCargoDocente;
    }

    public void setIdCargoDocente(int idCargoDocente) {
        this.idCargoDocente = idCargoDocente;
    }

    public int getCargaHoraria() {
        return CargaHoraria;
    }

    public void setCargaHoraria(int CargaHoraria) {
        this.CargaHoraria = CargaHoraria;
    }
    
    
}
