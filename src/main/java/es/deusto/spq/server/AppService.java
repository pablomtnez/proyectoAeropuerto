package es.deusto.spq.server;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import es.deusto.spq.pojo.UserData;
import es.deusto.spq.server.jdo.Usuario;

public class AppService {
    
    private PersistenceManager pm = null;
    private Transaction tx = null;
    protected static final Logger logger = LogManager.getLogger();
    private static AppService instance;

    public static AppService getInstance() {
        if (instance == null) {
            instance = new AppService();
        }
        return instance;
    }
    
    private AppService() {
        PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("datanucleus.properties");
        this.pm = pmf.getPersistenceManager();
        this.tx = pm.currentTransaction();
    }

    public boolean register(UserData userData){
        try {
            tx.begin();
            logger.info("Register : Checking whether the user already exits or not: '{}'", userData.getMail());
            Usuario usuario = null;
            try {
                usuario = pm.getObjectById(Usuario.class, userData.getDni());
            } catch (Exception e) {
               e.printStackTrace();
               logger.error("Exception launched: {}", e.getMessage());
            }
            logger.info("Usuario: {}", usuario);
                if(usuario != null) {
                    logger.info("Usuario already exists");
                    return false;
                } else {
                    logger.info("Creating Usuario: {}", usuario);
                    usuario = new Usuario(userData.getNombre(), userData.getApellido(), userData.getMail(), userData.getDni(), userData.getPassword());
                    pm.makePersistent(usuario);
                    logger.info("Usuario created: {}", usuario);
                }
                tx.commit();
                return true;

        } finally {
            if(tx.isActive()) {
                tx.rollback();
            }
        }
    }

    public boolean login(UserData userData){

        try {
            tx.begin();
            logger.info("Login : Checking whether the user already exits or not: '{}'", userData.getMail());
            Usuario usuario = null;
            try {
                usuario = pm.getObjectById(Usuario.class, userData.getMail());
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Exception launched: {}", e.getMessage());
            }
            if(usuario == null){
                logger.info("Login: Usuario does not exist");
                return false;
            }else{
                if (usuario.getPassword().equals(userData.getPassword())){
                    logger.info("Login: Usuario exists");
                    return true;
                }else{
                    logger.info("Login: Usuario and Password Do Not Match");
                    return false;
                }
            }
        }finally{
            if(tx.isActive()){
                tx.rollback();
            }
        }

    }
}
