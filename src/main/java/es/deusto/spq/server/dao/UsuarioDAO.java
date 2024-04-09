package es.deusto.spq.server.dao;

import java.util.ArrayList;
import java.util.List;
import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.server.jdo.Usuario;

public class UsuarioDAO extends DataAccessObjectBase implements IDataAccessObject<Usuario> {

    private static final Logger logger = LogManager.getLogger(UsuarioDAO.class);
    private static UsuarioDAO instance;

    private UsuarioDAO(){}

    public static UsuarioDAO getInstance(){
        if(instance == null){
            instance = new UsuarioDAO();
        }
        return instance;
    }

    @Override
    public void save(Usuario object) {
        super.saveObject(object);
    }

    @Override
    public void delete(Usuario object) {
        super.deleteObject(object);
    }

    @Override
    public List<Usuario> getAll() {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        List<Usuario> Usuarios = new ArrayList<Usuario>();
        try {
            tx.begin();
            Extent<Usuario> extent = pm.getExtent(Usuario.class, true);
            for(Usuario category : extent){
                Usuarios.add(category);
            }
            tx.commit();
        } catch (Exception ex) {
            logger.error("$ Error retrieving all the Usuarios: " + ex.getMessage());
        } finally {
            if(tx != null && tx.isActive()){
                tx.rollback();
            }
        }
        return Usuarios;
    }

    @Override
    public Usuario find(String param) {
        PersistenceManager pm = pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        Usuario result = null;
        try {
            tx.begin();
            Query<Usuario> query = pm.newQuery(Usuario.class);
            query.setFilter("email == :param");
            query.setUnique(true);
            result = (Usuario) query.execute(param);
            tx.commit();
            
            // Agregar registro de depuración para verificar el usuario recuperado
            logger.debug("Usuario recuperado de la base de datos: " + result);
            
            // Verificar si la contraseña del usuario recuperado es correcta
            if (result != null) {
                logger.debug("Contraseña del usuario recuperado: " + result.getPassword());
            }
        } catch (Exception ex) {
            logger.error("$ Error querying an Usuario: " + ex.getMessage());
        } finally {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            pm.close();
        }
        return result;
    }
    
}
