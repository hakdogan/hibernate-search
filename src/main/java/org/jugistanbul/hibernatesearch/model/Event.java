package org.jugistanbul.hibernatesearch.model;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;

import javax.persistence.*;
import java.util.List;
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

    @FullTextField
    private String name;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @IndexedEmbedded
    private List<Host> hosts;

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

    public List<Host> getHosts() {
        return hosts;
    }

    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id == event.id &&
                name.equals(event.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", hosts=" + hosts +
                '}';
    }
}
