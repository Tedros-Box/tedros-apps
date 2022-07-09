/**
 * 
 */
package com.tedros.person.ejb.controller;

import javax.ejb.Remote;

import com.tedros.ejb.base.controller.ITEjbReportController;
import com.tedros.person.report.model.EmployeeReportModel;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface IEmployeeReportController extends ITEjbReportController<EmployeeReportModel> {

}
