/**
 * 
 */
package com.tedros.person.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITEjbReportController;
import com.tedros.person.report.model.NaturalPersonReportModel;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface INaturalPersonReportController extends ITEjbReportController<NaturalPersonReportModel> {

}
