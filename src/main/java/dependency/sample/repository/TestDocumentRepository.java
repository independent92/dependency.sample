package dependency.sample.repository;

import dependency.sample.entity.TestDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestDocumentRepository extends ElasticsearchRepository<TestDocument, Long> {
}
