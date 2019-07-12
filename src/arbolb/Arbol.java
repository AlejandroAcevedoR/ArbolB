/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arbolb;

import java.util.ArrayList;

/**
 *
 * @author Laptop
 */
public class Arbol {
    Nodo raiz;
    int grado = 0;
    ArrayList<Integer> ingresados;

    public Arbol(Nodo raiz) {
        this.raiz = raiz;
    }

    public Nodo getRaiz() {
        return raiz;
    }

    public void setRaiz(Nodo raiz) {
        this.raiz = raiz;
    }

    public int getGrado() {
        return grado;
    }

    public void setGrado(int grado) {
        this.grado = grado;
    }    
       
    public int altura(Nodo root){
    
        if(root!=null){
            return 0;
        }else{
            return altura(root.nodo[0])+1;
        }
    }
       
    public boolean buscar(int busqueda){        
                
        boolean respuesta = false;
        
        for (int i = 0; i < ingresados.size(); i++) {
            if (ingresados.get(i) == busqueda){
                respuesta = true;
            }
        }
        
        return respuesta;
        
    }   
    
    public void insertar (int valor) {
        ingresados.add(valor);
        if (raiz.tengoHijos==false) {
            int j = 0;
            for (int i = 0; i<raiz.valores.length; i++) {
                if (raiz.valores[i] == 0) {
                    raiz.valores[i] = valor;
                    j = i;
                    ordenar(raiz.valores);
                    break;
                }
            }
            if (j == 2*grado) {
                split(raiz);
            }
        } else {
            setTengoHijos(raiz);
            ingresarEnHijos(raiz, valor);
            
        }
    }
      public void ordenar(int arr[]){
       int longitud = 0;
       for(int i = 0; i < arr.length; i++){
           if(arr[i] != 0){
               longitud++;
            }else{
            break;
            }
        }
             for(int ord = 0; ord < longitud; ord++){
            for(int ord1 = 0; ord1 < longitud - 1 ; ord1++){
             if(arr[ord1] > arr[ord1 + 1]){
                        int tmp = arr[ord1];
                        arr[ord1] = arr[ord1+1];
                        arr[ord1+1] = tmp;
                      
              }
           }
       }
    }
    public void setTengoHijos (Nodo nodo) {
        if (nodo == raiz) {
            if (raiz.nodo[0]!= null) {
                raiz.tengoHijos = true;
            } 
        }
        for (int i = 0; i<nodo.nodo.length; i++) {
            if (nodo.nodo[i] != null) {
                nodo.tengoHijos = true;
                setTengoHijos(nodo.nodo[i]);
            }
        }
    }
    public void ingresarEnHijos(Nodo conHijos, int valor) {
        boolean entro = false;
        if(conHijos != null && !conHijos.tengoHijos){
            ubicarValorEnArreglo(conHijos, valor);
            entro = true;
        }
        for(int i = 0; conHijos != null && i < 2*grado + 1  && !entro; i++){
            if(valor < conHijos.valores[i] || conHijos.valores[i] == 0){
                entro = true;
                ingresarEnHijos(conHijos.nodo[i], valor);
                i = 2*grado;
            }
        } 
    }
    public void ubicarValorEnArreglo(Nodo nodoA, int valor){
        int cont = 0;
        while(cont <= 2*grado){
            if (nodoA.valores[cont]==0) { 
                nodoA.valores[cont]=valor;
                ordenar(nodoA.valores);
                if (cont == 2*grado) {
                    split(nodoA);
                }
                break;
            }
            cont++;
        }
    }
    public void split (Nodo nodo) {
       
        Nodo hijoIzq = new Nodo();
        Nodo hijoDer = new Nodo();
        
        //split general 
        if (nodo.nodo[0]!=null) { //si tiene hijos antes de hacer el split entonces
            for (int i = 0; i <grado+1; i++) { // los separa los hijos del nodo en hijoIzq e hijoDer
                hijoIzq.nodo[i] = nodo.nodo[i];
                hijoIzq.nodo[i].padre = hijoIzq;
                nodo.nodo[i] = null;
                hijoDer.nodo[i] = nodo.nodo[grado+1+i];
                hijoDer.nodo[i].padre = hijoDer;
                nodo.nodo[grado+1+i] = null;
            }
        }

        for (int i =0; i<grado; i++){ //guarda los valores en hijoIzq e hijoDer
            hijoIzq.valores[i] = nodo.valores[i];
            nodo.valores[i] = 0;
            hijoDer.valores[i] = nodo.valores[grado+1+i];
            nodo.valores[grado+1+i] = 0;
        }
        nodo.valores[0] = nodo.valores[grado];
        nodo.valores[grado] = 0; //queda en nodo solo el valor que "subio"
        
        nodo.nodo[0] = hijoIzq; //asigna a nodo el nuevo hijo izquierdo (hijoIzq)
        nodo.nodo[0].padre = nodo; // se hizo en primer ciclo
        nodo.nodo[1] = hijoDer; // asigna a nodo el nuevo hijo derecho (hijoDer)
        nodo.nodo[1].padre = nodo; // se hizo en el primer ciclo        
        setTengoHijos(raiz);
        ordenarNodos(nodo);
        

        if (nodo.padre!=null) { // luego del split y asignar los hijos (hijoIzq, hijoDer), subir el valor al padre
            boolean subido = false;
            for (int i = 0; i<nodo.padre.valores.length && subido==false; i++) {
                if (nodo.padre.valores[i] == 0) {
                    nodo.padre.valores[i] = nodo.valores[0];
                    subido = true;
                    nodo.valores[0] = 0;
                    ordenar(nodo.padre.valores);
                }
            }
            int posHijos = 0;
            for (int i = 0; i<2*grado+3 ; i++) {
                if (nodo.padre.nodo[i]!=null) {
                    posHijos++;
                } else {
                    break;
                }
            }
            nodo.padre.nodo[posHijos] = nodo.nodo[0];
            nodo.padre.nodo[posHijos+1] = nodo.nodo[1];
            nodo.padre.nodo[posHijos].padre = nodo.padre;
            nodo.padre.nodo[posHijos+1].padre = nodo.padre;
            int aqui = 0;
            for (int i =0; i<2*grado+3 && nodo.padre.nodo[i]!=null; i++) {
                if (nodo.padre.nodo[i].valores[0] == nodo.valores[0]) {
                    aqui = i;
                    break;
                }
            }
            Nodo papa = nodo.padre;
            nodo = null;
            int j = aqui;
            while (j<2*grado+2 && papa.nodo[j]!=null && papa.nodo[j+1]!=null) {
                papa.nodo[j] = papa.nodo[j+1];
                j++;
            }
            papa.nodo[j] = null;
            ordenar(papa.valores);
            ordenarNodos(papa);
            if (papa.valores[2*grado]!=0) {
                split(papa);
            }
        }
    }
    public void ordenarNodos(Nodo aOrdenar){
       int i,j;
       i = 0;
       Nodo tmp;
       
       while(i < 2 * grado + 3 && aOrdenar.nodo[i] != null){
           j = 0;
           while(j < 2 * grado +2  && aOrdenar.nodo[j] != null && aOrdenar.nodo[j+1] != null){
               if(aOrdenar.nodo[j].valores[0] > aOrdenar.nodo[j+1].valores[0] ){
                   tmp = aOrdenar.nodo[j];
                   aOrdenar.nodo[j] = aOrdenar.nodo[j+1];
                   aOrdenar.nodo[j+1] = tmp;
                }
                j++;
            }
            i++;
        }   
    }
    
}
