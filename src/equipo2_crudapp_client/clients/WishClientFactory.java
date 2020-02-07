/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package equipo2_crudapp_client.clients;

/**
 *
 * @author leioa
 */
public class WishClientFactory {
    
    public static WishClient getWishClient(){
        return new WishClientImplementation();
    }
}
