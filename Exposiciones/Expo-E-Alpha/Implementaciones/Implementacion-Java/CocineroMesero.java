import java.util.Scanner;

import javax.security.sasl.SaslException;

public class CocineroMesero {
  // Constante seteada en 1 para que la cola solo pueda almacenar un mensaje.
  private static final int MAX_QUEUE_CAPACITY = 1;

  public static void CocineroMeseroDemo() {
    // Declaramos nuestros objetos
    QueueEjemplo dataQueue;
    Cocinero cocinero;
    Thread cocineroThread;
    Mesero mesero;
    Thread meseroThread;
    Scanner io = new Scanner(System.in);

    System.out.println("-----------------------------------------------");
    System.out.println("-------     COCINEROS MESEROS PASOS     -------");
    System.out.println("-----------------------------------------------\n");
    int pasos = 0;
    while (true){
      try {
        System.out.print("Ingresa el número de pasos a simular: ");
        pasos = io.nextInt();
        if (pasos <= 0){
          continue;
        }
        break;
      } catch (Exception e) {
        System.out.println("Número de pasos incorrecto, intenta de nuevo...");
      }  
    }

    System.out.println("Comenzando simulación...");
    try {
      // Wait for some time to demonstrate threads
      Thread.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }


    for(int i = 1; i<= pasos; i++){
      // Creamos nuestros objetos
      dataQueue = new QueueEjemplo(MAX_QUEUE_CAPACITY);
      cocinero = new Cocinero(dataQueue);
      mesero = new Mesero(dataQueue);

      cocineroThread = new Thread(cocinero);
      meseroThread = new Thread(mesero);

      // Iniciamos los hilos
      cocineroThread.start();
      meseroThread.start();

      try {
        // Wait for some time to demonstrate threads
        cocineroThread.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      //cocinero.stop();
      //mesero.stop();
      try {
        cocineroThread.join();
        meseroThread.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      } 
    }
  }
}