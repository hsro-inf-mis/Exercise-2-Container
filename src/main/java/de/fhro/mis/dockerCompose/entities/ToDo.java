package de.fhro.mis.dockerCompose.entities;

import de.fhro.mis.dockerCompose.converters.LocalDateAttributeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author Peter Kurfer
 * Created on 8/24/17.
 */
@Entity
@Table(name = "todo")
public class ToDo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@NotNull
	private String title = "";

	@NotNull
	private String description = "";

	@Convert(converter = LocalDateAttributeConverter.class)
	private LocalDateTime completed;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCompleted() {
		return completed;
	}

	public void setCompleted(LocalDateTime completed) {
		this.completed = completed;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ToDo)) return false;

		ToDo toDo = (ToDo) o;

		if (getId() != toDo.getId()) return false;
		if (!getTitle().equals(toDo.getTitle())) return false;
		return getDescription().equals(toDo.getDescription());
	}

	@Override
	public int hashCode() {
		int result = (int) (getId() ^ (getId() >>> 32));
		result = 31 * result + getTitle().hashCode();
		result = 31 * result + getDescription().hashCode();
		return result;
	}
}
