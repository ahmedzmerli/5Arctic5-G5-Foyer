package tn.esprit.tpfoyer.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Universite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long idUniversite;

    String nomUniversite;

    String adresse;

    @OneToOne(cascade = CascadeType.ALL)
    Foyer foyer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Universite that = (Universite) o;
        return idUniversite == that.idUniversite;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idUniversite);
    }
}


