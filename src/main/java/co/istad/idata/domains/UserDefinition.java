package co.istad.idata.domains;

import co.istad.idata.feature.api_generation.JsonConverter;
import com.networknt.schema.JsonSchema;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_definition")
public class UserDefinition implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String dataType;

    @Convert(converter = JsonConverter.class)
    @Column(columnDefinition = "jsonb")
    private String schema;

}
