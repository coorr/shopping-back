package shopping.coor.auth.domain.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import shopping.coor.auth.domain.Role.Role;
import shopping.coor.common.domain.BaseEntityAggregateRoot;
import shopping.coor.order.domain.Order;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(	name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
        })
@Getter
@NoArgsConstructor
@Setter
@Builder
@AllArgsConstructor
public class User extends BaseEntityAggregateRoot<User> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    @JsonIgnore
    private String password;

    @Builder.Default
    @ManyToMany
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Order> orders = new ArrayList<>();


    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(Long id, String username, String email, String password) {

        this.username = username;
        this.email = email;
        this.password = password;
    }
}
