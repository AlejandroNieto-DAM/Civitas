/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package civitas;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author alejandronietoalarcon
 */
public class MazoSorpresas {
    
    private ArrayList<Sorpresa> sorpresas;
    private boolean barajada;
    private int usadas;
    private boolean debug;
    
    public void init(){
        this.sorpresas = new ArrayList();
        this.barajada = false;
        this.usadas = 0;
    }
    
    public MazoSorpresas(boolean debug){
        this.init();
        this.debug = debug;
        Diario.getInstance().ocurreEvento("Debug MazoSorpresas: " + this.debug);
    }
    
    public MazoSorpresas(){
        this.init();
        this.debug = false;
    }
    
    public void alMazo(Sorpresa s){
        if(!this.barajada){
            this.sorpresas.add(s);
        }
    }
    
    public Sorpresa siguiente(){
        if(!this.barajada || (this.usadas == this.sorpresas.size()) && !this.debug){
            Collections.shuffle(sorpresas);
            this.usadas = 0;
            this.barajada = true; 
        }
        
        this.usadas++;
        Sorpresa s = this.sorpresas.get(this.usadas - 1);
        this.sorpresas.remove(s);
        this.sorpresas.add(s);
        
        return s;
    }
    
    
}
