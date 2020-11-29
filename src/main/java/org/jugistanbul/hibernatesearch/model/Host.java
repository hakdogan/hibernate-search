package org.jugistanbul.hibernatesearch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import javax.persistence.*;
import java.util.Objects;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 29.11.2020
 **/
@Entity
@Indexed
public class Host
{
    @Id
    @GeneratedValue
    @GenericField
    private int id;

    @FullTextField
    private String name;

    @FullTextField
    private String title;

    @ManyToOne
    @JsonIgnore
    private Event event;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return id == host.id &&
                name.equals(host.name) &&
                title.equals(host.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, title);
    }
}
