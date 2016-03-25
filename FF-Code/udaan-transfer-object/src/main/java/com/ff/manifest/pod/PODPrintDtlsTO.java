package com.ff.manifest.pod;

import java.util.List;

public class PODPrintDtlsTO {
	private List<PODManifestDtlsTO> firstCol;
	private List<PODManifestDtlsTO> secondCol;

	public List<PODManifestDtlsTO> getFirstCol() {
		return firstCol;
	}

	public void setFirstCol(List<PODManifestDtlsTO> firstCol) {
		this.firstCol = firstCol;
	}

	public List<PODManifestDtlsTO> getSecondCol() {
		return secondCol;
	}

	public void setSecondCol(List<PODManifestDtlsTO> secondCol) {
		this.secondCol = secondCol;
	}

}
