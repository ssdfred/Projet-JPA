package imdb.Dao;

import java.util.List;

import imdb.entities.Role;

public interface RoleDao {
    
    void save(Role role);
    
    Role findById(int id);
    
    List<Role> findAll();
    
    void update(Role role);
    
    void delete(Role role);
    public void closeEntityManager();
}
