package org.jugistanbul.hibernatesearch.model;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.Objects;

/**
 * @author hakdogan (hakdogan@kodcu.com)
 * Created on 29.11.2020
 **/
@Entity
@Indexed
public class Event
{
    @Id
    @GeneratedValue
    @GenericField
    private int id;

    @FullTextField(analyzer = "english")
    private String name;

    @ManyToOne
    @JsonbTransient
    private Host host;

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

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id &&
                name.equals(event.name) &&
                Objects.equals(host, event.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, host);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", host=" + host +
                '}';
    }
}
