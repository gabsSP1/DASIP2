/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import metier.modele.Activite;
import metier.service.ServiceException;
import metier.service.ServiceMetier;

/**
 *
 * @author gspecq
 */
public class ListeActiviteAction extends Action {

    @Override
    public void execute(HttpServletRequest request) {
        try {
            List<Activite> l = ServiceMetier.recupererActivites();
            request.setAttribute( "Activites", l );
        } catch (ServiceException ex) {
            Logger.getLogger(ListeActiviteAction.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute( "Activites", null );
        }
    }
    
}
