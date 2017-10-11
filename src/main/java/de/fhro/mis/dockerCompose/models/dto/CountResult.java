package de.fhro.mis.dockerCompose.models.dto;

/**
 * @author Peter Kurfer
 * Created on 8/26/17.
 */
public final class CountResult {

	private long count;

	public CountResult() {
	}

	public CountResult(long count) {
		this.count = count;
	}

	public long getCount() {
		return count;
	}

	public void setCount(long count) {
		this.count = count;
	}
}
