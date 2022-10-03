import java.util.LinkedList;
import java.util.Queue;
/**
 * Clase Cola implementada por nosotros
 * @author Daniel Malvaez
 * @author Gabriel Peralta
 * @author Darío Ortíz
 */
public class QueueEjemplo {
  private final Queue<String> queue = new LinkedList<>();
  private final int maxSize;

  /* 
  * Cuando la cola está llena, el cocinero espera en el objeto FULL_QUEUE y el mesero notifica
  * tan rápido como sirve la comida.
  */
  private final Object FULL_QUEUE = new Object();
  /* 
  * Si la cola está vacía, el mesero espera en el objeto EMPTY_QUEUE y el cocinero notificará
  * tan pronto como haya preparado la comida.
  */
  private final Object EMPTY_QUEUE = new Object();

  /**
   * Constructor
   * @param maxSize - Tamaño máximo de una cola 
   */
  QueueEjemplo(int maxSize) {
      this.maxSize = maxSize;
  }

  // En Java, el bloque synchronized usa un objeto para alcanzar la sincronización con hilos.

  /*
   * El cocinero espera en el objeto FULL_QUEUE, utilizando este método en la lista.
   */
  public void waitOnFull() throws InterruptedException {
    synchronized (FULL_QUEUE) {
      FULL_QUEUE.wait();
    }
  }

  /*
   * El mesero(s) notifica al cocinero(s) por medio de este método que la cola está llena
   */
  public void notifyAllForFull() {
    synchronized (FULL_QUEUE) {
      FULL_QUEUE.notifyAll();
    }
  }

  /*
   * El mesero espera utilizando este método en la lista EMPTY_QUEUE
   */
  public void waitOnEmpty() throws InterruptedException {
    synchronized (EMPTY_QUEUE) {
      EMPTY_QUEUE.wait();
    }
  }

  /*
   * El cocinero(s) notifica al mesero(s) por medio de este método que la cola ya está vacía
   */
  public void notifyAllForEmpty() {
    synchronized (EMPTY_QUEUE) {
      EMPTY_QUEUE.notify();
    }
  }

  /*
   * Añade un mensaje a la cola
   */
  public void add(String mensaje) {
    synchronized (queue) {
      queue.add(mensaje);
    }
  }

  /*
   * Remueve un mensaje de la cola
   */
  public String remove() {
    synchronized (queue) {
      return queue.poll();
    }
  }

  /*
   * Nos dice si la cola está llena
   */
  public boolean isFull(){
    if(maxSize == this.queue.size()){
      return true;
    }else{
      return false;
    }
  }

  /**
   * Nos dice si la cola está vacía
   */
  public boolean isEmpty(){
    if (this.queue.size() == 0){
      return true;
    }
    return false;
  }
}