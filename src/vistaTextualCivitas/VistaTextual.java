package vistaTextualCivitas;

import vistaTextualCivitas.Vista;
import civitas.Casilla;
import civitas.CivitasJuego;
import civitas.Diario;
import civitas.OperacionJuego;
import controladorCivitas.Respuesta;
import civitas.OperacionInmobiliaria;
import civitas.Jugador;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;



public class VistaTextual implements Vista {
  
    
  private static String separador = "=====================";
  
  private Scanner in;
  
  CivitasJuego juegoModel;
  
  public VistaTextual (CivitasJuego juegoModel) {
    in = new Scanner (System.in);
    this.juegoModel=juegoModel;
  }
  
  
           
 public  void pausa() {
    System.out.print ("\nPulsa una tecla");
    in.nextLine();
  }

  int leeEntero (int max, String msg1, String msg2) {
    Boolean ok;
    String cadena;
    int numero = -1;
    do {
      System.out.print (msg1);
      cadena = in.nextLine();
      try {  
        numero = Integer.parseInt(cadena);
        ok = true;
      } catch (NumberFormatException e) { // No se ha introducido un entero
        System.out.println (msg2);
        ok = false;  
      }
      if (ok && (numero < 0 || numero >= max)) {
        System.out.println (msg2);
        ok = false;
      }
    } while (!ok);

    return numero;
  }

  int menu (String titulo, ArrayList<String> lista) {
    String tab = "  ";
    int opcion;
    System.out.println (titulo);
    for (int i = 0; i < lista.size(); i++) {
      System.out.println (tab+i+"-"+lista.get(i));
    }

    opcion = leeEntero(lista.size(),
                          "\n"+tab+"Elige una opción: ",
                          tab+"Valor erróneo");
    return opcion;
  }

    @Override
    public void actualiza() {
        if(!this.juegoModel.finalDelJuego()){
           this.juegoModel.getJugadorActual().toString();
           //Tuve que poner a public el getCasillaActual de jugador
           this.juegoModel.getTablero().getCasilla(this.juegoModel.getJugadorActual().getCasillaActual()).toString();
        } else {
            //Tuve que poner public el rankig de CivitasJuego
            this.juegoModel.ranking();
        }
           
    }

    @Override
    public Respuesta comprar() {
        String ms1 = "¿ Se desea comprar la calle ?\n0 - SI\n1 - NO\n";
        String ms2 = "No ha introducido un valor correcto el valor debe ser 0 o 1";
        int opt = this.leeEntero(2, ms1, ms2);
        return Respuesta.values()[opt];
    }

    @Override
    public OperacionInmobiliaria elegirOperacion() {
        String titulo = "¿ Que numero de gestion inmobiliaria desea hacer ?";
        ArrayList<String> strings = new ArrayList();
        strings.add("CONSTRUIR CASA");
        strings.add("CONSTRUIR HOTEL");
        strings.add("TERMINAR");
        return OperacionInmobiliaria.values()[this.menu(titulo, strings)];
    }

    @Override
    public int elegirPropiedad() {
        //Tengo que poner el getPropiedades de protected a public
        String titulo = "¿ Sobre que propiedad desea hacer la gestion ?";
        ArrayList<String> strings = new ArrayList();
        for(int i = 0; i < this.juegoModel.getJugadorActual().getPropiedades().size(); i++){
            strings.add(this.juegoModel.getJugadorActual().getPropiedades().get(i).getNombre());
        }
        int value = this.menu(titulo, strings);
        return value;
    }

    @Override
    public void mostrarSiguienteOperacion(OperacionJuego operacion) {
        System.out.println("\nSiguiente operacion --> " + operacion);
    }

    @Override
    public void mostrarEventos() {
        if(Diario.getInstance().eventosPendientes()){
            for(int i = 0; i < Diario.getInstance().getEventos().size(); i++){
                System.out.println("Evento " + i + ": " + Diario.getInstance().getEventos().get(i));
            }
        }
    }

}
