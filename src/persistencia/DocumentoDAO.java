package persistencia;


import documento.Documento;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class DocumentoDAO {
    private static final String TOTAL_DOCUMENTOS = "TODOS";
    public void agregarDocumento(Documento documentoAgregar){
        String query = "INSERT INTO documentos (numero_expediente, fecha, tipo, remitente, estado)" +
                        "VALUES (?,?,?,?,?)";

        try (PreparedStatement statement = ConexionDB.getConexion().prepareStatement(query)){
            statement.setObject(1,documentoAgregar.getNumeroExpediente());
            statement.setObject(2, documentoAgregar.getFecha());
            statement.setString(3, documentoAgregar.getTipo());
            statement.setString(4, documentoAgregar.getRemitente());
            statement.setObject(5, documentoAgregar.getEstado().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Documento> obtenerDocumentos(int cantidad){
        return obtenerDocumentos(String.valueOf(cantidad));
    }

    public List<Documento> obtenerDocumentos(){
        return obtenerDocumentos(TOTAL_DOCUMENTOS);
    }




    private List<Documento> obtenerDocumentos(String cantidad){
        List<Documento> documentos = new ArrayList<>();
        ResultSet resultQuery = null;
        String query = cantidad.equals(TOTAL_DOCUMENTOS) ? "SELECT * FROM documentos "  : "SELECT * FROM documentos LIMIT " + cantidad;
        try (Statement statement = ConexionDB.getConexion().createStatement()){
            resultQuery = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException("No se puedo obtener los documentos", e);
        }

        try {
            while (resultQuery.next()){
                int id = resultQuery.getInt(1);
                int numero_expediente = resultQuery.getInt(2);
                LocalDate fecha = resultQuery.getObject(3, LocalDate.class);
                String tipo = resultQuery.getString(4);
                String remitente = resultQuery.getString(5);


                Documento.Estado estado = Documento.Estado.valueOf(resultQuery.getString(6).toUpperCase());

                documentos.add(new Documento(id,numero_expediente, fecha,tipo,remitente,estado));

            }
            resultQuery.close();
            return documentos;
        } catch (SQLException e){
            throw new RuntimeException("Error al leer los documentos guardados", e);
        }
    }



    public void cambiarEstado(int id, Documento.Estado nuevoEstado) throws DocumentoNoEncontradoException {
        String query = "UPDATE documentos SET estado = ? WHERE id = ?";

        try (PreparedStatement statement = ConexionDB.getConexion().prepareStatement(query)){
            statement.setString(1, nuevoEstado.toString());
            statement.setInt(2, id);
            if (statement.executeUpdate() == 0){
                throw new DocumentoNoEncontradoException("No existe el documento con el id: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el estado del documento",e);
        }

    }

    public void cambiarNoExpediente(int id, int nuevoExpediente) throws DocumentoNoEncontradoException{
        String query = "UPDATE documentos SET numero_expediente = ? WHERE id = ?";

        try (PreparedStatement statement = ConexionDB.getConexion().prepareStatement(query)){
            statement.setInt(1, nuevoExpediente);
            statement.setInt(2, id);
            if (statement.executeUpdate() == 0){
                throw new DocumentoNoEncontradoException("No existe el documento con el id: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el estado del documento",e);
        }
    }






    public static void main(String[] args) {
        DocumentoDAO gd = new DocumentoDAO();

        gd.agregarDocumento(new Documento(Documento.Estado.APROBADO));
        gd.agregarDocumento(new Documento(Documento.Estado.EN_REVISION));
        gd.obtenerDocumentos().forEach(System.out::println);

    }


}
