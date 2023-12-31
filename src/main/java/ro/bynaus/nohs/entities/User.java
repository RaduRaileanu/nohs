package ro.bynaus.nohs.entities;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;

    @ManyToOne
    private Organisation organisation;
    
    @OneToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @OneToOne
    @JoinColumn(name = "billing_info_id")
    private BillingInfo billingInfo;

    @OneToMany(mappedBy = "user")
    private Set<Payment> payments;

    @OneToMany(mappedBy = "user")
    private Set<Post> posts;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;
}
