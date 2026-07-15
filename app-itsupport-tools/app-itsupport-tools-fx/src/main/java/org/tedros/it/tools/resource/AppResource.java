/**
 * 
 */
package org.tedros.it.tools.resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.tedros.it.tools.start.TConstant;
import org.tedros.util.TedrosFolder;

/**
 * @author Davis Gordon
 *
 */
public class AppResource {

	private static final String FOLDER = TConstant.UUI;
	public static final String APP_MODULE_PATH = TedrosFolder.MODULE_FOLDER.getFullPath()+FOLDER+File.separator;
	private static final String JOB_EVIDENCE_JASPER ="jobevidence";
	private static final String SUB_REPO1_JASPER ="subrep_images";
	private static final String SUB_REPO2_JASPER ="subrep_textarea";
	
	private static final String SERVICE_CATALOG_JASPER ="service_catalog";
	private static final String SUBREP_GROUPS_JASPER ="subrep_groups";
	private static final String SUBREP_SERVICES_JASPER ="subrep_services";
	private static final String SUBREP_VARIANTS_JASPER ="subrep_variants";

	private static final String ATA_REUNIAO_JASPER = "ata-reuniao";
	private static final String ATA_REUNIAO_PAUTA_JASPER = "ata-reuniao-pauta";
	private static final String ATA_REUNIAO_ENC_JASPER = "ata-reuniao-encaminhamentos";
	private static final String ATA_REUNIAO_EVID_JASPER = "ata-reuniao-evidencia";
	
	private static final String JASPER_EXT =".jasper";
	private static final String JRXML_EXT =".jrxml";
	
	private static final String[] FILES = {
			JOB_EVIDENCE_JASPER + JASPER_EXT, JOB_EVIDENCE_JASPER + JRXML_EXT,
			SUB_REPO1_JASPER + JASPER_EXT, SUB_REPO1_JASPER + JRXML_EXT,
			SUB_REPO2_JASPER + JASPER_EXT, SUB_REPO2_JASPER + JRXML_EXT,
			SERVICE_CATALOG_JASPER + JASPER_EXT, SERVICE_CATALOG_JASPER + JRXML_EXT,
			SUBREP_GROUPS_JASPER + JASPER_EXT, SUBREP_GROUPS_JASPER + JRXML_EXT,
			SUBREP_SERVICES_JASPER + JASPER_EXT, SUBREP_SERVICES_JASPER + JRXML_EXT,
			SUBREP_VARIANTS_JASPER + JASPER_EXT, SUBREP_VARIANTS_JASPER + JRXML_EXT,
			
			ATA_REUNIAO_JASPER + JRXML_EXT, ATA_REUNIAO_JASPER + JASPER_EXT,
			ATA_REUNIAO_PAUTA_JASPER + JRXML_EXT, ATA_REUNIAO_PAUTA_JASPER + JASPER_EXT,
			ATA_REUNIAO_ENC_JASPER + JRXML_EXT, ATA_REUNIAO_ENC_JASPER + JASPER_EXT,
			ATA_REUNIAO_EVID_JASPER + JRXML_EXT, ATA_REUNIAO_EVID_JASPER + JASPER_EXT 
			};

	
	
	public static InputStream getServiceCatalogJasperInputStream() throws FileNotFoundException {
		File f = new File(APP_MODULE_PATH+SERVICE_CATALOG_JASPER+JASPER_EXT);
		if(f.isFile()) {
			return new FileInputStream(f);
		}
		
		return null;
	}
	
	public static InputStream getJobEvidenceJasperInputStream() throws FileNotFoundException {
		File f = new File(APP_MODULE_PATH+JOB_EVIDENCE_JASPER+JASPER_EXT);
		if(f.isFile()) {
			return new FileInputStream(f);
		}
		
		return null;
	}

	public static InputStream getAtaReuniaoJasperInputStream() throws FileNotFoundException {
		File f = new File(APP_MODULE_PATH + ATA_REUNIAO_JASPER + JASPER_EXT);
		if (f.isFile()) {
			return new FileInputStream(f);
		}
		return null;
	}
	
	public static void createResource() {
		for(String ref : FILES) {
			File f = new File(APP_MODULE_PATH+ref);
			if(!f.isFile()) {
				try(InputStream is = AppResource.class.getResourceAsStream(ref)){
					if (is != null) {
						FileUtils.copyInputStreamToFile(is, f);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
