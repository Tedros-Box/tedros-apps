/**
 * 
 */
package org.tedros.person.ejb.controller;

import javax.ejb.Remote;

import org.tedros.person.report.model.NaturalPersonReportModel;

import org.tedros.server.controller.ITEjbReportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface INaturalPersonReportController extends ITEjbReportController<NaturalPersonReportModel> {

}
