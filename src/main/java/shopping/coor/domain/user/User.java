package shopping.coor.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import shopping.coor.common.model.BaseEntityCreateUpdateAggregate;
import shopping.coor.domain.order.Order;
import shopping.coor.domain.user.role.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Getter
@NoArgsConstructor
@Setter
@Builder
@AllArgsConstructor
public class User extends BaseEntityCreateUpdateAggregate implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    private String password;

    @Builder.Default
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "u_id"),
            inverseJoinColumns = @JoinColumn(name = "r_id"))
    private Set<Role> roles = new HashSet<>();

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();


    public User(String username, String email, String password) {
        this.name = username;
        this.email = email;
        this.password = password;
    }
}
