package de.fhro.mis.dockerCompose.models.dto;

import de.fhro.mis.dockerCompose.entities.ToDo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Peter Kurfer
 * Created on 8/26/17.
 */
public final class ToDoArrayResult {

	private List<ToDo> data;

	public ToDoArrayResult() {
		data = new ArrayList<>();
	}

	public ToDoArrayResult(List<ToDo> data) {
		this.data = data;
	}

	public List<ToDo> getData() {
		return data;
	}

	public void setData(List<ToDo> data) {
		this.data = data;
	}

	public int getCount() {
		return data.size();
	}

	/**
	 * This setter is totally ignored and just for Jackson serialization required
	 *
	 * @param count ignored param
	 */
	public void setCount(int count) {

	}
}
