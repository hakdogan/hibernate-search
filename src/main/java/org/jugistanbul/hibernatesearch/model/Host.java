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
public class Host
{
    @Id
    @GeneratedValue
    @GenericField
    private int id;

    @KeywordField
    private String firstname;

    @KeywordField
    private String lastname;

    @FullTextField(analyzer = "english")
    private String title;

    @OneToMany(mappedBy = "host", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @IndexedEmbedded
    private List<Event> events;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Host host = (Host) o;
        return id == host.id &&
                firstname.equals(host.firstname) &&
                lastname.equals(host.lastname) &&
                title.equals(host.title) &&
                Objects.equals(events, host.events);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, lastname, title, events);
    }

    @Override
    public String toString() {
        return "Host{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", title='" + title + '\'' +
                ", events=" + events +
                '}';
    }
}
