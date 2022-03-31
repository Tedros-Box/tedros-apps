/**
 * 
 */
package com.tedros.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITEjbImportController;
import com.tedros.model.Produto;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IProdutoImportController extends ITEjbImportController<Produto> {

}
