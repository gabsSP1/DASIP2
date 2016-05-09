/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Control;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author gspecq
 */
public abstract class Action {
   public abstract void execute(HttpServletRequest request);
   
}
