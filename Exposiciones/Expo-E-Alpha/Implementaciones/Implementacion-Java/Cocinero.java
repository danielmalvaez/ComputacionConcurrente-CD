/**
 * Clase que modela a un cocinero y que este objeto implementa 
 * Runnable es decir para que un proceso lo pueda correr.
 * @author Daniel Malvaez
 * @author Gabriel Peralta
 * @author Darío Ortíz
 */
public class Cocinero implements Runnable {
  // Cola para los procesos
  private final QueueEjemplo queueEjemplo;
  // (Semáforo binario)
  private volatile boolean semaforo;
  // ID del cocinero
  private static int idSequence = 0;

  public Cocinero(QueueEjemplo queueEjemplo) {
    this.queueEjemplo = queueEjemplo;
    semaforo = true;
  }

  @Override
  public void run() {
    preparar_comida();
  }

  /** 
   * Método que prepara la comida 
  */
  public void preparar_comida() {
    while (semaforo) // mientras sea True o 1
    {
      String mensaje = generarMensaje();
      
      // mientras la cola esté llena
      while (queueEjemplo.isFull()) {
        try {
          // espera en llena
          queueEjemplo.waitOnFull();
        } catch (InterruptedException e) {
          break;
        }
      }
      // si el semaforo es False o 0 entonces rompe ciclo general
      if (!semaforo) {
        break;
      }
      
      queueEjemplo.add(mensaje);
      queueEjemplo.notifyAllForEmpty();

      semaforo = false;
    }
  }

  /**
   * Método que para al proceso cocinero salga
   */
  public void stop() {
    semaforo = false;
    queueEjemplo.notifyAllForFull();
  }

  /*
   * Nos genera un mensaje
   */
  public String generarMensaje(){
    String msj =  "El cocinero está preparando la comida\nEl cocinero está dando los toques finales\nEl cocinero terminó de preparar el platillo.";
    System.out.println(msj);
    // Sleep
    ThreadUtil.sleep(500);
    return msj;
  }
}