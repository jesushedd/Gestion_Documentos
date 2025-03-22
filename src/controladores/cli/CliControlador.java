package controladores.cli;

import controladores.Controlador;
import interfazUsuario.cli.CLI;
import modelo.ComparadoresDocumento;
import modelo.Documento;
import modelo.GestorDocumentos;
import java.util.List;

public class CliControlador implements Controlador {
    CLI interfaz;
    GestorDocumentos gestor;


    public CliControlador( GestorDocumentos gestor){
        this.interfaz = new CLI(this);
        this.gestor = gestor;
        this.gestor.registrarObsContenido(interfaz);
        this.gestor.registrarObsOrden(interfaz);
        this.interfaz.ejecutar();


    }

    @Override
    public void solicitarDatos() {
         if (interfaz.getEstado().equals(CLI.Menu.DOCUMENTO_VARIOS)){
             gestor.cargarDocumentosTodos();
             List<Documento> documentos =  gestor.obtenerDocumentosCargados();
             interfaz.mostrarElementos(FormatDocumento.format(documentos));
        }

    }
    @Override
    public void ordenarPorFechaAsc(){
        gestor.ordenarPor(ComparadoresDocumento.FECHA_ASCENDENTE);
    }
    @Override
    public void ordenarPorFechaDesc(){
        gestor.ordenarPor(ComparadoresDocumento.FECHA_DESCENDENTE);
    }
    @Override
    public void ordenarPorTipo(){
        gestor.ordenarPor(ComparadoresDocumento.POR_TIPO);
    }
    @Override
    public void ordenarPorExpedienteAsc(){
        gestor.ordenarPor(ComparadoresDocumento.EXPEDIENTE_ASCENDENTE);
    }
    @Override
    public void ordenarPorExpedienteDesc(){
        gestor.ordenarPor(ComparadoresDocumento.EXPEDIENTE_DESCENDENTE);
    }
}
