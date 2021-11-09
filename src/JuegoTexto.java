
import civitas.CivitasJuego;
import controladorCivitas.Controlador;
import java.util.ArrayList;
import vistaTextualCivitas.VistaTextual;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */

/**
 *
 * @author alejandronietoalarcon
 */
public class JuegoTexto {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<String> nombreJugadores = new ArrayList();
        nombreJugadores.add("Alejandro");
        nombreJugadores.add("Irene");
        nombreJugadores.add("Javi");
       //nombreJugadores.add("Francisco");
        CivitasJuego modelo = new CivitasJuego(nombreJugadores);
        VistaTextual vista = new VistaTextual(modelo);
        Controlador controlador = new Controlador(modelo, vista);
        controlador.juega();
        
    }
    
}
