package com.ff.web.manifest.Utils;

import org.apache.commons.lang.StringUtils;

import com.ff.domain.manifest.InManifestDO;
import com.ff.domain.manifest.ManifestDO;
import com.ff.domain.manifest.OutManifestDO;
import com.ff.domain.manifest.RTOManifestDO;
import com.ff.web.manifest.constants.ManifestConstants;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating ManifestDO objects.
 */
public abstract class ManifestDOFactory {
	
	/**
	 * Gets the manifest do.
	 *
	 * @param manifestType the manifest type
	 * @return the manifest do
	 */
	public static ManifestDO getManifestDO(String manifestType) {
		ManifestDO manifestDO = null;
		if (StringUtils.equalsIgnoreCase(manifestType,
				ManifestConstants.MANIFEST_TYPE_OUT)) {
			manifestDO = new OutManifestDO();
		} else if (StringUtils.equalsIgnoreCase(manifestType,
				ManifestConstants.MANIFEST_TYPE_IN)) {
			manifestDO = new InManifestDO();
		} else if (StringUtils.equalsIgnoreCase(manifestType,
				ManifestConstants.MANIFEST_TYPE_RTO)) {
			manifestDO = new RTOManifestDO();
		}
		return manifestDO;
	}
}
