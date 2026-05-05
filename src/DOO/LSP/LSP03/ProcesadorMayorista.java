package DOO.LSP.LSP03;

class ProcesadorMayorista extends ProcesadorPedido {
    private static final double MINIMO = 75.0;

    @Override
    public double procesar(double importe) {
        if (importe < MINIMO) {
            return 0;
        }
        return super.procesar(importe);
    }
}
