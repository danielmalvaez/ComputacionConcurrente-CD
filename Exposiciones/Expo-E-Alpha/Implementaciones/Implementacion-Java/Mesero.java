public class Mesero implements Runnable {

    private final QueueEjemplo dataQueue;
    private volatile boolean semaforo;

    public Mesero(QueueEjemplo dataQueue) {
        this.dataQueue = dataQueue;
        semaforo = true;
    }

    @Override
    public void run() {
        sirve_comida();
    }
    
    public void sirve_comida() {
        while (semaforo) {
            String mensaje;
            if (dataQueue.isEmpty()) {
                try {
                    dataQueue.waitOnEmpty();
                } catch (InterruptedException e) {
                    break;
                }
            }
            if (!semaforo) {
                break;
            }
            mensaje = dataQueue.remove();
            dataQueue.notifyAllForFull();
            useMessage(mensaje);

            semaforo = false;
        }
    }
    public void stop() {
        semaforo = false;
        dataQueue.notifyAllForEmpty();
    }

    public void useMessage(String message){
        if (message != null) {
                System.out.println("El mesero toma platillo y lo sirve");
                //Sleeping on random time to make it realistic
                ThreadUtil.sleep(500);
        }
    }
}