/**
 * 
 */
package com.ff.web.manifest.pod.constants;

/**
 * @author nkattung
 * 
 */
public interface PODManifestConstants {

	String INVALID_CN = "PODERR001";
	String NO_RESULT = "PODERR002";
	String CN_EXISTS = "PODERR003";
	String INVALID_CN_IN_POD = "PODERR004";

	String POD_MANIFEST_IN = "I";
	String POD_MANIFEST_OUT = "O";

	String POD_MANIFEST = "P";

	String QRY_IS_CONSG_MANIFESTED = "isConsgnmentManifested";
	String QRY_GET_OUTGOING_POD_CONSG_DETAILS = "getManifestedConsignmentDtls";
	String QRY_GET_OUTGOING_POD_CONSG_DETAILS_FROM_DESTHUB = "getManifestedConsignmentDtlsFromDestHub";
	String QRY_IS_CONSG_IS_BELONGS_TO_MANIFEST = "isConsgnmentBelongsToManifest";
	String QRY_CONSINGMENTS_OF_MANIFEST =  "getConsignmentsOfManifest";
	String QRY_GET_CONSG_DETAILS_FOR_DESTBRANCH_TO_DESTHUB="getConsDelivDetailsForDestBranchToDestHub";
	String QRY_IS_CONS_IN_POD_MANIFESTD = "isConsgnmentInPODManifested";
	

	String PARAM_DEST_OFF_ID = "destOfficeId";
	String PARAM_ORG_OFF_ID = "orgoffiecId";

	String CONSGINMENT_NO = "consgNo";
	String MANIFEST_TYPE = "manifestType";

	String POD_RECEIVED = "R";
	String POD_NOT_RECEIVED = "N";
	String BRANCH_OFFICE = "Branch Office";
	String HUB_OFFICE = "Hub Office";
	String HUB_OFFC_TYPE_CODE="HO";
	String BRANCH_OFFC_TYPE_CODE="BO";
	String INVALID_CN_AT_DESTHUB = "PODERR016";
	String QRY_GET_CONSG_DETAILS_FOR_DESTBRANCH_TO_DESTHUB_FOR_PARENT_CN = "getConsDelivDetailsForDestBranchToDestHubForParentCN";
	String CHILD_CONSIGNMENT_NOT_ALLOWED = "PODERR017";
}
