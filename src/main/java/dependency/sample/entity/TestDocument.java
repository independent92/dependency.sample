package dependency.sample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Document(indexName = "test_document", type = "testDocument")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDocument {
    @Id
    private Long id;
    private String title;
    private String text;
}
