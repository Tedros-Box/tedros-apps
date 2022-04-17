/**
 * 
 */
package com.tedros.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITEjbImportController;
import com.tedros.location.model.Country;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ICountryImportController extends ITEjbImportController<Country> {

}
