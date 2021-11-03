/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package civitas;

import java.util.ArrayList;

/**
 *
 * @author alejandronietoalarcon
 */
public class Sorpresa {
    
    private String texto;
    private int valor;
    private TipoSorpresa tipo;
       
    
    Sorpresa(TipoSorpresa tipo, String texto, int valor){
        this.tipo = tipo;
        this.texto = texto;
        this.valor = valor;
    }
    
    void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(this.tipo == TipoSorpresa.PAGARCOBRAR){
            aplicarAJugador_pagarCobrar(actual, todos);
        } else {
            aplicarAJugador_porCasaHotel(actual, todos);
        }
    }
    
    private void aplicarAJugador_pagarCobrar(int actual, ArrayList<Jugador> todos){
        this.informe(actual, todos);
        todos.get(actual).modificarSaldo(this.valor);
    }
    
    private void aplicarAJugador_porCasaHotel(int actual, ArrayList<Jugador> todos){
        this.informe(actual, todos);
        todos.get(actual).modificarSaldo(this.valor * todos.get(actual).cantidadCasasHoteles());
    }
    
    private void informe(int actual, ArrayList<Jugador> todos){
        Diario.getInstance().ocurreEvento("Se aplica la sorpresa: " + tipo + " al jugador " + todos.get(actual).getNombre());
    }
    
    @Override
    public String toString(){
        return this.texto;
    }
    
}
