/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package civitas;

/**
 *
 * @author alejandronietoalarcon
 */
public class JugadorEspeculador extends Jugador {
    
    private int factorEspeculador;
    
    protected JugadorEspeculador(Jugador otro) {
        super(otro);
        this.factorEspeculador = 2;
        this.CasasMax = this.CasasMax * this.factorEspeculador;
        this.HotelesMax = this.HotelesMax * this.factorEspeculador;
        this.actualizaPropiedadesPorConversion();
    }

    
    private void actualizaPropiedadesPorConversion(){
        this.getPropiedades().forEach(casilla -> {
            casilla.actualizaPropietarioPorConversion(this);
        });
        
    }
    
    @Override
    public String toString(){
        return super.toString() + "\n ES UN JUGADOR ESPECULADOR";
    }
    
    
    
    
    
    
    
}
