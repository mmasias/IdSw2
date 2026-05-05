package DOO.LSP.LSP03;

public class Auditor {

    static double sumaBruta(double[] pedidos) {
        double total = 0;
        for (double p : pedidos) {
            total += p;
        }
        return total;
    }

    static void auditar(String nombre, String tipo, ProcesadorPedido procesador, double[] pedidos) {
        StringBuilder fallos = new StringBuilder();
        for (double importe : pedidos) {
            double resultado = procesador.procesar(importe);
            if (resultado > importe) {
                fallos.append("\n               ")
                      .append((int) resultado).append(" EUR > ")
                      .append((int) importe).append(" EUR");
            }
            if (resultado < importe * 0.50) {
                fallos.append("\n               ")
                      .append((int) resultado).append(" EUR < ")
                      .append((int) (importe * 0.50)).append(" EUR (50% de ")
                      .append((int) importe).append(")");
            }
        }

        if (fallos.length() == 0) {
            System.out.println("  [" + nombre + "] OK  - " + tipo);
        } else {
            System.out.println("  [" + nombre + "] MAL - " + tipo + fallos.toString());
        }
    }

    public static void main(String[] args) {
        double[] pedidos = { 100.0, 200.0, 50.0, 300.0 };

        System.out.println("Auditoria");
        System.out.println("Suma bruta: " + (int) sumaBruta(pedidos) + " EUR");
        System.out.println();

        auditar("Premium",   "post mas cerrada: garantiza mas descuento que la base", new ProcesadorPremium(),   pedidos);
        auditar("Promocion", "pre  mas abierta: acepta importes que la base rechazaria", new ProcesadorPromocion(), pedidos);
        auditar("Deudores",  "post abierta:     devuelve fuera del rango garantizado",  new ProcesadorDeudores(),  pedidos);
        auditar("Mayorista", "pre  cerrada:     rechaza pedidos que la base aceptaria", new ProcesadorMayorista(), pedidos);
    }
}
