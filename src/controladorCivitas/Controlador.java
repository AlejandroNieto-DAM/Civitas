/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladorCivitas;

import GUI.CivitasView;
import civitas.CivitasJuego;
import civitas.Diario;
import civitas.GestionInmobiliaria;
import civitas.OperacionInmobiliaria;
import civitas.OperacionJuego;
import vistaTextualCivitas.VistaTextual;

/**
 *
 * @author alejandronietoalarcon
 */
public class Controlador {
    
    private CivitasJuego juego;
    private CivitasView vista;
    
    public Controlador(CivitasJuego juego, CivitasView vista){
        this.juego = juego;
        this.vista = vista;
    }
    
    public void juega(){
        boolean finalJuego = false;
        int contador = 3;
        while(finalJuego == false){
            
            this.vista.actualiza();
            this.vista.pausa();
            
            OperacionJuego op = this.juego.siguientePaso();

            this.vista.mostrarSiguienteOperacion(op);
            if(op != OperacionJuego.PASAR_TURNO){
                this.vista.mostrarEventos();
            }
            finalJuego = this.juego.finalDelJuego();
            if(finalJuego == false){
                if(op == OperacionJuego.COMPRAR){
                   Respuesta res = this.vista.comprar();
                    if(res == Respuesta.SI){
                        this.juego.comprar();
                       
                    }    
                    this.juego.siguientePasoCompletado(op);
                } else if(op == OperacionJuego.GESTIONAR){
                    OperacionInmobiliaria oi = this.vista.elegirOperacion();
                    if(oi != OperacionInmobiliaria.TERMINAR){
                      int id = this.vista.elegirPropiedad();
                      GestionInmobiliaria gi = new GestionInmobiliaria(oi, id);
                      if(oi == OperacionInmobiliaria.CONSTRUIR_CASA){
                          this.juego.construirCasa(id);
                      } else if(oi == OperacionInmobiliaria.CONSTRUIR_HOTEL){
                          this.juego.construirHotel(id);
                      }            
                    }
                    this.juego.siguientePasoCompletado(op);      
                }
            }  
        }
        
        this.juego.ranking();
        this.vista.actualiza();
    }
}
