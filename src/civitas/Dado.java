/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package civitas;

import java.util.Random;

/**
 *
 * @author alejandronietoalarcon
 */
public class Dado {
    
    static final private Dado instance = new Dado();
    
    

    private Random random;
    private int ultimoResultado;
    private boolean debug;
    
    public Dado() {
        this.random = new Random();
        this.ultimoResultado = 0;
        this.debug = false;
    }
    
    public int tirar(){
        if(debug){
            return 1;
        }
        this.ultimoResultado = random.nextInt(6) + 1;
        return this.ultimoResultado; 
    }
    
    public int quienEmpieza(int n){
        return random.nextInt(n - 1);
    }
    
    public void setDebug(boolean d){
        this.debug = d;
        Diario.getInstance().ocurreEvento("Debug Dado: " + this.debug);
    }
    
    public int getUltimoResultado(){
        return this.ultimoResultado;
    }
    
    public static Dado getInstance(){
        return instance;
    }
    
}
