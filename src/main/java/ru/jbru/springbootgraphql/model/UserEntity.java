package ru.jbru.springbootgraphql.model;

import lombok.*;
import org.hibernate.Hibernate;
import ru.jbru.springbootgraphql.security.UserRole;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

import static java.util.Collections.emptySet;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity extends BaseEntity {

    @Column(unique = true, nullable = false, length = 64)
    private String email;

    private String password;

    @Column(length = 64)
    private String firstName;

    @Column(length = 64)
    private String lastName;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role")
    @Column(name = "role")
    private Set<UserRole> roles = emptySet();

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        UserEntity that = (UserEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
