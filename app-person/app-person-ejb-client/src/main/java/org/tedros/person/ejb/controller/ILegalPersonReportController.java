/**
 * 
 */
package org.tedros.person.ejb.controller;

import javax.ejb.Remote;

import org.tedros.person.report.model.LegalPersonReportModel;

import org.tedros.server.controller.ITEjbReportController;

/**
 * @author Davis Gordon
 *
 */
@Remote
public interface ILegalPersonReportController extends ITEjbReportController<LegalPersonReportModel> {

}
