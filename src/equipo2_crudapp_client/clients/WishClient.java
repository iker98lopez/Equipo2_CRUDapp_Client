/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.clients;

import javax.ws.rs.core.GenericType;

/**
 *
 * @author leioa
 */
public interface WishClient {
    public void modifyWish(Object requestEntity, String id);
    public <T> T findAllWishes(GenericType<T> responseType);
    public <T> T findWish(Class<T> responseType, String id);
    public void removeWish(String wish);
    public void createWish(Object requestEntity);
    public void close();
}
