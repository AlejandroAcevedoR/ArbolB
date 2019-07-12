/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolb;

public class Nodo
{
    public int []valores;
    public Nodo []nodo;
    public static int numValores;
    public boolean tengoHijos = false;
    public int ocupados = 0;
    public Nodo padre;
    public Nodo(){
    }   
    
    public void inicializacion(int grado){
        
        this.valores = new int[grado - 1];
        this.nodo = new Nodo[grado];
        
    }

    public int[] getValores() {
        return valores;
    }

    public void setValores(int[] valores) {
        this.valores = valores;
    }

    public Nodo[] getNodo() {
        return nodo;
    }

    public void setNodo(Nodo[] nodo) {
        this.nodo = nodo;
    }
    

    public boolean isTengoHijos() {
        return tengoHijos;
    }

    public void setTengoHijos(boolean tengoHijos) {
        this.tengoHijos = tengoHijos;
    }
       
    public String listavalores(){
        
        int[] temp = this.valores;
        String respuesta = "";
        
        for (int i = 0; i < temp.length; i++) {
            if (i == (temp.length - 1)) {
                respuesta = respuesta + String.valueOf(temp[i]);
            }else{
                respuesta = respuesta + String.valueOf(temp[i]) + ", ";
            }
        }
        
        return respuesta;
        
    }
              
}