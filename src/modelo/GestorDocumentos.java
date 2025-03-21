package modelo;

import interfazUsuario.ObservadorContenido;
import interfazUsuario.ObservadorOrden;
import modelo.persistencia.DocumentoDAO;
import modelo.persistencia.DocumentoNoEncontradoException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GestorDocumentos {


    public GestorDocumentos(){
        this.documentoDAO = new DocumentoDAO();
        this.tipoOrdenamiento = Comparator.comparing(Documento::getFecha);
    }

    private final List<Documento> repositorio = new ArrayList<>();
    private Documento documentoSeleccionado;
    private DocumentoDAO documentoDAO;
    private final List<ObservadorContenido> observadoresContenido = new ArrayList<>();
    private final List<ObservadorOrden> observadoresDeOrden = new ArrayList<>();
    private Comparator<Documento> tipoOrdenamiento;


    public void registrarObsContenido(ObservadorContenido observadorContenido){
        this.observadoresContenido.add(observadorContenido);
    }

    public void registrarObsOrden(ObservadorOrden observadorOrden){
        this.observadoresDeOrden.add(observadorOrden);
    }

    public List<Documento> obtenerExpediente(int numeroDeExpediente,int cantidad){
        try {
            //añadir solo la cantidad requerida
            List<Documento> expediente = documentoDAO.obtenerDocumentosPorExpediente(numeroDeExpediente).subList(0,cantidad);
            repositorio.clear();
            repositorio.addAll(expediente);
        } catch (DocumentoNoEncontradoException e) {
            repositorio.clear();
        }
        return repositorio;
    }



    public  void ordenarPorFechaLejana(){
        tipoOrdenamiento = Comparator.comparing(Documento::getFecha);
        repositorio.sort(tipoOrdenamiento);
        notificarOrden();
    }

    public void ordenarPorFechaCercana(){
        tipoOrdenamiento = Comparator.comparing(Documento::getFecha).reversed();
        repositorio.sort(tipoOrdenamiento);
        notificarOrden();
    }

    public List<Integer> obtenerExpedientes(int cantidad){
        List<Integer> expedientes = new ArrayList<>();
        try {
            expedientes = documentoDAO.obtenerNumerosExpedientes().subList(0, cantidad);
            return expedientes;
        } catch (DocumentoNoEncontradoException e) {
            return expedientes;
        }
    }

    public void ordenarPorNumeroExpedienteAsc(){
        tipoOrdenamiento = Comparator.comparing(Documento::getNumeroExpediente);
        repositorio.sort(tipoOrdenamiento);
        notificarOrden();
    }

    public void ordenarPorNumeroExpedienteDesc(){
        tipoOrdenamiento = Comparator.comparing(Documento::getNumeroExpediente);
        repositorio.sort(tipoOrdenamiento);
        notificarOrden();
    }

    public void seleccionarDocumento(int id){
        var doc =  repositorio.stream().filter(documento -> {
            return id == documento.getId();
        }).findFirst();

        if (doc.isPresent()){
            documentoSeleccionado = doc.get();
        }

        notificarContenido();
    }



    public void obtenerTodosDocumentos(){
        try {
            List<Documento> documentos =  documentoDAO.obtenerDocumentos();
            repositorio.clear();
            repositorio.addAll(documentos);
            repositorio.sort(tipoOrdenamiento);
        } catch (DocumentoNoEncontradoException e) {
            repositorio.clear();
        }
        notificarContenido();
    }

    public void subirArchivo(){
        //TODO

    }

    public void ordenarPorTipo(){
        tipoOrdenamiento = Comparator.comparing(Documento::getTipo);
        repositorio.sort(tipoOrdenamiento);
        notificarOrden();
    }

    public void subirArchivos(){
        //TODO

    }

    public void eliminarArchivo(){
        //TODO

    }

    public void guadarDocumento(String numeroExpediente, String fecha, String tipo, String remitente, String estado){
        //TODO
        String[] datos = { numeroExpediente, fecha, tipo, remitente, estado};
        var doc = parseDocumento(datos);
        documentoDAO.agregarDocumento(doc);
        repositorio.add(doc);
        repositorio.sort(tipoOrdenamiento);

    }

    private Documento parseDocumento(String [] datos){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        int numeroExpediente =  Integer.parseInt(datos[0]);
        LocalDate fecha = LocalDate.parse(datos[1], formatter);
        Estado estado  = Estado.valueOf(datos[4].toUpperCase());
        return new Documento(numeroExpediente, fecha, datos[2],datos[3], estado );


    }

    public static void main(String[] args) {
        var gd = new GestorDocumentos();
    }

    public void notificarOrden(){
        observadoresDeOrden.forEach(ObservadorOrden::actualizarOrden);

    }

    public void notificarContenido(){
        observadoresContenido.forEach(ObservadorContenido::actualizarContenido);

    }

    public boolean eliminarDocumento(int id){
        try {
            documentoDAO.eliminarDocumento(id);
            return true;
        } catch (DocumentoNoEncontradoException e) {
            return false;
        }
    }

    public boolean eliminarExpediente(int numeroExpediente){
        try {
            documentoDAO.eliminarExpediente(numeroExpediente);
            return true;
        } catch (DocumentoNoEncontradoException e) {
            return false;
        }

    }
}
