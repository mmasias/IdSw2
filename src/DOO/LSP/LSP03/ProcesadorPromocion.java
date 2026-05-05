package DOO.LSP.LSP03;

class ProcesadorPromocion extends ProcesadorPedido {

    @Override
    public double procesar(double importe) {
        if (importe == 0) {
            return 0;
        }
        return super.procesar(importe);
    }
}
